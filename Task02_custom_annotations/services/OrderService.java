package Task02_custom_annotations.services;

import Task02_custom_annotations.annotations.Action;
import Task02_custom_annotations.annotations.DeprecatedFeature;
import Task02_custom_annotations.annotations.Service;

/**
 *
 * @author k-vishall
 */
@Service(name = "OrderService")
public class OrderService {

    @Action(description = "Processes the order", priority = 2)
    public void processOrder() {
        System.out.println("Processing order...");
    }

    @Action(description = "Cancels the order")
    @DeprecatedFeature(reason = "This feature is obsolete", alternative = "Use cancelOrderV2()")
    public void cancelOrder() {
        System.out.println("Cancelling order...");
    }
}
