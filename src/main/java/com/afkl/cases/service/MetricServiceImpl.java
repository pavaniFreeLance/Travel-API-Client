package com.afkl.cases.service;

import org.springframework.stereotype.Service;

import com.afkl.cases.bean.MetricBean;

@Service
public class MetricServiceImpl implements MetricService {

	private MetricBean metricBean;
	private Object lock = new Object();

	public MetricServiceImpl() {

		metricBean = new MetricBean(0, 0, 0, 0, 0, Long.MAX_VALUE, Long.MIN_VALUE, 0);

	}

	@Override
	public void increaseCount(final int status, long responseTimeInMilli) {
		incrementStatusMetric(status, responseTimeInMilli);

	}

	private void incrementStatusMetric(final int status, long responseTimeInMilli) {

		/*
		 * Updating totalRequests totalOKResponse, total400Response total500Response,
		 * avgResponseTime, minResponseTime MaxReponseTime, totalResponseTime
		 */
		synchronized (lock) {

			switch (status / 100) {
			case 2:
				metricBean.setTotalOKResponse(metricBean.getTotalOKResponse() + 1);
				break;
			case 4:
				metricBean.setTotal400Response(metricBean.getTotal400Response() + 1);
				break;
			case 5:
				metricBean.setTotal500Response(metricBean.getTotal500Response() + 1);
				break;
			}
			metricBean.setTotalRequests(metricBean.getTotalRequests() + 1);
			metricBean.setTotalResponseTime(metricBean.getTotalResponseTime() + responseTimeInMilli);
			metricBean.setMinResponseTime(Math.min(metricBean.getMinResponseTime(), responseTimeInMilli));
			metricBean.setMaxReponseTime(Math.max(metricBean.getMaxReponseTime(), responseTimeInMilli));
			metricBean.setAvgResponseTime(metricBean.getTotalResponseTime() / metricBean.getTotalRequests());

		}

	}

	@Override
	public MetricBean getFullMetric() {
		return metricBean;
	}

}
