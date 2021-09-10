# 2.x版本说明 
- springboot      2.5.4
- springCloud     Hoxton.SR9

### 项目库结构

使用tree命令显示项目结构

```
tree -d -L 2
```

```

├── common
├── db
│   ├── db-hbase
│   ├── db-jpa
│   ├── db-jpa-mysql
│   ├── db-jpa-oracle
│   └── db-mongo
├── io
│   ├── io-base
│   ├── io-core
│   ├── io-mq
│   └── io-store
│       ├── io-store-coap
│       ├── io-store-lwm2m
│       ├── io-store-modbus
│       ├── io-store-mqtt
│       ├── io-store-opc
│       ├── io-store-rxtx
│       ├── io-store-socket
│       └── io-store-websocket
├── mq
│   ├── mq-base
│   ├── mq-kafka
│   ├── mq-ons
│   ├── mq-rocketmq
│   └── mq-starter
├── rpc
│   ├── rpc-grpc
│   └── rpc-thrift
└── utils
    ├── utils-base
    ├── utils-disruptor
    ├── utils-dubbo
    ├── utils-excel
    ├── utils-fastdfs
    ├── utils-feign
    ├── utils-gps
    ├── utils-httpclient
    ├── utils-jetty
    ├── utils-redis
    ├── utils-rxtx
    └── utils-task

```