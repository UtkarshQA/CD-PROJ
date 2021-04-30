package com.MyAccount.Common;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.MyAccount.Utilities.TestBase;

public class Request extends TestBase {

	public JSONObject GetMyProfileResponse() throws ClassNotFoundException, SQLException, ParseException, IOException,
			JSONException, HttpException, URISyntaxException {
		String PROFILE_URL = properties.getProperty("C_URL") + "/profile";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(PROFILE_URL);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(builder.build());
		httpGet.setHeader("Content-Type", "application/json");
		JSONObject myProfileMethodObject = requestBody();
		StringEntity myProfileMethodEntity = new StringEntity(myProfileMethodObject.toString());
		httpGet.setEntity(myProfileMethodEntity);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int ProfileResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
		String myProfileJsonString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject myProfileResponseJson = new JSONObject(myProfileJsonString);
		if (ProfileResponseStatusCode <= 200 || ProfileResponseStatusCode < 300) {
			ProfileResponse = true;
		} else {
			ProfileResponse = false;
			ProfileResponse_message_content = commonMethods.message_content(myProfileResponseJson);
		}
		return myProfileResponseJson;
	}

	public JSONObject GetInsproserviceResponse() throws ClassNotFoundException, SQLException, ParseException,
			IOException, JSONException, HttpException, URISyntaxException {
		String REQUEST_URL = g_url + "/o/insproservice/v1.0/";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(REQUEST_URL);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(builder.build());
		httpGet.setHeader("Content-Type", "application/json");
		HttpResponse httpResponse = httpClient.execute(httpGet);
		JSONObject myProfileResponseJson = null;
		try {
			int ProfileResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
			String myProfileJsonString = EntityUtils.toString(httpResponse.getEntity());
			myProfileResponseJson = new JSONObject(myProfileJsonString);
			if (ProfileResponseStatusCode <= 200 || ProfileResponseStatusCode < 300) {
				InsproserviceResponse = true;
			} else {
				InsproserviceResponse = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			InsproserviceResponse = false;
		}
		return myProfileResponseJson;
	}

	public JSONObject GetPaymentMethodsResponse() throws ClassNotFoundException, SQLException, ParseException,
			IOException, JSONException, HttpException, URISyntaxException {
		String REQUEST_URL = properties.getProperty("C_URL") + "/payment/paymentMethods";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(REQUEST_URL);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(builder.build());
		httpGet.setHeader("Content-Type", "application/json");
		JSONObject myProfileMethodObject = requestBody();
		StringEntity myProfileMethodEntity = new StringEntity(myProfileMethodObject.toString());
		httpGet.setEntity(myProfileMethodEntity);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int PaymentMethodsStatusCode = httpResponse.getStatusLine().getStatusCode();
		String myProfileJsonString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject myProfileResponseJson = new JSONObject(myProfileJsonString);
		if (PaymentMethodsStatusCode <= 200 || PaymentMethodsStatusCode < 300) {
			PaymentMethodsResponse = true;
		} else {
			PaymentMethodsResponse = false;
			PaymentMethodsResponse_message_content = commonMethods.message_content(myProfileResponseJson);
		}
		return myProfileResponseJson;
	}

	public JSONObject GetProductSummaryResponse() throws ClassNotFoundException, SQLException, ParseException,
			IOException, JSONException, HttpException, URISyntaxException {
		String REQUEST_URL = properties.getProperty("C_URL") + "/product/summary";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(REQUEST_URL);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(builder.build());
		httpGet.setHeader("Content-Type", "application/json");
		JSONObject myProductSummaryObject = requestBody();
		StringEntity myProductSummaryEntity = new StringEntity(myProductSummaryObject.toString());
		httpGet.setEntity(myProductSummaryEntity);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int ProductSummaryResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
		String myProductSummaryJsonString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject myProductSummaryResponseJson = new JSONObject(myProductSummaryJsonString);
		if (ProductSummaryResponseStatusCode <= 200 || ProductSummaryResponseStatusCode < 300) {
			ProductSummaryResponse = true;
		} else {
			ProductSummaryResponse = false;
			ProductSummaryResponse_message_content = commonMethods.message_content(myProductSummaryResponseJson);
		}
		return myProductSummaryResponseJson;
	}

	public JSONObject GetHomepageResponse() throws ClassNotFoundException, SQLException, ParseException, IOException,
			JSONException, HttpException, URISyntaxException {
		String REQUEST_URL = properties.getProperty("C_URL") + "/homepage";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(REQUEST_URL);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(builder.build());
		httpGet.setHeader("Content-Type", "application/json");
		JSONObject myHomeObject = requestBody();
		StringEntity myHomeEntity = new StringEntity(myHomeObject.toString());
		httpGet.setEntity(myHomeEntity);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int HomepageResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
		String myHomeJsonString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject myHomeResponseJson = new JSONObject(myHomeJsonString);
		if (HomepageResponseStatusCode <= 200 || HomepageResponseStatusCode < 300) {
			HomepageResponse = true;
		} else {
			HomepageResponse = false;
			HomepageResponse_message_content = commonMethods.message_content(myHomeResponseJson);
		}
		return myHomeResponseJson;
	}

	public JSONObject GetPaymentResponse() throws ClassNotFoundException, SQLException, ParseException, IOException,
			JSONException, HttpException, URISyntaxException {
		String REQUEST_URL = properties.getProperty("C_URL") + "/payment";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(REQUEST_URL);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(builder.build());
		httpGet.setHeader("Content-Type", "application/json");
		JSONObject myPaymentMethodObject = requestBody();
		StringEntity myPaymentMethodEntity = new StringEntity(myPaymentMethodObject.toString());
		httpGet.setEntity(myPaymentMethodEntity);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int PaymentResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
		String myPaymentJsonString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject myPaymentResponseJson = new JSONObject(myPaymentJsonString);
		if (PaymentResponseStatusCode <= 200 || PaymentResponseStatusCode < 300) {
			PaymentResponse = true;
		} else {
			PaymentResponse = false;
			PaymentResponse_message_content = commonMethods.message_content(myPaymentResponseJson);
		}
		return myPaymentResponseJson;
	}

	public JSONObject GetPayNowResponse_homePage(String policyNumber) throws ClassNotFoundException, SQLException,
			ParseException, IOException, JSONException, HttpException, URISyntaxException {
		String REQUEST_URL = properties.getProperty("C_URL") + "/homepage/paynow";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(REQUEST_URL);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(builder.build());
		httpGet.setHeader("Content-Type", "application/json");
		JSONObject payNowObject = requestBody_productDetail(policyNumber);
		StringEntity payNowEntity = new StringEntity(payNowObject.toString());
		httpGet.setEntity(payNowEntity);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int PayNowResponse_homePageStatusCode = httpResponse.getStatusLine().getStatusCode();
		String payNowJsonString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject payNowResponseJson = new JSONObject(payNowJsonString);
		if (PayNowResponse_homePageStatusCode <= 200 || PayNowResponse_homePageStatusCode < 300) {
			PayNow_homePageResponse = true;
		} else {
			PayNow_homePageResponse = false;
			PayNow_homePageResponse_message_content = commonMethods.message_content(payNowResponseJson);
		}
		return payNowResponseJson;
	}

	public JSONObject DeletePaymentMethodResponse(String recordId) throws ClassNotFoundException, SQLException,
			ParseException, IOException, JSONException, HttpException, URISyntaxException {
		String REQUEST_URL = properties.getProperty("C_URL") + "/payment/paymentMethod";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(REQUEST_URL);
		HttpDeleteWithBody httpdelete = new HttpDeleteWithBody(builder.build());
		httpdelete.setHeader("Content-Type", "application/json");
		JSONObject deletPaymentMethodObject = requestBody_deletePaymentMethod(recordId);
		StringEntity deletPaymentMethodEntity = new StringEntity(deletPaymentMethodObject.toString());
		httpdelete.setEntity(deletPaymentMethodEntity);
		HttpResponse httpResponse = httpClient.execute(httpdelete);
		int DeletePaymentStatusCode = httpResponse.getStatusLine().getStatusCode();
		String deletPaymentMethodJsonString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject deletPaymentMethodResponseJson = new JSONObject(deletPaymentMethodJsonString);
		if (DeletePaymentStatusCode <= 200 || DeletePaymentStatusCode < 300) {
			DeletePaymentResponse = true;
		} else {
			DeletePaymentResponse = false;
			DeletePaymentResponse_message_content = commonMethods.message_content(deletPaymentMethodResponseJson);
		}
		return deletPaymentMethodResponseJson;
	}

	public JSONObject GetProductsDetailsResponse(String policyNumber) throws ClassNotFoundException, SQLException,
			ParseException, IOException, JSONException, HttpException, URISyntaxException {
		String REQUEST_URL = properties.getProperty("C_URL") + "/product/details";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(REQUEST_URL);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(builder.build());
		httpGet.setHeader("Content-Type", "application/json");
		JSONObject myProductsDetailsMethodObject = requestBody_productDetail(policyNumber);
		StringEntity myProductsDetailsMethodEntity = new StringEntity(myProductsDetailsMethodObject.toString());
		httpGet.setEntity(myProductsDetailsMethodEntity);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int ProductsDetailsResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
		String myProductsDetailsJsonString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject myProductsDetailsResponseJson = new JSONObject(myProductsDetailsJsonString);
		if (ProductsDetailsResponseStatusCode <= 200 || ProductsDetailsResponseStatusCode < 300) {
			ProductsDetailsResponse = true;
		} else {
			ProductsDetailsResponse = false;
			ProductsDetailsResponse_message_content = commonMethods.message_content(myProductsDetailsResponseJson);
		}
		return myProductsDetailsResponseJson;
	}

	public JSONObject GetAutoPaymentPreferenceResponse(String policyNumber) throws ClassNotFoundException, SQLException,
			ParseException, IOException, JSONException, HttpException, URISyntaxException {
		String REQUEST_URL = properties.getProperty("C_URL") + "/payment/autoPaymentPreference";
		HttpClient httpClient = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(REQUEST_URL);
		HttpGetWithEntity httpGet = new HttpGetWithEntity(builder.build());
		httpGet.setHeader("Content-Type", "application/json");
		JSONObject autoPaymentPreferenceResponseMethodObject = requestBody_productDetail(policyNumber);
		StringEntity autoPaymentPreferenceEntity = new StringEntity(autoPaymentPreferenceResponseMethodObject.toString());
		httpGet.setEntity(autoPaymentPreferenceEntity);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		int  AutoPaymentPreferenceResponseStatusCode = httpResponse.getStatusLine().getStatusCode();
		String autoPaymentPreferenceJsonString = EntityUtils.toString(httpResponse.getEntity());
		JSONObject autoPaymentPreferenceResponseJson = new JSONObject(autoPaymentPreferenceJsonString);
		if (AutoPaymentPreferenceResponseStatusCode <= 200 || AutoPaymentPreferenceResponseStatusCode < 300) {
			AutoPaymentPreferenceResponse = true;
		} else {
			AutoPaymentPreferenceResponse = false;
			AutoPaymentPreferenceResponse_message_content = commonMethods.message_content(autoPaymentPreferenceResponseJson);
		}
		return autoPaymentPreferenceResponseJson;
	}

//	private JSONObject requestBody_oneTimePayment(String policyNumber, String totalPaymentAmount,
//			String paymentMethodRecordId) {
//		JSONObject createOneTimePaymentObject = new JSONObject();
//		JSONArray paymentDetailsArray = new JSONArray();
//		JSONObject policyHolderJsonObjct = new JSONObject();
//		policyHolderJsonObjct.put("firstName", g_firstName);
//		policyHolderJsonObjct.put("lastName", g_lastName);
//		createOneTimePaymentObject.put("client", g_client);
//		createOneTimePaymentObject.put("accountNumber", g_accountNumber);
//		createOneTimePaymentObject.put("policyNumber", policyNumber);
//		createOneTimePaymentObject.put("policyHolder", policyHolderJsonObjct);
//		createOneTimePaymentObject.put("userid", g_screenName);
//		createOneTimePaymentObject.put("totalPaymentAmount", totalPaymentAmount);
//		JSONObject paymentDetailsJsonObject = new JSONObject();
//		JSONObject paymentDetailJsonObject = new JSONObject();
//		paymentDetailJsonObject.put("accountNumber", g_accountNumber);
//		paymentDetailJsonObject.put("policyNumber", policyNumber);
//		paymentDetailJsonObject.put("paymentAmount", totalPaymentAmount);
//		paymentDetailJsonObject.put("paymentMethodRecordId", paymentMethodRecordId);
//		paymentDetailsJsonObject.put("paymentDetail", paymentDetailJsonObject);
//		paymentDetailsArray.put(paymentDetailsJsonObject);
//		createOneTimePaymentObject.put("paymentDetails", paymentDetailsArray);
//		return createOneTimePaymentObject;
//	}

	private JSONObject requestBody_productDetail(String policyNumber) throws ClassNotFoundException, SQLException {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("client", g_client);
		requestJsonObject.put("accountNumber", g_accountNumber);
		requestJsonObject.put("policyNumber", policyNumber);
		JSONObject jsonObjectpolicyHolder = new JSONObject();
		jsonObjectpolicyHolder.put("firstName", g_firstName);
		jsonObjectpolicyHolder.put("lastName", g_lastName);
		requestJsonObject.put("policyHolder", jsonObjectpolicyHolder);
		requestJsonObject.put("userid", g_screenName);
		return requestJsonObject;
	}

	private JSONObject requestBody() throws ClassNotFoundException, SQLException {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("client", g_client);
		requestJsonObject.put("accountNumber", g_accountNumber);
		requestJsonObject.put("policyNumber", g_planNumber);
		JSONObject jsonObjectpolicyHolder = new JSONObject();
		jsonObjectpolicyHolder.put("firstName", g_firstName);
		jsonObjectpolicyHolder.put("lastName", g_lastName);
		requestJsonObject.put("policyHolder", jsonObjectpolicyHolder);
		requestJsonObject.put("userid", g_screenName);
		return requestJsonObject;
	}

	private JSONObject requestBody_deletePaymentMethod(String recordId) throws ClassNotFoundException, SQLException {
		JSONObject requestJsonObject = new JSONObject();
		requestJsonObject.put("client", g_client);
		requestJsonObject.put("accountNumber", g_accountNumber);
		requestJsonObject.put("policyNumber", g_planNumber);
		JSONObject jsonObjectpolicyHolder = new JSONObject();
		jsonObjectpolicyHolder.put("firstName", g_firstName);
		jsonObjectpolicyHolder.put("lastName", g_lastName);
		requestJsonObject.put("policyHolder", jsonObjectpolicyHolder);
		requestJsonObject.put("userid", g_screenName);
		requestJsonObject.put("recordId", recordId);
		return requestJsonObject;
	}

}
