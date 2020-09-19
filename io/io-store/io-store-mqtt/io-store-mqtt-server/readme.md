
###选型对比

[MQTT Broker 比较与选型——开源与商业服务器/服务对比](https://wivwiv.com/post/best-mqtt-broker/)

[2020 年常见MQTT 客户端工具比较](https://www.emqx.io/cn/blog/mqtt-client-tools)

###mqtt broker 集群设计要点
开箱即用的mqtt broker都没有实现集群功能，原因就是实现集群需要借助额外的基础设施，如redis或者其他数据库配合。
要实现支持集群功能的mqtt broker也不难，以redis为例：
1. 在mqtt connect 成功阶段把clientId和broker 的节点信息存储在redis中
2. 在subscribe 阶段把clientId和他的topic tree 也存储在redis中
3. 在publish阶段从redis中找出 match 的 topic 所包含的clientId，把消息发出去就行
4. 在disconnect阶段，把redis里面的相关信息清除掉。

###开源实现

- Java 实现

[hivemq-ce](https://github.com/hivemq/hivemq-community-edition)

[Cicizz/jmqtt](https://github.com/Cicizz/jmqtt)

[Amazingwujun/mqttx](https://github.com/Amazingwujun/mqttx)

- C 实现

[eclipse/mosquitto](https://github.com/eclipse/mosquitto)

[hui6075/mosquitto-cluster](https://github.com/hui6075/mosquitto-cluster)

- Go 实现

[fhmq/hmq](https://github.com/fhmq/hmq)

###client工具
[mqttfx](http://mqttfx.jensd.de/index.php/download)
