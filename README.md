###Clone & run using docker-compose inside the projects:

####To run the Docker (If not running)
    ~>$ open -a Docker

####Then goto specific project folder and run following cmd:
    ~>$ mvn clean package -DskipTests

    ~>$ docker-compose up -d --build

####To Check all container running properly
    ~>$ docker container ls -la

####To Stop Docker
    ~>$ killall Docker

####[H2-DB Console](http://localhost:8080/h2-console)
    
    username:sa
    pass:sa