Feature: Training Service Functionality

  Scenario: Create a new training
    Given the trainer with name "John" and surname "Doe" does exist in the system
    When a trainer with username "John.Doe" creates new training
    Then the training should be in the system

  Scenario: Create a new training with non-existing trainer
    Given a trainer with username "John.Doe" does not exists in the system
    Then training creation with the trainer "John.Doe" have to throw the error