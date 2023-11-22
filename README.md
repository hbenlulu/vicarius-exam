
## Machine Setup

### Installing Java
[From Oracle website](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-macos.html#GUID-F575EB4A-70D3-4AB4-A20E-DBE95171AB5F)


### Installing Elasticsearch (macos)
[From ES website](https://www.elastic.co/guide/en/elasticsearch/reference/8.7/targz.html)

Run the following commands on your machine:
* curl -O https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.7.1-darwin-x86_64.tar.gz
* curl https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.7.1-darwin-x86_64.tar.gz.sha512 | shasum -a 512 -c -
* tar -xzf elasticsearch-8.7.1-darwin-x86_64.tar.gz
* cd ./elasticsearch-8.7.1
* vi config/elasticsearch.yml : **update config "xpack.security.enabled: false"**
* ./bin/elasticsearch : **run elasticsearch server**


### Using the API
* creating an index: POST "/index/create?name=<index-name>"
* deleting an index: DELETE "/index/delete?name=<index-name>"
* indexing a document (log-trace object): POST "/doc/log-trace" with request body
``` 
{
  "message": "invalid user login",
  "clientId": "Intel",
  "deviceType": "laptop",
  "os": "macos"
}
```
* getting a document with id: GET "/doc/log-trace/<id>"
* getting all document (with pagination): GET "/doc/log-trace"


### Building
You will need maven 3.9.5 installed. If you want to run tests with test-containers you will need also Docker engine to be installed.
* building with tests: mvn clean install
* building without tests: mvn clean install -DskipTests


### Running
* **Verify the elasticsearch server is running**
* From source folder run the following command: "java -jar ./target/exam-0.0.1-SNAPSHOT.jar"

