package id.levalapp.grask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Grask {
    private final List<List<Runnable>> taskGroups = new ArrayList<>();

    // Adds tasks to be executed in parallel within the same proc call
    public Grask proc(Runnable... runnables) {
        List<Runnable> taskGroup = new ArrayList<>();
        for (Runnable runnable : runnables) {
            taskGroup.add(runnable);
        }
        taskGroups.add(taskGroup);
        return this;
    }

    // Executes all defined tasks using ForkJoinPool, ensuring proc groups run sequentially
    public void grusk() {
        for (List<Runnable> taskGroup : taskGroups) {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            forkJoinPool.invoke(new TaskAction(taskGroup));
        }
    }

    private static class TaskAction extends RecursiveAction {
        private final List<Runnable> tasks;

        TaskAction(List<Runnable> tasks) {
            this.tasks = tasks;
        }

        @Override
        protected void compute() {
            tasks.forEach(Runnable::run);
        }
    }
}
