Feature: Product Input Port Operations

  Scenario: Add a new product
    Given a product with name "New Product"
    When I add the product
    Then the product should be saved successfully

  Scenario: Get all products
    Given there are existing products in the system
    When I request all products
    Then I should receive a list of all products

  Scenario: Get product by ID
    Given a product with ID 1 exists in the system
    When I request the product with ID 1
    Then I should receive the correct product details

  Scenario: Get product by non-existent ID
    When I request the product with ID 999
    Then I should receive no product

  Scenario: Update an existing product
    Given a product with ID 1 exists in the system
    When I update the product name to "Updated Product"
    Then the product should be updated successfully

  Scenario: Update a non-existent product
    Given a product with ID 999 does not exist in the system
    When I try to update the non-existent product
    Then no update should occur

  Scenario: Delete an existing product
    Given a product with ID 1 exists in the system
    When I delete the product with ID 1
    Then the product should be removed from the system
    Then the product with ID 1 should not exist in the system

  Scenario: Delete a non-existent product
    Given a product with ID 999 does not exist in the system
    When I try to delete the non-existent product
    Then no deletion should occur