# Meta-Annotations in Java

## Overview
Meta-annotations are annotations used to define or provide metadata about other annotations. They play a crucial role in defining the behavior and usage of custom annotations in Java.

---

## Core Meta-Annotations

### 1. **@Retention**
Specifies how long the annotation is retained in the program lifecycle.

- **Values:**
  - `RetentionPolicy.SOURCE`: Annotation is discarded during compilation.
  - `RetentionPolicy.CLASS`: Annotation is included in the `.class` file but not accessible at runtime.
  - `RetentionPolicy.RUNTIME`: Annotation is retained at runtime and accessible via reflection.

#### Example:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomAnnotation {
    String description();
}
```

---

### 2. **@Target**
Defines where the annotation can be applied (e.g., class, method, field).

- **Common `ElementType` values:**
  - `TYPE`: Class, interface, or enum.
  - `FIELD`: Field or variable.
  - `METHOD`: Method.
  - `PARAMETER`: Method parameter.
  - `CONSTRUCTOR`: Constructor.

#### Example:
```java
@Target(ElementType.METHOD)
public @interface MyMethodAnnotation {
    String value();
}
```

---

### 3. **@Inherited**
Allows annotations to be inherited by subclasses.

#### Example:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Configurable {
    String value();
}

@Configurable(value = "ParentConfig")
public class Parent {}

public class Child extends Parent {}
```

**Explanation:**
- The `Child` class automatically inherits the `@Configurable` annotation from the `Parent` class.

---

### 4. **@Documented**
Ensures the annotation is included in Javadocs.

#### Example:
```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
    String value();
}
```

**Explanation:**
- The `@Service` annotation will appear in the generated Javadocs.

---

### 5. **@Repeatable**
Introduced in Java 8, this allows an annotation to be applied multiple times to the same element.

#### Example:
```java
@Repeatable(Tasks.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Task {
    String description();
    int priority() default 5;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Tasks {
    Task[] value();
}
```

**Usage:**
```java
public class TaskExample {

    @Task(description = "Task 1", priority = 1)
    @Task(description = "Task 2", priority = 2)
    public void performTasks() {
        // Task execution logic
    }
}
```

**Explanation:**
- The `@Task` annotation is applied multiple times to the same method and grouped under the container annotation `@Tasks`.

---

## Summary of Meta-Annotations

| Meta-Annotation    | Purpose                                                            |
|--------------------|--------------------------------------------------------------------|
| `@Retention`       | Specifies how long the annotation is retained (source, class, or runtime). |
| `@Target`          | Defines where the annotation can be applied (e.g., method, class, field). |
| `@Inherited`       | Marks an annotation so that it can be inherited by subclasses.   |
| `@Documented`      | Ensures that the annotation is included in Javadoc.               |
| `@Repeatable`      | Allows an annotation to be applied multiple times to the same element. |

---

## Example: Combining Meta-Annotations

Here’s a comprehensive example that combines multiple meta-annotations:

```java
@Repeatable(Tasks.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Task {
    String description();
    int priority() default 5;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Tasks {
    Task[] value();
}

public class MyTasks {

    @Task(description = "Task 1", priority = 1)
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
```

**Explanation:**
- The `@Task` annotation is used multiple times on the `performMultipleTasks` method.
- The `Tasks` container annotation groups these repeated annotations.
- Reflection is used to process and display annotation values.

---

## Conclusion
Meta-annotations provide the foundation for building flexible, reusable, and powerful custom annotations in Java. By leveraging meta-annotations such as `@Retention`, `@Target`, `@Inherited`, `@Documented`, and `@Repeatable`, you can create annotations that suit a wide range of use cases while maintaining clarity and structure in your code.

