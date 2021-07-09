package com.neemre.btcdcli4j.examples.client;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.neemre.btcdcli4j.core.domain.EstimateFee;
import org.apache.http.impl.client.CloseableHttpClient;

import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.Commands;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import com.neemre.btcdcli4j.core.domain.Account;
import com.neemre.btcdcli4j.core.domain.AddedNode;
import com.neemre.btcdcli4j.core.domain.Address;
import com.neemre.btcdcli4j.core.domain.AddressInfo;
import com.neemre.btcdcli4j.core.domain.AddressOverview;
import com.neemre.btcdcli4j.core.domain.Block;
import com.neemre.btcdcli4j.core.domain.BlockChainInfo;
import com.neemre.btcdcli4j.core.domain.Info;
import com.neemre.btcdcli4j.core.domain.MemPoolInfo;
import com.neemre.btcdcli4j.core.domain.MiningInfo;
import com.neemre.btcdcli4j.core.domain.MultiSigAddress;
import com.neemre.btcdcli4j.core.domain.NetworkInfo;
import com.neemre.btcdcli4j.core.domain.NetworkTotals;
import com.neemre.btcdcli4j.core.domain.Output;
import com.neemre.btcdcli4j.core.domain.OutputOverview;
import com.neemre.btcdcli4j.core.domain.Payment;
import com.neemre.btcdcli4j.core.domain.PeerNode;
import com.neemre.btcdcli4j.core.domain.RawTransactionOverview;
import com.neemre.btcdcli4j.core.domain.RedeemScript;
import com.neemre.btcdcli4j.core.domain.SignatureResult;
import com.neemre.btcdcli4j.core.domain.SinceBlock;
import com.neemre.btcdcli4j.core.domain.Tip;
import com.neemre.btcdcli4j.core.domain.Transaction;
import com.neemre.btcdcli4j.core.domain.TxOutSetInfo;
import com.neemre.btcdcli4j.core.domain.WalletInfo;

import static com.neemre.btcdcli4j.examples.util.OutputUtils.printResult;

/**A subclass of {@code BtcdClientImpl} that writes detailed operational data (<i>e.g.</i> the 
 * result of each method call) to {@code stdout}. This implementation is provided for informational 
 * purposes only; it is not meant for use in production environments.*/
public class VerboseBtcdClientImpl extends BtcdClientImpl {

	public VerboseBtcdClientImpl(CloseableHttpClient httpProvider, Properties nodeConfig) 
			throws BitcoindException, CommunicationException {
		super(httpProvider, nodeConfig);
		System.out.println("Initiating the 'bitcoind' core wrapper");
	}

	@Override
	public String addMultiSigAddress(Integer minSignatures, List<String> addresses) 
			throws BitcoindException, CommunicationException {
		String multiSigAddress = super.addMultiSigAddress(minSignatures, addresses);
		printResult(Commands.ADD_MULTI_SIG_ADDRESS.getName(), new String[]{"minSignatures",
				"addresses"}, new Object[]{minSignatures, addresses}, multiSigAddress);
		return multiSigAddress;
	}

	@Override
	public String addMultiSigAddress(Integer minSignatures, List<String> addresses, String account)
			throws BitcoindException, CommunicationException {
		String multiSigAddress = super.addMultiSigAddress(minSignatures, addresses, account);
		printResult(Commands.ADD_MULTI_SIG_ADDRESS.getName(), new String[]{"minSignatures", 
				"addresses", "account"}, new Object[]{minSignatures, addresses, account}, 
				multiSigAddress);
		return multiSigAddress;
	}

	@Override
	public void addNode(String node, String command) throws BitcoindException, 
			CommunicationException {
		super.addNode(node, command);
		printResult(Commands.ADD_NODE.getName(), new String[]{"node", "command"}, 
				new Object[]{node, command}, null);
	}

	@Override
	public void backupWallet(String filePath) throws BitcoindException, CommunicationException {
		super.backupWallet(filePath);
		printResult(Commands.BACKUP_WALLET.getName(), new String[]{"filePath"}, 
				new Object[]{filePath}, null);
	}

	@Override
	public MultiSigAddress createMultiSig(Integer minSignatures, List<String> addresses)
			throws BitcoindException, CommunicationException {
		MultiSigAddress multiSigAddress = super.createMultiSig(minSignatures, addresses);
		printResult(Commands.CREATE_MULTI_SIG.getName(), new String[]{"minSignatures", "addresses"},
				new Object[]{minSignatures, addresses}, multiSigAddress);
		return multiSigAddress;
	}

	@Override
	public String createRawTransaction(List<OutputOverview> outputs, 
			Map<String, BigDecimal> toAddresses) throws BitcoindException, CommunicationException {
		String hexTransaction = super.createRawTransaction(outputs, toAddresses);
		printResult(Commands.CREATE_RAW_TRANSACTION.getName(), new String[]{"outputs", 
				"toAddresses"}, new Object[]{outputs, toAddresses}, hexTransaction);
		return hexTransaction;
	}

	@Override
	public RawTransactionOverview decodeRawTransaction(String hexTransaction, Boolean isWitness) throws BitcoindException,
			CommunicationException {
		RawTransactionOverview rawTransaction = super.decodeRawTransaction(hexTransaction, isWitness);
		printResult(Commands.DECODE_RAW_TRANSACTION.getName(), new String[]{"hexTransaction"},
				new Object[]{hexTransaction}, rawTransaction);
		return rawTransaction;
	}

	@Override
	public RedeemScript decodeScript(String hexRedeemScript) throws BitcoindException, 
			CommunicationException {
		RedeemScript redeemScript = super.decodeScript(hexRedeemScript);
		printResult(Commands.DECODE_SCRIPT.getName(), new String[]{"hexRedeemScript"}, 
				new Object[]{hexRedeemScript}, redeemScript);
		return redeemScript;
	}

	@Override
	public String dumpPrivKey(String address) throws BitcoindException, CommunicationException {
		String privateKey = super.dumpPrivKey(address);
		printResult(Commands.DUMP_PRIV_KEY.getName(), new String[]{"address"}, 
				new Object[]{address}, privateKey);
		return privateKey;
	}

	@Override
	public void dumpWallet(String filePath) throws BitcoindException, CommunicationException {
		super.dumpWallet(filePath);
		printResult(Commands.DUMP_WALLET.getName(), new String[]{"filePath"}, 
				new Object[]{filePath}, null);
	}

	@Override
	public String encryptWallet(String passphrase) throws BitcoindException, CommunicationException {
		String noticeMsg = super.encryptWallet(passphrase);
		printResult(Commands.ENCRYPT_WALLET.getName(), new String[]{"passphrase"}, 
				new Object[]{passphrase}, noticeMsg);
		return noticeMsg;
	}	

	@Override
	public BigDecimal estimateSmartFee(Integer maxBlocks, EstimateFee.Mode mode) throws BitcoindException, CommunicationException {
		BigDecimal estimatedFee = super.estimateSmartFee(maxBlocks, mode);
		printResult(Commands.ESTIMATE_SMART_FEE.getName(), new String[]{"maxBlocks", "estimateMode"},
				new Object[]{maxBlocks, mode}, estimatedFee);
		return estimatedFee;
	}

	@Override
	public BigDecimal estimatePriority(Integer maxBlocks) throws BitcoindException, 
			CommunicationException {
		BigDecimal estimatedPriority = super.estimatePriority(maxBlocks);
		printResult(Commands.ESTIMATE_PRIORITY.getName(), new String[]{"maxBlocks"}, 
				new Object[]{maxBlocks}, estimatedPriority);
		return estimatedPriority;
	}

	@Override
	public String getAccount(String address) throws BitcoindException, CommunicationException {
		String account = super.getAccount(address);
		printResult(Commands.GET_ACCOUNT.getName(), new String[]{"address"}, new Object[]{address},
				account);
		return account;
	}

	@Override
	public String getAccountAddress(String account) throws BitcoindException, CommunicationException {
		String address = super.getAccountAddress(account);
		printResult(Commands.GET_ACCOUNT_ADDRESS.getName(), new String[]{"account"}, 
				new Object[]{account}, address);
		return address;
	}

	@Override
	public List<AddedNode> getAddedNodeInfo(Boolean withDetails) throws BitcoindException, 
			CommunicationException {
		List<AddedNode> addedNodes = super.getAddedNodeInfo(withDetails);
		printResult(Commands.GET_ADDED_NODE_INFO.getName(), new String[]{"withDetails"}, 
				new Object[]{withDetails}, addedNodes);
		return addedNodes;
	}

	@Override
	public List<AddedNode> getAddedNodeInfo(Boolean withDetails, String node) 
			throws BitcoindException, CommunicationException {
		List<AddedNode> addedNodes = super.getAddedNodeInfo(withDetails, node);
		printResult(Commands.GET_ADDED_NODE_INFO.getName(), new String[]{"withDetails", "node"},
				new Object[]{withDetails, node}, addedNodes);
		return addedNodes;
	}

	@Override
	public List<String> getAddressesByAccount(String account) throws BitcoindException, 
			CommunicationException {
		List<String> addresses = super.getAddressesByAccount(account);
		printResult(Commands.GET_ADDRESSES_BY_ACCOUNT.getName(), new String[]{"account"},
				new Object[]{account}, addresses);
		return addresses;
	}

	@Override
	public BigDecimal getBalance() throws BitcoindException, CommunicationException {
		BigDecimal balance = super.getBalance();
		printResult(Commands.GET_BALANCE.getName(), null, null, balance);
		return balance;
	}

	@Override
	public BigDecimal getBalance(String account) throws BitcoindException, CommunicationException {
		BigDecimal balance = super.getBalance(account);
		printResult(Commands.GET_BALANCE.getName(), new String[]{"account"}, new Object[]{account},
				balance);
		return balance;
	}

	@Override
	public BigDecimal getBalance(String account, Integer confirmations) throws BitcoindException, 
			CommunicationException {
		BigDecimal balance = super.getBalance(account, confirmations);
		printResult(Commands.GET_BALANCE.getName(), new String[]{"account", "confirmations"}, 
				new Object[]{account, confirmations}, balance);
		return balance;
	}

	@Override
	public BigDecimal getBalance(String account, Integer confirmations, Boolean withWatchOnly) 
			throws BitcoindException, CommunicationException {
		BigDecimal balance = super.getBalance(account, confirmations, withWatchOnly);
		printResult(Commands.GET_BALANCE.getName(), new String[]{"account", "confirmations", 
				"withWatchOnly"}, new Object[]{account, confirmations, withWatchOnly}, balance);
		return balance;
	}

	@Override
	public String getBestBlockHash() throws BitcoindException, CommunicationException {
		String headerHash = super.getBestBlockHash();
		printResult(Commands.GET_BEST_BLOCK_HASH.getName(), null, null, headerHash);
		return headerHash;
	}

	@Override
	public Block getBlock(String headerHash) throws BitcoindException, CommunicationException {
		Block block = super.getBlock(headerHash);
		printResult(Commands.GET_BLOCK.getName(), new String[]{"headerHash"}, 
				new Object[]{headerHash}, block);
		return block;
	}

	@Override
	public Object getBlock(String headerHash, Boolean isDecoded) throws BitcoindException, 
			CommunicationException {
		Object block = super.getBlock(headerHash, isDecoded);
		printResult(Commands.GET_BLOCK.getName(), new String[]{"headerHash", "isDecoded"}, 
				new Object[]{headerHash, isDecoded}, block);
		return block;
	}

	@Override
	public BlockChainInfo getBlockChainInfo() throws BitcoindException, CommunicationException {
		BlockChainInfo blockChainInfo = super.getBlockChainInfo();
		printResult(Commands.GET_BLOCK_CHAIN_INFO.getName(), null, null, blockChainInfo);
		return blockChainInfo;
	}

	@Override
	public Integer getBlockCount() throws BitcoindException, CommunicationException {
		Integer blockHeight = super.getBlockCount();
		printResult(Commands.GET_BLOCK_COUNT.getName(), null, null, blockHeight);
		return blockHeight;
	}

	@Override
	public String getBlockHash(Integer blockHeight) throws BitcoindException, CommunicationException {
		String headerHash = super.getBlockHash(blockHeight);
		printResult(Commands.GET_BLOCK_HASH.getName(), new String[]{"blockHeight"}, 
				new Object[]{blockHeight}, headerHash);
		return headerHash;
	}

	@Override
	public List<Tip> getChainTips() throws BitcoindException, CommunicationException {
		List<Tip> chainTips = super.getChainTips();
		printResult(Commands.GET_CHAIN_TIPS.getName(), null, null, chainTips);
		return chainTips;
	}

	@Override
	public Integer getConnectionCount() throws BitcoindException, CommunicationException {
		Integer connectionCount = super.getConnectionCount();
		printResult(Commands.GET_CONNECTION_COUNT.getName(), null, null, connectionCount);
		return connectionCount;
	}

	@Override
	public BigDecimal getDifficulty() throws BitcoindException, CommunicationException {
		BigDecimal difficulty = super.getDifficulty();
		printResult(Commands.GET_DIFFICULTY.getName(), null, null, difficulty);
		return difficulty;
	}

	@Override
	public Boolean getGenerate() throws BitcoindException, CommunicationException {
		Boolean isGenerate = super.getGenerate();
		printResult(Commands.GET_GENERATE.getName(), null, null, isGenerate);
		return isGenerate;
	}

	@Override
	public Long getHashesPerSec() throws BitcoindException, CommunicationException {
		Long hashesPerSec = super.getHashesPerSec();
		printResult(Commands.GET_HASHES_PER_SEC.getName(), null, null, hashesPerSec);
		return hashesPerSec;
	}

	@Override
	public Info getInfo() throws BitcoindException, CommunicationException {
		Info info = super.getInfo();
		printResult(Commands.GET_INFO.getName(), null, null, info);
		return info;
	}

	@Override
	public MemPoolInfo getMemPoolInfo() throws BitcoindException, CommunicationException {
		MemPoolInfo memPoolInfo = super.getMemPoolInfo();
		printResult(Commands.GET_MEM_POOL_INFO.getName(), null, null, memPoolInfo);
		return memPoolInfo;
	}

	@Override
	public MiningInfo getMiningInfo() throws BitcoindException, CommunicationException {
		MiningInfo miningInfo = super.getMiningInfo();
		printResult(Commands.GET_MINING_INFO.getName(), null, null, miningInfo);
		return miningInfo;
	}

	@Override
	public NetworkTotals getNetTotals() throws BitcoindException, CommunicationException {
		NetworkTotals netTotals = super.getNetTotals();
		printResult(Commands.GET_NET_TOTALS.getName(), null, null, netTotals);
		return netTotals;
	}

	@Override
	public BigInteger getNetworkHashPs() throws BitcoindException, CommunicationException {
		BigInteger networkHashPs = super.getNetworkHashPs();
		printResult(Commands.GET_NETWORK_HASH_PS.getName(), null, null, networkHashPs);
		return networkHashPs;
	}

	@Override
	public BigInteger getNetworkHashPs(Integer blocks) throws BitcoindException, 
			CommunicationException {
		BigInteger networkHashPs = super.getNetworkHashPs(blocks);
		printResult(Commands.GET_NETWORK_HASH_PS.getName(), new String[]{"blocks"}, 
				new Object[]{blocks}, networkHashPs);
		return networkHashPs;
	}

	@Override
	public BigInteger getNetworkHashPs(Integer blocks, Integer blockHeight) throws BitcoindException,
			CommunicationException {
		BigInteger networkHashPs = super.getNetworkHashPs(blocks, blockHeight);
		printResult(Commands.GET_NETWORK_HASH_PS.getName(), new String[]{"blocks", "blockHeight"},
				new Object[]{blocks, blockHeight}, networkHashPs);
		return networkHashPs;
	}

	@Override
	public NetworkInfo getNetworkInfo() throws BitcoindException, CommunicationException {
		NetworkInfo networkInfo = super.getNetworkInfo();
		printResult(Commands.GET_NETWORK_INFO.getName(), null, null, networkInfo);
		return networkInfo;
	}

	@Override
	public String getNewAddress() throws BitcoindException, CommunicationException {
		String address = super.getNewAddress();
		printResult(Commands.GET_NEW_ADDRESS.getName(), null, null, address);
		return address;
	}

	@Override
	public String getNewAddress(String account) throws BitcoindException, CommunicationException {
		String address = super.getNewAddress(account);
		printResult(Commands.GET_NEW_ADDRESS.getName(), new String[]{"account"}, 
				new Object[]{account}, address);
		return address;
	}

	@Override
	public List<PeerNode> getPeerInfo() throws BitcoindException, CommunicationException {
		List<PeerNode> peerInfo = super.getPeerInfo();
		printResult(Commands.GET_PEER_INFO.getName(), null, null, peerInfo);
		return peerInfo;
	}

	@Override
	public String getRawChangeAddress() throws BitcoindException, CommunicationException {
		String address = super.getRawChangeAddress();
		printResult(Commands.GET_RAW_CHANGE_ADDRESS.getName(), null, null, address);
		return address;
	}

	@Override
	public List<String> getRawMemPool() throws BitcoindException, CommunicationException {
		List<String> memPoolTxns = super.getRawMemPool();
		printResult(Commands.GET_RAW_MEM_POOL.getName(), null, null, memPoolTxns);
		return memPoolTxns;
	}

	@Override
	public List<? extends Object> getRawMemPool(Boolean isDetailed) throws BitcoindException, 
			CommunicationException {
		List<? extends Object> memPoolTxns = super.getRawMemPool(isDetailed);
		printResult(Commands.GET_RAW_MEM_POOL.getName(), new String[]{"isDetailed"}, 
				new Object[]{isDetailed}, memPoolTxns);
		return memPoolTxns;
	}

	@Override
	public String getRawTransaction(String txId) throws BitcoindException, CommunicationException {
		String hexTransaction = super.getRawTransaction(txId);
		printResult(Commands.GET_RAW_TRANSACTION.getName(), new String[]{"txId"}, 
				new Object[]{txId}, hexTransaction);
		return hexTransaction;
	}

	@Override
	public Object getRawTransaction(String txId, Integer verbosity) throws BitcoindException, 
			CommunicationException {
		Object transaction = super.getRawTransaction(txId, verbosity);
		printResult(Commands.GET_RAW_TRANSACTION.getName(), new String[]{"txId", "verbosity"}, 
				new Object[]{txId, verbosity}, transaction);
		return transaction;
	}

	@Override
	public BigDecimal getReceivedByAccount(String account) throws BitcoindException, 
			CommunicationException {
		BigDecimal totalReceived = super.getReceivedByAccount(account);
		printResult(Commands.GET_RECEIVED_BY_ACCOUNT.getName(), new String[]{"account"},
				new Object[]{account}, totalReceived);
		return totalReceived;
	}

	@Override
	public BigDecimal getReceivedByAccount(String account, Integer confirmations) 
			throws BitcoindException, CommunicationException {
		BigDecimal totalReceived = super.getReceivedByAccount(account, confirmations);
		printResult(Commands.GET_RECEIVED_BY_ACCOUNT.getName(), new String[]{"account", 
				"confirmations"}, new Object[]{account, confirmations}, totalReceived);
		return totalReceived;
	}

	@Override
	public BigDecimal getReceivedByAddress(String address) throws BitcoindException, 
			CommunicationException {
		BigDecimal totalReceived = super.getReceivedByAddress(address);
		printResult(Commands.GET_RECEIVED_BY_ADDRESS.getName(), new String[]{"address"},
				new Object[]{address}, totalReceived);
		return totalReceived;
	}

	@Override
	public BigDecimal getReceivedByAddress(String address, Integer confirmations) 
			throws BitcoindException, CommunicationException {
		BigDecimal totalReceived = super.getReceivedByAddress(address, confirmations);
		printResult(Commands.GET_RECEIVED_BY_ADDRESS.getName(), new String[]{"address", 
				"confirmations"}, new Object[]{address, confirmations}, totalReceived);
		return totalReceived;
	}

	@Override
	public Transaction getTransaction(String txId) throws BitcoindException, CommunicationException {
		Transaction transaction = super.getTransaction(txId);
		printResult(Commands.GET_TRANSACTION.getName(), new String[]{"txId"}, new Object[]{txId},
				transaction);
		return transaction;
	}

	@Override
	public Transaction getTransaction(String txId, Boolean withWatchOnly) throws BitcoindException, 
			CommunicationException {
		Transaction transaction = super.getTransaction(txId, withWatchOnly);
		printResult(Commands.GET_TRANSACTION.getName(), new String[]{"txId", "withWatchOnly"},
				new Object[]{txId, withWatchOnly}, transaction);
		return transaction;
	}

	@Override
	public TxOutSetInfo getTxOutSetInfo() throws BitcoindException, CommunicationException {
		TxOutSetInfo txnOutSetInfo = super.getTxOutSetInfo();
		printResult(Commands.GET_TX_OUT_SET_INFO.getName(), null, null, txnOutSetInfo);
		return txnOutSetInfo;
	}

	@Override
	public BigDecimal getUnconfirmedBalance() throws BitcoindException, CommunicationException {
		BigDecimal unconfirmedBalance = super.getUnconfirmedBalance();
		printResult(Commands.GET_UNCONFIRMED_BALANCE.getName(), null, null, unconfirmedBalance);
		return unconfirmedBalance;
	}

	@Override
	public WalletInfo getWalletInfo() throws BitcoindException, CommunicationException {
		WalletInfo walletInfo = super.getWalletInfo();
		printResult(Commands.GET_WALLET_INFO.getName(), null, null, walletInfo);
		return walletInfo;
	}

	@Override
	public String help() throws BitcoindException, CommunicationException {
		String help = super.help();
		printResult(Commands.HELP.getName(), null, null, help);
		return help;
	}

	@Override
	public String help(String command) throws BitcoindException, CommunicationException {
		String help = super.help(command);
		printResult(Commands.HELP.getName(), new String[]{"command"}, new Object[]{command}, help);
		return help;
	}

	@Override
	public void importAddress(String address) throws BitcoindException, CommunicationException {
		super.importAddress(address);
		printResult(Commands.IMPORT_ADDRESS.getName(), new String[]{"address"}, 
				new Object[]{address}, null);
	}

	@Override
	public void importAddress(String address, String account) throws BitcoindException, 
			CommunicationException {
		super.importAddress(address, account);
		printResult(Commands.IMPORT_ADDRESS.getName(), new String[]{"address", "account"},
				new Object[]{address, account}, null);
	}

	@Override
	public void importAddress(String address, String account, Boolean withRescan) 
			throws BitcoindException, CommunicationException {
		super.importAddress(address, account, withRescan);
		printResult(Commands.IMPORT_ADDRESS.getName(), new String[]{"address", "account", 
				"withRescan"}, new Object[]{address, account, withRescan}, null);
	}

	@Override
	public void importPrivKey(String privateKey) throws BitcoindException, CommunicationException {
		super.importPrivKey(privateKey);
		printResult(Commands.IMPORT_PRIV_KEY.getName(), new String[]{"privateKey"}, 
				new Object[]{privateKey}, null);
	}

	@Override
	public void importPrivKey(String privateKey, String account) throws BitcoindException, 
			CommunicationException {
		super.importPrivKey(privateKey, account);
		printResult(Commands.IMPORT_PRIV_KEY.getName(), new String[]{"privateKey", "account"},
				new Object[]{privateKey, account}, null);
	}

	@Override
	public void importPrivKey(String privateKey, String account, Boolean withRescan) 
			throws BitcoindException, CommunicationException {
		super.importPrivKey(privateKey, account, withRescan);
		printResult(Commands.IMPORT_PRIV_KEY.getName(), new String[]{"privateKey", "account", 
				"withRescan"}, new Object[]{privateKey, account, withRescan}, null);
	}

	@Override
	public void importWallet(String filePath) throws BitcoindException, CommunicationException {
		super.importWallet(filePath);
		printResult(Commands.IMPORT_WALLET.getName(), new String[]{"filePath"}, 
				new Object[]{filePath}, null);
	}

	@Override
	public void keyPoolRefill() throws BitcoindException, CommunicationException {
		super.keyPoolRefill();
		printResult(Commands.KEY_POOL_REFILL.getName(), null, null, null);
	}

	@Override
	public void keyPoolRefill(Integer keypoolSize) throws BitcoindException, CommunicationException {
		super.keyPoolRefill(keypoolSize);
		printResult(Commands.KEY_POOL_REFILL.getName(), new String[]{"keypoolSize"}, 
				new Object[]{keypoolSize}, null);
	}

	@Override
	public Map<String, BigDecimal> listAccounts() throws BitcoindException, CommunicationException {
		Map<String, BigDecimal> accounts = super.listAccounts();
		printResult(Commands.LIST_ACCOUNTS.getName(), null, null, accounts);
		return accounts;
	}

	@Override
	public Map<String, BigDecimal> listAccounts(Integer confirmations) throws BitcoindException, 
			CommunicationException {
		Map<String, BigDecimal> accounts = super.listAccounts(confirmations);
		printResult(Commands.LIST_ACCOUNTS.getName(), new String[]{"confirmations"}, 
				new Object[]{confirmations}, accounts);
		return accounts;
	}

	@Override
	public Map<String, BigDecimal> listAccounts(Integer confirmations, Boolean withWatchOnly) 
			throws BitcoindException, CommunicationException {
		Map<String, BigDecimal> accounts = super.listAccounts(confirmations, withWatchOnly);
		printResult(Commands.LIST_ACCOUNTS.getName(), new String[]{"confirmations", 
				"withWatchOnly"}, new Object[]{confirmations, withWatchOnly}, accounts);
		return accounts;
	}

	@Override
	public List<List<AddressOverview>> listAddressGroupings() throws BitcoindException, 
			CommunicationException {
		List<List<AddressOverview>> groupings = super.listAddressGroupings();
		printResult(Commands.LIST_ADDRESS_GROUPINGS.getName(), null, null, groupings);
		return groupings;
	}

	@Override
	public List<OutputOverview> listLockUnspent() throws BitcoindException, CommunicationException {
		List<OutputOverview> lockedOutputs = super.listLockUnspent();
		printResult(Commands.LIST_LOCK_UNSPENT.getName(), null, null, lockedOutputs);
		return lockedOutputs;
	}

	@Override
	public List<Account> listReceivedByAccount() throws BitcoindException, CommunicationException {
		List<Account> accounts = super.listReceivedByAccount();
		printResult(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), null, null, accounts);
		return accounts;
	}

	@Override
	public List<Account> listReceivedByAccount(Integer confirmations) throws BitcoindException, 
			CommunicationException {
		List<Account> accounts = super.listReceivedByAccount(confirmations);
		printResult(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), new String[]{"confirmations"},
				new Object[]{confirmations}, accounts);
		return accounts;
	}

	@Override
	public List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused) 
			throws BitcoindException, CommunicationException {
		List<Account> accounts = super.listReceivedByAccount(confirmations, withUnused);
		printResult(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), new String[]{"confirmations", 
				"withUnused"}, new Object[]{confirmations, withUnused}, accounts);
		return accounts;
	}

	@Override
	public List<Account> listReceivedByAccount(Integer confirmations, Boolean withUnused, 
			Boolean withWatchOnly) throws BitcoindException, CommunicationException {
		List<Account> accounts = super.listReceivedByAccount(confirmations, withUnused, 
				withWatchOnly);
		printResult(Commands.LIST_RECEIVED_BY_ACCOUNT.getName(), new String[]{"confirmations", 
				"withUnused", "withWatchOnly"}, new Object[]{confirmations, withUnused, 
				withWatchOnly}, accounts);
		return accounts;
	}		

	@Override
	public List<Address> listReceivedByAddress() throws BitcoindException, CommunicationException {
		List<Address> addresses = super.listReceivedByAddress();
		printResult(Commands.LIST_RECEIVED_BY_ADDRESS.getName(), null, null, addresses);
		return addresses;
	}

	@Override
	public List<Address> listReceivedByAddress(Integer confirmations) throws BitcoindException, 
			CommunicationException {
		List<Address> addresses = super.listReceivedByAddress(confirmations);
		printResult(Commands.LIST_RECEIVED_BY_ADDRESS.getName(), new String[]{"confirmations"},
				new Object[]{confirmations}, addresses);
		return addresses;
	}

	@Override
	public List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused) 
			throws BitcoindException, CommunicationException {
		List<Address> addresses = super.listReceivedByAddress(confirmations, withUnused);
		printResult(Commands.LIST_RECEIVED_BY_ADDRESS.getName(), new String[]{"confirmations",
				"withUnused"}, new Object[]{confirmations, withUnused}, addresses);
		return addresses;
	}

	@Override
	public List<Address> listReceivedByAddress(Integer confirmations, Boolean withUnused, 
			Boolean withWatchOnly) throws BitcoindException, CommunicationException {
		List<Address> addresses = super.listReceivedByAddress(confirmations, withUnused,
				withWatchOnly);
		printResult(Commands.LIST_RECEIVED_BY_ADDRESS.getName(), new String[]{"confirmations", 
				"withUnused", "withWatchOnly"}, new Object[]{confirmations, withUnused, 
				withWatchOnly}, addresses);
		return addresses;
	}

	@Override
	public SinceBlock listSinceBlock() throws BitcoindException, CommunicationException {
		SinceBlock sinceBlock = super.listSinceBlock();
		printResult(Commands.LIST_SINCE_BLOCK.getName(), null, null, sinceBlock);
		return sinceBlock;
	}

	@Override
	public SinceBlock listSinceBlock(String headerHash) throws BitcoindException, 
			CommunicationException {
		SinceBlock sinceBlock = super.listSinceBlock(headerHash);
		printResult(Commands.LIST_SINCE_BLOCK.getName(), new String[]{"headerHash"}, 
				new Object[]{headerHash}, sinceBlock);
		return sinceBlock;
	}

	@Override
	public SinceBlock listSinceBlock(String headerHash, Integer confirmations) 
			throws BitcoindException, CommunicationException {
		SinceBlock sinceBlock = super.listSinceBlock(headerHash, confirmations);
		printResult(Commands.LIST_SINCE_BLOCK.getName(), new String[]{"headerHash", 
				"confirmations"}, new Object[]{headerHash, confirmations}, sinceBlock);
		return sinceBlock;
	}

	@Override
	public SinceBlock listSinceBlock(String headerHash, Integer confirmations, Boolean withWatchOnly) 
			throws BitcoindException, CommunicationException {
		SinceBlock sinceBlock = super.listSinceBlock(headerHash, confirmations, withWatchOnly);
		printResult(Commands.LIST_SINCE_BLOCK.getName(), new String[]{"headerHash", "confirmations",
				"withWatchOnly"}, new Object[]{headerHash, confirmations, withWatchOnly}, sinceBlock);
		return sinceBlock;
	}

	@Override
	public List<Payment> listTransactions() throws BitcoindException, CommunicationException {
		List<Payment> payments = super.listTransactions();
		printResult(Commands.LIST_TRANSACTIONS.getName(), null, null, payments);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account) throws BitcoindException, 
			CommunicationException {
		List<Payment> payments = super.listTransactions(account);
		printResult(Commands.LIST_TRANSACTIONS.getName(), new String[]{"account"}, 
				new Object[]{account}, payments);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account, Integer count) throws BitcoindException, 
			CommunicationException {
		List<Payment> payments = super.listTransactions(account, count);
		printResult(Commands.LIST_TRANSACTIONS.getName(), new String[]{"account", "count"},
				new Object[]{account, count}, payments);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account, Integer count, Integer offset) 
			throws BitcoindException, CommunicationException {
		List<Payment> payments = super.listTransactions(account, count, offset);
		printResult(Commands.LIST_TRANSACTIONS.getName(), new String[]{"account", "count", "offset"},
				new Object[]{account, count, offset}, payments);
		return payments;
	}

	@Override
	public List<Payment> listTransactions(String account, Integer count, Integer offset, 
			Boolean withWatchOnly) throws BitcoindException, CommunicationException {
		List<Payment> payments = super.listTransactions(account, count, offset, withWatchOnly);
		printResult(Commands.LIST_TRANSACTIONS.getName(), new String[]{"account", "count", "offset",
				"withWatchOnly"}, new Object[]{account, count, offset, withWatchOnly}, payments);
		return payments;
	}

	@Override
	public List<Output> listUnspent() throws BitcoindException, CommunicationException {
		List<Output> unspentOutputs = super.listUnspent();
		printResult(Commands.LIST_UNSPENT.getName(), null, null, unspentOutputs);
		return unspentOutputs;
	}

	@Override
	public List<Output> listUnspent(Integer minConfirmations) throws BitcoindException, 
			CommunicationException {
		List<Output> unspentOutputs = super.listUnspent(minConfirmations);
		printResult(Commands.LIST_UNSPENT.getName(), new String[]{"minConfirmations"}, 
				new Object[]{minConfirmations}, unspentOutputs);
		return unspentOutputs;
	}

	@Override
	public List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations) 
			throws BitcoindException, CommunicationException {
		List<Output> unspentOutputs = super.listUnspent(minConfirmations, maxConfirmations);
		printResult(Commands.LIST_UNSPENT.getName(), new String[]{"minConfirmations", 
				"maxConfirmations"}, new Object[]{minConfirmations, maxConfirmations}, 
				unspentOutputs);
		return unspentOutputs;
	}

	@Override
	public List<Output> listUnspent(Integer minConfirmations, Integer maxConfirmations, 
			List<String> addresses) throws BitcoindException, CommunicationException {
		List<Output> unspentOutputs = super.listUnspent(minConfirmations, maxConfirmations, 
				addresses);
		printResult(Commands.LIST_UNSPENT.getName(), new String[]{"minConfirmations",
				"maxConfirmations", "addresses"}, new Object[]{minConfirmations, maxConfirmations,
				addresses}, unspentOutputs);
		return unspentOutputs;
	}

	@Override
	public Boolean lockUnspent(Boolean isUnlocked) throws BitcoindException, CommunicationException {
		Boolean isSuccess = super.lockUnspent(isUnlocked);
		printResult(Commands.LOCK_UNSPENT.getName(), new String[]{"isUnlocked"}, 
				new Object[]{isUnlocked}, isSuccess);
		return isSuccess;
	}

	@Override
	public Boolean lockUnspent(Boolean isUnlocked, List<OutputOverview> outputs) 
			throws BitcoindException, CommunicationException {
		Boolean isSuccess = super.lockUnspent(isUnlocked, outputs);
		printResult(Commands.LOCK_UNSPENT.getName(), new String[]{"isUnlocked", "outputs"}, 
				new Object[]{isUnlocked, outputs}, isSuccess);
		return isSuccess;
	}

	@Override
	public Boolean move(String fromAccount, String toAccount, BigDecimal amount) 
			throws BitcoindException, CommunicationException {
		Boolean isSuccess = super.move(fromAccount, toAccount, amount);
		printResult(Commands.MOVE.getName(), new String[]{"fromAccount", "toAccount", "amount"},
				new Object[]{fromAccount, toAccount, amount}, isSuccess);
		return isSuccess;
	}

	@Override
	public Boolean move(String fromAccount, String toAccount, BigDecimal amount, Integer dummy,
			String comment) throws BitcoindException, CommunicationException {
		Boolean isSuccess = super.move(fromAccount, toAccount, amount, dummy, comment);
		printResult(Commands.MOVE.getName(), new String[]{"fromAccount", "toAccount", "amount", 
				"dummy", "comment"}, new Object[]{fromAccount, toAccount, amount, dummy, comment},
				isSuccess);
		return isSuccess;
	}

	@Override
	public void ping() throws BitcoindException, CommunicationException {
		super.ping();
		printResult(Commands.PING.getName(), null, null, null);
	}

	@Override
	public Boolean prioritiseTransaction(String txId, BigDecimal deltaPriority, Long deltaFee) 
			throws BitcoindException, CommunicationException {
		Boolean isSuccess = super.prioritiseTransaction(txId, deltaPriority, deltaFee);
		printResult(Commands.PRIORITISE_TRANSACTION.getName(), new String[]{"txId", "deltaPriority",
				"deltaFee"}, new Object[]{txId, deltaPriority, deltaFee}, isSuccess);
		return isSuccess;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount) 
			throws BitcoindException, CommunicationException {
		String transactionId = super.sendFrom(fromAccount, toAddress, amount);
		printResult(Commands.SEND_FROM.getName(), new String[]{"fromAccount", "toAddress", "amount"},
				new Object[]{fromAccount, toAddress, amount}, transactionId);
		return transactionId;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, 
			Integer confirmations) throws BitcoindException, CommunicationException {
		String transactionId = super.sendFrom(fromAccount, toAddress, amount, confirmations);
		printResult(Commands.SEND_FROM.getName(), new String[]{"fromAccount", "toAddress", "amount", 
				"confirmations"}, new Object[]{fromAccount, toAddress, amount, confirmations}, 
				transactionId);
		return transactionId;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, 
			Integer confirmations, String comment) throws BitcoindException, CommunicationException {
		String transactionId = super.sendFrom(fromAccount, toAddress, amount, confirmations,
				comment);
		printResult(Commands.SEND_FROM.getName(), new String[]{"fromAccount", "toAddress", "amount",
				"confirmations", "comment"}, new Object[]{fromAccount, toAddress, amount, 
				confirmations, comment}, transactionId);
		return transactionId;
	}

	@Override
	public String sendFrom(String fromAccount, String toAddress, BigDecimal amount, 
			Integer confirmations, String comment, String commentTo) throws BitcoindException, 
			CommunicationException {
		String transactionId = super.sendFrom(fromAccount, toAddress, amount, confirmations, 
				comment, commentTo);
		printResult(Commands.SEND_FROM.getName(), new String[]{"fromAccount", "toAddress", "amount",
				"confirmations", "comment", "commentTo"}, new Object[]{fromAccount, toAddress, 
				amount, confirmations, comment, commentTo}, transactionId);
		return transactionId;
	}

	@Override
	public String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses) 
			throws BitcoindException, CommunicationException {
		String transactionId = super.sendMany(fromAccount, toAddresses);
		printResult(Commands.SEND_MANY.getName(), new String[]{"fromAccount", "toAddresses"}, 
				new Object[]{fromAccount, toAddresses}, transactionId);
		return transactionId;
	}

	@Override
	public String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses, 
			Integer confirmations) throws BitcoindException, CommunicationException {
		String transactionId = super.sendMany(fromAccount, toAddresses, confirmations);
		printResult(Commands.SEND_MANY.getName(), new String[]{"fromAccount", "toAddresses", 
				"confirmations"}, new Object[]{fromAccount, toAddresses, confirmations}, 
				transactionId);
		return transactionId;
	}

	@Override
	public String sendMany(String fromAccount, Map<String, BigDecimal> toAddresses, 
			Integer confirmations, String comment) throws BitcoindException, CommunicationException {
		String transactionId = super.sendMany(fromAccount, toAddresses, confirmations, comment);
		printResult(Commands.SEND_MANY.getName(), new String[]{"fromAccount", "toAddresses", 
				"confirmations", "comment"}, new Object[]{fromAccount, toAddresses, confirmations, 
				comment}, transactionId);
		return transactionId;
	}

	@Override
	public String sendRawTransaction(String hexTransaction) throws BitcoindException, 
			CommunicationException {
		String transactionId = super.sendRawTransaction(hexTransaction);
		printResult(Commands.SEND_RAW_TRANSACTION.getName(), new String[]{"hexTransaction"}, 
				new Object[]{hexTransaction}, transactionId);
		return transactionId;
	}

	@Override
	public String sendRawTransaction(String hexTransaction, Boolean withHighFees) 
			throws BitcoindException, CommunicationException {
		String transactionId = super.sendRawTransaction(hexTransaction, withHighFees);
		printResult(Commands.SEND_RAW_TRANSACTION.getName(), new String[]{"hexTransaction", 
				"withHighFees"}, new Object[]{hexTransaction, withHighFees}, transactionId);
		return transactionId;
	}

	@Override
	public String sendToAddress(String toAddress, BigDecimal amount) throws BitcoindException, 
			CommunicationException {
		String transactionId = super.sendToAddress(toAddress, amount);
		printResult(Commands.SEND_TO_ADDRESS.getName(), new String[]{"toAddress", "amount"},
				new Object[]{toAddress, amount}, transactionId);
		return transactionId;
	}

	@Override
	public String sendToAddress(String toAddress, BigDecimal amount, String comment) 
			throws BitcoindException, CommunicationException {
		String transactionId = super.sendToAddress(toAddress, amount, comment);
		printResult(Commands.SEND_TO_ADDRESS.getName(), new String[]{"toAddress", "amount", 
				"comment"}, new Object[]{toAddress, amount, comment}, transactionId);
		return transactionId;
	}

	@Override
	public String sendToAddress(String toAddress, BigDecimal amount, String comment, 
			String commentTo) throws BitcoindException, CommunicationException {
		String transactionId = super.sendToAddress(toAddress, amount, comment, commentTo);
		printResult(Commands.SEND_TO_ADDRESS.getName(), new String[]{"toAddress", "amount", 
				"comment", "commentTo"}, new Object[]{toAddress, amount, comment, commentTo}, 
				transactionId);
		return transactionId;
	}

	@Override
	public void setAccount(String address, String account) throws BitcoindException, 
			CommunicationException {
		super.setAccount(address, account);
		printResult(Commands.SET_ACCOUNT.getName(), new String[]{"address", "account"},
				new Object[]{address, account}, null);
	}

	@Override
	public void setGenerate(Boolean isGenerate) throws BitcoindException, CommunicationException {
		super.setGenerate(isGenerate);
		printResult(Commands.SET_GENERATE.getName(), new String[]{"isGenerate"}, 
				new Object[]{isGenerate}, null);
	}

	@Override
	public void setGenerate(Boolean isGenerate, Integer processors) throws BitcoindException, 
			CommunicationException {
		super.setGenerate(isGenerate, processors);
		printResult(Commands.SET_GENERATE.getName(), new String[]{"isGenerate", "processors"},
				new Object[]{isGenerate, processors}, null);
	}

	@Override
	public Boolean setTxFee(BigDecimal txFee) throws BitcoindException, CommunicationException {
		Boolean isSuccess = super.setTxFee(txFee);
		printResult(Commands.SET_TX_FEE.getName(), new String[]{"txFee"}, new Object[]{txFee},
				isSuccess);
		return isSuccess;
	}

	@Override
	public String signMessage(String address, String message) throws BitcoindException, 
			CommunicationException {
		String signature = super.signMessage(address, message);
		printResult(Commands.SIGN_MESSAGE.getName(), new String[]{"address", "message"}, 
				new Object[]{address, message}, signature);
		return signature;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction) throws BitcoindException, 
			CommunicationException {
		SignatureResult signatureResult = super.signRawTransaction(hexTransaction);
		printResult(Commands.SIGN_RAW_TRANSACTION.getName(), new String[]{"hexTransaction"},
				new Object[]{hexTransaction}, signatureResult);
		return signatureResult;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs) 
			throws BitcoindException, CommunicationException {
		SignatureResult signatureResult = super.signRawTransaction(hexTransaction, outputs);
		printResult(Commands.SIGN_RAW_TRANSACTION.getName(), new String[]{"hexTransaction", 
				"outputs"}, new Object[]{hexTransaction, outputs}, signatureResult);
		return signatureResult;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs, 
			List<String> privateKeys) throws BitcoindException, CommunicationException {
		SignatureResult signatureResult = super.signRawTransaction(hexTransaction, outputs, 
				privateKeys);
		printResult(Commands.SIGN_RAW_TRANSACTION.getName(), new String[]{"hexTransaction", 
				"outputs", "privateKeys"}, new Object[]{hexTransaction, outputs, privateKeys}, 
				signatureResult);
		return signatureResult;
	}

	@Override
	public SignatureResult signRawTransaction(String hexTransaction, List<Output> outputs, 
			List<String> privateKeys, String sigHashType) throws BitcoindException, 
			CommunicationException {
		SignatureResult signatureResult = super.signRawTransaction(hexTransaction, outputs, 
				privateKeys, sigHashType);
		printResult(Commands.SIGN_RAW_TRANSACTION.getName(), new String[]{"hexTransaction", 
				"outputs", "privateKeys", "sigHashType"}, new Object[]{hexTransaction, outputs, 
				privateKeys, sigHashType}, signatureResult);
		return signatureResult;
	}

	@Override
	public String stop() throws BitcoindException, CommunicationException {
		String noticeMsg = super.stop();
		printResult(Commands.STOP.getName(), null, null, noticeMsg);
		return noticeMsg;
	}

	@Override
	public String submitBlock(String block) throws BitcoindException, CommunicationException {
		String blockStatus = super.submitBlock(block);
		printResult(Commands.SUBMIT_BLOCK.getName(), new String[]{"block"}, new Object[]{block},
				blockStatus);
		return blockStatus;
	}

	@Override
	public String submitBlock(String block, Map<String, Object> extraParameters) 
			throws BitcoindException, CommunicationException {
		String blockStatus = super.submitBlock(block, extraParameters);
		printResult(Commands.SUBMIT_BLOCK.getName(), new String[]{"block", "extraParameters"}, 
				new Object[]{block, extraParameters}, blockStatus);
		return blockStatus;
	}

	@Override
	public AddressInfo validateAddress(String address) throws BitcoindException, 
			CommunicationException {
		AddressInfo addressInfo = super.validateAddress(address);
		printResult(Commands.VALIDATE_ADDRESS.getName(), new String[]{"address"}, 
				new Object[]{address}, addressInfo);
		return addressInfo;
	}

	@Override
	public Boolean verifyChain() throws BitcoindException, CommunicationException {
		Boolean isChainValid = super.verifyChain();
		printResult(Commands.VERIFY_CHAIN.getName(), null, null, isChainValid);
		return isChainValid;
	}

	@Override
	public Boolean verifyChain(Integer checkLevel) throws BitcoindException, CommunicationException {
		Boolean isChainValid = super.verifyChain(checkLevel);
		printResult(Commands.VERIFY_CHAIN.getName(), new String[]{"checkLevel"}, 
				new Object[]{checkLevel}, isChainValid);
		return isChainValid;
	}

	@Override
	public Boolean verifyChain(Integer checkLevel, Integer blocks) throws BitcoindException, 
			CommunicationException {
		Boolean isChainValid = super.verifyChain(checkLevel, blocks);
		printResult(Commands.VERIFY_CHAIN.getName(), new String[]{"checkLevel", "blocks"}, 
				new Object[]{checkLevel, blocks}, isChainValid);
		return isChainValid;
	}

	@Override
	public Boolean verifyMessage(String address, String signature, String message) 
			throws BitcoindException, CommunicationException {
		Boolean isSigValid = super.verifyMessage(address, signature, message);
		printResult(Commands.VERIFY_MESSAGE.getName(), new String[]{"address", "signature", 
				"message"}, new Object[]{address, signature, message}, isSigValid);
		return isSigValid;
	}

	@Override
	public void walletLock() throws BitcoindException, CommunicationException {
		super.walletLock();
		printResult(Commands.WALLET_LOCK.getName(), null, null, null);
	}

	@Override
	public void walletPassphrase(String passphrase, Integer authTimeout) throws BitcoindException, 
			CommunicationException {
		super.walletPassphrase(passphrase, authTimeout);
		printResult(Commands.WALLET_PASSPHRASE.getName(), new String[]{"passphrase", "authTimeout"},
				new Object[]{passphrase, authTimeout}, null);
	}

	@Override
	public void walletPassphraseChange(String curPassphrase, String newPassphrase) 
			throws BitcoindException, CommunicationException {
		super.walletPassphraseChange(curPassphrase, newPassphrase);
		printResult(Commands.WALLET_PASSPHRASE_CHANGE.getName(), new String[]{"curPassphrase", 
				"newPassphrase"}, new Object[]{curPassphrase, newPassphrase}, null);
	}

	@Override
	public Properties getNodeConfig() {
		Properties nodeConfig = super.getNodeConfig();
		System.out.printf("Node configuration passed to & used by the client: '%s'\n", nodeConfig);
		return nodeConfig;
	}

	@Override
	public String getNodeVersion() {
		String nodeVersion = super.getNodeVersion();
		System.out.printf("Node version reported by the client: '%s'\n", nodeVersion);
		return nodeVersion;
	}

	@Override
	public void close() {
		System.out.println("Closing the 'bitcoind' core wrapper");
		super.close();
	}
}