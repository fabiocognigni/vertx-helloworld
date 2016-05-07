### Prerequisites

* [(docker-machine and) docker](https://docs.docker.com/engine/installation/)
* Java 8+
* Maven

### Usage

* `docker-machine start`
* `git clone https://github.com/fabiocognigni/vertx-helloworld.git`
* `cd vertx-helloworld`
* `./build-run-container.sh`
* `curl -i http://$(docker-machine ip):8080`


