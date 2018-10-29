###  DEVELOP LOG
 
 
##### CONCLUSION

Awesome collection contains the  utils or thoughts which my personal most commonly used during coding, concluding about:

* jedis
* rabbitmq
* elasticsearch
 




##### ATTENTION  

* SYSTEM : Run the correct mainapp in defrrent dir.

* ELASTIC : Not neessory for configuring any elsatic args when running a local es , because of springboot own es ,whoes data stage in /data dir.  

* 


##### MIDDLEWARE RUN.

* Install jar into mvn mannually : mvn install:install-file -Dfile=./extra/myJar/fastdfs-client-java-1.27-RELEASE.jar -DgroupId=org.csource  -DartifactId=fastdfs-client-java -Dversion=fastdfs-client-java -Dpackaging=jar

* Logstash start : cd /usr/share/logstash &&  bin/logstash -e 'input{tcp{port=>4560 codec=>json_lines}}output{elasticsearch{hosts=>["127.0.0.1:9200"]}stdout{codec=>rubydebug}}'

* Kibanna start : sudo -i service kibana start

* ElasticSearch start :  cd /usr/share/elasticsearch && ./elasticsearch

* Memcached start :  memcached -d -u root -m 512 127.0.0.1 -p 11211 

* FastDfs :
  /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
  /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf restart
  /usr/bin/fdfs_storaged /etc/fdfs/storage.conf
  /usr/bin/fdfs_storaged /etc/fdfs/storage.conf restart

##### WIKI





##### EXTRA
Contact By Email : forgeekscn@gmail.com


#### LINKIN
++++++++++++  rabbit-mq   +++++++++++

stop restart  => rabbitmq-server –detached   /  rabbitmqctl stop

check status => rabbitmqctl status  / rabbitmqctl list_queues  / rabbitmqctl list_exchanges /
                rabbitmqctl list_bindings /rabbitmqctl list_connections / rabbitmqctl  list_channels

clear queues => rabbitmqctl reset

add user => sudo rabbitmqctl  add_user  hechao hechao

add permission => sudo rabbitmqctl  set_user_tags  hechao administrator

config file location => /etc/rabbitmq/rabbitmq.conf  /etc/rabbitmq/rabbitmq-env.conf

port => app:5672 web:15672

user-pwd localhost => hechao/hechao

config plugins => rabbitmq-plugins enable rabbitmq_management

usual config:

env.conf =>

RABBITMQ_NODE_IP_ADDRESS= //IP地址，空串bind所有地址，指定地址bind指定网络接口

RABBITMQ_NODE_PORT=       //TCP端口号，默认是5672

RABBITMQ_NODENAME=        //节点名称。默认是rabbit

RABBITMQ_CONFIG_FILE= //配置文件路径 ，即rabbitmq.config文件路径

RABBITMQ_MNESIA_BASE=     //mnesia所在路径

RABBITMQ_LOG_BASE=        //日志所在路径

RABBITMQ_PLUGINS_DIR=     //插件所在路径

rabbitmq.config =>

tcp_listerners    #设置rabbimq的监听端口，默认为[5672]。

disk_free_limit     #磁盘低水位线，若磁盘容量低于指定值则停止接收数据，默认值为{mem_relative, 1.0},即与内存相关联1：1，也可定制为多少byte.

vm_memory_high_watermark    #设置内存低水位线，若低于该水位线，则开启流控机制，默认值是0.4，即内存总量的40%。

hipe_compile     #将部分rabbimq代码用High Performance Erlang compiler编译，可提升性能，该参数是实验性，若出现erlang vm segfaults，应关掉。

force_fine_statistics    #该参数属于rabbimq_management，若为true则进行精细化的统计，但会影响性能。

frame_max     #包大小，若包小则低延迟，若包则高吞吐，默认是131072=128K。

heartbeat     #客户端与服务端心跳间隔，设置为0则关闭心跳，默认是600秒。

+++++++++++  mysql ++++++++++++

check is running => sudo netstat -tap | grep mysql

base operate => sudo service mysql restart/start/stop

ubuntu change pwd => sudo vim /etc/mysql/debian.cnf

local pwd => root/root


+++++++++   myth  +++++++++++++++++

test swager page => http://localhost:8884/swagger-ui.html#!/order-controller/orderPayUsingPOST

rabbit mq admin page => http://localhost:15672/#/

admin page => http://localhost:8888/myth-admin/index.html
         