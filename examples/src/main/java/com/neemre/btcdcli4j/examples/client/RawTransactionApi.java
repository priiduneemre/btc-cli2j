package com.neemre.btcdcli4j.examples.client;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import org.apache.http.impl.client.CloseableHttpClient;

import com.neemre.btcdcli4j.core.client.BtcdClient;
import com.neemre.btcdcli4j.core.domain.Output;
import com.neemre.btcdcli4j.core.domain.OutputOverview;
import com.neemre.btcdcli4j.core.domain.enums.SigHashTypes;
import com.neemre.btcdcli4j.examples.util.ResourceUtils;

/**A list of examples demonstrating the use of <i>bitcoind</i>'s raw transaction RPCs (via the JSON-RPC 
 * API).*/
public class RawTransactionApi {

	public static void main(String[] args) throws Exception {
		CloseableHttpClient httpProvider = ResourceUtils.getHttpProvider();
		Properties nodeConfig = ResourceUtils.getNodeConfig();
		BtcdClient client = new VerboseBtcdClientImpl(httpProvider, nodeConfig);

		client.createRawTransaction(Arrays.asList(new OutputOverview[]{new OutputOverview(
				"c61706760cbae899d8ef130c6d6405ce67c163340c7f78a5388c1adb44e271a7", 0)}), 
				new HashMap<String, BigDecimal>() {{
					put("mkedpaeZELrF36mjpNmEdmLznN7YKKHVSV", new BigDecimal("0.299"));
					put("mh2GwDCi8rhm319vRLyLyDTPgmnFLprwb7", new BigDecimal("1.900"));}});
		client.decodeRawTransaction("01000000019851e46f856eaa07c129e37d8ebfbc4e99f764e4623dd344dabf6718"
				+ "e2d66f650000000000ffffffff02580f0200000000001976a914af7d3ceab273e95a623b45fa51134583"
				+ "6ddccf8488acd8470300000000001976a9140ebec5470aec62ac2731ac536f4860768e31983288ac0000"
				+ "0000", true);
		client.decodeScript("5321021a971b74e19dbfa8d72fba6900bb49f6301cc8b7f86d1a5dd59397d3bdce88782103"
				+ "85fdc5e1adec9636cb1930e653da92024d3d26834306b6785a0b206995ccb7c121028d60b26dfdb6c92d"
				+ "ac52958106b0477a661f3eb562bde36448e37231e252e9a3410450fed2184797eef3ce649df3adccb19a"
				+ "dc02916c3d565cb02c50b2aaabd9394eb2bd024692b57602c95a86a9c3dabbbaa163db340cbd58506c11"
				+ "ec9bf757c88854ae");
		client.getRawTransaction("c6332a3b5624bb0dea70ba47d89ad150df443bf65b725a1e91b52753b20f7ca1");
		client.getRawTransaction("c6332a3b5624bb0dea70ba47d89ad150df443bf65b725a1e91b52753b20f7ca1", 1);
		client.sendRawTransaction("0100000001a771e244db1a8c38a5787f0c3463c167ce05646d0c13efd899e8ba0c"
				+ "760617c6000000006a473044022059dd8deaa2a448c2ad9e30a56f66d1f01c1faa4edc8e49dfd0351f62"
				+ "110924870220174b0e6ce04a109b39b8a51f1265211bf9bea00d562537bfc217914f8b7208d601210264"
				+ "d2aa10dbe5048d323ebc16bd42fe33dd6b91850fcf277aa9d0bf6d5fa7879bffffffff02802b530b0000"
				+ "00001976a9141083bfc4f51e3556a7908dde8a7d32e827e1212d88ace03cc801000000001976a914384c"
				+ "891aeb1caee8c44039031a2d874228e5b4cd88ac00000000");
		client.sendRawTransaction("01000000019851e46f856eaa07c129e37d8ebfbc4e99f764e4623dd344dabf6718e2"
				+ "d66f65000000006a473044022011fdc063358b8d85700a81b4b16077f88256706e4fa0740ea09756b950"
				+ "d02dc802205166f7234f2cc1e395930944751513253dc4458d8b636bd4b96b510d5ff96fd201210264d2"
				+ "aa10dbe5048d323ebc16bd42fe33dd6b91850fcf277aa9d0bf6d5fa7879bffffffff0300ef1c0d000000"
				+ "001976a9141083bfc4f51e3556a7908dde8a7d32e827e1212d88ac580f0200000000001976a914af7d3c"
				+ "eab273e95a623b45fa511345836ddccf8488acd8470300000000001976a9140ebec5470aec62ac2731ac"
				+ "536f4860768e31983288ac00000000", true);
		client.signRawTransaction("01000000019851e46f856eaa07c129e37d8ebfbc4e99f764e4623dd344dabf6718e2"
				+ "d66f650000000000ffffffff0300ef1c0d000000001976a9141083bfc4f51e3556a7908dde8a7d32e827"
				+ "e1212d88ac580f0200000000001976a914af7d3ceab273e95a623b45fa511345836ddccf8488acd84703"
				+ "00000000001976a9140ebec5470aec62ac2731ac536f4860768e31983288ac00000000");
		client.signRawTransaction("01000000019851e46f856eaa07c129e37d8ebfbc4e99f764e4623dd344dabf6718e2"
				+ "d66f650000000000ffffffff0300ef1c0d000000001976a9141083bfc4f51e3556a7908dde8a7d32e827"
				+ "e1212d88ac580f0200000000001976a914af7d3ceab273e95a623b45fa511345836ddccf8488acd84703"
				+ "00000000001976a9140ebec5470aec62ac2731ac536f4860768e31983288ac00000000",
				Arrays.asList(new Output[]{new Output("656fd6e21867bfda44d33d62e464f7994ebcbf8e7de329c1"
					+ "07aa6e856fe45198", 0, "76a9141083bfc4f51e3556a7908dde8a7d32e827e1212d88ac", 
					null)}));
		client.signRawTransaction("01000000019851e46f856eaa07c129e37d8ebfbc4e99f764e4623dd344dabf6718e2"
				+ "d66f650000000000ffffffff0300ef1c0d000000001976a9141083bfc4f51e3556a7908dde8a7d32e827"
				+ "e1212d88ac580f0200000000001976a914af7d3ceab273e95a623b45fa511345836ddccf8488acd84703"
				+ "00000000001976a9140ebec5470aec62ac2731ac536f4860768e31983288ac00000000",
				Arrays.asList(new Output[]{new Output("656fd6e21867bfda44d33d62e464f7994ebcbf8e7de329c1"
					+ "07aa6e856fe45198", 0, "76a9141083bfc4f51e3556a7908dde8a7d32e827e1212d88ac", 
					null)}), Arrays.asList(new String[]{"cNbvKQrwsEtwriHYK9ji1k7BZqggD3ZezXfkWJZFNp"
							+ "6PmSTfWEkT"}));
		client.signRawTransaction("01000000019851e46f856eaa07c129e37d8ebfbc4e99f764e4623dd344dabf6718e2"
				+ "d66f650000000000ffffffff0300ef1c0d000000001976a9141083bfc4f51e3556a7908dde8a7d32e827"
				+ "e1212d88ac580f0200000000001976a914af7d3ceab273e95a623b45fa511345836ddccf8488acd84703"
				+ "00000000001976a9140ebec5470aec62ac2731ac536f4860768e31983288ac00000000",
				Arrays.asList(new Output[]{new Output("656fd6e21867bfda44d33d62e464f7994ebcbf8e7de329c1"
					+ "07aa6e856fe45198", 0, "76a9141083bfc4f51e3556a7908dde8a7d32e827e1212d88ac", 
					null)}), Arrays.asList(new String[]{"cNbvKQrwsEtwriHYK9ji1k7BZqggD3ZezXfkWJZFNp"
							+ "6PmSTfWEkT"}), SigHashTypes.NONE.getName());
	}
}