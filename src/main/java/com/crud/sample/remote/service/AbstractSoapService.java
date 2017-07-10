package com.crud.sample.remote.service;

import java.io.IOException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.crud.sample.exception.remote.RemoteCallException;
import com.crud.soap.calculaor.ObjectFactory;

public abstract class AbstractSoapService {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractSoapService.class);
	
	@Autowired
	private WebServiceTemplate webServiceTemplate;
	
	public <I, O> O performCall(I input) throws RemoteCallException{
		try {

			O response = (O) this.webServiceTemplate.marshalSendAndReceive(input);
			
			return response;
			
		} catch (Exception e) {
			String errMsg = "Failed to fetch response from remote soap service";
			
			logger.error(errMsg, e);
			throw new RemoteCallException(errMsg, e);
		}
	}
	
	public static class RemoveContentLengthInterceptor implements HttpRequestInterceptor {

		@Override
		public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
			if (request instanceof HttpEntityEnclosingRequest) {				
				if (request.containsHeader(HTTP.CONTENT_LEN)) {
					request.removeHeaders(HTTP.CONTENT_LEN);
				}
			}
		}
	}
	
	
}
