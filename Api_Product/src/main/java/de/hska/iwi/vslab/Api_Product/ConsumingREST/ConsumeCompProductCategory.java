package de.hska.iwi.vslab.Api_Product.ConsumingREST;

import org.springframework.web.client.RestTemplate;
import de.hska.iwi.vslab.Api_Product.Controllers.ApiProductController;

public class ConsumeCompProductCategory {

    private String urlCoreProduct = "http://localhost:8081//comp_product_category/product";

    RestTemplate restTemplate = new RestTemplate();

    public void addProduct(String name, double price, int categoryId, String details) {
        // try {
        // ApiProductController instance = new ApiProductController();
        // String baseUrl = instance.getBaseUrlOfServiceInstance();
        restTemplate.postForLocation(urlCoreProduct, name, price, categoryId, details);
        // } catch (Exception e) {
        // System.out.println(e);
        // }
    }

}