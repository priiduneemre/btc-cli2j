package com.neemre.btcdcli4j.http.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.neemre.btcdcli4j.NodeProperties;
import com.neemre.btcdcli4j.common.Constants;
import com.neemre.btcdcli4j.common.Errors;
import com.neemre.btcdcli4j.http.HttpConstants;
import com.neemre.btcdcli4j.http.HttpLayerException;

public class SimpleHttpClientImpl implements SimpleHttpClient {

	private CloseableHttpClient provider;
	private Properties nodeConfig;


	public SimpleHttpClientImpl(CloseableHttpClient provider, Properties nodeConfig) {
		this.provider = provider;
		this.nodeConfig = nodeConfig;
	}

	public String execute(String reqMethod, String reqPayload) throws HttpLayerException {
		CloseableHttpResponse response = null;
		try {
			response = provider.execute(getNewRequest(reqMethod, reqPayload));
			HttpEntity respPayloadEntity = response.getEntity();
			String respPayload = Constants.STRING_EMPTY;
			if(respPayloadEntity != null) {
				respPayload = EntityUtils.toString(respPayloadEntity);
			}
			EntityUtils.consume(respPayloadEntity);
			return respPayload;
		} catch (ClientProtocolException e) {
			throw new HttpLayerException(Errors.REQUEST_HTTP_FAILED, e);
		} catch (IOException e) {
			throw new HttpLayerException(Errors.IO_UNKNOWN, e);
		} catch (URISyntaxException e) {
			throw new HttpLayerException(Errors.PARSE_URI_FAILED, e);
		} finally {
			try {
				if(response != null) {
					response.close();
				}
			} catch (IOException e) {
				throw new HttpLayerException(Errors.IO_UNKNOWN, e);
			}
		}
	}

	private HttpRequestBase getNewRequest(String reqMethod, String reqPayload) 
			throws URISyntaxException, UnsupportedEncodingException {
		if(reqMethod.equals(HttpConstants.REQ_METHOD_POST)) {
			HttpPost request = new HttpPost(new URI(String.format("%s://%s:%s/", 
					nodeConfig.get(NodeProperties.RPC_PROTOCOL.getKey()), 
					nodeConfig.get(NodeProperties.RPC_HOST.getKey()), 
					nodeConfig.get(NodeProperties.RPC_PORT.getKey()))));
			String authScheme = nodeConfig.get(NodeProperties.HTTP_AUTH_SCHEME.getKey()).toString();
			request.setHeader(resolveAuthHeader(authScheme));
			request.setEntity(new StringEntity(reqPayload));
			return request;			
		}
		return null;
	}

	private Header resolveAuthHeader(String authScheme) {
		if(authScheme.equals(HttpConstants.AUTH_SCHEME_NONE)) {
			return null;
		}
		if(authScheme.equals(HttpConstants.AUTH_SCHEME_BASIC)) {
			return new BasicHeader(HttpConstants.REQ_HEADER_AUTH, HttpConstants.AUTH_SCHEME_BASIC 
					+ " " + getCredentials(HttpConstants.AUTH_SCHEME_BASIC));
		}
		return null;
	}

	private String getCredentials(String authScheme) {
		if(authScheme.equals(HttpConstants.AUTH_SCHEME_NONE)){
			return Constants.STRING_EMPTY;
		} else if(authScheme.equals(HttpConstants.AUTH_SCHEME_BASIC)) {
			return Base64.encodeBase64String((nodeConfig.get(NodeProperties.RPC_USER.getKey()) 
					+ ":" + nodeConfig.get(NodeProperties.RPC_PASSWORD.getKey())).getBytes());
		}
		return null;
	}
}