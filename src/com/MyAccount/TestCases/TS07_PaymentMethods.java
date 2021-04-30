package com.MyAccount.TestCases;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS07_PaymentMethods extends TestBase {
	private String random_address_1;
	private String random_address_2;
	private String random_address_3;
	private String random_city;
	private String state;
	private String zipCode;
	private String expMonth;

	private String expYear = commonMethods.nextYear();

	private String cardType;
	private String cardHolderName;
	private String maskCardNumber;
	private String maskedRoutingNumber;
	private String maskedAccountNumber;
	private String routingNumber;
	private String accountName;

	@BeforeTest
	private void preCondition() throws Exception {
		System.out.println("***** Start Test Suite: " + getClass().getSimpleName() + " *****");
		seleniumUtil.openBrowser();
		seleniumUtil.openURL(g_url);
		commonMethods.userLogin(g_userName, g_userPassword);
		seleniumUtil.waitForElementClickable(commonObjectRepositories.lnk_paymentCenter);
		seleniumUtil.clickByJS(commonObjectRepositories.lnk_paymentCenter);
		seleniumUtil.waitForElementClickable(paymentMethods.lnk_paymentMethods);
		seleniumUtil.clickByJS(paymentMethods.lnk_paymentMethods);
		seleniumUtil.waitForElementVisible(paymentMethods.btn_addPaymentMethod);

		request.GetPaymentMethodsResponse();
		if (PaymentMethodsResponse == true) {
			List<WebElement> listOfPaymentTypeName = driver.findElements(paymentMethods.lbl_paymentTypeName);

			if (listOfPaymentTypeName.size() != 0) {
				for (int i = 0; i < listOfPaymentTypeName.size(); i++) {
					String strPaymentTypeName = listOfPaymentTypeName.get(i).getText().trim();
					commonMethods.delete_paymentMethod(strPaymentTypeName);
				}
				seleniumUtil.refresh();
				seleniumUtil.waitForElementVisible(paymentMethods.btn_addPaymentMethod);
			} else {
				logger.info("No payment methods found.");
			}
		} else {
			logger.error(PaymentMethodsResponse_message_content);
		}

	}

	@Test
	private void paymentMethods_TC01() throws Exception {

		logger = extent.createTest(
				"To verify required feild filled after save change button should be enable at credit Or debit card.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			seleniumUtil.clickByJS(paymentMethods.btn_addPaymentMethod);
			seleniumUtil.waitForElementPresent(paymentMethods.rbtn_creditOrDebitCard);
			seleniumUtil.clickByJS(paymentMethods.rbtn_creditOrDebitCard);
			seleniumUtil.selectByIndex(paymentMethods.ddl_cardType, 2);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_cardHolderName, "Utkarsh Patel");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_cardNumber, "123213");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.selectByIndex(paymentMethods.ddl_expMonth, 2);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.selectByIndex(paymentMethods.ddl_expYear, 2);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_address_1, "Test address 1");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_city, "Test city");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.selectByIndex(paymentMethods.ddl_selectedState, 2);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_zipCode, "31231");
			Thread.sleep(1000);
			seleniumUtil.isElementDisplayed(paymentMethods.btn_enable_saveChanges);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_enable_saveChanges), true);
		} else {
			logger.error(PaymentMethodsResponse_message_content);
		}

	}

	@Test
	private void paymentMethods_TC02() throws Exception {
		logger = extent.createTest(
				"To verify max char length for card number, address 1, 2, 3, city, zipcode at credit Or debit card.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			seleniumUtil.setText(paymentMethods.txt_cardNumber, "123456789012345678");
			int cardNumber = seleniumUtil
					.getOnlyDigits(seleniumUtil.getAttributeValue(paymentMethods.txt_cardNumber, "value")).length();
			seleniumUtil.verify(cardNumber, 16);
			seleniumUtil.setText(paymentMethods.txt_address_1, "Address890123456789012345 Address");
			int add1_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value").length();
			seleniumUtil.verify(add1_lan, 30);
			seleniumUtil.setText(paymentMethods.txt_address_2, "Address890123456789012345 Address");
			int add2_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value").length();
			seleniumUtil.verify(add2_lan, 30);
			seleniumUtil.setText(paymentMethods.txt_address_3, "Address890123456789012345 Address");
			int add3_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value").length();
			seleniumUtil.verify(add3_lan, 30);
			seleniumUtil.setText(paymentMethods.txt_city, "City567889012345678901234567 City");
			int city_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value").length();
			seleniumUtil.verify(city_lan, 30);
			seleniumUtil.setText(paymentMethods.txt_zipCode, "123456789012");
			int zipCode_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value").length();
			seleniumUtil.verify(zipCode_lan, 10);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}

	}

	@Test
	private void paymentMethods_TC03() throws Exception {
		logger = extent.createTest("To verify Error validating card number range at credit Or debit card.")
				.assignCategory(getClass().getSimpleName());

		if (PaymentMethodsResponse == true) {
			
			cardType = "MasterCard";
			cardHolderName = "Utkarsh Patel";

			random_address_1 = "Updated address_1_" + seleniumUtil.generateString(3);
			random_address_2 = "Updated address_2_" + seleniumUtil.generateString(3);
			random_address_3 = "Updated address_3_" + seleniumUtil.generateString(3);
			random_city = "Updated city_" + seleniumUtil.generateString(3);
			state = "UT";
			zipCode = "223344";
			expMonth = "Feb";
			seleniumUtil.waitForElementVisible(paymentMethods.rbtn_creditOrDebitCard);
			seleniumUtil.click(paymentMethods.rbtn_creditOrDebitCard);
			seleniumUtil.selectByText(paymentMethods.ddl_cardType, cardType);
			seleniumUtil.setText(paymentMethods.txt_cardHolderName, cardHolderName);
			seleniumUtil.setText(paymentMethods.txt_cardNumber, "10101010");
			seleniumUtil.selectByText(paymentMethods.ddl_expMonth, expMonth);
			seleniumUtil.selectByText(paymentMethods.ddl_expYear, expYear);
			seleniumUtil.setText(paymentMethods.txt_address_1, random_address_1);
			seleniumUtil.setText(paymentMethods.txt_address_2, random_address_2);
			seleniumUtil.setText(paymentMethods.txt_address_3, random_address_3);
			seleniumUtil.setText(paymentMethods.txt_city, random_city);
			seleniumUtil.selectByText(paymentMethods.ddl_selectedState, state);
			seleniumUtil.setText(paymentMethods.txt_zipCode, zipCode);
			seleniumUtil.click(paymentMethods.btn_enable_saveChanges);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_val_msg_modalDialog);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg_modalDialog),
					consts.val_msg_validating_card);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
	}

	@Test
	private void paymentMethods_TC04() throws Exception {
		logger = extent.createTest("Add new credit Or debit card.").assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			seleniumUtil.setText(paymentMethods.txt_cardNumber, consts.cardNumber_MasterCard);
			seleniumUtil.click(paymentMethods.btn_enable_saveChanges);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
					consts.succ_msg_paymentMethod_add_succ);
		} else {
			logger.error(PaymentMethodsResponse_message_content);
		}
	}

	// Credit card

	@Test
	private void paymentMethods_TC05() throws Exception {
		logger = extent.createTest("To verify credit Or debit card details at payment method landing page.")
				.assignCategory(getClass().getSimpleName());
		JSONObject JSOPaymentMethods = request.GetPaymentMethodsResponse();
		if (PaymentMethodsResponse == true) {
			JSONArray JSArrayPaymentMethods = JSOPaymentMethods.getJSONArray("paymentMethods");
			JSONObject JSObjectPaymentMethods_1 = JSArrayPaymentMethods.getJSONObject(0);
			JSONObject JSObjectPaymentMethod_1 = JSObjectPaymentMethods_1.getJSONObject("paymentMethod");
			JSONObject JSObjectCreditCard = JSObjectPaymentMethod_1.getJSONObject("creditCard");

			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_1),
					JSObjectPaymentMethod_1.get("paymentMethodHeader"));
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_2), JSObjectPaymentMethod_1.get("cardHolderName"));
			maskCardNumber = (String) JSObjectCreditCard.get("cardNumber");
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_3), maskCardNumber);
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_4), JSObjectCreditCard.get("expirationDate"));
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
		

	}

	@Test
	private void paymentMethods_TC06() throws Exception {
		logger = extent.createTest("To verify edit payment method details at credit Or debit card.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			seleniumUtil.clickByJS(paymentMethods.lnk_edit);
			seleniumUtil.waitForElementVisible(paymentMethods.ddl_cardType);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_cardType), cardType);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_cardHolderName, "value"), cardHolderName);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_cardNumber, "value"), maskCardNumber);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_expMonth), expMonth);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_expYear), expYear);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value"), random_address_1);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value"), random_address_2);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value"), random_address_3);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value"), random_city);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_selectedState), state);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value"), zipCode);
			seleniumUtil.click(paymentMethods.btn_cancel);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
	
	}

	@Test
	private void paymentMethods_TC07() throws Exception {
		logger = extent.createTest("Update payment method details at credit Or debit card.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			
			random_address_1 = "Updated address_1_" + seleniumUtil.generateString(3);
			random_address_2 = "Updated address_2_" + seleniumUtil.generateString(3);
			random_address_3 = "Updated address_3_" + seleniumUtil.generateString(3);
			random_city = "Updated city_" + seleniumUtil.generateString(3);
			state = "VA";
			zipCode = "20101";
			expMonth = "Mar";
			seleniumUtil.clickByJS(paymentMethods.lnk_edit);
			seleniumUtil.waitForElementVisible(paymentMethods.txt_cardHolderName);
			seleniumUtil.selectByText(paymentMethods.ddl_expMonth, expMonth);
			seleniumUtil.selectByText(paymentMethods.ddl_expYear, expYear);
			seleniumUtil.setText(paymentMethods.txt_address_1, random_address_1);
			seleniumUtil.setText(paymentMethods.txt_address_2, random_address_2);
			seleniumUtil.setText(paymentMethods.txt_address_3, random_address_3);
			seleniumUtil.setText(paymentMethods.txt_city, random_city);
			seleniumUtil.selectByText(paymentMethods.ddl_selectedState, state);
			seleniumUtil.setText(paymentMethods.txt_zipCode, zipCode);
			seleniumUtil.click(paymentMethods.btn_enable_saveChanges);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
					consts.succ_msg_paymentMethod_update_succ);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
	
	}

	@Test
	private void paymentMethods_TC08() throws Exception {
		logger = extent.createTest("After updated, verify credit Or debit card details at payment method landing page.")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOPaymentMethods = request.GetPaymentMethodsResponse();
		if (PaymentMethodsResponse == true) {
			JSONArray JSArrayPaymentMethods = JSOPaymentMethods.getJSONArray("paymentMethods");
			JSONObject JSObjectPaymentMethods_1 = JSArrayPaymentMethods.getJSONObject(0);
			JSONObject JSObjectPaymentMethod_1 = JSObjectPaymentMethods_1.getJSONObject("paymentMethod");
			JSONObject JSObjectCreditCard = JSObjectPaymentMethod_1.getJSONObject("creditCard");

			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_1),
					JSObjectPaymentMethod_1.get("paymentMethodHeader"));
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_2), JSObjectPaymentMethod_1.get("cardHolderName"));
			maskCardNumber = (String) JSObjectCreditCard.get("cardNumber");
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_3), maskCardNumber);
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_4), JSObjectCreditCard.get("expirationDate"));
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
		

	}

	@Test
	private void paymentMethods_TC09() throws Exception {
		logger = extent.createTest("To verify updated payment method details at credit Or debit card.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			seleniumUtil.waitForElementClickable(paymentMethods.lnk_edit);
			seleniumUtil.clickByJS(paymentMethods.lnk_edit);
			seleniumUtil.waitForElementVisible(paymentMethods.ddl_cardType);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_cardType), cardType);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_cardHolderName, "value"), cardHolderName);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_cardNumber, "value"), maskCardNumber);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_expMonth), expMonth);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_expYear), expYear);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value"), random_address_1);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value"), random_address_2);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value"), random_address_3);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value"), random_city);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_selectedState), state);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value"), zipCode);
			seleniumUtil.click(paymentMethods.btn_cancel);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
		
	}

	// Bank Account

	@Test
	private void paymentMethods_TC10() throws Exception {
		logger = extent
				.createTest(
						"To verify required feild filled after save change button should be enable at bank account.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {

			seleniumUtil.refresh();
			seleniumUtil.clickByJS(paymentMethods.btn_addPaymentMethod);
			seleniumUtil.waitForElementPresent(paymentMethods.rbtn_bankAccount);
			seleniumUtil.clickByJS(paymentMethods.rbtn_bankAccount);
			seleniumUtil.setText(paymentMethods.txt_accountName, "Test account name");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_routingNumber, "1234");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_confirmRountingNumber, "1234");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_accountNumber, "1234");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_confirmAccountNumber, "1234");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_address_1, "Test address 1");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_city, "Test city");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.selectByIndex(paymentMethods.ddl_selectedState, 2);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_disable_saveChanges), true);
			seleniumUtil.setText(paymentMethods.txt_zipCode, "31231");
			Thread.sleep(1000);
			seleniumUtil.isElementDisplayed(paymentMethods.btn_enable_saveChanges);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentMethods.btn_enable_saveChanges), true);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
		

	}

	@Test
	private void paymentMethods_TC11() throws Exception {
		logger = extent.createTest(
				"To verify max char length for routing number, confirm rounting number, account number, confirm account number, address 1, 2, 3, city, zipcode at bank account.")
				.assignCategory(getClass().getSimpleName());

		if (PaymentMethodsResponse == true) {

			seleniumUtil.setText(paymentMethods.txt_routingNumber, "12345678901");
			int routingNumber = seleniumUtil
					.getOnlyDigits(seleniumUtil.getAttributeValue(paymentMethods.txt_routingNumber, "value")).length();
			seleniumUtil.verify(routingNumber, 9);
			seleniumUtil.setText(paymentMethods.txt_confirmRountingNumber, "12345678901");
			int confirmRountingNumber = seleniumUtil
					.getOnlyDigits(seleniumUtil.getAttributeValue(paymentMethods.txt_confirmRountingNumber, "value"))
					.length();
			seleniumUtil.verify(confirmRountingNumber, 9);
			seleniumUtil.setText(paymentMethods.txt_accountNumber, "123456789012345678");
			int accountNumber = seleniumUtil
					.getOnlyDigits(seleniumUtil.getAttributeValue(paymentMethods.txt_accountNumber, "value")).length();
			seleniumUtil.verify(accountNumber, 17);
			seleniumUtil.setText(paymentMethods.txt_confirmAccountNumber, "123456789012345678");
			int confirmAccountNumber = seleniumUtil
					.getOnlyDigits(seleniumUtil.getAttributeValue(paymentMethods.txt_confirmAccountNumber, "value"))
					.length();
			seleniumUtil.verify(confirmAccountNumber, 17);
			seleniumUtil.setText(paymentMethods.txt_address_1, "Address890123456789012345 Address");
			int add1_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value").length();
			seleniumUtil.verify(add1_lan, 30);
			seleniumUtil.setText(paymentMethods.txt_address_2, "Address890123456789012345 Address");
			int add2_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value").length();
			seleniumUtil.verify(add2_lan, 30);
			seleniumUtil.setText(paymentMethods.txt_address_3, "Address890123456789012345 Address");
			int add3_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value").length();
			seleniumUtil.verify(add3_lan, 30);
			seleniumUtil.setText(paymentMethods.txt_city, "City567889012345678901234567 City");
			int city_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value").length();
			seleniumUtil.verify(city_lan, 30);
			seleniumUtil.setText(paymentMethods.txt_zipCode, "123456789012");
			int zipCode_lan = seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value").length();
			seleniumUtil.verify(zipCode_lan, 10);
			
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}

		
	}

	@Test
	private void paymentMethods_TC12() throws Exception {
		logger = extent
				.createTest("To verify routing number and confirm Routing number is not matching at bank account.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			
			seleniumUtil.setText(paymentMethods.txt_routingNumber, "123456789");
			seleniumUtil.setText(paymentMethods.txt_confirmRountingNumber, "2345678901");
			seleniumUtil.click(paymentMethods.btn_enable_saveChanges);
			Thread.sleep(2000);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_val_msg_modalDialog);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg_modalDialog),
					consts.val_msg_routingNum_and_confirmRoutingNum_notMatching);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}


	}

	@Test
	private void paymentMethods_TC13() throws Exception {
		logger = extent.createTest(
				"To verify routing & confirm routing number and account & confirm account number is not matching at bank account.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			
			seleniumUtil.setText(paymentMethods.txt_routingNumber, "12345");
			seleniumUtil.setText(paymentMethods.txt_confirmRountingNumber, "42342");
			seleniumUtil.setText(paymentMethods.txt_accountNumber, "1234567890");
			seleniumUtil.setText(paymentMethods.txt_confirmAccountNumber, "123455");
			seleniumUtil.click(paymentMethods.btn_enable_saveChanges);
			Thread.sleep(2000);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_val_msg_modalDialog);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg_modalDialog),
					consts.val_msg_accountNum_and_confirmAccountNum_notMatching + "\n"
							+ consts.val_msg_routingNum_and_confirmRoutingNum_notMatching);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
		
	}

	@Test
	private void paymentMethods_TC14() throws Exception {
		logger = extent
				.createTest("To verify account number and confirm account number is not matching at bank account.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			routingNumber = "091000019";
			seleniumUtil.setText(paymentMethods.txt_routingNumber, routingNumber);
			seleniumUtil.setText(paymentMethods.txt_confirmRountingNumber, routingNumber);
			seleniumUtil.setText(paymentMethods.txt_accountNumber, "1234567890");
			seleniumUtil.setText(paymentMethods.txt_confirmAccountNumber, "123455");
			seleniumUtil.click(paymentMethods.btn_enable_saveChanges);
			Thread.sleep(2000);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_val_msg_modalDialog);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg_modalDialog),
					consts.val_msg_accountNum_and_confirmAccountNum_notMatching);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
	
	}

	@Test
	private void paymentMethods_TC15() throws Exception {
		logger = extent.createTest("Add bank account payment details.").assignCategory(getClass().getSimpleName());

		if (PaymentMethodsResponse == true) {
			
			accountName = "Utkarsh Patel";
			routingNumber = "091000019";
			random_address_1 = "Updated address_1_" + seleniumUtil.generateString(3);
			random_address_2 = "Updated address_2_" + seleniumUtil.generateString(3);
			random_address_3 = "Updated address_3_" + seleniumUtil.generateString(3);
			random_city = "Updated city_" + seleniumUtil.generateString(3);
			state = "UT";
			zipCode = "223344";
			expMonth = "Feb";
			seleniumUtil.setText(paymentMethods.txt_accountName, accountName);
			seleniumUtil.setText(paymentMethods.txt_routingNumber, routingNumber);
			seleniumUtil.setText(paymentMethods.txt_confirmRountingNumber, routingNumber);
			seleniumUtil.setText(paymentMethods.txt_accountNumber, consts.accountNumber2);
			seleniumUtil.setText(paymentMethods.txt_confirmAccountNumber, consts.accountNumber2);
			seleniumUtil.setText(paymentMethods.txt_address_1, random_address_1);
			seleniumUtil.setText(paymentMethods.txt_address_2, random_address_2);
			seleniumUtil.setText(paymentMethods.txt_address_3, random_address_3);
			seleniumUtil.setText(paymentMethods.txt_city, random_city);
			seleniumUtil.selectByText(paymentMethods.ddl_selectedState, state);
			seleniumUtil.setText(paymentMethods.txt_zipCode, zipCode);
			seleniumUtil.click(paymentMethods.btn_enable_saveChanges);
			Thread.sleep(2000);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
					consts.succ_msg_paymentMethod_add_succ);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}

	
	}

	@Test
	private void paymentMethods_TC16() throws Exception {
		logger = extent.createTest("To verify bank account details at payment method landing page.")
				.assignCategory(getClass().getSimpleName());
		JSONObject JSOPaymentMethods = request.GetPaymentMethodsResponse();
		if (PaymentMethodsResponse == true) {
			JSONArray JSArrayPaymentMethods = JSOPaymentMethods.getJSONArray("paymentMethods");
			JSONObject JSObjectPaymentMethods_2 = JSArrayPaymentMethods.getJSONObject(0);
			JSONObject JSObjectPaymentMethod_2 = JSObjectPaymentMethods_2.getJSONObject("paymentMethod");
			JSONObject JSObjectBankAccount = JSObjectPaymentMethod_2.getJSONObject("bankAccount");

			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_1),
					JSObjectPaymentMethod_2.get("paymentMethodHeader"));
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_2), JSObjectPaymentMethod_2.get("cardHolderName"));
			maskedAccountNumber = (String) JSObjectBankAccount.get("accountNumber");

			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_3), maskedAccountNumber);
			maskedRoutingNumber = (String) JSObjectBankAccount.get("maskedRoutingNumber");
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_4), maskedRoutingNumber);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}


	}

	@Test
	private void paymentMethods_TC17() throws Exception {
		logger = extent.createTest("To verify edit payment method details at bank account.")
				.assignCategory(getClass().getSimpleName());
		
		if (PaymentMethodsResponse == true) {
			
			seleniumUtil.clickByJS(paymentMethods.lnk_edit);
			seleniumUtil.waitForElementVisible(paymentMethods.txt_accountName);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_accountName, "value"), accountName);
			seleniumUtil.verify(
					seleniumUtil.getOnlyDigits(seleniumUtil.getAttributeValue(paymentMethods.txt_routingNumber, "value")),
					routingNumber);
			seleniumUtil.verify(
					seleniumUtil.getOnlyDigits(
							seleniumUtil.getAttributeValue(paymentMethods.txt_confirmRountingNumber, "value")),
					routingNumber);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_accountNumber, "value"),
					maskedAccountNumber);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value"), random_address_1);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value"), random_address_2);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value"), random_address_3);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value"), random_city);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_selectedState), state);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value"), zipCode);
			seleniumUtil.click(paymentMethods.btn_cancel);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
		
	}

	@Test
	private void paymentMethods_TC18() throws Exception {
		logger = extent.createTest("Update payment method details at bank account.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {

			routingNumber = "011401533";
			random_address_1 = "Updated address_1_" + seleniumUtil.generateString(3);
			random_address_2 = "Updated address_2_" + seleniumUtil.generateString(3);
			random_address_3 = "Updated address_3_" + seleniumUtil.generateString(3);
			random_city = "Updated city_" + seleniumUtil.generateString(3);
			state = "VA";
			zipCode = "20101";
			expMonth = "Mar";
			seleniumUtil.clickByJS(paymentMethods.lnk_edit);
			seleniumUtil.waitForElementVisible(paymentMethods.txt_accountName);
			seleniumUtil.setText(paymentMethods.txt_routingNumber, routingNumber);
			seleniumUtil.setText(paymentMethods.txt_confirmRountingNumber, routingNumber);
			seleniumUtil.setText(paymentMethods.txt_address_1, random_address_1);
			seleniumUtil.setText(paymentMethods.txt_address_2, random_address_2);
			seleniumUtil.setText(paymentMethods.txt_address_3, random_address_3);
			seleniumUtil.setText(paymentMethods.txt_city, random_city);
			seleniumUtil.selectByText(paymentMethods.ddl_selectedState, state);
			seleniumUtil.setText(paymentMethods.txt_zipCode, zipCode);
			seleniumUtil.click(paymentMethods.btn_enable_saveChanges);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
					consts.succ_msg_paymentMethod_update_succ);
			
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}

	}

	@Test
	private void paymentMethods_TC19() throws Exception {
		logger = extent.createTest("After updated, verify bank account details at payment method landing page..")
				.assignCategory(getClass().getSimpleName());
		JSONObject JSOPaymentMethods = request.GetPaymentMethodsResponse();
		if (PaymentMethodsResponse == true) {
			JSONArray JSArrayPaymentMethods = JSOPaymentMethods.getJSONArray("paymentMethods");
			JSONObject JSObjectPaymentMethods_2 = JSArrayPaymentMethods.getJSONObject(0);
			JSONObject JSObjectPaymentMethod_2 = JSObjectPaymentMethods_2.getJSONObject("paymentMethod");
			JSONObject JSObjectBankAccount = JSObjectPaymentMethod_2.getJSONObject("bankAccount");

			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_1),
					JSObjectPaymentMethod_2.get("paymentMethodHeader"));
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_2), JSObjectPaymentMethod_2.get("cardHolderName"));
			maskedAccountNumber = (String) JSObjectBankAccount.get("accountNumber");

			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_3), maskedAccountNumber);
			maskedRoutingNumber = (String) JSObjectBankAccount.get("maskedRoutingNumber");
			seleniumUtil.verify(seleniumUtil.getText(paymentMethods.lbl_4), maskedRoutingNumber);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
		

	}

	@Test
	private void paymentMethods_TC20() throws Exception {
		logger = extent.createTest("To verify updated payment method details at bank account.")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			seleniumUtil.waitForElementClickable(paymentMethods.lnk_edit);
			seleniumUtil.clickByJS(paymentMethods.lnk_edit);
			seleniumUtil.waitForElementVisible(paymentMethods.txt_accountName);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_accountName, "value"), accountName);
			seleniumUtil.verify(
					seleniumUtil.getOnlyDigits(seleniumUtil.getAttributeValue(paymentMethods.txt_routingNumber, "value")),
					routingNumber);
			seleniumUtil.verify(
					seleniumUtil.getOnlyDigits(
							seleniumUtil.getAttributeValue(paymentMethods.txt_confirmRountingNumber, "value")),
					routingNumber);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_accountNumber, "value"),
					maskedAccountNumber);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value"), random_address_1);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value"), random_address_2);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value"), random_address_3);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value"), random_city);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_selectedState), state);
			seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value"), zipCode);
			seleniumUtil.click(paymentMethods.btn_cancel);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
		
	}

	// Delete Payment method

	@Test
	private void paymentMethods_TC21() throws Exception {
		logger = extent.createTest("To verify delete payment method.[Bank Account]")
				.assignCategory(getClass().getSimpleName());
		if (PaymentMethodsResponse == true) {
			seleniumUtil.refresh();
			seleniumUtil.waitForElementVisible(
					By.xpath("//h3[text()='" + consts.paymentType_Checking + "']/parent::div//span[text()='Delete']"));
			seleniumUtil.clickByJS(
					By.xpath("//h3[text()='" + consts.paymentType_Checking + "']/parent::div//span[text()='Delete']"));
			seleniumUtil.waitForElementVisible(paymentMethods.btn_deleteMethod);
			seleniumUtil.click(paymentMethods.btn_deleteMethod);
			seleniumUtil.waitForElementInVisible(paymentMethods.btn_deleteMethod);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
					consts.succ_msg_delete_paymentMethod);
		} else {
			logger.error(PaymentMethodsResponse_message_content);

		}
	}

	@AfterTest
	public void postCondition() {
		seleniumUtil.quitBrowser();
		System.out.println("***** End Test Suite: " + getClass().getSimpleName() + " *****");

	}
}
