Grask Framework  
================  

Grask - Grasping Task Management in Parallel Systems  

Grask is a lightweight and flexible framework designed for managing parallel and sequential workflows with a fluent and intuitive API. It enables efficient task execution by combining simplicity, speed, and reliability, making it ideal for building modern, high-performance systems.  

Features  
--------  
- Parallel Task Execution: Run tasks simultaneously for optimized performance.  
- Sequential Chaining: Define workflows that depend on the output of prior steps.  
- Unified Syntax: Use a single, consistent keyword (`proc`) for all task declarations.  
- Fast and Effective: Execute tasks rapidly without sacrificing quality.  

Installation  
------------  
To integrate Grask into your project, add the following dependency to your `pom.xml`:  

```
<dependency>  
    <groupId>id.levalapp.grask</groupId>  
    <artifactId>grask-framework</artifactId>  
    <version>1.0.0</version>  
</dependency>
```

Then update your Maven dependencies:
```
mvn clean install
```

Usage  
-----  

Example Workflow:  

```java
import id.levalapp.grask.Grask;

public class Example {
    public static void main(String[] args) {
        new Grask()
            .proc(() -> task1(), () -> task2())   // Parallel Step 1
            .proc(() -> task3())                 // Sequential Step 2
            .proc(() -> task4(), () -> task5())  // Parallel Step 3
            .proc(() -> task6())                 // Sequential Step 4
            .grusk();                            // Execute all
    }

    static void task1() { System.out.println("Task 1"); }
    static void task2() { System.out.println("Task 2"); }
    static void task3() { System.out.println("Task 3"); }
    static void task4() { System.out.println("Task 4"); }
    static void task5() { System.out.println("Task 5"); }
    static void task6() { System.out.println("Task 6"); }
}
```

API  
---  

### `proc(Runnable... tasks)`  
Adds tasks to be executed. Tasks within a single `proc` call run in parallel.  

Example:  
```java
new Grask().proc(() -> task1(), () -> task2());
```

### `grusk()`  
Executes the workflow, running all tasks as defined in sequence and parallel.  

License  
-------  
This project is licensed under the MIT License.  