//package com.Medical_Recirds2.MEdical;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Allow all endpoints
//                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5173") // Update as per your frontend's origin
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH"); // Allow required methods
//    }
//}
//
package com.Medical_Recirds2.MEdical;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5173") // Update as per your frontend's origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH"); // Allow required methods
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("Registering resource handler for /uploads/** to file:E:/Digital health id system/MEdical/src/main/resources/uploads/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:E:/Digital health id system/MEdical/src/main/resources/uploads/")
                .setCachePeriod(0); // Disable caching for testing
    }
}