package com.neemre.btcdcli4j.core.http.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neemre.btcdcli4j.core.NodeProperties;
import com.neemre.btcdcli4j.core.common.Constants;
import com.neemre.btcdcli4j.core.common.DataFormats;
import com.neemre.btcdcli4j.core.common.Errors;
import com.neemre.btcdcli4j.core.http.HttpConstants;
import com.neemre.btcdcli4j.core.http.HttpLayerException;

public class SimpleHttpClientImpl implements SimpleHttpClient {
	
	private static final Logger LOG = LoggerFactory.getLogger(SimpleHttpClientImpl.class);
	
	private CloseableHttpClient provider;
	private Properties nodeConfig;


	public SimpleHttpClientImpl(CloseableHttpClient provider, Properties nodeConfig) {
		LOG.debug("** SimpleHttpClientImpl(): initiating the HTTP communication layer");
		this.provider = provider;
		this.nodeConfig = nodeConfig;
	}

	@Override
	public String execute(String reqMethod, String reqPayload) throws HttpLayerException {
		CloseableHttpResponse response = null;
		try {
			response = provider.execute(getNewRequest(reqMethod, reqPayload), new BasicHttpContext());
			response = checkResponse(response);
			HttpEntity respPayloadEntity = response.getEntity();
			String respPayload = Constants.STRING_EMPTY;
			if (respPayloadEntity != null) {
				respPayload = EntityUtils.toString(respPayloadEntity);
				EntityUtils.consume(respPayloadEntity);
			}
			LOG.debug("-- execute(..): '{}' response payload received for HTTP '{}' request with " 
					+ "status line '{}'", ((respPayloadEntity == null) ? "null" : "non-null"), 
					reqMethod, response.getStatusLine());
			return respPayload;
		} catch (ClientProtocolException e) {
			throw new HttpLayerException(Errors.REQUEST_HTTP_FAULT, e);
		} catch (IOException e) {
			throw new HttpLayerException(Errors.IO_UNKNOWN, e);
		} catch (URISyntaxException e) {
			throw new HttpLayerException(Errors.PARSE_URI_FAILED, e);
		} finally {
			if (response != null) {
				try {
					LOG.debug("-- execute(..): attempting to recycle old HTTP response (reply to a "
							+ "'{}' request) with status line '{}'", reqMethod, response
							.getStatusLine());
					response.close();
				} catch (IOException e) {
					LOG.warn("<< execute(..): failed to recycle old HTTP response, message was: "
							+ "'{}'", e.getMessage());
				}
			}
		}
	}
	
	@Override
	public void close() {
		try {
			LOG.debug(">> close(..): attempting to shut down the underlying HTTP provider");
			provider.close();
		} catch (IOException e) {
			LOG.warn("<< close(..): failed to shut down the underlying HTTP provider, message was: "
					+ "'{}'", e.getMessage());
		}
	}

	private HttpRequestBase getNewRequest(String reqMethod, String reqPayload) 
			throws URISyntaxException, UnsupportedEncodingException {
		HttpRequestBase request;
		if (reqMethod.equals(HttpConstants.REQ_METHOD_POST)) {
			HttpPost postRequest = new HttpPost();
			postRequest.setEntity(new StringEntity(reqPayload, ContentType.create(
					DataFormats.JSON.getMediaType(), Constants.UTF_8)));
			request = postRequest;
		} else {
			throw new IllegalArgumentException(Errors.ARGS_HTTP_METHOD_UNSUPPORTED.getDescription());
		}
		request.setURI(new URI(String.format("%s://%s:%s/", 
					nodeConfig.getProperty(NodeProperties.RPC_PROTOCOL.getKey()), 
					nodeConfig.getProperty(NodeProperties.RPC_HOST.getKey()), 
					nodeConfig.getProperty(NodeProperties.RPC_PORT.getKey()))));
		String authScheme = nodeConfig.getProperty(NodeProperties.HTTP_AUTH_SCHEME.getKey());
		request.addHeader(resolveAuthHeader(authScheme));
		LOG.debug("<< getNewRequest(..): returning a new HTTP '{}' request with target endpoint "
				+ "'{}' and headers '{}'", reqMethod, request.getURI(), request.getAllHeaders());
		return request;
	}

	private Header resolveAuthHeader(String authScheme) {
		if (authScheme.equals(HttpConstants.AUTH_SCHEME_NONE)) {
			return null;
		}
		if (authScheme.equals(HttpConstants.AUTH_SCHEME_BASIC)) {
			return new BasicHeader(HttpConstants.HEADER_AUTH, HttpConstants.AUTH_SCHEME_BASIC 
					+ " " + getCredentials(HttpConstants.AUTH_SCHEME_BASIC));
		}
		return null;
	}

	private String getCredentials(String authScheme) {
		if (authScheme.equals(HttpConstants.AUTH_SCHEME_NONE)) {
			return Constants.STRING_EMPTY;
		} else if (authScheme.equals(HttpConstants.AUTH_SCHEME_BASIC)) {
			return Base64.encodeBase64String((nodeConfig.getProperty(NodeProperties.RPC_USER.getKey())
					+ ":" + nodeConfig.getProperty(NodeProperties.RPC_PASSWORD.getKey())).getBytes());
		}
		throw new IllegalArgumentException(Errors.ARGS_HTTP_AUTHSCHEME_UNSUPPORTED.getDescription());
	}
	
	private CloseableHttpResponse checkResponse(CloseableHttpResponse response) 
			throws HttpLayerException {
		LOG.debug(">> checkResponse(..): checking HTTP response for non-OK status codes & "
				+ "unexpected header values");
		StatusLine statusLine = response.getStatusLine();
		if ((statusLine.getStatusCode() >= 400) && (statusLine.getStatusCode() <= 499)) {
			throw new HttpLayerException(Errors.RESPONSE_HTTP_CLIENT_FAULT, statusLine.toString());
		}
		if ((statusLine.getStatusCode() == 500)) {
			return response;
		}
		if ((statusLine.getStatusCode() >= 501) && (statusLine.getStatusCode() <= 599)) {
			throw new HttpLayerException(Errors.RESPONSE_HTTP_SERVER_FAULT, statusLine.toString());
		}	
		return response;
	}
}
