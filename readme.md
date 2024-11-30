# Drift Framework  

**Drift** is a lightweight and intuitive framework for orchestrating workflows with a focus on **parallel** and **sequential task execution**. Inspired by the precision and smooth transitions of car drifting, Drift offers a sleek API for building efficient, high-performance systems.

---

## Features  

- **Dynamic Task Management**: Define tasks with varying parameters and types effortlessly.  
- **Parallel Execution**: Execute tasks simultaneously for optimized performance.  
- **Sequential Chaining**: Seamlessly connect the output of one step to the next.  
- **Sleek API**: Utilize the intuitive `shift` and `swift` keywords for fluid task orchestration.  
- **Java Virtual Threads**: Leverages cutting-edge concurrency features for scalability.  

---

## Installation  

Add the following dependency to your `pom.xml` to include Drift in your project:

```xml
<dependency>  
    <groupId>id.levalapp.drift</groupId>  
    <artifactId>drift-framework</artifactId>  
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

public class Example {
    public static void main(String[] args) {
        String finalResult = new Drift("Hello", 42, true, 3.14, 'A', "World")
            .shift(
                params -> {
                    System.out.println("Task 1 received: " + params[0] + ", " + params[1] + ", " + params[2] + ", " + params[3] + ", " + params[4] + ", " + params[5]);
                    return params[0] + "-" + params[1] + "-" + params[2];
                },
                params -> {
                    System.out.println("Task 2 received: " + params[0] + ", " + params[1] + ", " + params[2] + ", " + params[3] + ", " + params[4] + ", " + params[5]);
                    return params[5] + params[4].toString() + params[3].toString();
                }
            )
            .shift(
                params -> {
                    System.out.println("Task 3 received: " + params[0] + ", " + params[1]);
                    return params[0] + "_processed";
                },
                params -> {
                    System.out.println("Task 4 received: " + params[0] + ", " + params[1]);
                    return params[1].toString().length();
                }
            )
            .swift(params -> {
                System.out.println("Final Task received: " + params[0] + ", " + params[1]);
                return "Final Output: " + params[0] + ", Length: " + params[1];
            });

        System.out.println("Final Result: " + finalResult);
    }
}
```

---

### Workflow Breakdown  

1. **Drift Initialization**:
   - Start the workflow with dynamic input parameters: `"Hello"`, `42`, `true`, `3.14`, `'A'`, `"World"`.  

2. **Shift Tasks**:
   - Use `shift` to define intermediate tasks.
   - Each task receives inputs as an array and returns a result for the next step.  

3. **Swift Finalization**:
   - Conclude the workflow with `swift`, which processes all accumulated results to produce the final output.

---

## Output  

For the above example, the output will be:

```
Task 1 received: Hello, 42, true, 3.14, A, World
Task 2 received: Hello, 42, true, 3.14, A, World
Task 3 received: Hello-42-true, WorldA3.14
Task 4 received: Hello-42-true, WorldA3.14
Final Task received: Hello-42-true_processed, 11
Final Result: Final Output: Hello-42-true_processed, Length: 11
```

---

## License  

This project is licensed under the MIT License.  