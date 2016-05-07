tag=vertx/vertx-helloworld

host=$(docker-machine ip)
port=8080

function print_msg {
    delimiter='======================================================================================'
    echo $delimiter
    echo $1
    echo $delimiter
}

print_msg 'Building fat jar Vertx application artifact ...'
mvn clean package

print_msg 'Building Docker image ...'
docker build -t $tag .

print_msg "Running Docker container: service will be available at http://$host:8080 ..."
docker run -t -i -p $port:$port $tag