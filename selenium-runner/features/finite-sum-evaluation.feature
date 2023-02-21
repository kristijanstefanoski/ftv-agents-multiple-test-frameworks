Feature: Finite geometrical sum
  Sum calculation.

  Scenario Outline: Calculate_finite_sum
    Given Enter sum expression evaluator page
    When Input the values of first <first>, ratio <ratio> and count <count>
    Then The sum result should be <result>

    Examples:
      | first | ratio | count | result |
      | "12"  | "0.5" | "3"   | 21     |
      | "4"   | "0.5" | "3"   | 7      |