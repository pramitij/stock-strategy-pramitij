package edu.neu.csye7374;

import java.util.concurrent.ThreadLocalRandom;

public class AbstractBullMetricStrategySingleton implements MetricStrategyAPI {
    private static AbstractBullMetricStrategySingleton instance;

    private AbstractBullMetricStrategySingleton() {
        super();
        instance=null;
    }

    public static synchronized AbstractBullMetricStrategySingleton getInstance() {
        if (instance == null) {
            instance = new AbstractBullMetricStrategySingleton();
        }
        return instance;
    }


    @Override
    public int calculateMetric() {
        // TODO Auto-generated method stub
        int number = (int)((ThreadLocalRandom.current().nextInt(1, 40 + 1)*2.5)/2.99);
        return number;
    }
}
