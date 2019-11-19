package de.hska.iwi.vslab.Api_Product.Controllers;

import de.hska.iwi.vslab.Api_Product.ConsumingREST.Product;
import de.hska.iwi.vslab.Api_Product.Services.ApiProductService;

import java.util.Optional;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.ServiceInstance;

@RestController
public class ApiProductController {

    @Autowired
    private ApiProductService apiProductService;

    @Autowired
    private LoadBalancerClient loadBalancer;

    private static final Logger log = LoggerFactory.getLogger(ApiProductController.class);

    public String getBaseUrlOfServiceInstance() {
        ServiceInstance serviceInstance = loadBalancer.choose("core_product");
        return serviceInstance.getUri().toString(); // equals baseUrl
    }

    /**
     * Checks if categoryId actually exists, if yes then the product is added.
     */
    @PostMapping("/product/{id}")
    public void addProduct(@RequestBody String name, double price, int categoryId, String details) {
        log.info("addProduct(name, price, categoryId, details) was called");
        apiProductService.addProduct(name, price, categoryId, details);
    }

    @GetMapping("/product")
    public Product[] getAllProduct() {
        log.info("getAllProducts() was called");
        String baseUrl = getBaseUrlOfServiceInstance();
        return apiProductService.getProducts(baseUrl);
    }

    @RequestMapping(value = { "/product/find" }, method = RequestMethod.GET)
    public Product[] getProducts(@RequestParam(value = "searchValue", required = false) Optional<String> searchValue,
            @RequestParam(value = "priceMinValue", required = false) Optional<String> priceMinValue,
            @RequestParam(value = "priceMaxValue", required = false) Optional<String> priceMaxValue) {

        return apiProductService.findProduct(searchValue, priceMinValue, priceMaxValue);
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable int id) {
        log.info("getProduct(" + id + ") was called");
        return apiProductService.getProduct(id);
    }

    @PutMapping("/product")
    public void updateProduct(@RequestBody int id, String name, double price, int categoryId, String details) {
        log.info("updateProduct(" + name.toString() + ") was called");
        apiProductService.updateProduct(id, name, price, categoryId, details);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        log.info("deleteProduct(" + id + ") was called");
        apiProductService.deleteProduct(id);
    }

    @DeleteMapping("/product")
    public void deleteProduct() {
        log.info("deleteProduct() was called");
        apiProductService.deleteAllProducts();
    }

}
