# ubuntu安装OpenJDK8
 在开始安装之前，可以使用  
 
    ```java -version```
     
 命令检查是否安装过JDK，如果返回如下信息，则该系统中大概率没有安装JDK：  
    
    Command 'java' not found, but can be installed with:
    sudo apt install default-jre              # version 2:1.11-72, or
    sudo apt install openjdk-11-jre-headless  # version 11.0.13+8-0ubuntu1~20.04
    sudo apt install openjdk-13-jre-headless  # version 13.0.7+5-0ubuntu1~20.04
    sudo apt install openjdk-16-jre-headless  # version 16.0.1+9-1~20.04
    sudo apt install openjdk-17-jre-headless  # version 17.0.1+12-1~20.04
    sudo apt install openjdk-8-jre-headless   # version 8u312-b07-0ubuntu1~20.04
 
 根据提示安装openjdk即可。
 
    ```apt install openjdk-8-jre-headless```
 
 安装完成查看版本。
 
     ```java -version```