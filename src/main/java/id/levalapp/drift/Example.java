package id.levalapp.drift;

public class Example {
    public static void main(String[] args) {
        String finalResult = new Drift("Hello", 42, true, 3.14, 'A', "World")
            .shift(
                params -> {
                    Object param1 = params[0];
                    Object param2 = params[1];
                    Object param3 = params[2];
                    Object param4 = params[3];
                    Object param5 = params[4];
                    Object param6 = params[5];
                    System.out.println("Task 1 received: " + param1 + ", " + param2 + ", " + param3 + ", " + param4 + ", " + param5 + ", " + param6);
                    return param1 + "-" + param2 + "-" + param3;
                },
                params -> {
                    Object param1 = params[0];
                    Object param2 = params[1];
                    Object param3 = params[2];
                    Object param4 = params[3];
                    Object param5 = params[4];
                    Object param6 = params[5];
                    System.out.println("Task 2 received: " + param1 + ", " + param2 + ", " + param3 + ", " + param4 + ", " + param5 + ", " + param6);
                    return param6 + param5.toString() + param4.toString();
                }
            )
            .shift(
                params -> {
                    Object result1 = params[0];
                    Object result2 = params[1];
                    System.out.println("Task 3 received: " + result1 + ", " + result2);
                    return result1 + "_processed";
                },
                params -> {
                    Object result1 = params[0];
                    Object result2 = params[1];
                    System.out.println("Task 4 received: " + result1 + ", " + result2);
                    return result2.toString().length();
                }
            )
            .swift(params -> {
                Object finalResult1 = params[0];
                Object finalResult2 = params[1];
                System.out.println("Final Task received: " + finalResult1 + ", " + finalResult2);
                return "Final Output: " + finalResult1 + ", Length: " + finalResult2;
            });

        System.out.println("Final Result: " + finalResult);
    }
}
