
import java.lang.reflect.Method;

public class MyTasks {

    @Task(description = "Task 1", priority = 3)
    @Task(description = "Task 2", priority = 2)
    public void performMultipleTasks() {
        System.out.println("Performing multiple tasks.");
    }

    public static void main(String[] args) {
        for (Method method : MyTasks.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Tasks.class)) {
                Tasks tasks = method.getAnnotation(Tasks.class);
                for (Task task : tasks.value()) {
                    System.out.println("Task: " + task.description() + " with priority: " + task.priority());
                }
            }
        }
    }
}
