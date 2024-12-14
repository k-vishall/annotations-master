# Basics: Built-in Annotations

## 1. `@Override`
- Indicates that a method overrides a superclass method.
- Ensures the method name and parameters match the method in the superclass.
- Example:
  ```java
  @Override
  public String toString() {
      return "Example class.";
  }
  ```

## 2. `@Deprecated`
- Marks a method, class, or field as deprecated.
- Indicates that the marked element should not be used and might be removed in future versions.
- Example:
  ```java
  @Deprecated
  public void oldMethod() {
      System.out.println("This method is deprecated.");
  }
  ```

## 3. `@SuppressWarnings`
- Suppresses compiler warnings for the annotated method or block of code.
- Common use case: suppress unchecked type warnings.
- Example:
  ```java
  @SuppressWarnings("unchecked")
  public void example() {
      List rawList = new ArrayList();
      rawList.add("No warnings shown");
  }
  ```

---

### Key Notes:
- These annotations make the code more readable and maintainable.
- Use `@Deprecated` sparingly and always provide an alternative method if you deprecate something.
- Avoid overusing `@SuppressWarnings`, as warnings are often helpful.
