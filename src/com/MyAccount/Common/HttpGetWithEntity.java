package com.MyAccount.Common;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase{
	 public final static String METHOD_NAME = "GET";

	    public HttpGetWithEntity(String uri) throws URISyntaxException{
	        this.setURI(new URI(uri));
	    }

	    public HttpGetWithEntity(URI uri){
	        this.setURI(uri);
	    }

	
	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return METHOD_NAME;
	}

}
