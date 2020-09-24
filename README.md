# CompanyQClient
An API to allow company objects to be pushed and popped

To run:
java -jar target/customer-q-client-0.1.0.jar

To add a company:
curl -i -X POST -d @cc.json -H "Content-Type: application/json" http://localhost:8080/queue/addCompany
where:
cc.json--> {"name":"GavWebCo2","description":"The final description", userName="Jim"}

To retrieve a company:
curl -i -X GET -H "Content-Type: application/json" http://localhost:8080/queueManager/getCompany?username="Jim"
