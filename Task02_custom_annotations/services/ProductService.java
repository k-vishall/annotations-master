package Task02_custom_annotations.services;

import Task02_custom_annotations.annotations.Service;

/**
 *
 * @author k-vishall
 */
@Service(name = "ProductService")
public class ProductService {

    public void manageInventory() {
        System.out.println("Managing inventory...");
    }
}
