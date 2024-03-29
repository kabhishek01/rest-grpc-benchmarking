package com.kabh.client.utils;

public class Measurement {
    private String metricsName;
    private long sumResponseTime = 0;
    private int errorCount;
    private int iterationCount;

    public int getPayLoadCount() {
        return payLoadCount;
    }

    public void setPayLoadCount(int payLoadCount) {
        this.payLoadCount = payLoadCount;
    }

    private int payLoadCount;

    public String getMetricsName() {
        return metricsName;
    }

    public void setMetricsName(String metricsName) {
        this.metricsName = metricsName;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public void setIterationCount(int iterationCount) {
        this.iterationCount = iterationCount;
    }

    public long getSumResponseTime() {
        return sumResponseTime;
    }

    public void setSumResponseTime(long sumResponseTime) {
        this.sumResponseTime = sumResponseTime;
    }




}
