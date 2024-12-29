# Advanced Topics in Annotations

In this section, we explore advanced concepts and applications of annotations in Java, including reflection-based processing, annotation processors, and practical use cases in frameworks.

---

## 1. **Processing Annotations with Reflection**
Reflection allows you to inspect and manipulate annotations at runtime. This is especially useful for frameworks and libraries that dynamically process annotations.

### Example: Annotation Processor with Reflection

#### Step 1: Create Custom Annotations
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {
}
```

#### Step 2: Annotate Methods
```java
public class SampleService {

    @LogExecutionTime
    public void performTask() {
        System.out.println("Task is being performed.");
    }

    public void nonAnnotatedTask() {
        System.out.println("This task is not annotated.");
    }
}
```

#### Step 3: Process Annotations with Reflection
```java
import java.lang.reflect.Method;

public class AnnotationProcessor {

    public static void main(String[] args) throws Exception {
        SampleService service = new SampleService();
        
        for (Method method : SampleService.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                long start = System.currentTimeMillis();
                method.invoke(service);
                long end = System.currentTimeMillis();
                System.out.println("Execution time: " + (end - start) + "ms");
            } else {
                method.invoke(service);
            }
        }
    }
}
```

**Explanation:**
- `@LogExecutionTime` is processed at runtime using reflection.
- Execution time is logged only for annotated methods.

---

## 2. **Annotation Processing at Compile Time**
Annotation processing during compile time involves generating code, validating annotations, or creating documentation using tools like the `AbstractProcessor` class.

### Example: Compile-Time Annotation Processor

#### Step 1: Create the Annotation
```java
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Entity {
    String tableName();
}
```

#### Step 2: Annotate a Class
```java
@Entity(tableName = "users")
public class User {
    private String name;
    private int age;

    // Getters and setters
}
```

#### Step 3: Write the Annotation Processor
```java
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.SourceVersion;
import java.util.Set;

@SupportedAnnotationTypes("Entity")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EntityProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Entity.class)) {
            Entity entity = element.getAnnotation(Entity.class);
            System.out.println("Processing entity: " + entity.tableName());
        }
        return true;
    }
}
```

**Explanation:**
- The `@Entity` annotation is processed during compilation.
- The processor retrieves the `tableName` value and performs operations like generating additional code or documentation.

---

## 3. **Chaining and Combining Annotations**
Annotations can be combined or chained for complex functionality. This is often seen in frameworks like Spring and Hibernate.

### Example: Chaining Annotations
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Transactional {
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {
    String role();
}

@Transactional
@Secured(role = "ADMIN")
public class AdminService {
    public void performAdminTask() {
        System.out.println("Admin task performed.");
    }
}
```

**Explanation:**
- `@Transactional` and `@Secured` are applied together to ensure that a method runs within a transaction and only for authorized users.

---

## 4. **Real-World Use Cases of Annotations**

### Case 1: Dependency Injection in Spring
Annotations like `@Autowired` and `@Qualifier` are used for dependency injection.
```java
@Component
public class MyService {

    @Autowired
    private Repository repository;
}
```

### Case 2: Validation in Hibernate Validator
Annotations like `@NotNull` and `@Size` are used for bean validation.
```java
public class User {

    @NotNull
    private String username;

    @Size(min = 8, max = 20)
    private String password;
}
```

### Case 3: RESTful APIs with Spring Boot
Annotations like `@RestController`, `@GetMapping`, and `@PostMapping` are used to define REST endpoints.
```java
@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
```

---

## 5. **Advanced Real-World Use Cases**

### Case 1: Logging Frameworks
Frameworks like SLF4J or custom logging solutions use annotations to automate log generation.
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Loggable {
}

public class LoggingAspect {

    public static void processLogs(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Loggable.class)) {
                System.out.println("Logging execution of: " + method.getName());
            }
        }
    }
}
```

### Case 2: Custom Security Annotations
Security frameworks use annotations like `@Secured` or custom ones to enforce role-based access control.
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresRole {
    String role();
}

public class SecurityService {
    public static void checkAccess(Object object, String userRole) throws IllegalAccessException {
        for (Method method : object.getClass().getDeclaredMethods()) {
            RequiresRole annotation = method.getAnnotation(RequiresRole.class);
            if (annotation != null && !annotation.role().equals(userRole)) {
                throw new IllegalAccessException("User does not have required role: " + annotation.role());
            }
        }
    }
}
```

### Case 3: ORM Mapping (Hibernate)
Annotations like `@Entity`, `@Table`, and `@Column` are used to map Java classes to database tables.
```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    // Getters and setters
}
```

### Case 4: Task Scheduling
Task schedulers like Quartz use annotations to define and schedule tasks.
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scheduled {
    String cron();
}

public class Scheduler {

    public static void scheduleTasks(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            Scheduled scheduled = method.getAnnotation(Scheduled.class);
            if (scheduled != null) {
                System.out.println("Scheduling task with cron: " + scheduled.cron());
            }
        }
    }
}
```

---

## 6. **Best Practices for Advanced Annotation Usage**

1. **RetentionPolicy:** Use `RUNTIME` for annotations processed at runtime and `CLASS` for compile-time tasks.
2. **Reflection:** Be cautious with reflection-based annotation processing as it can impact performance.
3. **Documentation:** Use `@Documented` for better visibility in Javadocs.
4. **Validation:** Validate annotation usage to ensure correctness.
5. **Code Generation:** Leverage compile-time processing for generating boilerplate code.

---

## Conclusion
Advanced annotation usage empowers Java developers to create flexible, reusable, and dynamic systems. By combining reflection, compile-time processing, and real-world use cases, annotations can significantly enhance the functionality and maintainability of your code.

