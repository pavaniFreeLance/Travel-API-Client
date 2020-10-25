package com.afkl.cases.service;

import com.afkl.cases.bean.MetricBean;

public interface MetricService {

	void increaseCount(final int status, long responseTimeInMilli);

	MetricBean getFullMetric();

}
