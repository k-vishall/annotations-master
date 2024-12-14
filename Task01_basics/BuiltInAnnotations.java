import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author k-vishall
 */
public class BuiltInAnnotations {

    // Example of @Override
    @Override
    public String toString() {
        return "This is a BuiltInAnnotationsExample class.";
    }

    // Example of @Deprecated
    @Deprecated
    public void oldMethod() {
        System.out.println("This method is deprecated and should not be used.");
    }

    // Example of @SuppressWarnings
    @SuppressWarnings("unchecked")
    public void uncheckedWarningExample() {
        List rawList = new ArrayList(); // This will normally show a warning
        rawList.add("Suppressed warning example");
        System.out.println("Warning suppressed: " + rawList.get(0));
    }

    public static void main(String[] args) {
        BuiltInAnnotations builtInAnnotations = new BuiltInAnnotations();

        // Call overridden method
        System.out.println(builtInAnnotations.toString());

        // Call deprecated method
        builtInAnnotations.oldMethod();

        // Call method with suppressed warnings
        builtInAnnotations.uncheckedWarningExample();
    }
}
