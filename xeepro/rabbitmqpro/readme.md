# rabbitmq下載安裝

 ## erlang
 
 ### erlang环境
    
    https://www.erlang.org/

 ### 添加erlang源
 
    echo "deb https://packages.erlang-solutions.com/ubuntu bionic contrib" | sudo tee /etc/apt/sources.list.d/erlang-solution.list
 
 ### 添加密钥，使拥有拉取权限
  
    wget -O- https://packages.erlang-solutions.com/ubuntu/erlang_solutions.asc | sudo apt-key add -
 
 ### 查看版本
  
    apt-cache madison erlang

 ### 安装erlang，-y表示确认
    
    sudo apt-get install erlang=1:25.3.2-1 -y

 ### 通过查看erlang信息来确认安装结果
 
    erl 或者erl -version
    
 ## RabbitMQ

 ### RabbitMQ下載地址
 
    RabbitMQ官网：https://www.rabbitmq.com/

 ### 添加RabbitMQ源
    
    curl -s https://packagecloud.io/install/repositories/rabbitmq/rabbitmq-server/script.deb.sh | sudo bash
 
 ### 查看源中版本
 
    apt-cache madison rabbitmq-server

 ###  安装指定版本的rabbitmq

    sudo apt-get install rabbitmq-server=3.11.15-1

 ### 查看安装结果

    service rabbitmq-server status

 ## 基本命令
  ### 以应用方式
    sudo rabbitmq-server         # 启动
    sudo rabbitmqctl stop       # 停止
    sudo rabbitmqctl status     # 查看状态
 ### 以服务方式启动（安装完之后在任务管理器中服务一栏能看到RabbtiMq）
  启动：

      service rabbitmq-server start
  停止：
  
      service rabbitmq-server stop
  重启：
  
      service rabbitmq-server restart

  ### 启用可视化插件

    sudo rabbitmq-plugins enable rabbitmq_management   

  ### 重启生效
    
    sudo service rabbitmq-server restart    

   RabbitMQ提供了一个可视化操作MQ的web系统，在这里面你可以很方便的对MQ进行管理和查看消息；不过安装完成后需要手动开启。
   启动后，可以通过ip地址加端口号访问MQ的可视化平台，默认访问端口为15672；

   注意，rabbitmq_management可视化平台启用后，它会自动创建一个默认账户/密码为guest/guest的账户，
   但是它只能在当前系统登录，外部无法访问；为了系统安全建议将此账户删除，然后重新创建一个账户进行登录。

  ### 删除默认账户

    sudo rabbitmqctl delete_user  guest
    
  ### 创建普通账户

    sudo rabbitmqctl add_user swh swh123
    
  ### 赋予swh账户管理员角色，要记得赋权限，否则登录会报401错误
    
    sudo rabbitmqctl set_user_tags swh  administrator
    
    如果springboot連接時報錯，需要在管理控制台修改用戶權限
  

## 概念
 1. Message(消息):

  消息，就是我们需要传递和共享的信息，消息由一些列的可选属性组成，包括路由键，优先级，是否持久化等信息

 2. Publisher(生产者)

  消息的生产者，也是一个向交换机发布消息的客户端应用程序。

 3. Exchange(交换机):

  交换机，这是RabbitMQ中的一个非常重要的概念，在rabbitMq中，生产者产生的消息都不是直接发送到队列中去的，而是发送到了交换机中，
  交换机会通过一定的规则绑定队列，交换机会根据相应的路由规则发送给对服务器中的队列。

 4. Binding(绑定):

  绑定， 用于交换机和消息列队之间的关联。一个绑定就是基于路由键（routing-key）将交换机和消息队列连接起来的路由规则。
  所以可以将交换机理解成一个有绑定有成的路由表。

 5. Queue(队列):

  消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息可以投入一个或多个队列中。消息一直在对队列里边，
  等待消费者连接到这个队列将其消费。

 6. Connection(连接):

  每个producer（生产者）或者consumer（消费者）要通过RabbitMQ发送与消费消息，首先就要与RabbitMQ建立连接，这个连接就是Connection。
  Connection是一个TCP长连接。

 7. Channel(通道或者信道)

  信道，多路复用连接中的一条独立的双向数据流通道。信道是简历在真实的TCP连接内的虚拟连接。AMQP命令都是通过信道发出去的，
  不管是发布消息、订阅队列还是接收消息，这些动作都是通过信道完成的。因为对于操作系统过来说建立和销毁TCP都是非常昂贵的开销，
  所以引入了信道的概念，以复用一条TCP连接。

 8. Consumer(消费者)

  消息的消费者，表示一个从消息队列中取得消息的客户端应用。

 9. Virtual Host(虚拟主机)

  虚拟主机，标识一批交换机、消息队列和相关对象。 虚拟主机是相同的身份认证和加密环境的独立服务器域。 每个vhost本质就是一个mini版
  的rabbitMQ服务器，拥有自己的队列，交换机，绑定和权限机制。vhost是AMQP概念的基础，必须在连接时指定，RabbitMQ的默认vhost是/.

 10. Broker(rabbitmq服务)

  标识消息队列服务器实体
  
 ### Exchange类型
 
  Exchange分发消息的时候根据类型的不同分发策略有所区别，目前常见的有四种类型：
  direct、fanout、topic、headers。 headers匹配AMQP消息的header而不是路由键，
  此外headers交换机和direct交换机完成一直但是性能差很多，几乎用不到了，所以直接看另外三种类型。
