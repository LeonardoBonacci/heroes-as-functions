# heroes-as-functions

```
docker run -d -p 6650:6650 -p 8080:8080 -v $PWD/data:/pulsar/data --name pulsar apachepulsar/pulsar-standalone
```

```
./bin/pulsar-admin tenants create manning
./bin/pulsar-admin tenants list

./bin/pulsar-admin namespaces create manning/chapter03  
./bin/pulsar-admin namespaces list manning

./bin/pulsar-admin topics create persistent://manning/chapter03/example-topic
./bin/pulsar-admin topics list manning/chapter03

./bin/pulsar-client consume persistent://manning/chapter03/example-topic --num-messages 0 --subscription-name example-sub --subscription-type Exclusive
./bin/pulsar-client produce persistent://manning/chapter03/example-topic --num-produce 2 --messages "Hello Pulsar"   
```

```
./bin/pulsar-admin functions create \
 --jar /Users/jeffreyvanhelden/eclipse-workspace/heroes-as-functions/echofunction/target/chapter4-1.0.0.nar \
 --function-config-file /Users/jeffreyvanhelden/eclipse-workspace/heroes-as-functions/echofunction/src/main/resources/function-config.yaml

./bin/pulsar-admin functions list
./bin/pulsar-admin functions getstatus --name keyword-filter
./bin/pulsar-admin functions get --name keyword-filter

./bin/pulsar-client consume persistent://public/default/filtered-feed --num-messages 0 --subscription-name example-sub --subscription-type Exclusive
./bin/pulsar-client consume persistent://public/default/keyword-filter-log --num-messages 0 --subscription-name example-sub --subscription-type Exclusive
./bin/pulsar-client produce persistent://public/default/raw-feed --num-produce 2 --messages "Hello Pulsar"   
```

```
docker run --name pulsar -id -p 6650:6650 -p 8080:8080 -v /Users/jeffreyvanhelden/eclipse-workspace/heroes-as-functions/fileconnector:/pulsar/dropbox apachepulsar/pulsar-standalone

docker exec -it pulsar mkdir -p /tmp/input
docker exec -it pulsar chmod a+w /tmp/input
docker exec -it pulsar mkdir -p /tmp/processed   
docker exec -it pulsar chmod a+w /tmp/processed
docker exec -it pulsar cp /pulsar/dropbox/src/test/resources/example-1.txt /tmp/input

docker exec -it pulsar /pulsar/bin/pulsar-admin source create --archive /pulsar/dropbox/target/chapter5-0.0.1.nar --source-config-file /pulsar/dropbox/src/main/resources/config.yml

"Created successfully"                                                 ❺

docker exec -it pulsar /pulsar/bin/pulsar-admin source list            ❻
[
  "directory-source"
]
```


```
export GIT_PROJECT=/Users/jeffreyvanhelden/eclipse-workspace/pulsar-in-action/chapter5
docker run --name pulsar -id -p 6650:6650 -p 8080:8080 -v $GIT_PROJECT:/pulsar/dropbox apachepulsar/pulsar-standalone

docker exec -it pulsar cp /pulsar/dropbox/src/test/resources/example-1.txt /tmp/input

docker exec -it pulsar /pulsar/bin/pulsar-admin source create \
 --archive /pulsar/dropbox/target/chapter5-1.nar \
 --source-config-file /pulsar/dropbox/src/main/resources/config.yml

docker exec -it pulsar /pulsar/bin/pulsar-admin source list
```


```

docker run -d -p 6650:6650 -p 8080:8080 -v $PWD/data:/pulsar/data --name pulsar apachepulsar/pulsar-standalone
docker run --name pulsar -id -p 6650:6650 -p 8080:8080 -v $GIT_PROJECT:/pulsar/dropbox apachepulsar/pulsar-standalone

docker cp netty-source-config.yaml pulsar:/pulsar/conf/

docker exec -it pulsar /bin/bash

docker exec -it pulsar /bin/bash
curl -O http://mirror-hk.koddos.net/apache/pulsar/pulsar-2.10.1/connectors/pulsar-io-netty-2.10.1.nar

docker exec -it pulsar /pulsar/bin/pulsar-admin source create \
 --archive /pulsar/dropbox/target/chapter5-2.nar \
 --source-config-file /pulsar/conf/netty-source-config.yaml


 docker exec -it pulsar /pulsar/bin/pulsar-admin source create --archive /pulsar/dropbox/target/chapter5-0.0.1.nar --source-config-file /pulsar/dropbox/src/main/resources/config.yml


./bin/pulsar-admin sources create \
 --archive pulsar-io-netty-2.10.1.nar \
 --tenant public \
 --namespace default \
 --name netty \
 --destination-topic-name netty-topic \
 --source-config-file conf/netty-source-config.yaml \
 --parallelism 1

```


```

docker pull apachepulsar/pulsar-standalone:2.8.3

docker run -d -it -p 6650:6650 -p 8080:8080 -v $PWD/data:/pulsar/data --name pulsar-netty-standalone apachepulsar/pulsar-standalone:2.8.3

docker cp netty-source-config.yaml pulsar-netty-standalone:/pulsar/conf/


docker exec -it pulsar-netty-standalone /bin/bash
curl -O http://mirror-hk.koddos.net/apache/pulsar/pulsar-2.10.1/connectors/pulsar-io-netty-2.10.1.nar

```

```
docker run -d -it -p 6650:6650 -p 8080:8080 -v $PWD/data:/pulsar/data --name pulsar-netty-standalone apachepulsar/pulsar:2.10.1 bin/pulsar standalone

docker cp netty-source-config.yaml pulsar-netty-standalone:/pulsar/conf/

docker exec -it pulsar-netty-standalone /bin/bash
curl -O http://mirror-hk.koddos.net/apache/pulsar/pulsar-2.10.1/connectors/pulsar-io-netty-2.10.1.nar

docker exec -it pulsar-netty-standalone /bin/bash

./bin/pulsar-admin sources localrun \
  --archive pulsar-io-netty-2.10.1.nar \
  --tenant public \
  --namespace default \
  --name netty \
  --destination-topic-name netty-topic \
  --source-config-file conf/netty-source-config.yaml \
  --parallelism 1


```

```
docker cp chapter5-3.nar pulsar-netty-standalone:/pulsar
docker cp netty2-source-config.yaml pulsar-netty-standalone:/pulsar/conf/

docker exec -it pulsar-netty-standalone /bin/bash

./bin/pulsar-admin sources localrun \
  --archive chapter5-3.nar \
  --tenant public \
  --namespace default \
  --name netty2 \
  --destination-topic-name netty-topic \
  --source-config-file conf/netty2-source-config.yaml \
  --parallelism 1
```

```
docker cp vertx-source-config.yaml pulsar-netty-standalone:/pulsar/conf/
docker cp pulsar-io-vertx-2.6.2.nar pulsar-netty-standalone:/pulsar

docker exec -it pulsar-netty-standalone /bin/bash

./bin/pulsar-admin sources localrun \
  --archive pulsar-io-vertx.jar \
  --tenant public \
  --namespace default \
  --name vertx \
  --destination-topic-name vertx-topic \
  --source-config-file conf/vertx-source-config.yaml \
  --parallelism 1
```

```
docker cp spring-source-config.yaml pulsar-netty-standalone:/pulsar/conf/
docker cp pulsar-io-quarkus-2.6.2.nar pulsar-netty-standalone:/pulsar

docker exec -it pulsar-netty-standalone /bin/bash

./bin/pulsar-admin sources localrun \
  --archive pulsar-io-quarkus-2.6.2.nar \
  --tenant public \
  --namespace default \
  --name spring \
  --destination-topic-name spring-topic \
  --source-config-file conf/spring-source-config.yaml \
  --parallelism 1

```