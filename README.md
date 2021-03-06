hadoop-mini-clusters
====================
hadoop-mini-clusters provides an easy way to test Hadoop projects directly in your IDE, without the need for a full blown development cluster or container orchestration. It allows the user to debug with the full power of the IDE. It provides a consistent API around the existing Mini Clusters across the ecosystem, eliminating the tedious task of learning the nuances of each project's approach.

<p align="center">
  <img src="https://travis-ci.org/sakserv/hadoop-mini-clusters.svg?branch=master"/>

<a href='https://coveralls.io/github/sakserv/hadoop-mini-clusters?branch=master'><img src='https://coveralls.io/repos/sakserv/hadoop-mini-clusters/badge.svg?branch=master&service=github' alt='Coverage Status' /></a>

  <img src="https://maven-badges.herokuapp.com/maven-central/com.github.sakserv/hadoop-mini-clusters/badge.svg"/>

</p>

Modules:
------------
The project structure changed with 0.1.0. Each mini cluster now resides in a module of its own. See the module names below.

Modules Included:
-----------------
*   hadoop-mini-clusters-hdfs - Mini HDFS Cluster
*   hadoop-mini-clusters-yarn - Mini YARN Cluster (no MR)
*   hadoop-mini-clusters-mapreduce - Mini MapReduce Cluster
*   hadoop-mini-clusters-hbase - Mini HBase Cluster
*   hadoop-mini-clusters-zookeeper - Curator based Local Cluster
*   hadoop-mini-clusters-hiveserver2 - Local HiveServer2 instance
*   hadoop-mini-clusters-hivemetastore - Derby backed HiveMetaStore
*   hadoop-mini-clusters-storm - Storm LocalCluster
*   hadoop-mini-clusters-kafka - Local Kafka Broker
*   hadoop-mini-clusters-oozie - Local Oozie Server - Thanks again Vladimir
*   hadoop-mini-clusters-mongodb - I know... not Hadoop
*   hadoop-mini-clusters-activemq - Thanks Vladimir Zlatkin!
*   hadoop-mini-clusters-hyperscaledb - For testing various databases

Tests:
------
Tests are included to show how to configure and use each of the mini clusters. See the *IntegrationTest classes.

Windows Support:
----------------
Support for Windows Developer workstations has been added. Note that only Window 8 and above are supported.
Most of the project will work on Windows 7 except MapReduce/YARN, due to changes in Windows.
See: [Access Denied Windows 7](http://stackoverflow.com/questions/25790319/getting-access-denied-error-while-running-hadoop-2-3-mapreduce-jobs-in-windows-7)


Using:
------
*  Maven Central - latest release

```XML
	<dependency>
		<groupId>com.github.sakserv</groupId>
		<artifactId>hadoop-mini-clusters</artifactId>
		<version>0.1.9</version>
	</dependency>
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-common</artifactId>
    	<version>0.1.9</version>
    </dependency>
```

Profile Support:
----------------
Multiple versions of HDP are available. The current list is:

*   HDP 2.5.3.0 (default)
*   HDP 2.5.0.0
*   HDP 2.4.2.0
*   HDP 2.4.0.0
*   HDP 2.3.4.0
*   HDP 2.3.2.0
*   HDP 2.3.0.0

To use a different profiles, add the profile name to your maven build:
```
mvn test -P2.3.0.0
```

Note that backwards compatibility is not guarenteed.

Examples:
---------

*  HDFS Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-hdfs</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        HdfsLocalCluster hdfsLocalCluster = new HdfsLocalCluster.Builder()
            .setHdfsNamenodePort(12345)
            .setHdfsNamenodeHttpPort(12341)
            .setHdfsTempDir("embedded_hdfs")
            .setHdfsNumDatanodes(1)
            .setHdfsEnablePermissions(false)
            .setHdfsFormat(true)
            .setHdfsEnableRunningUserAsProxyUser(true)
            .setHdfsConfig(new Configuration())
            .build();
                
        hdfsLocalCluster.start();
```

* YARN Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-yarn</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        YarnLocalCluster yarnLocalCluster = new YarnLocalCluster.Builder()
            .setNumNodeManagers(1)
            .setNumLocalDirs(Integer.parseInt(1)
            .setNumLogDirs(Integer.parseInt(1)
            .setResourceManagerAddress("localhost")
            .setResourceManagerHostname("localhost:37001")
            .setResourceManagerSchedulerAddress("localhost:37002")
            .setResourceManagerResourceTrackerAddress("localhost:37003")
            .setResourceManagerWebappAddress("localhost:37004")
            .setUseInJvmContainerExecutor(false)
            .setConfig(new Configuration())
            .build();
   
        yarnLocalCluster.start();
```

* MapReduce Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-mapreduce</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        MRLocalCluster mrLocalCluster = new MRLocalCluster.Builder()
            .setNumNodeManagers(1)
            .setJobHistoryAddress("localhost:37005")
            .setResourceManagerAddress("localhost")
            .setResourceManagerHostname("localhost:37001")
            .setResourceManagerSchedulerAddress("localhost:37002")
            .setResourceManagerResourceTrackerAddress("localhost:37003")
            .setResourceManagerWebappAddress("localhost:37004")
            .setUseInJvmContainerExecutor(false)
            .setConfig(new Configuration())
            .build();

        mrLocalCluster.start();
```

* HBase Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-hbase</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        HbaseLocalCluster hbaseLocalCluster = new HbaseLocalCluster.Builder()
            .setHbaseMasterPort(25111)
            .setHbaseMasterInfoPort(-1)
            .setNumRegionServers(1)
            .setHbaseRootDir("embedded_hbase")
            .setZookeeperPort(12345)
            .setZookeeperConnectionString("localhost:12345")
            .setZookeeperZnodeParent("/hbase-unsecure")
            .setHbaseWalReplicationEnabled(false)
            .setHbaseConfiguration(new Configuration())
            .build();
            
        hbaseLocalCluster.start();
```

*  Zookeeper Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-zookeeper</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        ZookeeperLocalCluster zookeeperLocalCluster = new ZookeeperLocalCluster.Builder()
            .setPort(12345)
            .setTempDir("embedded_zookeeper")
            .setZookeeperConnectionString("localhost:12345")
            .setMaxClientCnxns(60)
            .setElectionPort(20001)
            .setQuorumPort(20002)
            .setDeleteDataDirectoryOnClose(false)
            .setServerId(1)
            .setTickTime(2000)
            .build();
        zookeeperLocalCluster.start();
```

*  HiveServer2 Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-hiveserver2</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        HiveLocalServer2 hiveLocalServer2 = new HiveLocalServer2.Builder()
            .setHiveServer2Hostname("localhost")
            .setHiveServer2Port(12348)
            .setHiveMetastoreHostname("localhost")
            .setHiveMetastorePort(12347)
            .setHiveMetastoreDerbyDbDir("metastore_db")
            .setHiveScratchDir("hive_scratch_dir")
            .setHiveWarehouseDir("warehouse_dir")
            .setHiveConf(new HiveConf())
            .setZookeeperConnectionString("localhost:12345")
            .build();
        hiveLocalServer2.start();
```

*  HiveMetastore Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-hivemetastore</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        HiveLocalMetaStore hiveLocalMetaStore = new HiveLocalMetaStore.Builder()
            .setHiveMetastoreHostname("localhost")
            .setHiveMetastorePort(12347)
            .setHiveMetastoreDerbyDbDir("metastore_db")
            .setHiveScratchDir("hive_scratch_dir")
            .setHiveWarehouseDir("warehouse_dir")
            .setHiveConf(new HiveConf())
            .build();
        hiveLocalMetaStore.start();
```

*  Storm Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-storm</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        StormLocalCluster stormLocalCluster = new StormLocalCluster.Builder()
            .setZookeeperHost("localhost")
            .setZookeeperPort(12345)
            .setEnableDebug(true)
            .setNumWorkers(1)
            .setStormConfig(new Config())
            .build();
        stormLocalCluster.start();
```

*  Kafka Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-kafka</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        KafkaLocalBroker kafkaLocalBroker = new KafkaLocalBroker.Builder()
            .setKafkaHostname("localhost")
            .setKafkaPort(11111)
            .setKafkaBrokerId(0)
            .setKafkaProperties(new Properties())
            .setKafkaTempDir("embedded_kafka")
            .setZookeeperConnectionString("localhost:12345")
            .build();
        kafkaLocalBroker.start();
```

*  Oozie Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-oozie</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        OozieLocalServer oozieLocalServer = new OozieLocalServer.Builder()
                .setOozieTestDir("embedded_oozie")
                .setOozieHomeDir("oozie_home")
                .setOozieUsername(System.getProperty("user.name"))
                .setOozieGroupname("testgroup")
                .setOozieYarnResourceManagerAddress("localhost")
                .setOozieHdfsDefaultFs("hdfs://localhost:8020/")
                .setOozieConf(new Configuration())
                .setOozieHdfsShareLibDir("/tmp/oozie_share_lib")
                .setOozieShareLibCreate(Boolean.TRUE)
                .setOozieLocalShareLibCacheDir("share_lib_cache")
                .setOoziePurgeLocalShareLibCache(Boolean.FALSE)
                .build();
        oozieLocalServer.start();
```

*  MongoDB Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-mongodb</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        MongodbLocalServer mongodbLocalServer = new MongodbLocalServer.Builder()
            .setIp("127.0.0.1")
            .setPort(11112)
            .build();
        mongodbLocalServer.start();
```

*  ActiveMQ Example
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-activemq</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        ActivemqLocalBroker amq = new ActivemqLocalBroker.Builder()
            .setHostName("localhost")
            .setPort(11113)
            .setQueueName("defaultQueue")
            .setStoreDir("activemq-data")
            .setUriPrefix("vm://")
            .setUriPostfix("?create=false")
            .build();
        amq.start();
```

*  HyperSQL DB
```XML
	<dependency>
    	<groupId>com.github.sakserv</groupId>
    	<artifactId>hadoop-mini-clusters-hyperscaledb</artifactId>
    	<version>0.1.9</version>
    </dependency>
```
```Java
        hsqldbLocalServer = new HsqldbLocalServer.Builder()
            .setHsqldbHostName("127.0.0.1")
            .setHsqldbPort("44111")
            .setHsqldbTempDir("embedded_hsqldb")
            .setHsqldbDatabaseName("testdb")
            .setHsqldbCompatibilityMode("mysql")
            .setHsqldbJdbcDriver("org.hsqldb.jdbc.JDBCDriver")
            .setHsqldbJdbcConnectionStringPrefix("jdbc:hsqldb:hsql://")
            .build();
        hsqldbLocalServer.start();
```

Modifying Properties
--------------------
To change the defaults used to construct the mini clusters, modify src/main/java/resources/default.properties as needed.


Intellij Testing
----------------

If you desire running the full test suite from Intellij, make sure Fork Mode is set to method (Run -> Edit Configurations -> fork mode)


InJvmContainerExecutor
----------------------
YarnLocalCluster now supports Oleg Z's InJvmContainerExecutor. See [Oleg Z's Github](https://github.com/hortonworks/mini-dev-cluster/wiki/Core-Features) for more.
