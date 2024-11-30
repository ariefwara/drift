package id.levalapp.grask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Grask {
    private final List<Runnable> tasks = new ArrayList<>();

    // Adds tasks to be executed in parallel
    public Grask proc(Runnable... runnables) {
        for (Runnable runnable : runnables) {
            tasks.add(runnable);
        }
        return this;
    }

    // Executes all defined tasks using virtual threads
    public void grusk() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            tasks.forEach(executor::submit);
        }
    }
}
