package de.hska.iwi.vslab.Api_Product.Controllers;

import de.hska.iwi.vslab.Api_Product.ConsumingREST.Product;
import de.hska.iwi.vslab.Api_Product.Services.ApiProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiProductController {

    @Autowired
    private ApiProductService apiProductService;

	private static final Logger log = LoggerFactory.getLogger(ApiProductController.class);

    /**
     * Checks if categoryId actually exists, if yes then the product is added. 
     */
    @PostMapping("/comp_product_category/product/{id}")
    public void addProduct(@RequestBody String name, double price, int categoryId, String details) { 
        log.info("addProduct(name, price, categoryId, details) was called");
        apiProductService.addProduct(name, price, categoryId, details);
    }

    
    @GetMapping("/product")
    public Product[] getAllProduct() {
        log.info("getAllProducts() was called");
        return apiProductService.getProducts();
    }

    /* TODO
    @RequestMapping(value = {"/product/find"},
                             method = RequestMethod.GET)
    public List<Product> getProducts(
            @RequestParam(value = "searchValue", required = false) Optional<String> searchValue,
            @RequestParam(value = "priceMinValue", required = false) Optional<String> priceMinValue,
            @RequestParam(value = "priceMaxValue", required = false) Optional<String> priceMaxValue) {

    log.info("getProducts() was called");
        Object[] args = new Object[3];
        if (searchValue.isPresent())
            args[0] = searchValue;
        if (priceMinValue.isPresent())
            args[1] = priceMinValue;
        if (priceMaxValue.isPresent())
            args[2] = priceMaxValue;

        return apiProductService.getAllProducts(args);

    }*/

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable int id) {
        log.info("getProduct("+id+") was called");
        return apiProductService.getProduct(id);
    }

    @PutMapping("/product")
    public void updateProduct(@RequestBody int id, String name, double price, int categoryId, String details) {
        log.info("updateProduct("+name.toString()+") was called");
        apiProductService.updateProduct(id, name, price, categoryId, details);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id){
        log.info("deleteProduct("+id+") was called");
        apiProductService.deleteProduct(id);
    }

    @DeleteMapping("/product")
    public void deleteProduct(){
        log.info("deleteProduct() was called");
        apiProductService.deleteAllProducts();
    }


}
