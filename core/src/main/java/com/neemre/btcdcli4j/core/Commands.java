package com.neemre.btcdcli4j.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**An enumeration specifying the <i>bitcoind</i> JSON-RPC API commands currently supported by 
 * btcd-cli4j.**/
@Getter
@ToString
@AllArgsConstructor
public enum Commands {

	ADD_MULTI_SIG_ADDRESS("addmultisigaddress", 2, 3),
	ADD_NODE("addnode", 2, 2),
	BACKUP_WALLET("backupwallet", 1, 1),
	CREATE_MULTI_SIG("createmultisig", 2, 2),
	CREATE_RAW_TRANSACTION("createrawtransaction", 2, 2),
	DECODE_RAW_TRANSACTION("decoderawtransaction", 1, 2),
	DECODE_SCRIPT("decodescript", 1, 1),
	DUMP_PRIV_KEY("dumpprivkey", 1, 1),
	DUMP_WALLET("dumpwallet", 1, 1),
	ENCRYPT_WALLET("encryptwallet", 1, 1),
	ESTIMATE_SMART_FEE("estimatesmartfee", 1, 2),
	ESTIMATE_PRIORITY("estimatepriority", 1, 1),
	GET_ACCOUNT("getaccount", 1, 1),
	GET_ACCOUNT_ADDRESS("getaccountaddress", 1, 1),
	GET_ADDED_NODE_INFO("getaddednodeinfo", 1, 2),
	GET_ADDRESSES_BY_ACCOUNT("getaddressesbyaccount", 1, 1),
	GET_BALANCE("getbalance", 0, 3),
	GET_BEST_BLOCK_HASH("getbestblockhash", 0, 0),
	GET_BLOCK("getblock", 1, 2),
	GET_BLOCK_CHAIN_INFO("getblockchaininfo", 0, 0),
	GET_BLOCK_COUNT("getblockcount", 0, 0),
	GET_BLOCK_HASH("getblockhash", 1, 1),
	GET_CHAIN_TIPS("getchaintips", 0, 0),
	GET_CONNECTION_COUNT("getconnectioncount", 0, 0),
	GET_DIFFICULTY("getdifficulty", 0, 0),
	GET_GENERATE("getgenerate", 0, 0),
	GET_HASHES_PER_SEC("gethashespersec", 0, 0),
	GET_INFO("getinfo", 0, 0),
	GET_MEM_POOL_INFO("getmempoolinfo", 0, 0),
	GET_MINING_INFO("getmininginfo", 0, 0),
	GET_NET_TOTALS("getnettotals", 0, 0),
	GET_NETWORK_HASH_PS("getnetworkhashps", 0, 2),
	GET_NETWORK_INFO("getnetworkinfo", 0, 0),
	GET_NEW_ADDRESS("getnewaddress", 0, 1),
	GET_PEER_INFO("getpeerinfo", 0, 0),
	GET_RAW_CHANGE_ADDRESS("getrawchangeaddress", 0, 0),
	GET_RAW_MEM_POOL("getrawmempool", 0, 1),
	GET_RAW_TRANSACTION("getrawtransaction", 1, 2),
	GET_RECEIVED_BY_ACCOUNT("getreceivedbyaccount", 1, 2),
	GET_RECEIVED_BY_ADDRESS("getreceivedbyaddress", 1, 2),
	GET_TRANSACTION("gettransaction", 1, 2),
	GET_TX_OUT_SET_INFO("gettxoutsetinfo", 0, 0),
	GET_UNCONFIRMED_BALANCE("getunconfirmedbalance", 0, 0),
	GET_WALLET_INFO("getwalletinfo", 0, 0),
	HELP("help", 0, 1),
	IMPORT_ADDRESS("importaddress", 1, 3),
	IMPORT_PRIV_KEY("importprivkey", 1, 3),
	IMPORT_WALLET("importwallet", 1, 1),
	KEY_POOL_REFILL("keypoolrefill", 0, 1),
	LIST_ACCOUNTS("listaccounts", 0, 2),
	LIST_ADDRESS_GROUPINGS("listaddressgroupings", 0, 0),
	LIST_LOCK_UNSPENT("listlockunspent", 0, 0),
	LIST_RECEIVED_BY_ACCOUNT("listreceivedbyaccount", 0, 3),
	LIST_RECEIVED_BY_ADDRESS("listreceivedbyaddress", 0, 3),
	LIST_SINCE_BLOCK("listsinceblock", 0, 3),
	LIST_TRANSACTIONS("listtransactions", 0, 4),
	LIST_UNSPENT("listunspent", 0, 3),
	LOCK_UNSPENT("lockunspent", 1, 2),
	MOVE("move", 3, 5),
	PING("ping", 0, 0),
	PRIORITISE_TRANSACTION("prioritisetransaction", 3, 3),
	SEND_FROM("sendfrom", 3, 6),
	SEND_MANY("sendmany", 2, 4),
	SEND_RAW_TRANSACTION("sendrawtransaction", 1, 2),
	SEND_TO_ADDRESS("sendtoaddress", 2, 4),
	SET_ACCOUNT("setaccount", 2, 2),
	SET_GENERATE("setgenerate", 1, 2),
	SET_TX_FEE("settxfee", 1, 1),
	SIGN_MESSAGE("signmessage", 2, 2),
	SIGN_RAW_TRANSACTION("signrawtransaction", 1, 4),
	STOP("stop", 0, 0),
	SUBMIT_BLOCK("submitblock", 1, 2),
	VALIDATE_ADDRESS("validateaddress", 1, 1),
	VERIFY_CHAIN("verifychain", 0, 2),
	VERIFY_MESSAGE("verifymessage", 3, 3),
	WALLET_LOCK("walletlock", 0, 0),
	WALLET_PASSPHRASE("walletpassphrase", 2, 2),
	WALLET_PASSPHRASE_CHANGE("walletpassphrasechange", 2, 2);

	private final String name;
	private final int minParams;
	private final int maxParams;
}