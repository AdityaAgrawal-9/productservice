package com.ecommercestore.productservice.configurations;

import com.ecommercestore.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    //Implementation for using configuration from application.properties file for ProductService type.
    @Bean
    @Primary
    public ProductService productService(
        // Use @Qualifier to inject the specific "fakeStore" implementation
        @Qualifier("fakeStoreProductService") ProductService fakeStoreProductService,

        // Use @Qualifier to inject the specific "Database" implementation
        @Qualifier("dbProductService") ProductService dbProductService,

        // Inject the property value from application.properties
        @Value("${productServiceType}")
        String productServiceType) {

        System.out.println("Passed configuration for service type : " + productServiceType);

        // Use simple Java logic to return the bean you want
        if ("fakeStoreProductService".equalsIgnoreCase(productServiceType)) {
            System.out.println("Returned configuration for service type : " +
                    fakeStoreProductService.getClass().getName());
            return fakeStoreProductService;
        } else if ("dbProductService".equalsIgnoreCase(productServiceType)) {
            System.out.println("Returned configuration for service type : " +
                    dbProductService.getClass().getName());
            return dbProductService;
        }
        else{
            return fakeStoreProductService;
        }
//        else {
//            // Default or error handling
//            System.out.println("No valid service type found, defaulting to mock.");
//            return mockService;
//        }

//      returning fakeStoreProductService by default.

    }


}
