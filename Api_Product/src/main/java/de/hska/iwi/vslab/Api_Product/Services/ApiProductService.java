package de.hska.iwi.vslab.Api_Product.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.hska.iwi.vslab.Api_Product.ConsumingREST.ConsumeCompProductCategory;
import de.hska.iwi.vslab.Api_Product.ConsumingREST.ConsumeCoreProduct;
import de.hska.iwi.vslab.Api_Product.ConsumingREST.Product;
import java.io.IOException;
import org.springframework.web.client.RestClientException;

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

    public Product[] getProducts(String baseUrl) {
        return coreProduct.getProducts(baseUrl);
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

    public Product[] findProduct(Optional<String> searchValue, Optional<String> priceMinValue,
            Optional<String> priceMaxValue) {
        return coreProduct.findProduct(searchValue, priceMinValue, priceMaxValue);
    }

}
