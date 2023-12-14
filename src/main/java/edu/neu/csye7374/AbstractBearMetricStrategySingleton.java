package edu.neu.csye7374;

import java.util.concurrent.ThreadLocalRandom;

public class AbstractBearMetricStrategySingleton implements MetricStrategyAPI {

    private static AbstractBearMetricStrategySingleton instance;

    private AbstractBearMetricStrategySingleton() {
        super();
        instance=null;
    }

    public static synchronized AbstractBearMetricStrategySingleton getInstance() {
        if (instance == null) {
            instance = new AbstractBearMetricStrategySingleton();
        }
        return instance;
    }


    @Override
    public int calculateMetric() {
        // TODO Auto-generated method stub
        int number = (int)((ThreadLocalRandom.current().nextInt( -40 + 1,0)*2.5)/2.99);
        return number;
    }
}
