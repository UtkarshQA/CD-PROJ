package com.MyAccount.TestCases;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS08_PaymentCenter_Products extends TestBase {

	private String strProductName;
	private String strPolicyNumber;
	private String strPaymentFrequecyType;
	private String strDueDate;
	private String strPaymentMethod;
	private String paymentFrequecy;
	private String paymentMethod;
	private String strPaymentStatus = null;
	private int CC_paymentMethod_counter = 0;
	private int Bank_paymentMethod_counter = 0;
	private boolean autoPay = false;
	private int counter_removeAutoPay = 0;

	@BeforeTest
	private void preCondition() throws Exception {
		System.out.println("***** Start Test Suite: " + getClass().getSimpleName() + " *****");
		seleniumUtil.openBrowser();
		seleniumUtil.openURL(g_url);
		commonMethods.userLogin(g_userName, g_userPassword);
		seleniumUtil.waitForElementVisible(commonObjectRepositories.lnk_paymentCenter);
		seleniumUtil.clickByJS(commonObjectRepositories.lnk_paymentCenter);
		seleniumUtil.waitForElementVisible(paymentCenter_products.lnk_products);
		seleniumUtil.clickByJS(dashboard.lnk_home);
		seleniumUtil.waitForElementVisible(dashboard.lbl_welcomeText);
		goToPaymentCenter();
	}

	public void goToPaymentCenter() {
		if (seleniumUtil.getCurrentPageURL().contains(consts.url_paymentCenter_products) == false) {
			seleniumUtil.clickByJS(commonObjectRepositories.lnk_paymentCenter);
			seleniumUtil.waitForElementVisible(paymentCenter_products.lnk_products);
			seleniumUtil.clickByJS(paymentCenter_products.lnk_products);
			seleniumUtil.waitForElementVisible(paymentCenter_products.tbl_products);
		}
	}

	private void clickOnAutoPayButton(String lable) {
		By btn_changeAutoPay = By.xpath("//button[text()='" + lable + "']");
		seleniumUtil.scrollToElement(btn_changeAutoPay);
		seleniumUtil.clickByJS(btn_changeAutoPay);
	}

	@Test(priority = 1)
	private void PaymentCenter_Products_TC01() throws Exception {
		logger = extent.createTest("To verify product details at products landing page.")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOPaymentMethods = request.GetPaymentResponse();

		if (PaymentResponse == true) {

			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {

				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);

				JSONObject JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");

				commonMethods.setProductTableXpathAtPaymentCenter_ProductsPage(
						JSObjectProductPayment.getString("policyNumber"));

				seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_productName),
						JSObjectProductPayment.get("productDesc"));
				seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_policyNumber),
						JSObjectProductPayment.get("policyNumber"));
				seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType),
						JSObjectProductPayment.get("currentPaymentFrequecyType"));
				seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentMethod),
						JSObjectProductPayment.get("currentPaymentMethod"));
				seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_amountDue)
						.contains((CharSequence) JSObjectProductPayment.get("amountDue")), true);
				seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_dueDate),
						JSObjectProductPayment.get("dueDate"));
				seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentStatus),
						JSObjectProductPayment.get("paymentStatus"));
			}
		} else {
			logger.fail(PaymentResponse_message_content);
		}
	}

	@Test(priority = 2)
	private void PaymentCenter_Products_TC02() throws Exception {
		logger = extent.createTest(
				"1.To verify products details at change auto pay or frequency modal, 2.To verify amount due change based on payment frequency, 3.Verify exsiting payment methods are availble in auto payment method dropdwon.")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOPaymentMethods = request.GetPaymentResponse();
		if (PaymentResponse == true) {
			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

			int counter_paymentStatus = 0;

			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {

				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);

				JSONObject JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");

				String paymentStatus = JSObjectProductPayment.getString("paymentStatus");

				if (paymentStatus.equals(consts.product_status_auto_pay) || paymentStatus.equals("")) {
					counter_paymentStatus++;
					commonMethods.setProductTableXpathAtPaymentCenter_ProductsPage(
							JSObjectProductPayment.getString("policyNumber"));
					seleniumUtil.clickByJS(commonMethods.rbtn);
					JSONObject JSObjectPaymentAction = JSObjectProductPayment.getJSONObject("paymentAction");
					JSONObject JSObjectChangeAutoPayOrFrequency = JSObjectPaymentAction
							.getJSONObject("changeAutoPayOrFrequency");
					JSObjectChangeAutoPayOrFrequency.getString("label");
					clickOnAutoPayButton(JSObjectChangeAutoPayOrFrequency.getString("label"));

					request.GetAutoPaymentPreferenceResponse(JSObjectProductPayment.getString("policyNumber"));
					if (AutoPaymentPreferenceResponse == true) {

						seleniumUtil.waitForElementVisible(paymentCenter_products.lbl_planName);
						String productDesc = JSObjectProductPayment.getString("productDesc");
						seleniumUtil.verify(seleniumUtil.getText(paymentCenter_products.lbl_planName), productDesc);
						String policyNumber = JSObjectProductPayment.getString("policyNumber");
						seleniumUtil.verify(seleniumUtil.getText(paymentCenter_products.lbl_planNumber), policyNumber);

						String currentPaymentFrequecyType = JSObjectProductPayment
								.getString("currentPaymentFrequecyType");

						JSONArray JSArrayAvailablePaymentFrequencyList = JSObjectProductPayment
								.getJSONArray("availablePaymentFrequencyList");

						String paymentFrequecy;

						if (JSArrayAvailablePaymentFrequencyList.length() != 1) {
							paymentFrequecy = seleniumUtil
									.getDropdownSelectedValue(paymentCenter_products.ddl_paymentFrequecy);
						} else {
							paymentFrequecy = seleniumUtil.getText(paymentCenter_products.lbl_paymentFrequecy);
						}
						seleniumUtil.verify(paymentFrequecy, currentPaymentFrequecyType);

						String currentPaymentMethod = JSObjectProductPayment.getString("currentPaymentMethod");

						seleniumUtil.verify(
								seleniumUtil.getDropdownSelectedValue(paymentCenter_products.ddl_paymentMethod),
								currentPaymentMethod);

						String dueDate = JSObjectProductPayment.getString("dueDate");
						seleniumUtil.verify(seleniumUtil.getText(paymentCenter_products.lbl_dueDate), dueDate);
						String premium = JSObjectProductPayment.getString("premium");
						seleniumUtil.verify(seleniumUtil.getText(paymentCenter_products.lbl_amountDue), premium + "*");

						// PAYMENT FREQUENCY

						if (JSArrayAvailablePaymentFrequencyList.length() != 1) {
							for (int j = 0; j < JSArrayAvailablePaymentFrequencyList.length(); j++) {

								JSONObject JSObjectPaymentFrequencyList = JSArrayAvailablePaymentFrequencyList
										.getJSONObject(j);
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										(String) JSObjectPaymentFrequencyList.get("value1"));
								seleniumUtil.verify(seleniumUtil.getText(paymentCenter_products.lbl_amountDue),
										JSObjectPaymentFrequencyList.get("value2") + "*");
							}
						}

						// AUTO PAY METHOD

						JSONObject JSObject_PaymentMethodsResponse = request.GetPaymentMethodsResponse();
						JSONArray JSObject_paymentMethods = JSObject_PaymentMethodsResponse
								.getJSONArray("paymentMethods");

						for (int k = 0; k < JSObject_paymentMethods.length(); k++) {
							JSONObject JSObject_paymentMethod = JSObject_paymentMethods.getJSONObject(k);
							JSONObject paymentMethod = JSObject_paymentMethod.getJSONObject("paymentMethod");
							seleniumUtil.selectByText(paymentCenter_products.ddl_paymentMethod,
									(String) paymentMethod.get("paymentMethodHeader"));
							seleniumUtil.verify(
									seleniumUtil.getDropdownSelectedValue(paymentCenter_products.ddl_paymentMethod),
									(String) paymentMethod.get("paymentMethodHeader"));
						}

						seleniumUtil.clickByJS(paymentCenter_products.btn_cancel);

						break;
					} else {
						logger.fail(AutoPaymentPreferenceResponse_message_content);
						break;
					}

				}
			}

			if (counter_paymentStatus == 0) {
				logger.skip("'Auto Pay' or 'Not on Auto Pay' policy is not found.");
			}
		} else {
			logger.fail(PaymentResponse_message_content);

		}

	}

	@Test(priority = 3)
	private void PaymentCenter_Products_TC03() throws Exception {
		logger = extent.createTest("To verify remove auto pay at products landing page.")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOPaymentMethods = request.GetPaymentResponse();
		if (PaymentResponse == true) {
			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");
			String strPolicyNumber = null;

			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {

				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);

				JSONObject JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");

				String paymentStatus = JSObjectProductPayment.getString("paymentStatus");

				if (paymentStatus.equals(consts.product_status_auto_pay)) {

					counter_removeAutoPay++;

					strPolicyNumber = JSObjectProductPayment.getString("policyNumber");

					commonMethods.setProductTableXpathAtPaymentCenter_ProductsPage(strPolicyNumber);

					seleniumUtil.clickByJS(commonMethods.rbtn);

					JSONObject JSObjectPaymentAction = JSObjectProductPayment.getJSONObject("paymentAction");
					JSONObject JSObjectChangeAutoPayOrFrequency = JSObjectPaymentAction
							.getJSONObject("changeAutoPayOrFrequency");
					JSObjectChangeAutoPayOrFrequency.getString("label");

					clickOnAutoPayButton(JSObjectChangeAutoPayOrFrequency.getString("label"));

					request.GetAutoPaymentPreferenceResponse(JSObjectProductPayment.getString("policyNumber"));
					if (AutoPaymentPreferenceResponse == true) {
						// Select remove auto pay

						String strAmountDue = seleniumUtil.getText(paymentCenter_products.lbl_amountDue);

						seleniumUtil.selectByValue(paymentCenter_products.ddl_paymentMethod, "00");
						seleniumUtil.waitForElementVisible(commonObjectRepositories.btn_iAgree);
						seleniumUtil.click(commonObjectRepositories.btn_iAgree);
						seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
						seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
						seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg)
								.contains(consts.succ_msg_removed_autoPay), true);

						JSOPaymentMethods = request.GetPaymentResponse();
						JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

						for (int j = 0; j < JSArrayActiveProducts.length(); j++) {

							JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(j);
							JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");

							if (JSObjectProductPayment.getString("policyNumber")
									.equals(seleniumUtil.getText(commonMethods.lbl_policyNumber))) {

								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType),
										JSObjectProductPayment.get("currentPaymentFrequecyType"));

								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentMethod),
										JSObjectProductPayment.get("currentPaymentMethod"));

								String amount = commonMethods
										.remove_info_outline(seleniumUtil.getText(commonMethods.lbl_amountDue));

								seleniumUtil.verify(strAmountDue.contains(amount), true);

								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentStatus),
										JSObjectProductPayment.get("paymentStatus"));
							}

						}

					} else {
						logger.fail(AutoPaymentPreferenceResponse_message_content);

					}

				}
			}

			if (counter_removeAutoPay == 0) {
				logger.skip("Auto pay policies are not found.");
			}

		} else {
			logger.fail(PaymentResponse_message_content);

		}

	}

	@Test(priority = 4)
	private void PaymentCenter_Products_TC04() throws Exception {

		logger = extent.createTest(
				"1.)To verify add new payment method at change auto pay or frequency modal, 2.) To verify default selection to new payment method added, 3.) To verify new added payment method at payment method page.")
				.assignCategory(getClass().getSimpleName());

		seleniumUtil.refresh();
		seleniumUtil.waitForElementPresent(paymentCenter_products.lnk_products);

		JSONObject JSOPaymentMethods = request.GetPaymentResponse();

		if (PaymentResponse == true) {
			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

			int counter_paymentStatus = 0;

			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
				JSONObject JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");
				String paymentStatus = JSObjectProductPayment.getString("paymentStatus");

				if (paymentStatus.equals(consts.product_status_auto_pay) || paymentStatus.equals("")) {
					counter_paymentStatus++;

					commonMethods.setProductTableXpathAtPaymentCenter_ProductsPage(
							JSObjectProductPayment.getString("policyNumber"));
					JSONObject JSObjectPaymentAction = JSObjectProductPayment.getJSONObject("paymentAction");
					JSONObject JSObjectChangeAutoPayOrFrequency = JSObjectPaymentAction
							.getJSONObject("changeAutoPayOrFrequency");
					JSObjectChangeAutoPayOrFrequency.getString("label");

					seleniumUtil.clickByJS(commonMethods.rbtn);
					clickOnAutoPayButton(JSObjectChangeAutoPayOrFrequency.getString("label"));

					request.GetAutoPaymentPreferenceResponse(JSObjectProductPayment.getString("policyNumber"));
					if (AutoPaymentPreferenceResponse == true) {
						// Add CC payment method
						commonMethods.delete_paymentMethod(consts.paymentType_VISA);
						seleniumUtil.selectByValue(paymentCenter_products.ddl_paymentMethod,
								consts.ddl_value_add_new_paymentMethod);
						commonMethods.add_new_paymentMethod_CC(consts.cardType_VISA, consts.cardNumber_VISA);
						seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg_modalDialog);
						seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg_modalDialog),
								consts.succ_msg_paymentMethod_add_succ);
						seleniumUtil.verify(
								seleniumUtil.getDropdownSelectedValue(paymentCenter_products.ddl_paymentMethod),
								consts.paymentType_VISA);

						// Add Bank account payment method
						commonMethods.delete_paymentMethod(consts.paymentType_Checking);
						seleniumUtil.selectByValue(paymentCenter_products.ddl_paymentMethod,
								consts.ddl_value_add_new_paymentMethod);
						commonMethods.add_new_paymentMethod_BankAccount(consts.routingNumber, consts.accountNumber);
						seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg_modalDialog);
						seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg_modalDialog),
								consts.succ_msg_paymentMethod_add_succ);
						seleniumUtil.verify(
								seleniumUtil.getDropdownSelectedValue(paymentCenter_products.ddl_paymentMethod),
								consts.paymentType_Checking);
						seleniumUtil.clickByJS(paymentCenter_products.btn_cancel);
						seleniumUtil.clickByJS(commonObjectRepositories.lnk_paymentCenter);
						seleniumUtil.waitForElementClickable(paymentMethods.lnk_paymentMethods);
						seleniumUtil.clickByJS(paymentMethods.lnk_paymentMethods);

						seleniumUtil.verify(
								seleniumUtil.isElementDisplayed(By.xpath(
										"//div[@class='headerColumn']/h3[text()='" + consts.paymentType_VISA + "']")),
								true);
						seleniumUtil.verify(seleniumUtil.isElementDisplayed(By.xpath(
								"//div[@class='headerColumn']/h3[text()='" + consts.paymentType_Checking + "']")),
								true);

						break;

					} else {
						logger.fail(AutoPaymentPreferenceResponse_message_content);
						break;
					}

				}
			}

			if (counter_paymentStatus == 0) {
				logger.skip("'Auto Pay' or 'Not on Auto Pay' policy is not found.");
			}
		} else {
			logger.fail(PaymentResponse_message_content);

		}
	}

	@Test(priority = 5)
	private void PaymentCenter_Products_TC05() throws Exception {
		logger = extent.createTest("To verify change auto payment and frequency at products landing page.")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOPaymentMethods = request.GetPaymentResponse();
		if (PaymentResponse == true) {
			goToPaymentCenter();
			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

			int counter_paymentStatus = 0;
			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {

				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
				JSONObject JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");
				String paymentStatus = JSObjectProductPayment.getString("paymentStatus");

				if (paymentStatus.equals(consts.product_status_auto_pay) || paymentStatus.equals("")) {
					counter_paymentStatus++;
					commonMethods.setProductTableXpathAtPaymentCenter_ProductsPage(
							JSObjectProductPayment.getString("policyNumber"));
					seleniumUtil.clickByJS(commonMethods.rbtn);
					JSONObject JSObjectPaymentAction = JSObjectProductPayment.getJSONObject("paymentAction");
					JSONObject JSObjectChangeAutoPayOrFrequency = JSObjectPaymentAction
							.getJSONObject("changeAutoPayOrFrequency");
					JSObjectChangeAutoPayOrFrequency.getString("label");
					clickOnAutoPayButton(JSObjectChangeAutoPayOrFrequency.getString("label"));

					request.GetAutoPaymentPreferenceResponse(JSObjectProductPayment.getString("policyNumber"));
					if (AutoPaymentPreferenceResponse == true) {
						String strFrequencyValue;
						JSONArray JSArrayAvailablePaymentFrequencyList = JSObjectProductPayment
								.getJSONArray("availablePaymentFrequencyList");

						if (JSArrayAvailablePaymentFrequencyList.length() != 1) {

							strFrequencyValue = seleniumUtil
									.getDropdownSelectedValue(paymentCenter_products.ddl_paymentFrequecy);

							if (strFrequencyValue.equals(consts.frequecyMonthly)) {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecyQuarterly);
							} else if (strFrequencyValue.equals(consts.frequecyQuarterly)) {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecySemiAnnually);
							} else if (strFrequencyValue.equals(consts.frequecySemiAnnually)) {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecyAnnually);
							} else if (strFrequencyValue.equals(consts.frequecyAnnually)) {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecyMonthly);
							} else {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecyAnnually);
							}

							strFrequencyValue = seleniumUtil
									.getDropdownSelectedValue(paymentCenter_products.ddl_paymentFrequecy);

						} else {

							strFrequencyValue = seleniumUtil.getText(paymentCenter_products.lbl_paymentFrequecy);
						}

						String strPaymentMethod = seleniumUtil
								.getDropdownSelectedValue(paymentCenter_products.ddl_paymentMethod);
						String strAmountDue = null;

						if (strPaymentMethod.equals(consts.paymentType_Checking)) {
							seleniumUtil.selectByText(paymentCenter_products.ddl_paymentMethod,
									consts.paymentType_VISA);
							strAmountDue = seleniumUtil.getText(paymentCenter_products.lbl_amountDue);
						} else if (strPaymentMethod.equals(consts.paymentType_VISA)) {
							seleniumUtil.selectByText(paymentCenter_products.ddl_paymentMethod,
									consts.paymentType_Checking);
							strAmountDue = seleniumUtil.getText(paymentCenter_products.lbl_amountDue);

						} else {
							seleniumUtil.selectByText(paymentCenter_products.ddl_paymentMethod,
									consts.paymentType_VISA);
							strAmountDue = seleniumUtil.getText(paymentCenter_products.lbl_amountDue);
						}

						strPaymentMethod = seleniumUtil
								.getDropdownSelectedValue(paymentCenter_products.ddl_paymentMethod);

						seleniumUtil.click(paymentCenter_products.btn_saveChange);
						seleniumUtil.waitForElementVisible(commonObjectRepositories.btn_iAgree);
						seleniumUtil.click(commonObjectRepositories.btn_iAgree);
						seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
						seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
								consts.succ_msg_chang_autoPay);

						JSOPaymentMethods = request.GetPaymentResponse();
						JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

						for (int j = 0; j < JSArrayActiveProducts.length(); j++) {

							JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(j);
							JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");

							if (JSObjectProductPayment.getString("policyNumber")
									.equals(seleniumUtil.getText(commonMethods.lbl_policyNumber))) {
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType),
										strFrequencyValue);
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentMethod),
										strPaymentMethod);
								seleniumUtil.verify(commonMethods.remove_info_outline(
										seleniumUtil.getText(commonMethods.lbl_amountDue)), strAmountDue);
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_dueDate),
										JSObjectProductPayment.get("dueDate"));
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentStatus),
										JSObjectProductPayment.get("paymentStatus"));
							}
						}
						break;
					} else {
						logger.fail(AutoPaymentPreferenceResponse_message_content);
						break;
					}

				}
			}

			if (counter_paymentStatus == 0) {
				logger.skip("'Auto Pay' or 'Not on Auto Pay' policy is not found.");
			}
		} else {
			logger.fail(PaymentResponse_message_content);

		}

	}

	@Test(priority = 6)
	private void PaymentCenter_Products_TC06() throws Exception {
		logger = extent.createTest("To verify change auto frequency at products landing page.")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOPaymentMethods = request.GetPaymentResponse();

		if (PaymentResponse == true) {
			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

			int counter_paymentStatus = 0;

			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
				JSONObject JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");
				String paymentStatus = JSObjectProductPayment.getString("paymentStatus");
				String strAmountDue = null;

				if (paymentStatus.equals(consts.product_status_auto_pay) || paymentStatus.equals("")) {

					counter_paymentStatus++;

					commonMethods.setProductTableXpathAtPaymentCenter_ProductsPage(
							JSObjectProductPayment.getString("policyNumber"));
					seleniumUtil.clickByJS(commonMethods.rbtn);
					JSONObject JSObjectPaymentAction = JSObjectProductPayment.getJSONObject("paymentAction");
					JSONObject JSObjectChangeAutoPayOrFrequency = JSObjectPaymentAction
							.getJSONObject("changeAutoPayOrFrequency");
					JSObjectChangeAutoPayOrFrequency.getString("label");
					clickOnAutoPayButton(JSObjectChangeAutoPayOrFrequency.getString("label"));

					request.GetAutoPaymentPreferenceResponse(JSObjectProductPayment.getString("policyNumber"));
					if (AutoPaymentPreferenceResponse == true) {
						String strFrequencyValue;
						JSONArray JSArrayAvailablePaymentFrequencyList = JSObjectProductPayment
								.getJSONArray("availablePaymentFrequencyList");

						if (JSArrayAvailablePaymentFrequencyList.length() != 1) {

							strFrequencyValue = seleniumUtil
									.getDropdownSelectedValue(paymentCenter_products.ddl_paymentFrequecy);

							if (strFrequencyValue.equals(consts.frequecyMonthly)) {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecyQuarterly);
							} else if (strFrequencyValue.equals(consts.frequecyQuarterly)) {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecySemiAnnually);
							} else if (strFrequencyValue.equals(consts.frequecySemiAnnually)) {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecyAnnually);
							} else if (strFrequencyValue.equals(consts.frequecyAnnually)) {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecyMonthly);
							} else {
								seleniumUtil.selectByText(paymentCenter_products.ddl_paymentFrequecy,
										consts.frequecyAnnually);
							}

							strFrequencyValue = seleniumUtil
									.getDropdownSelectedValue(paymentCenter_products.ddl_paymentFrequecy);
							strAmountDue = seleniumUtil.getText(paymentCenter_products.lbl_amountDue);

							seleniumUtil.click(paymentCenter_products.btn_saveChange);
							seleniumUtil.waitForElementVisible(commonObjectRepositories.btn_iAgree);
							seleniumUtil.click(commonObjectRepositories.btn_iAgree);
							seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
							seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
									consts.succ_msg_chang_autoPay);

						} else {

							strFrequencyValue = seleniumUtil.getText(paymentCenter_products.lbl_paymentFrequecy);
							strAmountDue = seleniumUtil.getText(paymentCenter_products.lbl_amountDue);

							seleniumUtil.click(paymentCenter_products.btn_saveChange);
						}

						JSOPaymentMethods = request.GetPaymentResponse();
						JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

						for (int j = 0; j < JSArrayActiveProducts.length(); j++) {

							JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(j);
							JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");

							if (JSObjectProductPayment.getString("policyNumber")
									.equals(seleniumUtil.getText(commonMethods.lbl_policyNumber))) {

								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType),
										strFrequencyValue);
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentMethod),
										JSObjectProductPayment.get("currentPaymentMethod"));
								seleniumUtil.verify(commonMethods.remove_info_outline(
										seleniumUtil.getText(commonMethods.lbl_amountDue)), strAmountDue);
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_dueDate),
										JSObjectProductPayment.get("dueDate"));
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentStatus),
										JSObjectProductPayment.get("paymentStatus"));
							}
						}
						break;
					} else {
						logger.fail(AutoPaymentPreferenceResponse_message_content);
						break;
					}

				}
			}

			if (counter_paymentStatus == 0) {
				logger.skip("'Auto Pay' or 'Not on Auto Pay' policy is not found.");
			}
		} else {
			logger.fail(PaymentResponse_message_content);

		}

	}

	@Test(priority = 7)
	private void PaymentCenter_Products_TC07() throws Exception {
		logger = extent.createTest("To verify change auto payment at products landing page.")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOPaymentMethods = request.GetPaymentResponse();

		if (PaymentResponse == true) {
			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

			int counter_paymentStatus = 0;

			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
				JSONObject JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");
				String paymentStatus = JSObjectProductPayment.getString("paymentStatus");

				if (paymentStatus.equals(consts.product_status_auto_pay) || paymentStatus.equals("")) {

					counter_paymentStatus++;
					seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
					commonMethods.setProductTableXpathAtPaymentCenter_ProductsPage(
							JSObjectProductPayment.getString("policyNumber"));
					seleniumUtil.clickByJS(commonMethods.rbtn);
					JSONObject JSObjectPaymentAction = JSObjectProductPayment.getJSONObject("paymentAction");
					JSONObject JSObjectChangeAutoPayOrFrequency = JSObjectPaymentAction
							.getJSONObject("changeAutoPayOrFrequency");
					JSObjectChangeAutoPayOrFrequency.getString("label");
					Thread.sleep(1000);
					clickOnAutoPayButton(JSObjectChangeAutoPayOrFrequency.getString("label"));

					request.GetAutoPaymentPreferenceResponse(JSObjectProductPayment.getString("policyNumber"));
					if (AutoPaymentPreferenceResponse == true) {
						String strPaymentMethod = seleniumUtil
								.getDropdownSelectedValue(paymentCenter_products.ddl_paymentMethod);

						if (strPaymentMethod.equals(consts.paymentType_Checking)) {
							seleniumUtil.selectByText(paymentCenter_products.ddl_paymentMethod,
									consts.paymentType_VISA);
						} else if (strPaymentMethod.equals(consts.paymentType_VISA)) {
							seleniumUtil.selectByText(paymentCenter_products.ddl_paymentMethod,
									consts.paymentType_Checking);
						} else {
							seleniumUtil.selectByText(paymentCenter_products.ddl_paymentMethod,
									consts.paymentType_VISA);
						}

						strPaymentMethod = seleniumUtil
								.getDropdownSelectedValue(paymentCenter_products.ddl_paymentMethod);
						String strAmountDue = seleniumUtil.getText(paymentCenter_products.lbl_amountDue);

						seleniumUtil.click(paymentCenter_products.btn_saveChange);
						seleniumUtil.waitForElementVisible(commonObjectRepositories.btn_iAgree);
						seleniumUtil.click(commonObjectRepositories.btn_iAgree);
						seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
						seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
								consts.succ_msg_chang_autoPay);

						JSOPaymentMethods = request.GetPaymentResponse();
						JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

						for (int j = 0; j < JSArrayActiveProducts.length(); j++) {

							JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(j);
							JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");

							if (JSObjectProductPayment.getString("policyNumber")
									.equals(seleniumUtil.getText(commonMethods.lbl_policyNumber))) {

								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType),
										JSObjectProductPayment.get("currentPaymentFrequecyType"));
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentMethod),
										JSObjectProductPayment.get("currentPaymentMethod"));
								seleniumUtil.verify(commonMethods.remove_info_outline(
										seleniumUtil.getText(commonMethods.lbl_amountDue)), strAmountDue);
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_dueDate),
										JSObjectProductPayment.get("dueDate"));
								seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentStatus),
										JSObjectProductPayment.get("paymentStatus"));
							}
						}
						break;
					} else {
						logger.fail(AutoPaymentPreferenceResponse_message_content);
						break;
					}
				}

			}

			if (counter_paymentStatus == 0) {
				logger.skip("'Auto Pay' or 'Not on Auto Pay' policy is not found.");
			}
		} else {
			logger.fail(PaymentResponse_message_content);

		}

	}

	@Test(priority = 8)
	private void PaymentCenter_Products_TC08() throws Exception {
		logger = extent.createTest("To verify product data at pay now page.[Past Due]")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOPaymentMethods = request.GetPaymentResponse();

		if (PaymentResponse == true) {

			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {

				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
				JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
				strPaymentStatus = (String) paymentMethod.get("paymentStatus");

				if (strPaymentStatus.equals(consts.product_status_past_due)) {
					commonMethods
							.setProductTableXpathAtPaymentCenter_ProductsPage(paymentMethod.getString("policyNumber"));
					seleniumUtil.clickByJS(commonMethods.rbtn);
					String strProductName = seleniumUtil.getText(commonMethods.lbl_productName);
					String strPolicyNumber = seleniumUtil.getText(commonMethods.lbl_policyNumber);
					String strPaymentFrequecyType = seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType);
					String strDueDate = seleniumUtil.getText(commonMethods.lbl_dueDate);
					String strAmountDue = commonMethods
							.remove_info_outline(seleniumUtil.getText(commonMethods.lbl_amountDue));
					seleniumUtil.waitForElementVisible(paymentCenter_products.btn_payNow);
					seleniumUtil.clickByJS(paymentCenter_products.btn_payNow);
					seleniumUtil.waitForElementVisible(payNow.leftBox);
					seleniumUtil.verify(seleniumUtil.getText(payNow.productName), strProductName);
					seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_1_leftBox), strPolicyNumber);
					seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_2_leftBox), strPaymentFrequecyType);
					seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_3_leftBox), strDueDate);
					seleniumUtil.verify(commonMethods.remove_info_outline(seleniumUtil.getText(payNow.lbl_4_leftBox)),
							strAmountDue);
					break;
				} else {
					logger.skip("Past due policy not found.");
				}
			}
		} else {
			logger.fail(PaymentResponse_message_content);

		}

	}

	@Test(priority = 9)
	private void PaymentCenter_Products_TC09() throws Exception {
		logger = extent.createTest("To verify product data at pay now page.[Due now]")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSOPaymentMethods = request.GetPaymentResponse();

		if (PaymentResponse == true) {
			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {

				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
				JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
				strPaymentStatus = (String) paymentMethod.get("paymentStatus");
				if (strPaymentStatus.equals(consts.product_status_due_now)) {

					goToPaymentCenter();

					commonMethods
							.setProductTableXpathAtPaymentCenter_ProductsPage(paymentMethod.getString("policyNumber"));
					seleniumUtil.clickByJS(commonMethods.rbtn);

					String strProductName = seleniumUtil.getText(commonMethods.lbl_productName);
					String strPolicyNumber = seleniumUtil.getText(commonMethods.lbl_policyNumber);
					String strPaymentFrequecyType = seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType);
					String strDueDate = seleniumUtil.getText(commonMethods.lbl_dueDate);
					String strAmountDue = commonMethods
							.remove_info_outline(seleniumUtil.getText(commonMethods.lbl_amountDue));
					seleniumUtil.waitForElementVisible(paymentCenter_products.btn_payNow);
					seleniumUtil.clickByJS(paymentCenter_products.btn_payNow);
					seleniumUtil.waitForElementVisible(payNow.leftBox);
					seleniumUtil.verify(seleniumUtil.getText(payNow.productName), strProductName);
					seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_1_leftBox), strPolicyNumber);
					seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_2_leftBox), strPaymentFrequecyType);
					seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_3_leftBox), strDueDate);
					seleniumUtil.verify(commonMethods.remove_info_outline(seleniumUtil.getText(payNow.lbl_4_leftBox)),
							strAmountDue);
					break;
				} else {
					logger.skip("Due now policy not found.");
				}
			}
		} else {
			logger.fail(PaymentResponse_message_content);

		}

	}

	@Test(priority = 10)
	private void PaymentCenter_Products_TC10() throws Exception {
		logger = extent.createTest("To verify payment method should not delete, if it's applied for auto pay.")
				.assignCategory(getClass().getSimpleName());

		if (AutoPaymentPreferenceResponse == true) {
		if (PaymentResponse == true) {
			seleniumUtil.clickByJS(commonObjectRepositories.lnk_paymentCenter);
			seleniumUtil.waitForElementVisible(paymentMethods.lnk_paymentMethods);
			seleniumUtil.clickByJS(paymentMethods.lnk_paymentMethods);
			seleniumUtil.waitForElementVisible(paymentMethods.btn_addPaymentMethod);

			JSONObject JSOPaymentMethods = request.GetPaymentResponse();
			JSONArray JSArrayActiveProducts = JSOPaymentMethods.getJSONArray("activeProducts");

			ArrayList<String> currentPaymentMethod = new ArrayList<String>();

			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
				JSONObject JSObjectProductPayment = JSObjectPaymentMethods.getJSONObject("productPayment");
				if (JSObjectProductPayment.getString("currentPaymentMethod").contains("-"))
					currentPaymentMethod.add(JSObjectProductPayment.getString("currentPaymentMethod"));
			}
			System.out.println(currentPaymentMethod);
			if (currentPaymentMethod.size() != 0) {
				seleniumUtil.clickByJS(By.xpath(
						"//h3[text()='" + currentPaymentMethod.get(0) + "']/parent::div//span[text()='Delete']"));
				seleniumUtil.waitForElementVisible(paymentMethods.btn_deleteMethod);
				seleniumUtil.click(paymentMethods.btn_deleteMethod);
				seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
				seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_info_msg),
						consts.info_msg_used_for_autoPay);
			} else {
				logger.skip("Payment methods are not applied for autopay.");
			}
		} else {
			logger.fail(PaymentResponse_message_content);

		}
		}else {
			logger.skip("Payment methods are not added due to error of something went wrong.");

		}
	}

	@Test(priority = 11)
	private void PaymentCenter_Products_TC11() throws Exception {

		logger = extent.createTest("To verify page redirect to product details page.[Active Products]")
				.assignCategory(getClass().getSimpleName());

		if (PaymentResponse == true) {
			goToPaymentCenter();

			List<WebElement> list_lnk_products_productTable = driver
					.findElements(commonObjectRepositories.lnk_products_productTable);

			for (int i = 0; i < list_lnk_products_productTable.size(); i++) {
				try {
					list_lnk_products_productTable.get(i).click();
				} catch (org.openqa.selenium.StaleElementReferenceException e) {
					list_lnk_products_productTable = driver
							.findElements(commonObjectRepositories.lnk_products_productTable);
					list_lnk_products_productTable.get(i).click();
				}
				seleniumUtil.waitForElementPresent(productDetails.btn_arrowBack);
				seleniumUtil.verify(seleniumUtil.isElementDisplayed(productDetails.btn_arrowBack), true);
				seleniumUtil.clickByJS(productDetails.btn_arrowBack);
				seleniumUtil.waitForElementPresent(paymentCenter_products.lnk_products);
				seleniumUtil.verify(seleniumUtil.isElementDisplayed(paymentCenter_products.lnk_products), true);
				break;
			}
		} else {
			logger.fail(PaymentResponse_message_content);
		}
	}

	@AfterTest
	public void postCondition() throws Exception {
		if (PaymentResponse == true) {
			goToPaymentCenter();
			if (counter_removeAutoPay == 0) {
				PaymentCenter_Products_TC03();
			}
		} else {
			logger.fail(PaymentResponse_message_content);

		}
		seleniumUtil.quitBrowser();
		System.out.println("***** End Test Suite: " + getClass().getSimpleName() + " *****");

	}

}
