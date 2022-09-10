package advancedcalculator;

import java.util.Scanner;
import java.util.Stack;

public class AdvancedCalculator {

    public static String infixTopostfix(String expression) {
        String infixEx = expression;
        StringBuilder postfixEx = new StringBuilder();
        String subString;
        String delimiter = ",";
        String operator = "";
        Stack s = new Stack();
        String operators = "*-+/^";

        for (int i = 0; i <= infixEx.length() - 1; i++) {
            if ("(".equals(String.valueOf(infixEx.charAt(i)))) {
                s.push("(");
            } else if (")".equals(String.valueOf(infixEx.charAt(i)))) {
                while (!"(".equals(s.peek().toString())) {
                    postfixEx.append(s.pop().toString());
                }
                //removing the openning bracket.
                s.pop();
            } //if operator is found test it wether to push it to the stack or to append it to the postfix expression
            else if (operators.indexOf(infixEx.charAt(i)) > -1) {
                operator = String.valueOf(infixEx.charAt(i));
                if (!s.isEmpty() && findPrecedence(operator) <= findPrecedence(s.peek().toString())) {
                    while (!s.isEmpty() && findPrecedence(operator) <= findPrecedence(s.peek().toString())) {
                        postfixEx.append(s.pop().toString());
//                    postfixEx.append(",");
                    }
                } else {
//                    adding a separator to the values
//                    postfixEx.append(",");
                    int len = postfixEx.length();
                    char last = postfixEx.charAt(len - 1);
                    if (operators.indexOf(last) <= -1) {
                        postfixEx.append(",");
                    }
                }
//push the operator into the stack when its precedence is higher than that of the operator on the top of the stack
                s.push(operator);
            } else {
                postfixEx.append(String.valueOf(infixEx.charAt(i)));

            }
//appending all stack items when we rach the end of the infix expression
            int length = infixEx.length() - 1;
            if (i == infixEx.length() - 1) {
                while (!s.empty()) {
                    postfixEx.append(s.pop().toString());
                }
            }

        }
        return postfixEx.toString();

    }

    public static String evalPostfix(String expression) {

        String value = "";
        String operand1;
        String operand2;
        String operator1 = "";
        String answer = "";
        Boolean end = false;

        Stack s = new Stack();
        String s1 = expression;
        String operator = "*-+/^";
        for (int i = 0; i < s1.length(); i++) {
            if (operator.indexOf(s1.charAt(i)) > -1) {
                if (!value.isBlank() && end == false) {
                    s.push(value);
                    value = "";
                }
                operator1 = String.valueOf(s1.charAt(i));
                operand2 = (s.pop().toString());
                operand1 = (s.pop().toString());
                answer = calculate(operand1, operand2, operator1);
                s.push(answer);
            } else {
                if (",".equals(String.valueOf(s1.charAt(i)))) {
                    s.push(value);
                    value = "";
                    end = false;
                } else {
                    value = value + String.valueOf(s1.charAt(i));
                }

            }
//            string.append(s1.charAt(i));
        }

        return answer;
    }

    public static void main(String[] args) {
        String Expression = "";
        String postFix = "";
        String answer = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a formulae to solve");
        Expression = sc.next();
        postFix = infixTopostfix(Expression);
        System.out.println("PostFix: " + postFix);
        answer = evalPostfix(postFix);
        System.out.println("Answer in Main: " + answer);
    }

    public static int findPrecedence(String op) {
        switch (op) {
            case "^":
                return 3;
            case "*","/":
                return 2;
            case "-","+":
                return 1;
            default:
                break;
        }
        return 0;
    }

    public static String calculate(String op1, String op2, String operator) {
        Double value = 0.0;
        switch (operator) {
            case "*":
                value = Double.parseDouble(op2) * Double.parseDouble(op1);
                return Double.toString(value);
            case "+":
                value = Double.parseDouble(op1) + Double.parseDouble(op2);
                return Double.toString(value);
            case "/":
                value = Double.parseDouble(op1) / Double.parseDouble(op2);
                System.out.println("/ " + value);
                return Double.toString(value);
            case "-":
                value = Double.parseDouble(op1) - Double.parseDouble(op2);
                return Double.toString(value);
            default:
                break;
        }
        return Double.toString(0);
    }

}
