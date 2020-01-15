package de.hska.iwi.vslab.Api_Product.Controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.hska.iwi.vslab.Api_Product.ConsumingREST.Product;
import de.hska.iwi.vslab.Api_Product.ConsumingREST.UrlBuilder;
import de.hska.iwi.vslab.Api_Product.Services.ApiProductService;

import java.util.Optional;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.ServiceInstance;

@RestController
@EnableCircuitBreaker
public class ApiProductController {

    @Autowired
    private ApiProductService apiProductService;

    private static final Logger log = LoggerFactory.getLogger(ApiProductController.class);

    /**
     * Checks if categoryId actually exists, if yes then the product is added.
     */
    @PostMapping(path = "/product", consumes = "application/json")
    @HystrixCommand(fallbackMethod = "defaultFallback")
    public void addProduct(@RequestBody(required = true) Product request) {
        log.info("addProduct(name, price, categoryId, details) was called" + request.getName() + request.getPrice()
                + request.getCategoryId() + request.getDetails());
        apiProductService.addProduct(request);
    }

    @GetMapping("/product")
    @HystrixCommand(fallbackMethod = "fallbackGetProducts")
    public Product[] getAllProduct() {
        log.info("getAllProducts() was called");
        return apiProductService.getProducts();
    }

    public Product[] fallbackGetProducts() {
        Product product1 = new Product("productFallback1",1.0, 1, "dies das");
        Product product2 = new Product("productFallback2",2.0, 1, "dies das");
        Product[] productA = new Product[2];
        productA[0] = product1;
        productA[1] = product2;
        return productA;
    }

    @RequestMapping(value = { "/product/find" }, method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallbackGetProducts")
    public Product[] getProducts(@RequestParam(value = "searchValue", required = false) Optional<String> searchValue,
            @RequestParam(value = "priceMinValue", required = false) Optional<String> priceMinValue,
            @RequestParam(value = "priceMaxValue", required = false) Optional<String> priceMaxValue) {

        return apiProductService.findProduct(searchValue, priceMinValue, priceMaxValue);
    }

    @GetMapping("/product/{id}")
    @HystrixCommand(fallbackMethod = "fallbackGetProduct")
    public Product getProduct(@PathVariable int id) {
        log.info("getProduct(" + id + ") was called");
        return apiProductService.getProduct(id);
    }

    public Product fallbackGetProduct() {
        Product product = new Product("productFallback",1.0, 1, "dies das");
        return product;
    }

    @PutMapping(path = "/product/{id}", consumes = "application/json")
    @HystrixCommand(fallbackMethod = "defaultFallbackWithId")
    public void updateProduct(@PathVariable int id, @RequestBody(required = true) Product request) {
        log.info("updateProduct(" + request.getName().toString() + ") was called");
        apiProductService.updateProduct(request.getId(), request.getName(), request.getPrice(), request.getCategoryId(),
                request.getDetails());
    }

    @DeleteMapping("/product/{id}")
    @HystrixCommand(fallbackMethod = "defaultFallbackWithId")
    public void deleteProduct(@PathVariable int id) {
        log.info("deleteProduct(" + id + ") was called");
        apiProductService.deleteProduct(id);
    }

    @DeleteMapping("/product")
    @HystrixCommand(fallbackMethod = "defaultFallback")
    public void deleteProduct() {
        log.info("deleteProduct() was called");
        apiProductService.deleteAllProducts();
    }

    public void defaultFallback(Throwable throwable) {
        System.out.printf("DefaultFallback, exception=%s%n", throwable);
    }

    public void defaultFallbackWithId(int id, Throwable throwable) {
        System.out.printf("DefaultFallbackWithId, id=%s, exception=%s%n", id, throwable);
    }
}
