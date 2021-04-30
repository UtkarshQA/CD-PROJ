package com.MyAccount.Common;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpException;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;

import com.MyAccount.Utilities.TestBase;

public class CommonMethods extends TestBase {
	public By lbl_productName;
	public By lbl_policyNumber;
	public By lbl_paymentFrequecyType;
	public By lbl_paymentMethod;
	public By lbl_amountDue;
	public By lbl_dueDate;
	public By lbl_paymentStatus;
	public By btn_payNow;
	public By rbtn;

	public void deleteUser(String companyID, String screenName) throws Exception {
//		String companyID = "330016";
//		String screenName = "pavan";
		seleniumUtil.openURL(properties.getProperty("LR_URL"));
		seleniumUtil.waitForElementVisible(liferay.txt_screenName);
		seleniumUtil.setText(liferay.txt_screenName, properties.getProperty("LR_UserName"));
		seleniumUtil.setText(liferay.txt_password, properties.getProperty("LR_Password"));
		seleniumUtil.click(liferay.btn_sigin);
		seleniumUtil.waitForElementVisible(liferay.lnk_controlPanel);
		seleniumUtil.clickByJS(liferay.lnk_controlPanel);
		seleniumUtil.waitForElementVisible(liferay.lnk_configuration);
		seleniumUtil.click(liferay.lnk_configuration);
		seleniumUtil.waitForElementVisible(liferay.lnk_serverAdminPortlet);
		seleniumUtil.click(liferay.lnk_serverAdminPortlet);
		seleniumUtil.waitForElementPresent(liferay.lnk_script);
		seleniumUtil.click(liferay.lnk_script);
		seleniumUtil.selectByValue(liferay.ddl_language, "groovy");
		String script = "user = com.liferay.portal.kernel.service.UserLocalServiceUtil.getUserByScreenName(" + companyID
				+ "," + screenName + ");\r\n"
				+ "policy = agia.policy.service.PolicyLocalServiceUtil.getPolicyByUserId(user.getUserId());\r\n"
				+ "agia.policy.service.PolicyLocalServiceUtil.deletePolicy(policy.getId());\r\n"
				+ "out.println(\"policy  Deleted\");\r\n"
				+ "com.liferay.portal.kernel.service.UserLocalServiceUtil.deleteUser(user);\r\n"
				+ "out.println(\"User Deleted\");";
		System.out.println(script);
		seleniumUtil.setText(liferay.txt_script, script);
		seleniumUtil.clickByJS(liferay.btn_execute);
		Thread.sleep(5000);
	}

	public void userLogin(String userName, String password) {
		seleniumUtil.waitForElementVisible(login.txt_userName);
		seleniumUtil.setText(login.txt_userName, userName);
		seleniumUtil.setText(login.txt_password, password);
		seleniumUtil.click(login.btn_login_enable);
		seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
		seleniumUtil.waitForElementVisible(commonObjectRepositories.lnk_logout);
	}

	public void setProductTableXpathAtPaymentCenter_ProductsPage(String strPolicyNumber) {
		rbtn = By.xpath("//input[@id='" + strPolicyNumber+ "']");

		lbl_productName = By.xpath("//table[contains(@class,'productTable')]//span[text()='" + strPolicyNumber
				+ "']/parent::td//a//span[@class='anchorText']");
		lbl_policyNumber = By.xpath("//table[contains(@class,'productTable')]//span[text()='" + strPolicyNumber + "']");
		lbl_paymentFrequecyType = By.xpath("//table[contains(@class,'productTable')]//span[text()='" + strPolicyNumber
				+ "']/parent::td/following-sibling::td[1]");
		lbl_paymentMethod = By.xpath("//table[contains(@class,'productTable')]//span[text()='" + strPolicyNumber
				+ "']/parent::td/following-sibling::td[2]");

		lbl_amountDue = By.xpath("//table[contains(@class,'productTable')]//span[text()='" + strPolicyNumber
				+ "']/parent::td/following-sibling::td[3]");
		lbl_dueDate = By.xpath("//table[contains(@class,'productTable')]//span[text()='" + strPolicyNumber
				+ "']/parent::td/following-sibling::td[4]//span[1]");
		lbl_paymentStatus = By.xpath("//table[contains(@class,'productTable')]//span[text()='" + strPolicyNumber
				+ "']/parent::td/following-sibling::td[4]//span[2]");

	}

	public void setProductTableXpathAtHomePage(int productIndex) {
		lbl_productName = By
				.xpath("//table[contains(@class,'productTable')]//tbody//tr[" + productIndex + "]//td[1]/a/span[1]");
		lbl_policyNumber = By
				.xpath("//table[contains(@class,'productTable')]//tbody//tr[" + productIndex + "]//td[1]/span[1]");
		lbl_paymentFrequecyType = By
				.xpath("//table[contains(@class,'productTable')]//tbody//tr[" + productIndex + "]//td[2]");
		lbl_amountDue = By.xpath("//table[contains(@class,'productTable')]//tbody//tr[" + productIndex + "]//td[3]");
		lbl_dueDate = By
				.xpath("//table[contains(@class,'productTable')]//tbody//tr[" + productIndex + "]//td[4]//span[1]");
		lbl_paymentStatus = By
				.xpath("//table[contains(@class,'productTable')]//tbody//tr[" + productIndex + "]//td[4]//span[2]");
	}

	public void setProductTableXpathflexWhiteBoxAtHomePage(int payNowIndex) {
		lbl_productName = By.xpath("(//div[@class='flexWhiteBox']//a[1])[" + payNowIndex + "]");
		lbl_paymentStatus = By.xpath(
				"(//div[@class='flexWhiteBox']//div[contains(@class,'headerColumn')]//span[contains(@class,'duePaymentButton') or  contains(@class,'pastdue')])["
						+ payNowIndex + "]");
		lbl_policyNumber = By.xpath("(//div[@class='flexWhiteBox']//li[1]/span[2])[" + payNowIndex + "]");
		lbl_paymentFrequecyType = By.xpath("(//div[@class='flexWhiteBox']//li[2]/span[2])[" + payNowIndex + "]");
		lbl_dueDate = By.xpath("(//div[@class='flexWhiteBox']//li[3]/span[2])[" + payNowIndex + "]");
		lbl_amountDue = By.xpath("(//div[@class='flexWhiteBox']//li[4]/span[2])[" + payNowIndex + "]");
		btn_payNow = By.xpath(
				"(//div[@class='flexWhiteBox']//div[@class='bottomButton']/button[contains(@class,'agiaBlueButton payNow')])["
						+ payNowIndex + "]");

	}

	public void delete_paymentMethod(String paymentType) throws ClassNotFoundException, ParseException, JSONException,
			SQLException, IOException, HttpException, URISyntaxException {
		JSONObject JSOPaymentMethods = request.GetPaymentMethodsResponse();
		JSONArray JSArrayPaymentMethods = JSOPaymentMethods.getJSONArray("paymentMethods");

		for (int i = 0; i < JSArrayPaymentMethods.length(); i++) {
			JSONObject pm = JSArrayPaymentMethods.getJSONObject(i);
			JSONObject paymentMethod = pm.getJSONObject("paymentMethod");
			String paymentMethodHeader = (String) paymentMethod.get("paymentMethodHeader");

			if (paymentMethodHeader.equals(paymentType)) {
				Object paymentRecordId = paymentMethod.get("paymentRecordId");
				JSONObject JSODeletePaymentMethod = request.DeletePaymentMethodResponse((String) paymentRecordId);
				if(DeletePaymentResponse == true) {
					String statusMessage = JSODeletePaymentMethod.getString("statusMessage");
					System.out.println(statusMessage);
				}else {
					JSONArray messages = JSODeletePaymentMethod.getJSONArray("messages");
					JSONObject JSObjectMessages = messages.getJSONObject(0);
					JSONObject message = JSObjectMessages.getJSONObject("message");
					JSONArray content = message.getJSONArray("content");
					String msg = (String) content.get(0).toString();
					System.out.println(msg);
				}
				
			}

		}
	}

	public void add_new_paymentMethod_CC(String cardType, String cardNumber) throws Exception {

		String cardHolderName = "Utkarsh Patel";
		String random_address_1 = "Updated address_1_" + seleniumUtil.generateString(3);
		String random_address_2 = "Updated address_2_" + seleniumUtil.generateString(3);
		String random_address_3 = "Updated address_3_" + seleniumUtil.generateString(3);
		String random_city = "Updated city_" + seleniumUtil.generateString(3);
		String state = "UT";
		String zipCode = "223344";
		String expMonth = "Feb";
		String expYear = "2022";

		// Add new payment method popup should open.
		seleniumUtil.waitForElementVisible(paymentMethods.rbtn_creditOrDebitCard);
		seleniumUtil.clickByJS(paymentMethods.rbtn_creditOrDebitCard);
		seleniumUtil.selectByText(paymentMethods.ddl_cardType, cardType);
		seleniumUtil.setText(paymentMethods.txt_cardHolderName, cardHolderName);
		seleniumUtil.setText(paymentMethods.txt_cardNumber, cardNumber);
		seleniumUtil.selectByText(paymentMethods.ddl_expMonth, expMonth);
		seleniumUtil.selectByText(paymentMethods.ddl_expYear, expYear);
		seleniumUtil.setText(paymentMethods.txt_address_1, random_address_1);
		seleniumUtil.setText(paymentMethods.txt_address_2, random_address_2);
		seleniumUtil.setText(paymentMethods.txt_address_3, random_address_3);
		seleniumUtil.setText(paymentMethods.txt_city, random_city);
		seleniumUtil.selectByText(paymentMethods.ddl_selectedState, state);
		seleniumUtil.setText(paymentMethods.txt_zipCode, zipCode);
		if (seleniumUtil.isElementDisplayed(payNow.chk_rememberPayment) == true) {
			seleniumUtil.click(payNow.chk_rememberPayment);
		}
		Thread.sleep(1000);
		seleniumUtil.clickByJS(paymentMethods.btn_enable_saveChanges);
		seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
		System.out.println(cardNumber + "Card number payment method added.");
		logger.info(cardNumber + " Card number payment method added.");
	}

	public void add_new_paymentMethod_BankAccount(String routingNumber, String accountNumber) throws Exception {
		String accountName = "Utkarsh Patel";
		String random_address_1 = "Updated address_1_" + seleniumUtil.generateString(3);
		String random_address_2 = "Updated address_2_" + seleniumUtil.generateString(3);
		String random_address_3 = "Updated address_3_" + seleniumUtil.generateString(3);
		String random_city = "Updated city_" + seleniumUtil.generateString(3);
		String state = "UT";
		String zipCode = "223344";
		seleniumUtil.waitForElementPresent(paymentMethods.rbtn_bankAccount);
		seleniumUtil.clickByJS(paymentMethods.rbtn_bankAccount);
		seleniumUtil.setText(paymentMethods.txt_accountName, accountName);
		seleniumUtil.setText(paymentMethods.txt_routingNumber, routingNumber);
		seleniumUtil.setText(paymentMethods.txt_confirmRountingNumber, routingNumber);
		seleniumUtil.setText(paymentMethods.txt_accountNumber, accountNumber);
		seleniumUtil.setText(paymentMethods.txt_confirmAccountNumber, accountNumber);
		seleniumUtil.setText(paymentMethods.txt_address_1, random_address_1);
		seleniumUtil.setText(paymentMethods.txt_address_2, random_address_2);
		seleniumUtil.setText(paymentMethods.txt_address_3, random_address_3);
		seleniumUtil.setText(paymentMethods.txt_city, random_city);
		seleniumUtil.selectByText(paymentMethods.ddl_selectedState, state);
		seleniumUtil.setText(paymentMethods.txt_zipCode, zipCode);

		if (seleniumUtil.isElementDisplayed(payNow.chk_rememberPayment) == true) {
			seleniumUtil.click(payNow.chk_rememberPayment);
		}

		Thread.sleep(1000);
		seleniumUtil.clickByJS(paymentMethods.btn_enable_saveChanges);
		seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
		System.out.println(accountNumber + "Bank account payment method added.");

		logger.info(accountNumber + " Bank account payment method added.");
	}

	public String remove_info_outline(String amount) {
		amount = amount.replace("info_outline", "");
		return amount;
	}
	
	public String remove_arrow_back(String productName) {
		productName = productName.replace("arrow_back", "").trim();
		return productName;
	}

	public String month(String strMonth) {

		if (strMonth.equals("Jan") == true) {
			strMonth = "01";
		} else if (strMonth.equals("Feb") == true) {
			strMonth = "02";
		} else if (strMonth.equals("Mar") == true) {
			strMonth = "03";
		} else if (strMonth.equals("Apr") == true) {
			strMonth = "04";
		} else if (strMonth.equals("May") == true) {
			strMonth = "05";
		} else if (strMonth.equals("Jun") == true) {
			strMonth = "06";
		} else if (strMonth.equals("Jul") == true) {
			strMonth = "07";
		} else if (strMonth.equals("Aug") == true) {
			strMonth = "08";
		} else if (strMonth.equals("Sep") == true) {
			strMonth = "09";
		} else if (strMonth.equals("Oct") == true) {
			strMonth = "10";
		} else if (strMonth.equals("Nov") == true) {
			strMonth = "11";
		} else if (strMonth.equals("Dec") == true) {
			strMonth = "12";
		}

		return strMonth;

	}

	public String nextYear() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		Date nextYear = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String nextExpYear = formatter.format(nextYear);
		return nextExpYear;

	}
	
	public String message_content(JSONObject JSONObject) {
		JSONArray messages = JSONObject.getJSONArray("messages");
		JSONObject JSObjectMessages = messages.getJSONObject(0);
		JSONObject message = JSObjectMessages.getJSONObject("message");
		JSONArray content = message.getJSONArray("content");
		String msg = (String) content.get(0).toString();
		return msg;
	}

}
