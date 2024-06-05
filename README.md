# bitcoin-service

This project is for creating bitcoin service and it fetches the bitcoin values from Coindesk APIs.
This project also contains optimized Dockerfile.

Steps to run the project on local:
1. Import the project into your favorite IDE and run it. The project will run on port 8080

Links:
Swagger Documentation: http://localhost:8080/swagger-ui/index.html

Flowchart can be found at the root of this project in .pptx file

You can use the above swagger UI link to execute the below APIs:

History API
-----------
Example Request:
{
    "start": "2024-05-04",
    "end": "2024-06-03",
    "outputCurrency": "INR"
}

Currencies API
--------------
http://localhost:8080/api/v1/bitcoin/currencies
