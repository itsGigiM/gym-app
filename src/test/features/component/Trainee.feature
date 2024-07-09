Feature: Trainee Service Functionality

  Scenario: Create a new trainee
    Given a trainee with username "John.Doe" does not exists in the system
    When the trainee with name "John" and surname "Doe" is created
    Then the trainee's name "John" should be retrievable with username "John.Doe"

  Scenario: Update an existing trainee
    Given the trainee with name "John" and surname "Doe" does exist in the system
    When the trainee's name is updated to "Jack"
    Then the trainee's name "Jack" should be retrievable with username "John.Doe"

  Scenario: Delete an existing trainee
    Given the trainee with name "John" and surname "Doe" does exist in the system
    When the trainee with username "John.Doe" is deleted
    Then the trainee should not be retrievable with username "John.Doe"

  Scenario: Update a non-existing trainee
    Given a trainee with username "John.Doe" does not exists in the system
    Then an error should be thrown when the trainee "John.Doe" name is updated to "John Smith"

  Scenario: Delete a non-existing trainee
    Given a trainee with username "John.Doe" does not exists in the system
    Then an error should be thrown if delete of the trainee "John.Doe" is requested

  Scenario: Retrieve a non-existing trainee
    Given a trainee with username "John.Doe" does not exists in the system
    Then the trainee should not be retrievable with username "John.Doe"

