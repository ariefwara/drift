# Drift Framework  

**Drift** is a lightweight, intuitive framework for orchestrating workflows with a focus on **parallel** and **sequential task execution**. Inspired by the precision and smooth transitions of car drifting, Drift offers a sleek API for building efficient, high-performance systems.

---

## Features  

- **Dynamic Task Management**: Define workflows with flexible, stateful execution.  
- **Parallel Execution**: Run tasks concurrently for optimized performance.  
- **Sequential Chaining**: Seamlessly connect and manage task sequences.  
- **Fallback Handling**: Use `grip` for default responses when no condition matches.  
- **Java Virtual Threads**: Leverage modern concurrency features for scalability.  
- **Intuitive API**: Use `shift` for task orchestration and `trail` for asynchronous side effects.  

---

## Installation  

Add the following dependency to your `pom.xml` to include Drift in your project:

```xml
<dependency>  
    <groupId>id.levalapp</groupId>  
    <artifactId>drift</artifactId>  
    <version>1.0.0</version>  
</dependency>
```

Update your Maven dependencies:

```bash
mvn clean install
```

---

## Usage  

Here’s how to use **Drift** to orchestrate workflows:

### Example Workflow

```java
import id.levalapp.drift.Drift;

import java.util.Map;

public class DriftExample {
    public static void main(String[] args) {
        boolean conditionA = false;
        boolean conditionB = true;
        boolean conditionC = false;
        boolean conditionD = false;
        boolean conditionE = false;

        Drift drift = new Drift(Map.of("key", "value"));

        drift.shift(
            (proc) -> conditionA ? proc.end(200, "Condition A Met") : proc,
            (proc) -> conditionB ? proc.end(200, "Condition B Met") : proc,
            (proc) -> conditionC ? proc.end(200, "Condition C Met") : proc
        ).shift(
            (proc) -> conditionD ? proc.end(200, "Condition D Met") : proc,
            (proc) -> conditionE ? proc.end(200, "Condition E Met") : proc
        ).grip(
            (proc) -> proc.end(200, "Default Response After All Batches")
        ).trail(
            (proc) -> {
                System.out.println("Async: Performing cleanup or logging...");
            }
        );

        Object[] finalResult = drift.getFinalResult();
        System.out.println("Final Result: " + finalResult[1]);
    }
}
```

---

### Workflow Breakdown  

1. **Drift Initialization**:
   - Initialize `Drift` with an initial state as a `Map`:
     ```java
     new Drift(Map.of("key", "value"))
     ```

2. **Shift Tasks**:
   - Use `shift` to define parallel tasks:
     ```java
     drift.shift(
         (proc) -> conditionA ? proc.end(200, "Condition A Met") : proc
     );
     ```

3. **Fallback with Grip**:
   - Handle default outcomes when no tasks meet conditions:
     ```java
     drift.grip((proc) -> proc.end(200, "Default Response"));
     ```

4. **Asynchronous Side Effects**:
   - Use `trail` for cleanup or logging:
     ```java
     drift.trail((proc) -> System.out.println("Async task executed."));
     ```

5. **Retrieve Results**:
   - Access the final result:
     ```java
     Object[] finalResult = drift.getFinalResult();
     ```

---

## Example Output  

For the above workflow with conditions:
- `conditionB = true`
- All others are `false`

The output will be:

```
Async: Performing cleanup or logging...
Final Result: Condition B Met
```

---

## License  

This project is licensed under the MIT License.  