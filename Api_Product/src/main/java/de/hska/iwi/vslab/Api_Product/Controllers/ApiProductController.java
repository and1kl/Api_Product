package de.hska.iwi.vslab.Api_Product.Controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.hska.iwi.vslab.Api_Product.ConsumingREST.Product;
import de.hska.iwi.vslab.Api_Product.Services.ApiProductService;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableCircuitBreaker
public class ApiProductController {
    private final Map<Integer, Product> productCache = new LinkedHashMap<Integer, Product>();
    @Autowired
    private ApiProductService apiProductService;

    private static final Logger log = LoggerFactory.getLogger(ApiProductController.class);

    /**
     * Checks if categoryId actually exists, if yes then the product is added.
     */
    @PostMapping(path = "/product", consumes = "application/json")
    @HystrixCommand(fallbackMethod = "addProductFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public void addProduct(@RequestBody(required = true) Product request) {
        apiProductService.addProduct(request);
    }

    public void addProductFallback(Product payload, Throwable e) {
        System.out.printf("Add Product Failed! name=%s, exception=%s%n ", payload.getName(), e);
    }

    @GetMapping("/product")
    @HystrixCommand(fallbackMethod = "getProductsCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Product[] getAllProduct() {
        Product[] products = apiProductService.getProducts();
        for(Product product : products) {
            productCache.putIfAbsent(product.getId(), product);
        }
        return products;
    }

    public Product[] getProductsCache() {
        Collection<Product> list = productCache.values();
        Product[] products = new Product[productCache.values().size()];
        products = list.toArray(products);
        return products;
    }

    @RequestMapping(value = { "/product/find" }, method = RequestMethod.GET)
    public Product[] getProducts(@RequestParam(value = "searchValue", required = false) Optional<String> searchValue,
            @RequestParam(value = "priceMinValue", required = false) Optional<String> priceMinValue,
            @RequestParam(value = "priceMaxValue", required = false) Optional<String> priceMaxValue) {

        return apiProductService.findProduct(searchValue, priceMinValue, priceMaxValue);
    }

    @GetMapping("/product/{id}")
    @HystrixCommand(fallbackMethod = "getProductCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public Product getProduct(@PathVariable int id) {
        return apiProductService.getProduct(id);
    }

    public Product getProductCache(int id) {
        return productCache.getOrDefault(id, new Product());
    }

    @PutMapping(path = "/product/{id}", consumes = "application/json")
    @HystrixCommand(fallbackMethod = "updateProductFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public void updateProduct(@PathVariable int id, @RequestBody(required = true) Product request) {
        apiProductService.updateProduct(request.getId(), request.getName(), request.getPrice(), request.getCategoryId(),
                request.getDetails());
    }

    public void updateProductFallback(int id, Product payload, Throwable e) {
        System.out.printf("Update Role Failed! id=%s, exception=%s%n ", id, e);
    }

    @DeleteMapping("/product/{id}")
    @HystrixCommand(fallbackMethod = "deleteProductFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public void deleteProduct(@PathVariable int id) {
        apiProductService.deleteProduct(id);
    }

    @DeleteMapping("/product")
    @HystrixCommand(fallbackMethod = "deleteProductsFallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public void deleteProduct() {
        apiProductService.deleteAllProducts();
    }

    public void deleteProductFallback(int id, Throwable throwable) {
        System.out.printf("Delete product failed, id=%s, exception=%s%n", id, throwable);
    }

    public void deleteProductsFallback(Throwable throwable) {
        System.out.printf("Delete products failed, exception=%s%n", throwable);
    }
}
