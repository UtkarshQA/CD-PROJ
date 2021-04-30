package com.MyAccount.Common;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
	
	public static final String METHOD_NAME = "DELETE";
	
	@Override
    public String getMethod() { return METHOD_NAME; }

    public HttpDeleteWithBody(String uri) throws URISyntaxException {
        this.setURI(new URI(uri));
    }
    public HttpDeleteWithBody(URI uri) {
    	this.setURI(uri);
     }
    
       

}
