package de.hska.iwi.vslab.Api_Product.ConsumingREST;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

public class UrlBuilder {

    private String baseUrl;

    String getBaseUrl(){
        return baseUrl;
    }

    public UrlBuilder(){
        LoadBalancerClient loadBalancer = BeanUtil.getBean(LoadBalancerClient.class);
        ServiceInstance si = loadBalancer.choose("core_product");
        this.baseUrl =  si.getUri().toString();
    }

    // CoreProduct
    String getProductURL(){
        return baseUrl + "/product";
    }

    String findProductURL(){
        return baseUrl + "/product/find";
    }

    String getSlashURL(){
        return baseUrl+"/";
    }

    String getUrlWithId(int id){
        return baseUrl+"/"+id;
    }
}
