package com.afkl.cases.dto;

public class MetricBeanDto {

	private long totalRequests;
	private long totalOKResponse;
	private long total400Response;
	private long total500Response;
	private long avgResponseTime;
	private long minResponseTime;
	private long maxReponseTime;
	private long totalResponseTime;

	public MetricBeanDto() {

	}

	public MetricBeanDto(long totalRequests, long totalOKResponse, long total400Response, long total500Response,
			long avgResponseTime, long minResponseTime, long maxReponseTime, long totalResponseTime) {
		super();
		this.totalRequests = totalRequests;
		this.totalOKResponse = totalOKResponse;
		this.total400Response = total400Response;
		this.total500Response = total500Response;
		this.avgResponseTime = avgResponseTime;
		this.minResponseTime = minResponseTime;
		this.maxReponseTime = maxReponseTime;
		this.totalResponseTime = totalResponseTime;
	}

	public long getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(long totalRequests) {
		this.totalRequests = totalRequests;
	}

	public long getTotalOKResponse() {
		return totalOKResponse;
	}

	public void setTotalOKResponse(long totalOKResponse) {
		this.totalOKResponse = totalOKResponse;
	}

	public long getTotal400Response() {
		return total400Response;
	}

	public void setTotal400Response(long total400Response) {
		this.total400Response = total400Response;
	}

	public long getTotal500Response() {
		return total500Response;
	}

	public void setTotal500Response(long total500Response) {
		this.total500Response = total500Response;
	}

	public long getAvgResponseTime() {
		return avgResponseTime;
	}

	public void setAvgResponseTime(long avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}

	public long getMinResponseTime() {
		return minResponseTime;
	}

	public void setMinResponseTime(long minResponseTime) {
		this.minResponseTime = minResponseTime;
	}

	public long getMaxReponseTime() {
		return maxReponseTime;
	}

	public void setMaxReponseTime(long maxReponseTime) {
		this.maxReponseTime = maxReponseTime;
	}

	public long getTotalResponseTime() {
		return totalResponseTime;
	}

	public void setTotalResponseTime(long totalResponseTime) {
		this.totalResponseTime = totalResponseTime;
	}

}
