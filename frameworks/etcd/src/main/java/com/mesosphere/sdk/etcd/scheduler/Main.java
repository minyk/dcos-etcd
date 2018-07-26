package com.mesosphere.sdk.etcd.scheduler;

import com.mesosphere.sdk.etcd.api.ETCDStatusResource;
import com.mesosphere.sdk.scheduler.*;
import com.mesosphere.sdk.specification.DefaultServiceSpec;
import com.mesosphere.sdk.specification.yaml.RawServiceSpec;
import com.mesosphere.sdk.etcd.api.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Main entry point for the Scheduler.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected one file argument, got: " + Arrays.toString(args));
        }

        LOGGER.info("Starting etcd scheduler");
        // Read config from provided file, and assume any config etcds are in the same directory as the file:
        SchedulerRunner
                .fromSchedulerBuilder(createSchedulerBuilder(new File(args[0])))
                .run();
    }

    private static SchedulerBuilder createSchedulerBuilder(File yamlSpecFile) throws Exception {
        RawServiceSpec rawServiceSpec = RawServiceSpec.newBuilder(yamlSpecFile).build();
        SchedulerConfig schedulerConfig = SchedulerConfig.fromEnv();

        SchedulerBuilder schedulerBuilder = DefaultScheduler.newBuilder(
                DefaultServiceSpec
                        .newGenerator(rawServiceSpec, schedulerConfig, yamlSpecFile.getParentFile())
                        .build(),
                schedulerConfig)
                .setPlansFrom(rawServiceSpec);

        return schedulerBuilder
                .setCustomResources(getResources(schedulerBuilder.getServiceSpec().getName()));
    }

    private static Collection<Object> getResources(String serviceName) {
        final Collection<Object> apiResources = new ArrayList<>();
        apiResources.add(new FileResource());
        apiResources.add(new ETCDStatusResource(serviceName));
        return apiResources;
    }
}
