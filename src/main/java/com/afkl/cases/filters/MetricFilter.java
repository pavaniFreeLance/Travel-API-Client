package com.afkl.cases.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afkl.cases.constants.Constants;
import com.afkl.cases.service.MetricService;

@Component
public class MetricFilter implements Filter {

	@Autowired
	private MetricService metricService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = ((HttpServletRequest) request);
		httpRequest.setAttribute(Constants.START_TIME, System.currentTimeMillis());

		chain.doFilter(request, response);

		final int status = ((HttpServletResponse) response).getStatus();

		metricService.increaseCount(status,
				System.currentTimeMillis() - Long.parseLong(httpRequest.getAttribute(Constants.START_TIME).toString()));

	}

}
