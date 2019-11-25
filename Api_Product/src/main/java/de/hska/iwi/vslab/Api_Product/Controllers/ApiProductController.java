package de.hska.iwi.vslab.Api_Product.Controllers;

import de.hska.iwi.vslab.Api_Product.ConsumingREST.Product;
import de.hska.iwi.vslab.Api_Product.ConsumingREST.UrlBuilder;
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

    private static final Logger log = LoggerFactory.getLogger(ApiProductController.class);

    /**
     * Checks if categoryId actually exists, if yes then the product is added.
     */
    @PostMapping(path = "/product", consumes = "application/json")
    public void addProduct(@RequestBody(required = true) String name, Double price, Integer categoryId,
            String details) {
        log.info("addProduct(name, price, categoryId, details) was called");
        apiProductService.addProduct(name, price, categoryId, details);
    }

    @GetMapping("/product")
    public Product[] getAllProduct() {
        log.info("getAllProducts() was called");
        return apiProductService.getProducts();
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

    @PutMapping(path = "/product/{id}", consumes = "application/json")
    public void updateProduct(@PathVariable int id, @RequestBody(required = true) Product request) {
        log.info("updateProduct(" + request.getName().toString() + ") was called");
        apiProductService.updateProduct(request.getId(), request.getName(), request.getPrice(), request.getCategoryId(),
                request.getDetails());
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
