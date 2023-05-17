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
    
  ### 赋予lrq账户管理员角色，要记得赋权限，否则登录会报401错误
    
    sudo rabbitmqctl set_user_tags swh  administrator



















# 常见

## B树与B+树
### B树
这里的B是Balance（平衡）的缩写。它是一种多路的平衡搜索树。
它跟普通的平衡二叉树的不同是，B树的每个节点可以存储多个数据，而且每个节点不止有两个子节点，
最多可以有上千个子节点。

B树中每个节点都存放着索引和数据，数据遍布整个树结构，搜索可能在非叶子节点结束，最好的情况是O(1)。
一般一棵B树的高度在3层左右，3层就可满足百万级别的数据量

### B+树
B+树是B树的一种变种,它与B树的区别是：
    * 叶子节点保存了完整的索引和数据，而非叶子节点只保存索引值，因此它的查询时间固定为 log(n).
    * 叶子节点中有指向下一个叶子节点的指针，叶子节点类似于一个单链表
    * 正因为叶子节点保存了完整的数据以及有指针作为连接，B+树可以增加了区间访问性，提高了范围查询，而B树的范围查询相对较差
    * B+树更适合外部存储。因为它的非叶子节点不存储数据，只保存索引。