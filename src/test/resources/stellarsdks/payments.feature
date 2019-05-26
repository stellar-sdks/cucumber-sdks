Feature: Accounts Paying Accounts
  The ability to send tokens from one account to another.

  Scenario: Alice pays Bob in Lumens
    Given account Alice with balance of 100 XLM
    And account Bob with balance of 50 XLM
    When account Alice pays account Bob 25 XLM
    Then account Alice must have balance 74.99999 XLM
    And account Bob must have balance 75 XLM