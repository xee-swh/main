# kafka

 ## kafka下载地址
  
    https://mirrors.cnnic.cn/apache/kafka/

 ## zookeeper下载地址

    https://www.apache.org/dyn/closer.lua/zookeeper/zookeeper-3.8.0/apache-zookeeper-3.8.0-bin.tar.gz

 ## Zookeeper安装
   ### 1、上传压缩包
 
   将下载好的apache-zookeeper-3.8.0-bin.tar.gz包放到/usr/local/tool(我自己创建的文件夹)文件夹下，
 
   为了方面查找，可以修改下名称,也可以不修改，根据个人习惯而定
 
    mv apache-zookeeper-3.8.0-bin.tar.gz zookeeper-3.8.0.tar.gz
 
   ### 2、新建zookeeper文件夹
 
   在/usr/local下新建zookeeper文件夹
 
    mkdir /usr/local/zookeeper
  
   ### 3、解压zookeeper
 
   将zk解压到刚新建的文件件下
 
    tar -zxvf apache-zookeeper-3.8.0-bin.tar.gz -C /usr/local/zookeeper
 
   ### 4、在解压后的文件夹下面创建data文件夹
 
   进入到/usr/local/zookeeper/apache-zookeeper-3.8.0-bin下，新建data和logs文件夹
 
    mkdir data
    mkdir logs
 
   ### 5、修改配置文件
 
   zk配置文件：zoo.cfg可能叫zoo_sample.cfg，把zoo_sample.cfg改成zoo.cfg即可

   我这里使用的zk3.8的，名称叫zoo_sample.cfg，修改下名称
 
    mv zoo_sample.cfg zoo.cfg
 
   进入conf文件夹下修改zoo.cfg文件
 
    vi conf/zoo.cfg
 
   dataDir自定义路径参数，我这里指向/usr/local/zookeeper/apache-zookeeper-3.8.0-bin/data
 
    dataDir=/usr/local/zookeeper/apache-zookeeper-3.8.0-bin/data
 
   其他配置项不变，保存退出
 
   ### 6、启动ZooKeeper
  
   在/usr/local/zookeeper/apache-zookeeper-3.8.0-bin目录下使用命令
  
    bin/zkServer.sh start
  
   如果此时报类似这种错误：
  
    Using config: /usr/local/zookeeper/zookeeper-3.5.5/conf/zoo.cfg
    grep: /usr/local/zookeeper/zookeeper-3.5.5/conf/zoo.cfg: 没有那个文件或目录
    mkdir: 无法创建目录"": 没有那个文件或目录
  
   解决方案
  
   将zookeeper的conf目录下的zoo_sample.cfg文件改成zoo.cfg，改完以后再执行一遍启动zookeeper命令

   ### 7、查看进程
  
   Zookeeper启动后，查看一下启动的进程信息
  
    jps 
   
   或者  
    
    jps -m
  
   Zookeeper启动后，会多一个进程QuorumPeerMain
  
   ### 8、重启ZooKeeper
  
   在/usr/local/zookeeper/apache-zookeeper-3.8.0-bin目录下使用命令
        
    bin/zkServer.sh restart
    
   ### 9、停止ZooKeeper
 
   在/usr/local/zookeeper/apache-zookeeper-3.8.0-bin目录下使用命令

    bin/zkServer.sh stop

   ### 10、启动ZooKeeper CLI(ZooKeeper客户端)
 
   在/zookeeper/apache-zookeeper-3.8.0-bin目录下使用命令
 
    bin/zkCli.sh
  
 ## KAFKA安装

  ### 1、上传安装包
 
  将下载好的kafka_2.12-2.8.2.tgz（我使用的版本）放到/usr/local文件夹下
 
  ### 2、创建kafka目录
  
  在/usr路径下创建一个文件夹kafka
    
    mkdir /usr/local/kafka

  ### 3、解压
 
  压缩包到/usr/kafka目录下

    tar -zxvf kafka_2.12-2.2.0.tgz -C /usr/local/kafka
    
  ### 4、创建kafka-logs目录

  在/usr/local/kafka/kafka_2.12-2.8.2新建一个文件夹kafka-logs

    mkdir /usr/local/kafka/kafka_2.12-2.8.2/kafka-logs

  ### 5、修改kafka的配置文件
 
  在kafka主目录下bin/config或bin/config/kraft文件夹中找到server.properties并进行修改

    vim bin/config/server.properties
    
  注意：kafka在启动服务之前，在server.properties文件中要设定3个参数:broker.id、log.dirs、zookeeper.connect

    broker.id=0
    log.dirs=/usr/local/kafka/kafka_2.12-2.8.2/kafka-logs
    zookeeper.connect=localhost:2181
 
    delete.topic.enble=true,如果没有在kafka/config/server.properties配置：delete.topic.enable=true时，
    执行topic的删除命令只是把topic标记为marked for deletion，并不是真正的删除，如果此时想彻底删除，就需要登录
    zookeeper客户端进行删除

  ### 6、配置kafka环境变量，配置zookeeper和kafka的全局命令 
 
  方法一：修改profile文件(我使用的该方法，也推荐用这一种，两钟区别需自行查阅相关资料)

    vim /etc/profile
    
  直接在最下面添加下面这些配置

    export KAFKA_HOME=/usr/local/kafka/kafka_2.12-2.2.0
    export PATH=KAFKA_HOME/bin:$PATH
  
  使配置生效

    source /etc/profile
  
  方法二：修改 .bashrc 文件

    vim ~/.bashrc
  
  直接在最下面添加下面这些配置

    export KAFKA_HOME=/usr/local/kafka/kafka_2.12-2.2.0
    export PATH=KAFKA_HOME/bin:$PATH
  
  使配置生效
    
    source ~/.bashrc

  ### 7、验证kafka是否已经生效
  
     echo $KAFKA_HOME 

 ## kafka的使用

   Kafka用到了Zookeeper，先开启zookeeper，再开启Kafka(依次开启)

  ### 1、启动Zookeeper服务，在kafka的根目录下使用命令
  
  下面是基于一个实例的zookeeper服务，可以在命令结尾处加个&符号(进程守护)，启动kafka命令同理。

    ./bin/zookeeper-server-start.sh config/zookeeper.properties &
    或者
    ./bin/zkServer.sh  start 
    
  ### 2、启动kafka，在kafka的根目录下使用命令
    
    ./bin/kafka-server-start.sh config/server.properties &

  ### 3、创建一个topic，在kafka的根目录下使用命令
 
    ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic1
    (--zookeeper： kafka连接zookeeper的url，和server.properties文件中的配置项 zookeeper.connect=localhost:2181 一致)
 
  ### 4、查看kafka的topic列表
   
   在kafka的根目录下使用命令
    
     ./bin/kafka-topics.sh --list --zookeeper localhost:2181

  ### 5、查看单个topic详情
  
   查看topic的详细信息，在kafka的根目录下使用命令

    ./bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic topic1

  ### 6、生产消费
  
  -生产消息，生产者客户端命令，在kafka的根目录下使用命令

    ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic topic1
    (--broker-list localhost:9092是进入生产消息的编辑模式，进行编辑并发送)
    
   输入消息
    
    >hello,kafka
    >this is kafka
    
  -消费消息，消费者客户端命令，在kafka的根目录下使用命令

    ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic1 --from-beginning
    --from-beginning表示从最开始接受消息
    
   消费消息如下：
      
    hello,kafka
    this is kafka
    
  ### 7、删除topic，
  
   在kafka的根目录下使用命令

    ./bin/kafka-topics.sh --delete --zookeeper localhost:2181 --topic topic1
    
  或者
    
    ./bin/kafka-run-class.sh kafka.admin.DeleteTopicCommand --zookeeper 127.0.0.1:2181 --topic topic1

  问题：
    
    对topic1进行删除操作后，再次查看topic的时候，topic1没有直接删除，而且后面出现了“marked for deletion”，只是将topic1标记了删除

  解决：
    
    如果没有在kafka/config/server.properties配置：delete.topic.enable=true时，执行topic的删除命令只是把topic标记为marked for deletion，
    并不是真正的删除，如果此时想彻底删除，就需要登录zookeeper客户端进行删除

  启动zookeeper客户端，在zookeeper根目录下使用命令：

    ./bin/zkCli.sh

  找到topic所在的目录，在zookeeper客户端输入这个命令

    ls /brokers/topics

  删除需要彻底删除的topic1，在zookeeper客户端输入这个命令

    deleteall /brokers/topics/topic1
    (rmr 命令已经被废弃了，可以执行 deleteall 命令)

  可以再次查看确认一下，在zookeeper客户端输入这个命令

    ls /brokers/topics

  在config/server.properties中找到log.dirs，删除log.dirs指定的文件目录，然后重新启动

  ### 8、通过下面命令验证，发现已经删除了。在kafka的根目录下使用命令

    ./bin/kafka-topics.sh --list --zookeeper localhost:2181
    
  ### 9、关闭服务
     
  先关闭Kafka，在kafka的根目录下使用命令

    ./bin/kafka-server-stop.sh
 
  再关闭Zookeeper，在Zookeeper的根目录下使用命令

     ./bin/zkServer.sh stop
 
  验证一下是否关闭

  查看当前运行的进程
    
    jps
    
  查看当前运行进程的详细信息 
    
    jps -m

## 实现原理
  kafka组成
  
  ### producer
   #### 生产者
   生产者（发布者）创建消息，一般情况下，一个消息会被发布到一个特定的主题上。生产者在默认情况下把消息均衡的分布
   到主题的所有分区上，而并不关心特定消息会被写入哪个分区。不过，生产者也可以把消息直接写到指定的分区。这通常通过
   消息键和分区器来实现，分区器为键生成一个散列值，并将其映射到指定的分区上。生产者也可以自定义分区器，根据不同的
   业务规则将消息映射到分区。 
    
   #### 消息发送确认机制
   ack应答机制:对于某些不太重要的数据，对数据的可靠性要求不是很高，能够容忍数据的少量丢失，所以没有必要等ISR中的follower全部接收成功。
   所以Kafka提供了三种可靠性级别，用户可以根据对可靠性和延迟的要求进行权衡。
   
   acks：
    
     0： producer不等待broker的ack，这一操作提供了一个最低的延迟，broker一接收到还没写入磁盘就已经返回，
         当broker故障时可能丢失数据；
         
     1： producer等待leader的ack，partition的leader落盘成功后返回ack，如果在follower同步成功之前
         leader故障，那么将会丢失数据；
         
     -1（all）：producer等待broker的ack，partition的leader和ISR里的follower全部落盘成功后才返回ack。
         但是如果在follower同步完成后，broker发送ack之前，leader发生故障，那么会造成重复数据。（极端情况
         下也有可能丢数据：ISR中只有一个Leader时，相当于1的情况）。
   ### consumer
   
   ### broker
   
   一个独立的kafka服务器被称为broker。broker接收来自生产者的消息，为消息设置偏移量，并提交消息到磁盘保存。
   broker为消费者提供服务，对读取分区的请求作出相应，返回已经提交到磁盘上的消息。
   
   集群：交给同一个zookeeper集群来管理的broker节点就组成了kafka的集群。
   
   broker是集群的组成部分，每个集群都有一个broker同时充当集群控制器的角色。控制器负责管理工作，包括将分区分配
   给broker和监控broker。在broker中，一个分区从属于一个broker，该broker被称为分区的首领。一个分区可以分配
   给多个broker（Topic设置了多个副本的时候），这时会发生分区复制。
   
   ### 主题
     Kafka的消息通过主题（Topic）进行分类，就好比是数据库的表，或者是文件系统里的文件夹。
  
   ### 分区
   
   Topic是逻辑上的概念，而partition（分区）是物理上的概念，每个partition对应于一个log文件，该log文件中存储的就是
   producer生产的数据。Producer生产的数据会被不断追加到该log文件末端，且每条数据都有自己的offset。消费者组中的每个
   消费者，都会实时记录自己消费到哪个offset，以便出错恢复时，从上次的位置继续消费。
   
   主题可以被分为若干个分区（Partition），一个分区就是一个提交日志。消息以追加的方式写入分区，然后以先进先出的顺序读取。
   注意，由于一个主题一般包含几个分区，因此无法在整个主题范围内保证消息的顺序，但可以保证消息在单个分区内的顺序。
   主题是逻辑上的概念，在物理上，一个主题是横跨多个服务器。
   
   ### 副本


## 副本
 
 1.Kafka副本作用：提高数据可靠性。
 
 2.Kafka中副本分为:Leader和Follower。Kafka生产者只会把数据发往Leader,然后Follower找Leader进行同步数据。
   读写由leader来完成，follower只备份，和leader同步数据，leader发生故障，follower顶上去。
   leader副本：可以理解为某个分区中，除了不是副本的那个分区。
   
 3.Kafka分区中的所有副本统称为AR(Assigned Repllicas)。
    AR=ISR+OSR
    
 4.ISR表示和Leader保持同步的Follower集合。如果Follower长时间未向Leader发送通信请求或同步数据，则该Follower
   将被踢出ISR。该时间阈值由replica.lag.time.max.ms参数设定，默认30s。Leader发生故障之后，就会从ISR中选举新的Leader。
   
 5.OSR:表示Follower与Leader副本同步时，超时的副本集合。

## 故障处理

### 1.LEO和HW

    LEO （ Log End Offset ）： 每个副本的最后一个 offset ， LEO 其实就是最新的 offset+ 1 。
    HW （ High Watermark ）： 所有副本中最小的 LEO 。

#### --follower故障
  follower发生故障后会被临时踢出ISR，待该follower恢复后，follower会读取本地磁盘记录的上次的HW，并将log
  文件高于HW的部分截取掉，从HW开始向leader进行同步。等该follower的LEO大于等于该Partition的HW，
  即follower追上leader之后，就可以重新加入ISR了。

#### --leader故障
  leader发生故障后，会从ISR中选出一个新的leader，之后为了保证多个副本之间的数据一致性，其余的follower
  会先将各自的log文件高于HW的部分截掉，然后从新的leader同步数据。
  
 注意：这只能保证副本之间的数据一致性，并不能保证数据不丢失或者不重复。









# 常见
