Feature: Math expression evaluation
  There is only so much science, as there is mathematics.

  Scenario Outline: Calculate_expression
    Given Enter expression evaluator page
    When Input the <expression> expression
    Then The result should be <result>

    Examples:
      | expression    | result |
      | "3 * 7"       | 21     |
      | "3 * (3 + 4)" | 21     |