import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;
import java.util.List;



public class stackApp {

    //Static means can be used by the class in and out without object instance
    public static boolean parenthesized(String expr) {
        Scanner input = new Scanner(expr);
        ArrayList<String> mystack = new ArrayList<>();

        while(input.hasNext()) {
            String s = input.next();
            if (s.equals("(")) {
                mystack.add(s);
            }
            else if (s.equals(")")) {
                if (mystack.isEmpty()) return false;
                if (!mystack.removeLast().equals("(")) return false;
            }
            else if (mystack.isEmpty()) return false;
        }

        if(!mystack.isEmpty()) return false; else return true;
    }

    public static double evaluate(String expr) {
        Scanner input = new Scanner(expr);
        
        ArrayList<String> op = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();

        while(input.hasNext()) {
            String s = input.next();
            if (s.equals("("));
            else if (s.equals("+")) op.add("+");
            else if (s.equals("-")) op.add("-");
            else if (s.equals("*")) op.add("*");
            else if (s.equals("/")) op.add("/");
            else if (s.equals("^")) op.add("^");
            else if (s.equals("%")) op.add("%");
            else if (s.equals(")")) {
                Double v1 = values.removeLast();
                Double v2 = values.removeLast();
                String operator = op.removeLast();
                if(operator.equals("+")) values.add(v2 + v1);
                else if(operator.equals("-")) values.add(v2 - v1);
                else if(operator.equals("*")) values.add(v2 * v1);
                else if(operator.equals("/")) values.add(v2 / v1);
                else if(operator.equals("^")) values.add(Math.pow(v2, v1));
                else if(operator.equals("%")) values.add(v2 % v1);
            }
            else values.add(Double.parseDouble(s));
        }
        return values.removeLast();
    }

    public static String parseString(String input) {
        StringBuilder result = new StringBuilder();
        Stack<String> operators = new Stack<>();
        List<String> output = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                continue; // Skip whitespace
            }
            String token = String.valueOf(c);
            if (Character.isDigit(c) || (c == '.')) {
                currentNumber.append(c);
            } else {
                if (currentNumber.length() > 0) {
                    output.add(currentNumber.toString());
                    currentNumber.setLength(0); // Reset for next number
                }

                if ("*/+-^%".contains(token)) {
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                        if (output.size() < 2 ) {
                            return "ERROR";
                        }
                        String op = operators.pop();
                        String right = output.remove(output.size() - 1);
                        String left = output.remove(output.size() - 1);
                        output.add(" ( " + left + " " + op + " " + right + " ) ");
                    }
                    operators.push(token);
                } else if (token.equals("(")) {
                    operators.push(token);
                } else if (token.equals(")")) {
                    while (!operators.isEmpty() && !operators.peek().equals("(")) {
                        if (output.size() < 2 ) {
                            return "ERROR";
                        }
                        String op = operators.pop();
                        String right = output.remove(output.size() - 1);
                        String left = output.remove(output.size() - 1);
                        output.add(" ( " + left + " " + op + " " + right + " ) ");
                    }
                    operators.pop(); // Remove the '(' from the stack
                }
            }
        }

        // Add any remaining number at the end
        if (currentNumber.length() > 0) {
            output.add(currentNumber.toString());
        }

        // Process remaining operators in the stack
        while (!operators.isEmpty()) {
            if (output.size() < 2 ) {
                return "";
            }
            String op = operators.pop();
            String right = output.remove(output.size() - 1);
            String left = output.remove(output.size() - 1);
            output.add(" ( " + left + " " + op + " " + right + " ) ");
        }

        // Construct final result
        for (String s : output) {
            result.append(s).append(" ");
        }

        return result.toString().trim();
    }

    private static int precedence(String op) {
        switch (op) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
            case "%":
                return 2;
            case "^":
                return 3; // Power operator has the highest precedence
            default:
                return 0;
        }
    }

    public static String ArithmaticCalc(String expr) {
        String parsedExpr = parseString(expr);
        if (parenthesized(parsedExpr) && !parsedExpr.equals("")) {
            System.out.println("Cleaned expression: " + parsedExpr);
            return String.valueOf(evaluate(parsedExpr));
        }
        else {
            return "ERROR | PLEASE INPUT VALID EXPRESSION";
        }
    }


    
    public static void main(String[] args) {
        //Scanner Reads Elements By Spaces
        Scanner reader = new Scanner(System.in);

        System.out.println("         o                       o     o       o                  __o__                                      \n" +"        <|>                    _<|>_  <|>     <|>                   |                                        \n" +"        / \\                          < >     / >                  / \\                                       \n" +"      o/   \\o       \\o__ __o     o     |      \\o__ __o             \\o/      o__ __o/   o      o     o__ __o/ \n" +"     <|__ __|>       |     |>   <|>    o__/_   |     v\\             |      /v     |   <|>    <|>   /v     |  \n" +"     /       \\      / \\   < >   / \\    |      / \\     <\\           < >    />     / \\  < >    < >  />     / \\ \n" +"   o/         \\o    \\o/         \\o/    |      \\o/     o/   \\        |     \\      \\o/   \\o    o/   \\      \\o/ \n" +"  /v           v\\    |           |     o       |     <|     o       o      o      |     v\\  /v     o      |  \n" +" />             <\\  / \\         / \\    <\\__   / \\    / \\    <\\__ __/>      <\\__  / \\     <\\/>      <\\__  / \\ \n");
        System.out.println("☆ Supports any arithmetic expression (+-*/^%) ☆ \n");
        while (true) {
            System.out.println("ׂ╰┈➤ Enter equation or type \"Exit\"");
            String expr = reader.nextLine();
            if (expr.equals("Exit")) {
                System.out.println("exiting...");
                break;
            }
            System.out.println("✎ Evaluates to " + ArithmaticCalc(expr) + "\n\n");
        }
    }

}
