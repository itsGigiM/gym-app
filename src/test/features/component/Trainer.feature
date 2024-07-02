Feature: Trainer Service Functionality

  Scenario: Create a new trainer
    Given a trainer with username "John.Doe" does not exists in the system
    When the trainer with name "John" and surname "Doe" is created
    Then the trainer's name "John" should be retrievable with username "John.Doe"

  Scenario: Update an existing trainer
    Given the trainer with name "John" and surname "Doe" does exist in the system
    When the trainer's name is updated to "Jack"
    Then the trainer's name "Jack" should be retrievable with username "John.Doe"

  Scenario: Update a non-existing trainer
    Given a trainer with username "John.Doe" does not exists in the system
    Then an error should be thrown when the trainer "John.Doe" name is updated to "John Smith"

  Scenario: Retrieve a non-existing trainer
    Given a trainer with username "John.Doe" does not exists in the system
    Then the trainer should not be retrievable with username "John.Doe"

  Scenario: Retrieve a training list
    Given the trainer with name "John" and surname "Doe" does exist in the system
    Then trainer "John.Doe" training list must be empty

