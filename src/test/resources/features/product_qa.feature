Feature: Product QA Management

  Scenario: Add a new product QA
    Given a product with ID 1 exists
    When I add a new QA with question "How to use this product?" to the product
    Then the QA should be successfully added to the product

  Scenario: Get a product QA
    Given a product with ID 1 has a QA with ID 1
    When I request the QA with ID 1 for the product
    Then I should receive the correct QA details

  Scenario: Get all product QAs
    Given a product with ID 1 has multiple QAs
    When I request all QAs for the product
    Then I should receive a list of all QAs for the product

  Scenario: Update a product QA
    Given a product with ID 1 has a QA with ID 1
    When I update the QA with ID 1 with new question "Is this product durable?"
    Then the QA should be successfully updated

  Scenario: Answer a product QA
    Given a product with ID 1 has an unanswered QA with ID 1
    When I answer the QA with ID 1 with "Yes, it's very durable."
    Then the QA should be successfully answered

  Scenario: Delete a product QA
    Given a product with ID 1 has a QA with ID 1
    When I delete the QA with ID 1
    Then the QA should be successfully removed from the product