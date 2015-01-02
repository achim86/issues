## Steps to reproduce

### 1. Download and run plain Apache Karaf Container version 2.4.0

### 2. Install demo components

`features:chooseurl camel 2.14.1`

`features:chooseurl activemq 5.10.0`

`features:install camel-blueprint`

`features:install camel-jms`

`features:install camel-stream`

**`features:install activemq-blueprint`**

**`features:install activemq-web-console`**

`install -s mvn:de.afinke/activemq-playground-broker/1.0.0-SNAPSHOT`

`install -s mvn:de.afinke/activemq-playground-connection/1.0.0-SNAPSHOT`

`install -s mvn:de.afinke/activemq-playground-route/1.0.0-SNAPSHOT`

### 3. Run CreateMessagesTest which will send 10 messages to the TestQueue

Messages should be forwarded to your console by the ReadMessagesRoute.

### 4. Shutdown karaf container and delete data folder to reinstall all features in the following order

`features:chooseurl camel 2.14.1`

`features:chooseurl activemq 5.10.0`

`features:install camel-blueprint`

`features:install camel-jms`

`features:install camel-stream`

**`features:install activemq-web-console`**

**`features:install activemq-blueprint`**

`install -s mvn:de.afinke/activemq-playground-broker/1.0.0-SNAPSHOT`

`install -s mvn:de.afinke/activemq-playground-connection/1.0.0-SNAPSHOT`

`install -s mvn:de.afinke/activemq-playground-route/1.0.0-SNAPSHOT`

### 5. Run CreateMessagesTest which will result in an exception in the Karaf container

`2015-01-02 10:57:19,000 | ERROR | sumer[TestQueue] | faultJmsMessageListenerContainer | ?                                   ? | 71 - org.apache.servicemix.bundles.spring-jms - 3.2.9.RELEASE_1 | Could not refresh JMS Connection for destination 'TestQueue' - retrying in 5000 ms. Cause: org.apache.activemq.pool.PooledConnectionFactory cannot be cast to javax.jms.ConnectionFactory`

JUnit Test is still green as its just used as an executor here.

## Observations so far

- The activemq-web-console wab embedds geronimo-jms_1.1_spec-1.1.1 (javax.jms) which may be the reason for the classcasting issue as geronimo-jms_1.1_spec-1.1.1 bundle is already installed in karaf (part of activemq feature) so there are two versions of javax.jms. `Embedded-Artifacts: WEB-INF/lib/geronimo-jms_1.1_spec-1.1.1.jar;g="org.apache.geronimo.specs";a="geronimo-jms_1.1_spec";v="1.1.1"`
- Many `org.apache.activmq.*` packages are exported by activemq-web-console. Seems to be unnecessary for me. For example org.apache.activemq.pool.
`karaf@root> exports | grep org.apache.activemq.pool
   103 org.apache.activemq.pool; version=5.9.0
   110 org.apache.activemq.pool; version=5.9.0` (where bundle id 103 is activemq-web-console and bundle id 110 activemq-osgi)