# Debezium


1. Download Mongo Connector

https://www.confluent.io/hub/mongodb/kafka-connect-mongodb

- Extract zip into plugins dir

debezium-demo > plugins >mongodb-kafka-connect-mongodb-1.13.0>(assets, lib, manifest.json)

And Mysql Connector



1. Check


curl http://localhost:8083/connector-plugins

Response:

> [{"class":"com.mongodb.kafka.connect.MongoSinkConnector","type":"sink","version":"1.13.0"},{"class":"com.mongodb.kafka.connect.MongoSourceConnector","type":"source","version":"1.13.0"},{"class":"io.debezium.connector.mysql.MySqlConnector","type":"source","version":"2.4.2.Final"},{"class":"org.apache.kafka.connect.mirror.MirrorCheckpointConnector","type":"source","version":"3.7.0"},{"class":"org.apache.kafka.connect.mirror.MirrorHeartbeatConnector","type":"source","version":"3.7.0"},{"class":"org.apache.kafka.connect.mirror.MirrorSourceConnector","type":"source","version":"3.7.0"}]

2. Up

        docker-compose up


3. Curl


      curl --location --request POST 'http://localhost:8083/connectors' \
      --header 'Content-Type: application/json' \
      --data @source.json

4. Check connector source status

        curl http://localhost:8083/connectors/{connector name}/status

Ex :

curl http://localhost:8083/connectors/source-mysql/status


5. (Optional) Delete

        curl -X DELETE http://localhost:8083/connectors/source-mysql


6. Validate


      curl -X PUT \
      -H "Content-Type: application/json" \
      -d '{
    "connector.class": "com.mongodb.kafka.connect.MongoSinkConnector",
    "database": "company-sync",
    "writemodel.strategy": "com.mongodb.kafka.connect.sink.writemodel.strategy.InsertOneDefaultStrategy",
    "tasks.max": "1",
    "topics": "db.sync.company.user",
    "name": "sink-mongo",
    "connection.uri": "mongodb://root:root@mongo:27017",
    "collection": "user",
    "change.data.capture.handler": "com.mongodb.kafka.connect.sink.cdc.debezium.rdbms.mysql.MysqlHandler"
      }' \
      http://localhost:8083/connector-plugins/com.mongodb.kafka.connect.MongoSinkConnector/config/validate
      


7. Curl


      curl --location --request POST 'http://localhost:8083/connectors' \
      --header 'Content-Type: application/json' \
      --data @sink.json



8. Show all connectors 
   
      curl http://localhost:8083/connectors




14. Insert new user

        mysql -uroot -p

        use company;

        INSERT INTO company.user (id, first_name, last_name) VALUES (101, 'Alice', 'Wonderland');

1. Create terminal to mongo

       mongosh -u root -p

1. Create collection


        use company-sync
        show dbs                        // to show the created databases
        show collections
        
    db.user.find();


Credits: 

https://sylhare.github.io/2022/11/07/Database-sync-with-debezium.html