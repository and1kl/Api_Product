package de.hska.iwi.vslab.Api_Product.ConsumingREST;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import java.util.Optional;

public class UrlBuilder {

    private String baseUrl_core_product;
    private String baseUrl_comp_product_category;

    String getBaseUrl_core_product(){
        return baseUrl_core_product;
    }
    String getBaseUrl_comp_product_category(){
        return baseUrl_comp_product_category;
    }

    public UrlBuilder(){
        LoadBalancerClient loadBalancer = BeanUtil.getBean(LoadBalancerClient.class);
        ServiceInstance si_core_product = loadBalancer.choose("core_product");
        ServiceInstance si_comp_product_category = loadBalancer.choose("comp_product_category");
        this.baseUrl_core_product =  si_core_product.getUri().toString();
        this.baseUrl_comp_product_category = si_comp_product_category.getUri().toString();
    }

    // CoreProduct
    String getProductURL(){
        return baseUrl_core_product + "/product";
    }

    String findProductURL(){
        return baseUrl_core_product + "/product/find";
    }

    String getSlashURL(){
        return baseUrl_core_product+"/";
    }

    String getUrlWithId(int id){
        return baseUrl_core_product+"/"+id;
    }
    String getFilterUrl(Optional<String> searchValue, Optional<String> priceMinValue, Optional<String> priceMaxValue){

        String url = baseUrl_core_product + "/product/find?searchValue=[SVAL]&priceMinValue=[PMIN]&priceMaxValue=[PMAX]";

        if (searchValue.isPresent())
                url = url.replace("[SVAL]",searchValue.get());
        else
            url = url.replace("[SVAL]","");

        if (priceMinValue.isPresent())
            url = url.replace("[PMIN]",priceMinValue.get());
        else
            url = url.replace("[PMIN]","");

        if (priceMaxValue.isPresent())
            url = url.replace("[PMAX]",priceMaxValue.get());
        else
            url = url.replace("[PMAX]","");

        return url;
    }



    // CompProductCategory
    String getAddProductURL(){
        return baseUrl_comp_product_category +"/comp_product_category/product";
    }
}
