package com.webshop.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class HomeController {

    private static final AtomicInteger hitCounter = new AtomicInteger(0);

    @RequestMapping("/")
    public String index() {
        int currentHits = hitCounter.incrementAndGet();
        String viewName = resolveViewName(currentHits);

        logRequestMetrics(currentHits, viewName);

        return viewName;
    }

    /**
     * Determines which view to serve based on traffic
     */
    private String resolveViewName(int hits) {
        // Example: route heavy traffic to a lightweight landing page
        if (hits % 5 == 0) {
            return "index-lite";
        }
        return "index";
    }

    /**
     * Logs meaningful request analytics
     */
    private void logRequestMetrics(int hits, String viewName) {
        System.out.printf(
                "🏈 [%s] Home hit #%d | Serving view: %s%n",
                LocalDateTime.now(),
                hits,
                viewName
        );
    }
}
