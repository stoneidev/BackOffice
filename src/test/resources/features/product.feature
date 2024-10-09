Feature: Product Management

  Scenario: Add a new product
    Given a product with id 1 and name "Sample Product"
    When the product is added
    Then the product should be retrievable by id 1