# Parking App

This application using simple Java / Maven / Spring Boot (version 3.2.2). It can be used to register and de-register to Parking of Vehicle based on there licence number. 
And also administrator can also upload list of vehicle they monitored with monitoring vehicle.

## Technology used
- Java - 17
- SpringBoot - 3.2.2
- Maven - 3.9.5
- Embedded Tomcat 10.x 

## Steps To Poject Setup

* Clone this repository - https://github.com/RahulShri25/TheParkingApp.git
* Make sure you are using JDK 17 and Maven 3.9.x
* You can build the project and run the tests by running ```mvn clean package
* Once successfully built, you can run the service by one of these two methods:

### API's Listing
### Registering vehicle using licence number.

POST /api/registercar
Accept: application/json
Content-Type: application/json

Request
{
    "licenceNumber": "abc107",
    "streetName": "Java"
}

RESPONSE: HTTP 201 (Created)
{
    "id": 302,
    "licenceNumber": "abc107",
    "streetName": "Java",
    "arrivalTime": "2024-02-19T14:41:02.68666",
    "departureTime": null,
    "currentStatus": "Parked"
}


### De-Registering vehicle using licence number

POST /api/registercar
Accept: application/json
Content-Type: application/json

Request
{
	"licenceNumber":"abc107"
}

RESPONSE: HTTP 200 (OK)
{
    "message": "You have successfully De-Registered your vehicle. Total Time : 2101 minute.And Total Cost : 315.15  in EUR."

}


### Load List of vehicle during monitoring

POST /api/addParkingObservdetail
Accept: application/json
Content-Type: application/json

Request

[
        {
            "licenceNumber":"abc107",
            "streetName":"Jakarta",
            "recordingDate":"2023-11-23T11:59:11.332Z"
        },
        {
            "licenceNumber":"abc108",
            "streetName":"Java",
            "recordingDate":"2023-11-23T11:59:11.332Z"
        }
 ]

RESPONSE: HTTP 200 (OK)
[
    {
        "id": 52,
        "licenceNumber": "abc107",
        "streetName": "Jakarta",
        "recordingDate": "2023-11-23T11:59:11.332"
    },
    {
        "id": 53,
        "licenceNumber": "abc108",
        "streetName": "Java",
        "recordingDate": "2023-11-23T11:59:11.332"
    }
]


