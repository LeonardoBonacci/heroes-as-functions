# Commands

```
docker cp spring-source-config.yaml pulsar-netty-standalone:/pulsar/conf/
docker cp target/pulsar-io-spring-2.6.2.nar pulsar-netty-standalone:/pulsar

docker exec -it pulsar-netty-standalone /bin/bash
./bin/pulsar-admin sources localrun \
  --archive pulsar-io-spring-2.6.2.nar \
  --tenant public \
  --namespace default \
  --name spring \
  --destination-topic-name spring-topic \
  --source-config-file conf/spring-source-config.yaml \
  --parallelism 1

docker exec -it pulsar-netty-standalone /bin/bash
./bin/pulsar-client consume -t Exclusive -s netty-sub spring-topic -n 0  

docker exec -it pulsar-netty-standalone /bin/bash
curl localhost:8888
curl localhost:8888/foos/foo

```