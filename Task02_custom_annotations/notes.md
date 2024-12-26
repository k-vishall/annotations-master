# Custom Annotations in Java

## 1. What are Custom Annotations?
Custom annotations are developer-defined metadata that can be applied to classes, methods, fields, and more. 
They allow you to extend the functionality of your code by marking elements with specific behavior or information.

---

## 2. Key Components of Custom Annotations

### **@Retention**
- Specifies how long the annotation is retained in the program.
- Values:
  - `RetentionPolicy.SOURCE`: Discarded during compilation.
  - `RetentionPolicy.CLASS`: Retained in the `.class` file but not accessible at runtime.
  - `RetentionPolicy.RUNTIME`: Retained and accessible at runtime (used in reflection).

### **@Target**
- Defines where the annotation can be applied.
- Common `ElementType` values:
  - `TYPE`: For classes, interfaces, or enums.
  - `FIELD`: For fields or variables.
  - `METHOD`: For methods.
  - `PARAMETER`: For method parameters.
  - `CONSTRUCTOR`: For constructors.

### **@Inherited**
- Allows an annotation to be inherited by subclasses.

### **@Documented**
- Includes the annotation in the generated JavaDocs.

### Key Notes:
- Use `RetentionPolicy.RUNTIME` if the annotation needs to be accessed at runtime.
- Avoid overcomplicating annotations; keep them simple and meaningful.
---

## 3. Components

### **Annotations**
Annotations are defined in the `annotations/` folder. Each annotation has a specific purpose:
- `Action`: Used to add metadata to methods, such as descriptions and priorities.
- `Service`: Marks classes as services (e.g., `OrderService`, `ProductService`).
- `DeprecatedFeature`: Marks methods as deprecated, providing reasons and alternatives.

#### Example: `Action.java`
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
    String description();
    int priority() default 1;
}
```

#### Example: `Service.java`
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Service {
    String name();
}
```

---

### **Services**
Services contain the actual business logic and are marked with the `@Service` annotation.

#### Example: `OrderService.java`
```java
@Service(name = "OrderService")
public class OrderService {
    @Action(description = "Processes the order", priority = 2)
    public void processOrder() {
        System.out.println("Processing order...");
    }
}
```

---

### **Main Class**
The main class demonstrates how to inspect annotations at runtime using reflection.

#### Example: `CustomAnnotationProcessor.java`
```java
public class CustomAnnotationProcessor {
    public static void main(String[] args) throws Exception {
        inspectService(OrderService.class);
    }

    private static void inspectService(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Service.class)) {
            Service serviceAnnotation = clazz.getAnnotation(Service.class);
            System.out.println("Inspecting Service: " + serviceAnnotation.name());

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Action.class)) {
                    Action action = method.getAnnotation(Action.class);
                    System.out.println(" - Method: " + method.getName());
                    System.out.println("   - Description: " + action.description());
                    System.out.println("   - Priority: " + action.priority());
                }
            }
        }
    }
}
```

---

## 4. Best Practices for Custom Annotations
- Use meaningful names and descriptions for annotation elements.
- Use `RetentionPolicy.RUNTIME` if reflection is needed.
- Keep annotations lightweight to avoid overhead.
- Document annotations with `@Documented` for better visibility in JavaDocs.
- Combine annotations for complex scenarios but avoid overusing them.

---

## 5. Real-World Applications
- **Frameworks**: Spring uses annotations like `@Service`, `@Controller`, and `@RestController`.
- **Validation**: Hibernate Validator uses annotations like `@NotNull` and `@Size`.
- **Code Generation**: Tools like Lombok use annotations to reduce boilerplate code.
```
