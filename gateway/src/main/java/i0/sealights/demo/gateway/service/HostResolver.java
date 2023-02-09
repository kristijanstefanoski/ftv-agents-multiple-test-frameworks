package i0.sealights.demo.gateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HostResolver {

    @Value( "${appservices.calculator.url}" )
    private String calculatorUrl;

    @Value( "${appservices.summator.url}" )
    private String summatorUrl;

    private static String API_PREFIX = "/api";
    private static String CALCULATOR_API = "/api/evaluate";
    private static String SUMMATOR_API = "/api/sum/geometric";



    public String resolveServiceUrl(final String apiUrl) {
        if (apiUrl.startsWith(CALCULATOR_API)) {
            return calculatorUrl + apiUrl.substring(4);
        } else if (apiUrl.startsWith(SUMMATOR_API)) {
            return summatorUrl + apiUrl.substring(4);
        } else {
            throw new ApiUrlNotFound("Could not find service for API: " + apiUrl);
        }
    }
}
