#Modbus总结

##1.概念
###Coil和Register
　　Modbus中定义的两种数据类型。Coil是位（bit）变量；Register是整型（Word，即16-bit）变量。
###Slave和Master与Server和Client
   
- Slave： 工业自动化用语；响应请求；
- Master：工业自动化用语；发送请求；
- Server：IT用语；响应请求；
- Client：IT用语；发送请求；
---
　　在Modbus中，Slave和Server意思相同，Master和Client意思相同。

##2.Modbus数据模型
　　Modbus中，数据可以分为两大类，分别为Coil和Register。
   每一种数据，根据读写方式的不同，又可细分为两种（只读，读写）
   
Modbus四种数据类型:

数据类型 | 字节类型 | 读写
:--- | :---: | :---:
Discretes Input　　 |　  位变量　    |     只读
Coils　　　　　　　　 |　  位变量　    |     读写
Input Registers　　 |　　16-bit整型  |　   只读
Holding Registers  |　  16-bit整型  |　   读写
---
　　通常，在Slave端中，定义四张表来实现四种数据。

##3.Modbus地址范围对应表
设备地址 　　 |　　Modbus地址　 　|        描述 　       |  功能 　|　R/W
:--- | :---: | :---: | :---: | :---:
1~10000 　　 |  address-1      |  Coils(Output)       |   0    |   R/W
10001~20000 |  address-10001  |  Discrete Inputs     |   01   |   R
30001~40000 |  address-30001  |  Input Registers     |   04   |   R
40001~50000 |  address-40001  |  Holding Registers   |   03   |   R/W
---

##4.Modbus变量地址
映射地址      |       Function Code     |    地址类型     |     R/W     |   描述
:--- | :--- | :--- | :--- | :---
0xxxx        |       01,05,15         |     Coil        |     R/W     |   -
1xxxx        |       02               |     离散输入     |     R       |   -
2xxxx        |       03,04,06,16      |     浮点寄存器   |     R/W     |   两个连续16-bit寄存器表示一个浮点数（IEEE754）
3xxxx        |       04               |     输入寄存器   |     R       |   每个寄存器表示一个16-bit无符号整数（0~65535）
4xxxx        |       03,06,16         |     保持寄存器   |     R/W     |   -
5xxxx        |       03,04,06,16      |     ASCII字符   |     R/W     |   每个寄存器表示两个ASCII字符
---
##5.限制

1. Modbus是在1970年末为可编程逻辑控制器通信开发的，这些有限的数据类型在那个时代是可以被PLC理解的，大型二进制对象数据是不支持的。
2. 对节点而言，没有一个标准的方法找到数据对象的描述信息，举个例子，确定一个寄存器数据是否表示一个介于30-175度之间的温度。
3. 由于Modbus是一个主/从协议，没有办法要求设备“报告异常”（构建在以太网的TCP/IP协议之上，被称为open-mbus除外）- 主节点必须循环的询问每个节点设备，并查找数据中的变化。在带宽可能比较宝贵的应用中，这种方式在应用中消耗带宽和网络时间，例如在低速率的无线链路上。
4. Modbus在一个数据链路上只能处理247个地址，这种情况限制了可以连接到主控站点的设备数量（再一次指出以太网TCP/IP除外）
5. Modbus传输在远端通讯设备之间缓冲数据的方式进行，有对通信一定是连续的限制，避免了传输中的缓冲区漏洞的问题
6. Modbus协议自身提供针对未经授权的命令或截取数据没有安全性


##6.Modbus4j

<https://github.com/infiniteautomation/modbus4j>

<https://infiniteautomation.com/modbus4j-open-source-modbus-library>