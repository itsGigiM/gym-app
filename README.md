# Questions for the mentor

1. Should I use DTOs

2. Is the User abstract or not? Does Trainee and Training have to extend the User class, or should I use any inheritance strategy?

    ```
    //Trainee.java/Trainer.java
        @OneToOne(cascade = CascadeType.REMOVE)
        @JoinColumn(name = "user_id")
        private User user;
    ```

3. Do I need to implement postprocessor memory initialization as in the Spring part?

4. I am not using hibernate.cfg.xml because as I understood Spring does that for us and I just need to input properties in application.properties file. Is this correct approach?