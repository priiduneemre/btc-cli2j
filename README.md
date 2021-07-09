##Introduction

The btcd-cli4j library is a simple Java wrapper around Bitcoin Core's JSON-RPC (via HTTP) interface. 

* **Latest release:** 0.5.1 ([.zip](https://github.com/priiduneemre/btcd-cli4j/archive/0.5.1.zip); [.tar.gz](https://github.com/priiduneemre/btcd-cli4j/archive/0.5.1.tar.gz))
* **Compatibility:** Bitcoin Core v0.10.0/0.10.1/0.10.2/0.10.3 (see [here](https://github.com/priiduneemre/btcd-cli4j/releases) for older versions)
* **API coverage:** 78 of 80 commands (fully) implemented
* **License:** Apache License 2.0 (see [LICENSE.md](https://github.com/priiduneemre/btcd-cli4j/blob/master/LICENSE.md))
* **Readme updated:** 2016-02-17 10:16:43 (UTC)

A list of all *bitcoind* JSON-RPC API commands currently supported by btcd-cli4j can be found in the `Commands` enum (see [here](https://github.com/priiduneemre/btcd-cli4j/blob/master/core/src/main/java/com/neemre/btcdcli4j/core/Commands.java) for more details).

btcd-cli4j follows a layered architecture in that the actions needed to communicate with the Bitcoin network have been separated into multiple levels of abstraction. The central interface used to invoke *bitcoind* API commands (`BtcdClient`) is solely concerned with Bitcoin-specific entity mapping & business logic and does not know anything about JSON-RPC or HTTP. Internally, `BtcdClientImpl` relies on `JsonRpcClient` for managing cross-application communication and adherence to the JSON-RPC standard. `JsonRpcClientImpl`, in turn, utilizes the interface provided by `SimpleHttpClient` to tunnel all JSON-RPC traffic over HTTP. Both the `JsonRpcClientImpl` and `SimpleHttpClientImpl` classes rely on external service providers internally (*Jackson JSON Processor* and *Apache HttpComponents Client* for the time being) which should be relatively easy to replace, were the need to arise.

By default, all incoming & outgoing decimal values (*i.e.* amounts, balances, ping times etc) are transformed into `BigDecimal`s with a scale of 8 and rounding mode of `RoundingMode.HALF_UP` by btcd-cli4j.


##Technologies & dependencies

The btcd-cli4j library has been designed for use with Java 7+, however it should also work fine with slightly older versions of the Java runtime (*i.e.* Java 6).

Core dependencies:
* Apache HttpComponents Client 4.3.6 [[link]](https://hc.apache.org/httpcomponents-client-ga/index.html)
* Jackson JSON Processor 2.5.0:
  * Streaming 2.5.0 (`jackson-core`) [[link]](https://github.com/FasterXML/jackson-core)
  * Annotations 2.5.0 (`jackson-annotations`) [[link]](https://github.com/FasterXML/jackson-annotations)
  * Databind 2.5.0 (`jackson-databind`) [[link]](https://github.com/FasterXML/jackson-databind)
* Lombok 1.16.2 [[link]](https://github.com/rzwitserloot/lombok)

Other dependencies:
* Simple Logging Facade for Java 1.7.10 [[link]](http://www.slf4j.org/)
* Apache Commons Lang 3.3.2 [[link]](http://commons.apache.org/proper/commons-lang/)

##Publishing locally for testing<a name="publish-local"></a>

If you want to test locally (i.e. not checking in changes to github and doing a jitpack release), run `mvn install`, which will publish jars to your local maven repo as such:
  "com.github.bitsoex.btcd-cli4j:btcd-cli4j-core:1.0-SNAPSHOT"

In your code depending upon this library, change your dependency to this snapshot version.  You might need to instruct your gradle to search your local maven, so add this to your build.gradle file:

repositories {
        mavenLocal()
}

##Getting started <a name="getting-started"></a>

Since the project is currently not hosted on the Maven Central Repository, you should begin by specifying the following custom repository in your `pom.xml` file:

	<repositories>
		<repository>
			<id>mvn-nemp-ftp</id>
			<name>Nemp's Maven Repository</name>
			<url>http://mvn.neemre.com/</url>
		</repository>
	</repositories>

Next, modify your `pom.xml` to include `btcd-cli4j-core` as a dependency:

	<dependency>
		<groupId>com.neemre.btcd-cli4j</groupId>
		<artifactId>btcd-cli4j-core</artifactId>
		<version>0.5.1</version>
	</dependency>

In order to communicate with *bitcoind*, btcd-cli4j needs to be aware of your node's exact configuration. The easiest way of providing this information is via a `node_config.properties` file, for example:

	node.bitcoind.rpc.protocol = http
	node.bitcoind.rpc.host = 127.0.0.1
	node.bitcoind.rpc.port = 8332
	node.bitcoind.rpc.user = falcon-pc
	node.bitcoind.rpc.password = 3F4DN9QGqWrB4DCdfYMXp8xdDYL4HDFzpaS9r76DbNhw
	node.bitcoind.http.auth_scheme = Basic

Finally, to begin using the wrapper, create an instance of `CloseableHttpClient` (the default HTTP provider), load up the properties file created in the previous step and pass the two into a new `BtcdClientImpl` instance: 

	PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(cm)
			.build();
	Properties nodeConfig = new Properties();
	InputStream is = new BufferedInputStream(new FileInputStream("node_config.properties"));
	nodeConfig.load(is);
	is.close();
	
	BtcdClient client = new BtcdClientImpl(httpProvider, nodeConfig);
	
That's it!
	
	MiningInfo miningInfo = client.getMiningInfo();
	
*P.S. To learn more about the default HTTP provider (e.g. performance tuning of* `CloseableHttpClient` *instances and/or use of SSL/TLS layering (i.e. HTTPS) (untested!)), see the official HttpComponents Client documentation* [here](http://hc.apache.org/httpcomponents-client-4.3.x/tutorial/html/connmgmt.html#d5e380) *and* [here](http://hc.apache.org/httpcomponents-client-4.3.x/tutorial/html/connmgmt.html#d5e436)*. Additionally, check out the related code samples:* [1](http://hc.apache.org/httpcomponents-client-4.3.x/httpclient/examples/org/apache/http/examples/client/ClientConfiguration.java) *and* [2](http://hc.apache.org/httpcomponents-client-4.3.x/httpclient/examples/org/apache/http/examples/client/ClientCustomSSL.java)*.* 

##Handling asynchronous events

Bitcoin Core also provides an asynchronous notification API by relying on a set of user-defined shell scripts specified in the `bitcoin.conf` configuration file (see [here](https://en.bitcoin.it/wiki/Running_Bitcoin#Bitcoin.conf_Configuration_File) for more details). Whenever a particular event is detected on the network, the appropriate shell script gets loaded with data & executed by *bitcoind*. To take advantage of this feature, add the following lines to your `bitcoin.conf` file (use any flavor of `netcat` you're comfortable with, such as `ncat` or `socat`): 

	alertnotify="echo %s | ncat 127.0.0.1 5158"
	blocknotify="echo %s | ncat 127.0.0.1 5159"
	walletnotify="echo %s | ncat 127.0.0.1 5160"

Next, modify your `pom.xml` to include `btcd-cli4j-daemon` as a dependency:

	<dependency>
		<groupId>com.neemre.btcd-cli4j</groupId>
		<artifactId>btcd-cli4j-daemon</artifactId>
		<version>0.5.1</version>
	</dependency>

To let the daemon know where to listen for notifications, open up your `node_config.properties` file and specify the ports listed in the shell scripts above, for example:

	node.bitcoind.notification.alert.port = 5158
	node.bitcoind.notification.block.port = 5159
	node.bitcoind.notification.wallet.port = 5160

Finally, instantiate the daemon with a preconfigured `BtcdClient` instance (see [Getting started](#getting-started)) like so:

	BtcdDaemon daemon = new BtcdDaemonImpl(client);

Alternatively, create a self-contained version of the daemon (*i.e.* by specifying the affected ports only):
	
	BtcdDaemon daemon = new BtcdDaemonImpl(5158, 5159, 5160);

Great! Now you're ready to add some event listeners:

	daemon.addBlockListener(new BlockListener() {
		@Override
		public void blockDetected(Block block) {
			System.out.printf("New block detected! (Event details: '%s')\n", block);
		}
	});
	
##Examples

For a short list of usage examples (mostly just sample API calls), see the [examples](https://github.com/priiduneemre/btcd-cli4j/tree/master/examples/src/main) module. 

Please note that some of the examples above will only work on the Bitcoin TESTNET3 chain (for obvious reasons).


##Supporting the project

If btcd-cli4j has been useful to you and you feel like contributing, consider posting a [bug report](https://github.com/priiduneemre/btcd-cli4j/issues) or a [pull request](https://github.com/priiduneemre/btcd-cli4j/pulls). Alternatively, a spare beer or pizza would also be much appreciated:

* Bitcoin: `12CfEQ7RAEwpS82jFZg1HgjeH8obbpMeL5`
* Litecoin: `LMwTYNgj7Hkugd1x8rjNXBYdYKgNvACjRF`
* Dogecoin: `D6DUTo8MTxredfHbbQsb7MLqu7zkuFSMnt`

If you have any further questions, feel free to visit us at the `#bitcoin` IRC channel on [freenode](https://freenode.net/).