Feature: Microservice Integration

  Scenario: Sending a message from the main application to the microservice
    When I send a message to the microservice
    Then the microservice should receive the message and trainer's data needs to be updated
