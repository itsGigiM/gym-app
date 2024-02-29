# Questions for the mentor

1. In the service layer, do I need to generate the ID or should it be passed by the client?


2. In applitaction.properties file I have following field:
```
"password.length=10"
```
TraineeService and TrainerService can access this field by:
```
@Value("${password.length}")
private int passwordLength;
```

When I run TaskSpringApplication.java file it works successfully,
but it throws
> "java.lang.IllegalArgumentException: Password n must be greater than zero."

while testing TraineeService and TrainerService.

I assume that the test directory cannot access this field. How can I access the application.properties file from the test directory?