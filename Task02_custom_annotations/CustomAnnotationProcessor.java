package Task02_custom_annotations;

import Task02_custom_annotations.annotations.Action;
import Task02_custom_annotations.annotations.DeprecatedFeature;
import Task02_custom_annotations.annotations.Service;
import Task02_custom_annotations.services.OrderService;
import Task02_custom_annotations.services.ProductService;
import java.lang.reflect.Method;

/**
 *
 * @author k-vishall
 */
public class CustomAnnotationProcessor {

    public static void main(String[] args) throws Exception {
        inspectService(OrderService.class);
        inspectService(ProductService.class);
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

                if (method.isAnnotationPresent(DeprecatedFeature.class)) {
                    DeprecatedFeature deprecated = method.getAnnotation(DeprecatedFeature.class);
                    System.out.println(" - Deprecated Method: " + method.getName());
                    System.out.println("   - Reason: " + deprecated.reason());
                    System.out.println("   - Alternative: " + deprecated.alternative());
                }
            }
        }
    }
}
