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
 
    delete.topic.enble=true这段代码会对以后删除kafka中的topic有影响，这段代码在文件尾部添加上即可
    listeners=PLAINTEXT://:9092 ：这个命令也很重要，需要记住(这个命令在文章里先不做分析)
    advertised.listeners=PLAINTEXT://自己的主机ip地址:9092：这个localhost我用的是主机ip地址

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

  ### 1、启动Zookeeper服务，在kafka的根目录下使用命令

  Kafka用到了Zookeeper，先开启zookeeper，再开启Kafka(依次开启)
  
  以下使用都是基于一个单实例的Zookkeeper服务，可以在命令结尾处加个&符号(进程守护)，启动kafak命令同理。

    ./bin/zookeeper-server-start.sh config/zookeeper.properties &
    
  ### 2、启动kafka，在kafka的根目录下使用命令
    
    ./bin/kafka-server-start.sh config/server.properties &

  ### 3、创建一个topic，在kafka的根目录下使用命令
 
    ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic1
    (--zookeeper： kafka连接zookeeper的url，和server.properties文件中的配置项 zookeeper.connect=localhost:2181 一致)
 
  ### 4、查看kafka的topic情况，在kafka的根目录下使用命令
    
     ./bin/kafka-topics.sh --list --zookeeper localhost:2181

  ### 5、描述topic，查看topic的详细信息，在kafka的根目录下使用命令

    ./bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic topic1

  ### 6、生产消费
  
  -生产消息，生产者客户端命令，在kafka的根目录下使用命令

    ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic topic1
    (这里的  --broker-list localhost:9092是进入生产消息的编辑模式，进行编辑并发送)
    
   输入消息
    
    >hello,kafka
    >this is kafka
    
  -消费消息，消费者客户端命令，在kafka的根目录下使用命令

    ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic1 --from-beginning
    --from-beginning表示从最开始接受消息
   消费消息如下：
      
    hello,kafka
    this is kafka
    
  ### 7、删除topic，在kafka的根目录下使用命令

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
    rmr 命令已经被废弃了，可以执行 deleteall 命令

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

    -producer
    -consumer
    -broker
    -分区
    -副本

## 对比







# 常见
