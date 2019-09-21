package com.np.dummy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StopWatch;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public class RequestResponseFilter extends AbstractRequestLoggingFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		HttpServletRequest requestToUse = request;
		HttpServletResponse responseToUse = response;

		if (request instanceof ContentCachingRequestWrapper) {
			requestToUse = new ContentCachingRequestWrapper(request);
		}
		if (response instanceof ContentCachingResponseWrapper) {
			responseToUse = new ContentCachingResponseWrapper(response);
		}

		// Start StopWatch
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		try {
			filterChain.doFilter(requestToUse, responseToUse);
		} catch (Exception e) {
			System.out.println("Exception " + e);
		} finally {
			log(requestToUse,responseToUse,stopWatch);
		}

	}

	private void log(HttpServletRequest requestToUse, HttpServletResponse responseToUse, StopWatch stopWatch) {
		//Stop StopWatch
		stopWatch.stop();

		//Determine VuConnect's response time
		Long responseTime = stopWatch.getLastTaskTimeMillis();
		String requestLogMessage = generateRequestLogMessage(requestToUse);
		String responseLogMessage = generateResponseLogMessage(responseToUse);

		System.out.println("WebserviceURL:"+requestToUse.getRequestURL() + " Request Body: " + requestLogMessage + " Response Body: " + responseLogMessage + " ResponseTime: {} " + responseTime);
	}

	private String generateRequestLogMessage(HttpServletRequest requestToUse) {
		ContentCachingResponseWrapper wrapper = WebUtils.getNativeRequest(requestToUse, ContentCachingResponseWrapper.class);
		String requestBody="";

		if (wrapper != null) {
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {

				try {
					requestBody = new String(buf, wrapper.getCharacterEncoding());
				}
				catch (UnsupportedEncodingException ex) {
					requestBody = "[unknown]";
				}
			}
		}
		return requestBody;
	}

	private String generateResponseLogMessage(HttpServletResponse responseToUse) {
		ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(responseToUse, ContentCachingResponseWrapper.class);
		String responseBody = "";
		
		if (wrapper != null) {
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				try {
					responseBody = new String(buf, wrapper.getCharacterEncoding());
				}
				catch (UnsupportedEncodingException ex) {
					responseBody = "[unknown]";
				}
			}
		}
		
		return responseBody;
	}


	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {

	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {

	}

}
