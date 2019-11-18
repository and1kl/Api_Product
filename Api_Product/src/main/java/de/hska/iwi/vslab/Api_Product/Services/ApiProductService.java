package de.hska.iwi.vslab.Api_Product.Services;

import org.springframework.stereotype.Service;

import de.hska.iwi.vslab.Api_Product.ConsumingREST.ConsumeCompProductCategory;
import de.hska.iwi.vslab.Api_Product.ConsumingREST.ConsumeCoreProduct;
import de.hska.iwi.vslab.Api_Product.ConsumingREST.Product;

/** 
 * The implementation of the service.
 */
@Service
public class ApiProductService {

    ConsumeCompProductCategory compProductCategory = new ConsumeCompProductCategory();

    public void addProduct(String name, double price, int categoryId, String details) { 
        compProductCategory.addProduct(name, price, categoryId, details);
    }

    ConsumeCoreProduct coreProduct = new ConsumeCoreProduct();

    public Product[] getProducts() {
        return coreProduct.getProducts();
    }

    public void deleteProduct(int id) {
        coreProduct.deleteProduct(id);
    }

    public void updateProduct(int id, String name, double price, int categoryId, String details) {
        Product product = coreProduct.getProduct(id);
        product.setCategoryId(categoryId);
        product.setDetails(details);
        product.setName(name);
        product.setPrice(price);
        coreProduct.updateProduct(product);
    }

    public void deleteAllProducts() {
        coreProduct.deleteAllProducts();
    }

    public Product getProduct(int id) {
        return coreProduct.getProduct(id);
    }

    public void findProduct(String searchValue, String priceMinValue, String priceMaxValue) {
        coreProduct.findProduct(searchValue, priceMinValue, priceMaxValue);
    }

    
}
