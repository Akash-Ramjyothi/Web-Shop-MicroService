package com.webshop.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        long startupStartTime = System.currentTimeMillis();

        printStartupBanner(args);

        ConfigurableApplicationContext context =
                SpringApplication.run(StoreApplication.class, args);

        long startupTimeMs = System.currentTimeMillis() - startupStartTime;
        logStartupMetrics(context, startupTimeMs);

        registerShutdownHook();
    }

    /**
     * Prints application startup banner and active arguments
     */
    private static void printStartupBanner(String[] args) {
        System.out.println("=======================================");
        System.out.println(" 🛒 WebShop Store Application Starting ");
        System.out.println(" Started at : " + LocalDateTime.now());
        System.out.println(" Arguments  : " + Arrays.toString(args));
        System.out.println("=======================================");
    }

    /**
     * Logs startup diagnostics and JVM details
     */
    private static void logStartupMetrics(
            ConfigurableApplicationContext context,
            long startupTimeMs
    ) {
        String jvmName = ManagementFactory.getRuntimeMXBean().getVmName();
        int activeBeans = context.getBeanDefinitionCount();

        System.out.printf(
                "🚀 Application started successfully | StartupTime=%dms | JVM=%s | BeansLoaded=%d%n",
