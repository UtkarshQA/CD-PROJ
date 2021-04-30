package com.MyAccount.TestCases;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS09_Dashboard extends TestBase {
	private String strProductName;
	private String strPolicyNumber;
	private String strPaymentFrequecyType;
	private String strDueDate;
	private String strAmountDue;
	private String strPaymentMethod;
	private String strPaymentStatus = null;
	private int due_now_counter = 0;
	private int past_due_counter = 0;

	private boolean addNewPaymentFlag_pastDue;
	private boolean addNewPaymentFlag_dueNow;

	@BeforeTest
	private void preCondition() throws Exception {
		System.out.println("***** Start Test Suite: " + getClass().getSimpleName() + " *****");
		seleniumUtil.openBrowser();
		seleniumUtil.openURL(g_url);
		commonMethods.userLogin(g_userName, g_userPassword);
		seleniumUtil.waitForElementVisible(dashboard.lbl_welcomeText);

	}

	@BeforeMethod
	private void preRequest() throws Exception {
		JSONObject JSOHomepage = request.GetHomepageResponse();
		if (HomepageResponse == true) {
			JSONArray JSArrayActiveProducts = JSOHomepage.getJSONArray("activeProducts");
			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
				JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
				JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
				if (paymentMethod.get("paymentStatus").equals(consts.product_status_due_now)) {
					due_now_counter++;
				} else if (paymentMethod.get("paymentStatus").equals(consts.product_status_past_due)) {
					past_due_counter++;
				}
			}
		} else {
			logger.error(HomepageResponse_message_content);

		}

	}

	@Test(priority = 1)
	private void Dashboard_TC01() throws Exception {
		logger = extent.createTest("To verify Your Active Products at dashboard page.[Your Active Products]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			JSONObject JSOHomepage = request.GetHomepageResponse();
			JSONArray JSArrayActiveProducts = JSOHomepage.getJSONArray("activeProducts");

			ArrayList<String> policyNumber = new ArrayList<String>();
			for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
				commonMethods.setProductTableXpathAtHomePage(i + 1);
				policyNumber.add(seleniumUtil.getText(commonMethods.lbl_policyNumber));
			}

			for (int j = 0; j < policyNumber.size(); j++) {

				for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
					JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");

					if (paymentMethod.getString("policyNumber").equals(policyNumber.get(j))) {
						commonMethods.setProductTableXpathAtHomePage(j + 1);
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_productName),
								paymentMethod.get("productDesc"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_policyNumber),
								paymentMethod.get("policyNumber"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType),
								paymentMethod.get("currentPaymentFrequecyType"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_amountDue)
								.contains((CharSequence) paymentMethod.get("amountDue")), true);
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_dueDate),
								paymentMethod.getString("dueDate").trim());
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentStatus),
								paymentMethod.get("paymentStatus"));
					}
				}

			}

		} else {
			logger.error(HomepageResponse_message_content);

		}

	}

	@Test(priority = 2)
	private void Dashboard_TC02() throws Exception {
		logger = extent.createTest("To verify past due boxs at dashboard page.[Past due's Policy]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			if (past_due_counter != 0) {
				JSONObject JSOHomepage = request.GetHomepageResponse();
				JSONArray JSArrayActiveProducts = JSOHomepage.getJSONArray("activeProducts");
				int counter_status_past_due = 0;
				int box = 1;
				for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
					JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
					strPaymentStatus = (String) paymentMethod.get("paymentStatus");

					if (strPaymentStatus.equals(consts.product_status_past_due) == true) {

						commonMethods.setProductTableXpathflexWhiteBoxAtHomePage(box);
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_productName),
								paymentMethod.get("productDesc"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentStatus),
								paymentMethod.get("paymentStatus"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_policyNumber),
								paymentMethod.get("policyNumber"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType),
								paymentMethod.get("currentPaymentFrequecyType"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_dueDate),
								paymentMethod.get("dueDate"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_amountDue)
								.contains((CharSequence) paymentMethod.get("amountDue")), true);
						box++;
						counter_status_past_due++;

					}
				}
				if (counter_status_past_due == 0) {
					logger.skip("Past due policies are not found.");
				}
			} else {
				logger.skip("Due now/Past due policies are not found.");
			}

		} else {
			logger.error(HomepageResponse_message_content);

		}

	}

	public void getProductdata(int productIndex) {
		commonMethods.setProductTableXpathflexWhiteBoxAtHomePage(productIndex);
		strProductName = seleniumUtil.getText(commonMethods.lbl_productName);
		strPolicyNumber = seleniumUtil.getText(commonMethods.lbl_policyNumber);
		strPaymentFrequecyType = seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType);
		strDueDate = seleniumUtil.getText(commonMethods.lbl_dueDate);
		strAmountDue = seleniumUtil.getText(commonMethods.lbl_amountDue);
	}

	public boolean addNewPayment_flag_payNow(String policyNumber) {

		boolean addNewPayment = false;

		try {
			JSONObject JSOPayNowResponseHomePage = request.GetPayNowResponse_homePage(policyNumber);
			JSONArray JSArrayActiveProducts = JSOPayNowResponseHomePage.getJSONArray("activeProducts");
			JSONObject JSObjectProductPayments = JSArrayActiveProducts.getJSONObject(0);
			JSONObject JSObjectProductPayment = JSObjectProductPayments.getJSONObject("productPayment");
			JSONObject JSObjectPaymentAction = JSObjectProductPayment.getJSONObject("paymentAction");
			JSONObject JSObjectPayNowLink = JSObjectPaymentAction.getJSONObject("payNowLink");
			JSONObject JSObjectSubmitPayment = JSObjectPaymentAction.getJSONObject("submitPayment");
			String strMessage;
			try {
				strMessage = JSObjectPayNowLink.getString("message");
				if (strMessage.trim().equals("")) {
					strMessage = null;
				}
			} catch (Exception e) {
				strMessage = null;
			}

			if ((strMessage != null) && (JSObjectSubmitPayment.getString("disable").equals("Y"))) {
				addNewPayment = false;
			} else {
				addNewPayment = true;
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return addNewPayment;
	}

	@Test(priority = 3)
	private void Dashboard_TC03() throws Exception {
		logger = extent.createTest("To verify product data at pay now page [Past due's Policy].")
				.assignCategory(getClass().getSimpleName());
		if (HomepageResponse == true) {

			if (past_due_counter != 0) {

				JSONObject JSOHomepage = request.GetPaymentResponse();
				JSONArray JSArrayActiveProducts = JSOHomepage.getJSONArray("activeProducts");

				int counter_status_past_due = 0;
				int box = 1;
				for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
					JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
					strPaymentStatus = (String) paymentMethod.get("paymentStatus");

					if (strPaymentStatus.equals(consts.product_status_past_due) == true) {

						commonMethods.setProductTableXpathflexWhiteBoxAtHomePage(box);
						getProductdata(box);

						seleniumUtil.click(commonMethods.btn_payNow);
						seleniumUtil.waitForElementPresent(payNow.leftBox);
						seleniumUtil.verify(seleniumUtil.getText(payNow.productName), strProductName);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_1_leftBox), strPolicyNumber);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_2_leftBox), strPaymentFrequecyType);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_3_leftBox), strDueDate);
						seleniumUtil.verify(
								commonMethods.remove_info_outline(seleniumUtil.getText(payNow.lbl_4_leftBox)),
								strAmountDue);
						counter_status_past_due++;
						addNewPaymentFlag_pastDue = addNewPayment_flag_payNow(strPolicyNumber);
						break;

					}
				}
				if (counter_status_past_due == 0) {
					logger.skip("Past due policies are not found.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(HomepageResponse_message_content);

		}

	}

	@Test(priority = 4)
	private void Dashboard_TC04() throws Exception {
		logger = extent.createTest("To verify add new payment method at pay now page.[Past due's Policy]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {

			if (past_due_counter != 0) {

				if (addNewPaymentFlag_pastDue == true) {

					if (seleniumUtil.isElementDisplayed(payNow.btn_addPaymentMethod) == true) {

						logger.info("Add new payment method, while payment method is not available.");
						seleniumUtil.click(payNow.btn_addPaymentMethod);
						commonMethods.add_new_paymentMethod_BankAccount(consts.routingNumber, consts.accountNumber);
						seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
						seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
								consts.succ_msg_paymentMethod_add_succ);
					} else {
						logger.info("Add new payment method, by drop down option.");
						seleniumUtil.selectByValue(payNow.ddl_paymentMethod, consts.ddl_value_add_new_paymentMethod);
						commonMethods.delete_paymentMethod(consts.paymentType_VISA);
						commonMethods.add_new_paymentMethod_CC(consts.cardType_VISA, consts.cardNumber_VISA);
						seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
						seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
								consts.succ_msg_paymentMethod_add_succ);
					}

				} else {
					logger.info("Add new payment method is disable.");

				}

			} else {

				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	@Test(priority = 5)
	private void Dashboard_TC05() throws Exception {
		logger = extent.createTest("To verify payment method data at pay now page.[Past due's Policy]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {

			if (past_due_counter != 0) {

				if (addNewPaymentFlag_pastDue == true) {
					JSONObject JSOHomepage = request.GetPaymentMethodsResponse();
					JSONArray JSArrayPaymentMethods = JSOHomepage.getJSONArray("paymentMethods");

					for (int i = 0; i < JSArrayPaymentMethods.length(); i++) {

						JSONObject pm = JSArrayPaymentMethods.getJSONObject(i);
						JSONObject paymentMethod = pm.getJSONObject("paymentMethod");
						Object paymentMethodHeader = paymentMethod.get("paymentMethodHeader");
						Object cardHolderName = paymentMethod.get("cardHolderName");
						JSONObject bankAccount = null;
						JSONObject creditCard = null;
						Object account_and_CC_Number = null;
						Object rountingNumber_and_expirationDate = null;

						try {
							bankAccount = paymentMethod.getJSONObject("bankAccount");
						} catch (org.json.JSONException e) {
							// TODO: handle exception
						}

						try {
							creditCard = paymentMethod.getJSONObject("creditCard");
						} catch (org.json.JSONException e) {
							// TODO: handle exception
						}

						try {
							if (!bankAccount.equals(null)) {
								account_and_CC_Number = bankAccount.get("accountNumber");
								rountingNumber_and_expirationDate = bankAccount.get("maskedRoutingNumber");
							}
						} catch (java.lang.NullPointerException e) {
							// TODO: handle exception
						}

						try {
							if (!creditCard.equals(null)) {
								account_and_CC_Number = creditCard.get("cardNumber");
								rountingNumber_and_expirationDate = creditCard.get("expirationDate");

							}
						} catch (java.lang.NullPointerException e) {
							// TODO: handle exception
						}
						seleniumUtil.selectByText(payNow.ddl_paymentMethod, (String) paymentMethodHeader);
						Thread.sleep(1000);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_1_rightBox), (String) paymentMethodHeader);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_2_rightBox), (String) cardHolderName);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_3_rightBox),
								(String) account_and_CC_Number);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_4_rightBox),
								(String) rountingNumber_and_expirationDate);
					}

				} else {
					logger.info("Add new payment method is disable.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}

		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	@Test(priority = 6)
	private void Dashboard_TC06() throws Exception {
		logger = extent.createTest("To verify edit payment method at pay now page.[Past due's Policy]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			if (past_due_counter != 0) {

				if (addNewPaymentFlag_pastDue == true) {
					JSONObject JSOHomepage = request.GetPaymentMethodsResponse();
					JSONArray JSArrayPaymentMethods = JSOHomepage.getJSONArray("paymentMethods");

					for (int i = 0; i < JSArrayPaymentMethods.length(); i++) {

						JSONObject pm = JSArrayPaymentMethods.getJSONObject(i);
						JSONObject JSONObject_PaymentMethod = pm.getJSONObject("paymentMethod");
						JSONObject JSONObject_Address = JSONObject_PaymentMethod.getJSONObject("address");

						Object paymentMethodHeader = JSONObject_PaymentMethod.get("paymentMethodHeader");
						Object cardHolderName = JSONObject_PaymentMethod.get("cardHolderName");

						JSONObject bankAccount = null;
						JSONObject creditCard = null;
						Object account_and_CC_Number = null;
						Object rountingNumber_and_expirationDate = null;
						Object financialInstitutionName = null;

						try {
							bankAccount = JSONObject_PaymentMethod.getJSONObject("bankAccount");
						} catch (org.json.JSONException e) {
							// TODO: handle exception
						}

						try {
							creditCard = JSONObject_PaymentMethod.getJSONObject("creditCard");
						} catch (org.json.JSONException e) {
							// TODO: handle exception
						}

						try {
							if (!bankAccount.equals(null)) {
								account_and_CC_Number = bankAccount.get("accountNumber");
								rountingNumber_and_expirationDate = bankAccount.get("originalRoutingNumber");
								financialInstitutionName = bankAccount.get("financialInstitutionName");

								seleniumUtil.selectByText(payNow.ddl_paymentMethod, (String) paymentMethodHeader);
								seleniumUtil.click(payNow.lnk_edit_method);

								seleniumUtil.waitForElementVisible(paymentMethods.txt_accountName);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_accountName, "value"),
										(String) cardHolderName);
								seleniumUtil.verify(
										seleniumUtil.getOnlyDigits(seleniumUtil
												.getAttributeValue(paymentMethods.txt_routingNumber, "value")),
										seleniumUtil.getOnlyDigits(rountingNumber_and_expirationDate));
								seleniumUtil.verify(
										seleniumUtil.getOnlyDigits(seleniumUtil
												.getAttributeValue(paymentMethods.txt_confirmRountingNumber, "value")),
										seleniumUtil.getOnlyDigits(rountingNumber_and_expirationDate));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_accountNumber, "value"),
										account_and_CC_Number);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value"),
										JSONObject_Address.get("addressLine1"));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value"),
										JSONObject_Address.get("addressLine2"));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value"),
										JSONObject_Address.get("addressLine3"));
								seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value"),
										JSONObject_Address.get("cityName"));
								seleniumUtil.verify(
										seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_selectedState),
										JSONObject_Address.get("stateCode"));
								seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value"),
										JSONObject_Address.get("zipCode"));
								seleniumUtil.click(paymentMethods.btn_cancel);

							}
						} catch (java.lang.NullPointerException e) {
							// TODO: handle exception
						}

						try {
							if (!creditCard.equals(null)) {
								account_and_CC_Number = creditCard.get("cardNumber");
								rountingNumber_and_expirationDate = creditCard.get("expirationDate");
								financialInstitutionName = creditCard.get("financialInstitutionName");

								String[] dateParts = ((String) rountingNumber_and_expirationDate).split("/");
								String expMonth = dateParts[0];
								String expYear = dateParts[1];

								String[] cardParts = ((String) paymentMethodHeader).split("-");
								String cardType = cardParts[0];

								seleniumUtil.selectByText(payNow.ddl_paymentMethod, (String) paymentMethodHeader);
								seleniumUtil.click(payNow.lnk_edit_method);

								seleniumUtil.waitForElementVisible(paymentMethods.ddl_cardType);
								seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_cardType),
										cardType);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_cardHolderName, "value"),
										(String) cardHolderName);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_cardNumber, "value"),
										account_and_CC_Number);
								seleniumUtil.verify(
										commonMethods.month(
												seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_expMonth)),
										expMonth);
								seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_expYear),
										expYear);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value"),
										JSONObject_Address.get("addressLine1"));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value"),
										JSONObject_Address.get("addressLine2"));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value"),
										JSONObject_Address.get("addressLine3"));
								seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value"),
										JSONObject_Address.get("cityName"));
								seleniumUtil.verify(
										seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_selectedState),
										JSONObject_Address.get("stateCode"));
								seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value"),
										JSONObject_Address.get("zipCode"));
								seleniumUtil.click(paymentMethods.btn_cancel);
							}
						} catch (java.lang.NullPointerException e) {
							// TODO: handle exception
						}

					}

				} else {
					logger.info("Add new payment method is disable.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}

		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	@Test(priority = 7)
	private void Dashboard_TC07() throws Exception {
		logger = extent.createTest("To verify 1xPayment by submit payment button at pay now page.[Past due's Policy]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {

			if (past_due_counter != 0) {

				if (addNewPaymentFlag_pastDue == true) {
//					seleniumUtil.selectByIndex(payNow.ddl_paymentMethod, 0);
//					seleniumUtil.click(payNow.btn_submitPayment);
//					seleniumUtil.click(payNow.btn_iAgree);
				} else {
					logger.info("Add new payment method is disable.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	@Test(priority = 8)
	private void Dashboard_TC08() throws Exception {
		logger = extent.createTest("To verify new added payment method at payment methods.[Past due's Policy]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {

			if (past_due_counter != 0) {
				if (addNewPaymentFlag_pastDue == true) {
					seleniumUtil.clickByJS(commonObjectRepositories.lnk_paymentCenter);
					seleniumUtil.waitForElementClickable(paymentMethods.lnk_paymentMethods);
					seleniumUtil.clickByJS(paymentMethods.lnk_paymentMethods);
					seleniumUtil.waitForElementVisible(paymentMethods.btn_addPaymentMethod);
					seleniumUtil
							.verify(seleniumUtil.isElementDisplayed(By.xpath("//div[@class='headerColumn']/h3[text()='"
									+ consts.paymentType_VISA + "'] | //div[@class='headerColumn']/h3[text()='"
									+ consts.paymentType_Checking + "']")), true);
				} else {
					logger.info("Add new payment method is disable.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	// --------------------- Due Now -----------------------

	@Test(priority = 9)
	private void Dashboard_TC09() throws Exception {
		logger = extent.createTest("To verify due now boxs at dashboard page.[Due now's Policy]")
				.assignCategory(getClass().getSimpleName());

		seleniumUtil.clickByJS(dashboard.lnk_home);
		seleniumUtil.waitForElementVisible(dashboard.lbl_welcomeText);

		if (HomepageResponse == true) {

			if (due_now_counter != 0) {
				JSONObject JSOHomepage = request.GetHomepageResponse();
				JSONArray JSArrayActiveProducts = JSOHomepage.getJSONArray("activeProducts");

				int counter_status_due_now = 0;
				int box = 1;
				for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
					JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
					strPaymentStatus = (String) paymentMethod.get("paymentStatus");

					if (strPaymentStatus.equals(consts.product_status_due_now) == true) {

						commonMethods.setProductTableXpathflexWhiteBoxAtHomePage(box);
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_productName),
								paymentMethod.get("productDesc"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentStatus),
								paymentMethod.get("paymentStatus"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_policyNumber),
								paymentMethod.get("policyNumber"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_paymentFrequecyType),
								paymentMethod.get("currentPaymentFrequecyType"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_dueDate),
								paymentMethod.get("dueDate"));
						seleniumUtil.verify(seleniumUtil.getText(commonMethods.lbl_amountDue)
								.contains((CharSequence) paymentMethod.get("amountDue")), true);
						box++;
						counter_status_due_now++;
					}
				}
				if (counter_status_due_now == 0) {
					logger.skip("Due now policies are not found.");
				}
			} else {
				logger.skip("Due now/Past due policies are not found.");
			}

		} else {
			logger.error(HomepageResponse_message_content);

		}

	}

	@Test(priority = 10)
	private void Dashboard_TC10() throws Exception {
		logger = extent.createTest("To verify product data at pay now page [Due now's Policy].")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			if (due_now_counter != 0) {

				JSONObject JSOHomepage = request.GetPaymentResponse();
				JSONArray JSArrayActiveProducts = JSOHomepage.getJSONArray("activeProducts");

				int counter_status_due_now = 0;
				for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
					commonMethods.setProductTableXpathflexWhiteBoxAtHomePage(i + 1);

					JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
					strPaymentStatus = (String) paymentMethod.get("paymentStatus");

					if (strPaymentStatus.equals(consts.product_status_due_now) == true) {
						getProductdata(i + 1);
						seleniumUtil.click(commonMethods.btn_payNow);
						seleniumUtil.waitForElementPresent(payNow.leftBox);
						seleniumUtil.verify(seleniumUtil.getText(payNow.productName), strProductName);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_1_leftBox), strPolicyNumber);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_2_leftBox), strPaymentFrequecyType);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_3_leftBox), strDueDate);
						seleniumUtil.verify(
								commonMethods.remove_info_outline(seleniumUtil.getText(payNow.lbl_4_leftBox)),
								strAmountDue);
						counter_status_due_now++;
						break;
					}
				}
				if (counter_status_due_now == 0) {
					logger.skip("Due now policies are not found.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(HomepageResponse_message_content);

		}

	}

	@Test(priority = 11)
	private void Dashboard_TC11() throws Exception {
		logger = extent.createTest("To verify product data at pay now page [Due now's Policy].")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			if (due_now_counter != 0) {

				JSONObject JSOHomepage = request.GetPaymentResponse();
				JSONArray JSArrayActiveProducts = JSOHomepage.getJSONArray("activeProducts");

				int counter_status_due_now = 0;
				int box = 1;
				for (int i = 0; i < JSArrayActiveProducts.length(); i++) {
					JSONObject JSObjectPaymentMethods = JSArrayActiveProducts.getJSONObject(i);
					JSONObject paymentMethod = JSObjectPaymentMethods.getJSONObject("productPayment");
					strPaymentStatus = (String) paymentMethod.get("paymentStatus");

					if (strPaymentStatus.equals(consts.product_status_due_now) == true) {

						commonMethods.setProductTableXpathflexWhiteBoxAtHomePage(box);
						getProductdata(box);
						seleniumUtil.click(commonMethods.btn_payNow);
						seleniumUtil.waitForElementPresent(payNow.leftBox);
						seleniumUtil.verify(seleniumUtil.getText(payNow.productName), strProductName);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_1_leftBox), strPolicyNumber);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_2_leftBox), strPaymentFrequecyType);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_3_leftBox), strDueDate);
						seleniumUtil.verify(
								commonMethods.remove_info_outline(seleniumUtil.getText(payNow.lbl_4_leftBox)),
								strAmountDue);
						counter_status_due_now++;
						addNewPaymentFlag_dueNow = addNewPayment_flag_payNow(strPolicyNumber);

						break;
					}
				}
				if (counter_status_due_now == 0) {
					logger.skip("Due now policies are not found.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(HomepageResponse_message_content);

		}

	}

	@Test(priority = 12)
	private void Dashboard_TC12() throws Exception {
		logger = extent.createTest("To verify add new payment method at pay now page.[Due now's Policy]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			if (due_now_counter != 0) {

				if (addNewPaymentFlag_dueNow == true) {
					if (seleniumUtil.isElementDisplayed(payNow.btn_addPaymentMethod) == true) {

						logger.info("Add new payment method, while payment method is not available.");
						seleniumUtil.click(payNow.btn_addPaymentMethod);
						commonMethods.add_new_paymentMethod_BankAccount(consts.routingNumber, consts.accountNumber);
						seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
						seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
								consts.succ_msg_paymentMethod_add_succ);
					} else {
						logger.info("Add new payment method, by drop down option.");
						seleniumUtil.selectByValue(payNow.ddl_paymentMethod, consts.ddl_value_add_new_paymentMethod);
						commonMethods.delete_paymentMethod(consts.paymentType_VISA);
						commonMethods.add_new_paymentMethod_CC(consts.cardType_VISA, consts.cardNumber_VISA);
						seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
						seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
								consts.succ_msg_paymentMethod_add_succ);
					}
				} else {
					logger.info("Add new payment method is disable.");
				}

			} else {

				logger.skip("Due now/Past due policies are not found.");
			}

		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	@Test(priority = 13)
	private void Dashboard_TC13() throws Exception {
		logger = extent.createTest("To verify payment method data at pay now page.[Due now's Policy]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			if (due_now_counter != 0) {

				if (addNewPaymentFlag_dueNow == true) {
					JSONObject JSOHomepage = request.GetPaymentMethodsResponse();
					JSONArray JSArrayPaymentMethods = JSOHomepage.getJSONArray("paymentMethods");

					for (int i = 0; i < JSArrayPaymentMethods.length(); i++) {

						JSONObject pm = JSArrayPaymentMethods.getJSONObject(i);
						JSONObject paymentMethod = pm.getJSONObject("paymentMethod");
						Object paymentMethodHeader = paymentMethod.get("paymentMethodHeader");
						Object cardHolderName = paymentMethod.get("cardHolderName");
						JSONObject bankAccount = null;
						JSONObject creditCard = null;
						Object account_and_CC_Number = null;
						Object rountingNumber_and_expirationDate = null;

						try {
							bankAccount = paymentMethod.getJSONObject("bankAccount");
						} catch (org.json.JSONException e) {
							// TODO: handle exception
						}

						try {
							creditCard = paymentMethod.getJSONObject("creditCard");
						} catch (org.json.JSONException e) {
							// TODO: handle exception
						}

						try {
							if (!bankAccount.equals(null)) {
								account_and_CC_Number = bankAccount.get("accountNumber");
								rountingNumber_and_expirationDate = bankAccount.get("maskedRoutingNumber");
							}
						} catch (java.lang.NullPointerException e) {
							// TODO: handle exception
						}

						try {
							if (!creditCard.equals(null)) {
								account_and_CC_Number = creditCard.get("cardNumber");
								rountingNumber_and_expirationDate = creditCard.get("expirationDate");

							}
						} catch (java.lang.NullPointerException e) {
							// TODO: handle exception
						}
						seleniumUtil.selectByText(payNow.ddl_paymentMethod, (String) paymentMethodHeader);
						Thread.sleep(1000);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_1_rightBox), (String) paymentMethodHeader);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_2_rightBox), (String) cardHolderName);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_3_rightBox),
								(String) account_and_CC_Number);
						seleniumUtil.verify(seleniumUtil.getText(payNow.lbl_4_rightBox),
								(String) rountingNumber_and_expirationDate);
					}
				} else {
					logger.info("Add new payment method is disable.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	@Test(priority = 14)
	private void Dashboard_TC14() throws Exception {
		logger = extent.createTest("To verify edit payment method at pay now page.[Due now's Policy]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			if (due_now_counter != 0) {

				if (addNewPaymentFlag_dueNow == true) {
					JSONObject JSOHomepage = request.GetPaymentMethodsResponse();
					JSONArray JSArrayPaymentMethods = JSOHomepage.getJSONArray("paymentMethods");

					for (int i = 0; i < JSArrayPaymentMethods.length(); i++) {

						JSONObject pm = JSArrayPaymentMethods.getJSONObject(i);
						JSONObject JSONObject_PaymentMethod = pm.getJSONObject("paymentMethod");
						JSONObject JSONObject_Address = JSONObject_PaymentMethod.getJSONObject("address");

						Object paymentMethodHeader = JSONObject_PaymentMethod.get("paymentMethodHeader");
						Object cardHolderName = JSONObject_PaymentMethod.get("cardHolderName");

						JSONObject bankAccount = null;
						JSONObject creditCard = null;
						Object account_and_CC_Number = null;
						Object rountingNumber_and_expirationDate = null;
						Object financialInstitutionName = null;

						try {
							bankAccount = JSONObject_PaymentMethod.getJSONObject("bankAccount");
						} catch (org.json.JSONException e) {
							// TODO: handle exception
						}

						try {
							creditCard = JSONObject_PaymentMethod.getJSONObject("creditCard");
						} catch (org.json.JSONException e) {
							// TODO: handle exception
						}

						try {
							if (!bankAccount.equals(null)) {
								account_and_CC_Number = bankAccount.get("accountNumber");
								rountingNumber_and_expirationDate = bankAccount.get("originalRoutingNumber");
								financialInstitutionName = bankAccount.get("financialInstitutionName");

								seleniumUtil.selectByText(payNow.ddl_paymentMethod, (String) paymentMethodHeader);
								seleniumUtil.click(payNow.lnk_edit_method);

								seleniumUtil.waitForElementVisible(paymentMethods.txt_accountName);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_accountName, "value"),
										(String) cardHolderName);
								seleniumUtil.verify(
										seleniumUtil.getOnlyDigits(seleniumUtil
												.getAttributeValue(paymentMethods.txt_routingNumber, "value")),
										seleniumUtil.getOnlyDigits(rountingNumber_and_expirationDate));
								seleniumUtil.verify(
										seleniumUtil.getOnlyDigits(seleniumUtil
												.getAttributeValue(paymentMethods.txt_confirmRountingNumber, "value")),
										seleniumUtil.getOnlyDigits(rountingNumber_and_expirationDate));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_accountNumber, "value"),
										account_and_CC_Number);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value"),
										JSONObject_Address.get("addressLine1"));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value"),
										JSONObject_Address.get("addressLine2"));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value"),
										JSONObject_Address.get("addressLine3"));
								seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value"),
										JSONObject_Address.get("cityName"));
								seleniumUtil.verify(
										seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_selectedState),
										JSONObject_Address.get("stateCode"));
								seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value"),
										JSONObject_Address.get("zipCode"));
								seleniumUtil.click(paymentMethods.btn_cancel);

							}
						} catch (java.lang.NullPointerException e) {
							// TODO: handle exception
						}

						try {
							if (!creditCard.equals(null)) {
								account_and_CC_Number = creditCard.get("cardNumber");
								rountingNumber_and_expirationDate = creditCard.get("expirationDate");
								financialInstitutionName = creditCard.get("financialInstitutionName");

								String[] dateParts = ((String) rountingNumber_and_expirationDate).split("/");
								String expMonth = dateParts[0];
								String expYear = dateParts[1];

								String[] cardParts = ((String) paymentMethodHeader).split("-");
								String cardType = cardParts[0];

								seleniumUtil.selectByText(payNow.ddl_paymentMethod, (String) paymentMethodHeader);
								seleniumUtil.click(payNow.lnk_edit_method);

								seleniumUtil.waitForElementVisible(paymentMethods.ddl_cardType);
								seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_cardType),
										cardType);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_cardHolderName, "value"),
										(String) cardHolderName);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_cardNumber, "value"),
										account_and_CC_Number);
								seleniumUtil.verify(
										commonMethods.month(
												seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_expMonth)),
										expMonth);
								seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_expYear),
										expYear);
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_1, "value"),
										JSONObject_Address.get("addressLine1"));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_2, "value"),
										JSONObject_Address.get("addressLine2"));
								seleniumUtil.verify(
										seleniumUtil.getAttributeValue(paymentMethods.txt_address_3, "value"),
										JSONObject_Address.get("addressLine3"));
								seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_city, "value"),
										JSONObject_Address.get("cityName"));
								seleniumUtil.verify(
										seleniumUtil.getDropdownSelectedValue(paymentMethods.ddl_selectedState),
										JSONObject_Address.get("stateCode"));
								seleniumUtil.verify(seleniumUtil.getAttributeValue(paymentMethods.txt_zipCode, "value"),
										JSONObject_Address.get("zipCode"));
								seleniumUtil.click(paymentMethods.btn_cancel);
							}
						} catch (java.lang.NullPointerException e) {
							// TODO: handle exception
						}

					}
				} else {
					logger.info("Add new payment method is disable.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	@Test(priority = 15)
	private void Dashboard_TC15() throws Exception {
		logger = extent.createTest("To verify 1xPayment by submit payment button at pay now page.[Due now's Policy]")
				.assignCategory(getClass().getSimpleName());
		if (HomepageResponse == true) {
			if (due_now_counter != 0) {
				if (addNewPaymentFlag_dueNow == true) {
//					seleniumUtil.selectByIndex(payNow.ddl_paymentMethod, 0);
//					seleniumUtil.click(payNow.btn_submitPayment);
//					seleniumUtil.click(payNow.btn_iAgree);
				} else {
					logger.info("Add new payment method is disable.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	@Test(priority = 16) // Delete added payment method
	private void Dashboard_TC16() throws Exception {
		logger = extent.createTest("To verify new added payment method at payment methods.[Due now's Policy]")
				.assignCategory(getClass().getSimpleName());
		if (HomepageResponse == true) {
			if (due_now_counter != 0) {

				if (addNewPaymentFlag_dueNow == true) {

					seleniumUtil.clickByJS(commonObjectRepositories.lnk_paymentCenter);
					seleniumUtil.waitForElementClickable(paymentMethods.lnk_paymentMethods);
					seleniumUtil.clickByJS(paymentMethods.lnk_paymentMethods);
					seleniumUtil.waitForElementVisible(paymentMethods.btn_addPaymentMethod);

					seleniumUtil
							.verify(seleniumUtil.isElementDisplayed(By.xpath("//div[@class='headerColumn']/h3[text()='"
									+ consts.paymentType_VISA + "'] | //div[@class='headerColumn']/h3[text()='"
									+ consts.paymentType_Checking + "']")), true);
				} else {
					logger.info("Add new payment method is disable.");
				}

			} else {
				logger.skip("Due now/Past due policies are not found.");
			}

		} else {
			logger.error(PayNow_homePageResponse_message_content);

		}

	}

	@Test(priority = 17)
	private void Dashboard_TC17() throws Exception {

		logger = extent.createTest("To verify page redirect from product details page.[Your Active Products]")
				.assignCategory(getClass().getSimpleName());

		seleniumUtil.clickByJS(dashboard.lnk_home);
		seleniumUtil.waitForElementVisible(dashboard.lbl_welcomeText);

		if (HomepageResponse == true) {
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
				seleniumUtil.waitForElementPresent(dashboard.lbl_welcomeText);
				seleniumUtil.verify(seleniumUtil.isElementDisplayed(dashboard.lbl_welcomeText), true);
				break;
			}
		} else {
			logger.error(HomepageResponse_message_content);

		}

	}

	@Test(priority = 18)
	private void Dashboard_TC18() throws Exception {

		logger = extent.createTest("To verify page redirect from product details page.[Due now/Past due Box]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			if (past_due_counter != 0 || due_now_counter != 0) {
				seleniumUtil.clickByJS(dashboard.lnk_home);
				seleniumUtil.waitForElementVisible(dashboard.lbl_welcomeText);

				List<WebElement> list_lnk_products_flexWhiteBox = driver
						.findElements(commonObjectRepositories.lnk_products_flexWhiteBox);

				for (int i = 0; i < list_lnk_products_flexWhiteBox.size(); i++) {
					try {
						list_lnk_products_flexWhiteBox.get(i).click();
					} catch (org.openqa.selenium.StaleElementReferenceException e) {
						list_lnk_products_flexWhiteBox = driver
								.findElements(commonObjectRepositories.lnk_products_flexWhiteBox);
						list_lnk_products_flexWhiteBox.get(i).click();
					}
					seleniumUtil.waitForElementPresent(productDetails.btn_arrowBack);
					seleniumUtil.verify(seleniumUtil.isElementDisplayed(productDetails.btn_arrowBack), true);
					seleniumUtil.clickByJS(productDetails.btn_arrowBack);
					seleniumUtil.waitForElementPresent(dashboard.lbl_welcomeText);
					seleniumUtil.verify(seleniumUtil.isElementDisplayed(dashboard.lbl_welcomeText), true);
					break;
				}
			} else {
				logger.skip("Due now/Past due policies are not found.");
			}

		} else {
			logger.error(HomepageResponse_message_content);

		}

	}

	@Test(priority = 19)
	private void Dashboard_TC19() throws Exception {

		logger = extent.createTest("To verify page redirect from product details page.[Pay Now]")
				.assignCategory(getClass().getSimpleName());

		if (HomepageResponse == true) {
			if (past_due_counter != 0 || due_now_counter != 0) {

				seleniumUtil.clickByJS(dashboard.lnk_home);
				seleniumUtil.waitForElementVisible(dashboard.lbl_welcomeText);

				List<WebElement> list_lnk_products_flexWhiteBox = driver
						.findElements(commonObjectRepositories.lnk_products_flexWhiteBox);

				for (int i = 0; i < list_lnk_products_flexWhiteBox.size(); i++) {
					commonMethods.setProductTableXpathflexWhiteBoxAtHomePage(i + 1);
					seleniumUtil.clickByJS(commonMethods.btn_payNow);
					seleniumUtil.waitForElementPresent(commonObjectRepositories.lnk_products_flexWhiteBox);
					String productName_payNow = seleniumUtil
							.getText(commonObjectRepositories.lnk_products_flexWhiteBox);
					seleniumUtil.clickByJS(commonObjectRepositories.lnk_products_flexWhiteBox);
					seleniumUtil.waitForElementPresent(productDetails.lbl_productName);
					String productName_detail = seleniumUtil.getText(productDetails.lbl_productName);
					seleniumUtil.verify(productName_payNow, commonMethods.remove_arrow_back(productName_detail));
					break;
				}
			} else {
				logger.skip("Due now/Past due policies are not found.");
			}
		} else {
			logger.error(HomepageResponse_message_content);
		}
	}

	@AfterTest
	public void postCondition() {
		seleniumUtil.quitBrowser();
		System.out.println("***** End Test Suite: " + getClass().getSimpleName() + " *****");

	}

}
