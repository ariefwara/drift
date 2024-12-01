package id.levalapp.drift;

import java.util.Map;

public class Example {

    public static void main(String[] args) {
        boolean conditionA = false;
        boolean conditionB = false;
        boolean conditionC = false;
        boolean conditionD = false;
        boolean conditionE = false;

        System.out.println("Final Result: " + new Drift(Map.of("initialKey", "initialValue"))
            .shift(
                (proc) -> {
                    System.out.println("Checking Condition A");
                    return conditionA ? proc.end(200, "Condition A Met") : proc;
                },
                (proc) -> {
                    System.out.println("Checking Condition B");
                    return conditionB ? proc.end(200, "Condition B Met") : proc.pass(Map.of("123", "123"));
                },
                (proc) -> {
                    System.out.println("Checking Condition C");
                    return conditionC ? proc.end(200, "Condition C Met") : proc;
                })
            .shift(
                (proc) -> {
                    System.out.println("Checking Condition D");
                    return conditionD ? proc.end(200, "Condition D Met") : proc;
                },
                (proc) -> {
                    System.out.println("Checking Condition E");
                    return conditionE ? proc.end(200, "Condition E Met") : proc;
                })
            .grip(
                (proc) -> proc.end(200, "Default Response After All Batches"))
            .getFinalResult()[1]);
    }
}
