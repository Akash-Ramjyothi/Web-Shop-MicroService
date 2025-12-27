package com.webshop.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class HomeController {

    private static final AtomicInteger hitCounter = new AtomicInteger(0);

    // Thresholds for traffic classification
    private static final int HIGH_TRAFFIC_THRESHOLD = 20;
    private static final int CRITICAL_TRAFFIC_THRESHOLD = 50;

    @RequestMapping("/")
    public String index() {
        long startTime = System.currentTimeMillis();

        int currentHits = hitCounter.incrementAndGet();
        TrafficStatus trafficStatus = evaluateTraffic(currentHits);
        String viewName = resolveViewName(trafficStatus);

        long responseTimeMs = System.currentTimeMillis() - startTime;
        logRequestMetrics(currentHits, trafficStatus, viewName, responseTimeMs);

        return viewName;
    }

    /**
     * Decides which view to serve based on traffic intensity
     */
    private String resolveViewName(TrafficStatus status) {
        switch (status) {
            case CRITICAL:
                return "index-lite";   // ultra-light page
            case HIGH:
                return "index-cached"; // CDN / cached version
            default:
                return "index";
        }
    }

    /**
     * Evaluates current traffic state
     */
    private TrafficStatus evaluateTraffic(int hits) {
        if (hits >= CRITICAL_TRAFFIC_THRESHOLD) {
            return TrafficStatus.CRITICAL;
        } else if (hits >= HIGH_TRAFFIC_THRESHOLD) {
            return TrafficStatus.HIGH;
        }
        return TrafficStatus.NORMAL;
    }

    /**
     * Logs structured request analytics
     */
    private void logRequestMetrics(
            int hits,
            TrafficStatus status,
            String viewName,
            long responseTimeMs
    ) {
        System.out.printf(
                "🏈 [%s] HomeHit=%d | Traffic=%s | View=%s | ResponseTime=%dms%n",
                LocalDateTime.now(),
                hits,
                status,
                viewName,
                responseTimeMs
        );
    }

    /**
     * Traffic classification enum
     */
    private enum TrafficStatus {
        NORMAL,
        HIGH,
        CRITICAL
    }
}
