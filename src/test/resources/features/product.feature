Feature: Product Management

  Scenario: Add a new product
    Given a product with name "New Product"
    When I add the product
    Then the product should be saved successfully