## 1.将代码clone或下载解压到/data目录下
```
git pull https://github.com/g1141385484-beep/openobserve_standalone_deployment_test.git
或者
wget https://github.com/g1141385484-beep/openobserve_standalone_deployment_test/archive/refs/heads/master.zip
```
## 2.使用docker-compose或者podman-compose启动项目
```
docker-compose -f docker-compose.yml up -d
```



## 3 openobserve登录地址和账密
web页地址：http://localhost:5080
账号密码： root@example.com/Complexpass#123

## 4.java-distributed-tracing-main下java服务的使用
cd 到对应服务目录下 使用./scripts/start.sh 启动。需要先启动discovery-service注册服务
如启动order-service
```
cd java-distributed-tracing-main/order-service
./scripts/start.sh
```

| 服务              | 端口   |
| --------------- | ---- |
| user-service    | 8081 |
| order-service   | 8082 |
| payment-service | 8083 |
| discovery-service| 8761 |

## 5.调用方式

```
#新增订单
curl -X POST http://localhost:8082/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "productName": "Laptop",
    "quantity": 1,
    "status": "PENDING",
    "totalAmount": 100
  }'

#获取用户列表
curl -X GET http://localhost:8081/api/users
#新建用户
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "zhangsan",
    "email": "123@qq.com",
    "phone": "12345678901"
  }'


```
  

  





