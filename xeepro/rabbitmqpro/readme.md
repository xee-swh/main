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
