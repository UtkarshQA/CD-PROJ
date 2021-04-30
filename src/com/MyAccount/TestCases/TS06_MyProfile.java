package com.MyAccount.TestCases;

import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

import jdk.internal.org.jline.utils.Log;
import jdk.jfr.internal.PrivateAccess;

public class TS06_MyProfile extends TestBase {
	private String random_address_1;
	private String random_address_2;
	private String random_address_3;
	private String random_city;
	private String phone;
	private boolean addressEditFlag;
	private boolean emailEditFlag;
	private boolean phoneNumberEditFlag;

	@BeforeTest
	private void preCondition() throws Exception {
		System.out.println("***** Start Test Suite: " + getClass().getSimpleName() + " *****");
		seleniumUtil.openBrowser();
		seleniumUtil.openURL(g_url);
		commonMethods.userLogin(g_userName, g_userPassword);
		seleniumUtil.waitForElementVisible(myProfile.lnk_profile);
		seleniumUtil.clickByJS(myProfile.lnk_profile);
		request.GetMyProfileResponse();
		if (ProfileResponse == true) {
			seleniumUtil.waitForElementVisible(myProfile.lbl_email);
			myProfileFlag();
		} else {
			logger.error(ProfileResponse_message_content);

		}

	}

	public void myProfileFlag() throws Exception {
		JSONObject JSMyProfile = request.GetMyProfileResponse();
		JSONObject JSEditOption = JSMyProfile.getJSONObject("editOption");
		JSONObject JSAddressEdit = JSEditOption.getJSONObject("addressEdit");
		JSONObject JSEmailEdit = JSEditOption.getJSONObject("emailEdit");
		JSONObject JSPhoneNumberEdit = JSEditOption.getJSONObject("phoneNumberEdit");

		if (JSAddressEdit.getString("disable").equals("Y")) {
			addressEditFlag = false;
		} else {
			addressEditFlag = true;
		}

		if (JSPhoneNumberEdit.getString("disable").equals("Y")) {
			phoneNumberEditFlag = false;
		} else {
			phoneNumberEditFlag = true;
		}

		if (JSEmailEdit.getString("disable").equals("Y")) {
			emailEditFlag = false;
		} else {
			emailEditFlag = true;
		}

	}

	@Test(priority = 1)
	private void MyProfile_TC01() throws Exception {
		logger = extent.createTest("To verify user details at my profile landing page.")
				.assignCategory(getClass().getSimpleName());

		JSONObject JSMyProfile = request.GetMyProfileResponse();

		if (ProfileResponse == true) {
			JSONObject JSPolicyHolder = JSMyProfile.getJSONObject("policyHolder");
			JSONObject JSAddress = JSMyProfile.getJSONObject("address");
			seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_fullName),
					JSPolicyHolder.get("firstName") + " " + JSPolicyHolder.get("lastName"));
			seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_dob), JSPolicyHolder.get("dateOfBirth"));

			if (JSAddress.get("addressLine1") != null && !JSAddress.get("addressLine1").equals("")) {
				seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_1), JSAddress.get("addressLine1"));
			}

			if (JSAddress.get("addressLine2") != null && !JSAddress.get("addressLine2").equals("")) {
				seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_2), JSAddress.get("addressLine2"));
				if (JSAddress.get("addressLine3") != null && !JSAddress.get("addressLine3").equals("")) {
					seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_3), JSAddress.get("addressLine3"));
					seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_4),
							JSAddress.get("cityName") + ", " + JSAddress.get("stateCode") + " " + JSAddress.get("zipCode"));
				} else {
					seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_3),
							JSAddress.get("cityName") + ", " + JSAddress.get("stateCode") + " " + JSAddress.get("zipCode"));
				}
			} else {

				if (JSAddress.get("addressLine3") != null && !JSAddress.get("addressLine3").equals("")) {
					seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_2), JSAddress.get("addressLine3"));
				} else {
					seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_2),
							JSAddress.get("cityName") + ", " + JSAddress.get("stateCode") + " " + JSAddress.get("zipCode"));
				}

			}
			seleniumUtil.verify(seleniumUtil.getOnlyDigits(seleniumUtil.getText(myProfile.lbl_phone)),
					seleniumUtil.getOnlyDigits(JSMyProfile.get("phoneNumber")));
			seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_email), JSPolicyHolder.get("emailAddress"));
		} else {
			logger.error(ProfileResponse_message_content);

		}



	}

	@Test(priority = 2)
	private void MyProfile_TC02() throws Exception {
		logger = extent.createTest("To verify user details at make change popup.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.click(myProfile.lnk_makeChanges);
		seleniumUtil.waitForElementVisible(myProfile.txt_address_1);
		JSONObject JSMyProfile = request.GetMyProfileResponse();
		
		if (ProfileResponse == true) {
			JSONObject JSPolicyHolder = JSMyProfile.getJSONObject("policyHolder");
			JSONObject JSAddress = JSMyProfile.getJSONObject("address");

			if (JSAddress.get("addressLine1") != null && !JSAddress.get("addressLine1").equals("")) {
				seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_address_1, "value"),
						JSAddress.get("addressLine1"));
			}

			if (JSAddress.get("addressLine2") != null && !JSAddress.get("addressLine2").equals("")) {
				seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_address_2, "value"),
						JSAddress.get("addressLine2"));
			}

			if (JSAddress.get("addressLine3") != null && !JSAddress.get("addressLine3").equals("")) {
				seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_address_3, "value"),
						JSAddress.get("addressLine3"));
			}

			seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_city, "value"), JSAddress.get("cityName"));
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(myProfile.ddl_state), JSAddress.get("stateCode"));
			seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_zipCode, "value"), JSAddress.get("zipCode"));
			seleniumUtil.verify(seleniumUtil.getOnlyDigits(seleniumUtil.getAttributeValue(myProfile.txt_phone, "value")),
					seleniumUtil.getOnlyDigits(JSMyProfile.get("phoneNumber")));
			seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_email, "value"),
					JSPolicyHolder.get("emailAddress"));
		} else {
			logger.error(ProfileResponse_message_content);

		}
		

	}

	@Test(priority = 3)
	private void MyProfile_TC03() throws Exception {
		logger = extent.createTest("To verify max char length for address 1, 2, 3, city, zipcode, mobile.")
				.assignCategory(getClass().getSimpleName());
		
		if (ProfileResponse == true) {
			if (addressEditFlag == true) {

				seleniumUtil.setText(myProfile.txt_address_1, "Address890123456789012345 Address");
				int add1_lan = seleniumUtil.getAttributeValue(myProfile.txt_address_1, "value").length();
				seleniumUtil.verify(add1_lan, 30);
				seleniumUtil.setText(myProfile.txt_address_2, "Address890123456789012345 Address");
				int add2_lan = seleniumUtil.getAttributeValue(myProfile.txt_address_2, "value").length();
				seleniumUtil.verify(add2_lan, 30);
				seleniumUtil.setText(myProfile.txt_address_3, "Address890123456789012345 Address");
				int add3_lan = seleniumUtil.getAttributeValue(myProfile.txt_address_3, "value").length();
				seleniumUtil.verify(add3_lan, 30);
				seleniumUtil.setText(myProfile.txt_city, "City567889012345678901234567 City");
				int city_lan = seleniumUtil.getAttributeValue(myProfile.txt_city, "value").length();
				seleniumUtil.verify(city_lan, 30);

				seleniumUtil.setText(myProfile.txt_zipCode, "123456789012");
				int zipCode_lan = seleniumUtil.getAttributeValue(myProfile.txt_zipCode, "value").length();
				seleniumUtil.verify(zipCode_lan, 10);
			} else {
				logger.info("Edit aaddress flag is disable.");
			}

			if (phoneNumberEditFlag == true) {

				seleniumUtil.setText(myProfile.txt_phone, "123456789012");
				int phone_lan = seleniumUtil.getOnlyDigits(seleniumUtil.getAttributeValue(myProfile.txt_phone, "value"))
						.length();
				seleniumUtil.verify(phone_lan, 10);
			} else {
				logger.info("Edit phone number flag is disable.");

			}

			seleniumUtil.click(myProfile.btn_cancel);
		} else {
			logger.error(ProfileResponse_message_content);

		}
		
	

	}

	@Test(priority = 4)
	private void MyProfile_TC04() throws Exception {
		logger = extent.createTest("To verify state and zipCode combination validation.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.click(myProfile.lnk_makeChanges);

		if (ProfileResponse == true) {
			if (addressEditFlag == true) {
				seleniumUtil.waitForElementVisible(myProfile.ddl_state);
				seleniumUtil.selectByIndex(myProfile.ddl_state, 1);
				seleniumUtil.click(myProfile.btn_enable_saveChanges);
				seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_val_msg);
				seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg),
						consts.val_msg_state_and_zipCode_combination);
			} else {
				logger.info("Edit aaddress flag is disable.");
			}
		} else {
			logger.error(ProfileResponse_message_content);

		}
		
	
	}

	@Test(priority = 5)
	private void MyProfile_TC05() throws Exception {
		logger = extent.createTest("To verify mobile and email format validation.")
				.assignCategory(getClass().getSimpleName());

		if (ProfileResponse == true) {
			if (phoneNumberEditFlag == true) {
				seleniumUtil.setText(myProfile.txt_phone, "123456789");
				seleniumUtil.click(myProfile.btn_enable_saveChanges);
				seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_val_msg);
				seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg),
						consts.val_msg_phoneNo_ten_digits);

				seleniumUtil.setText(myProfile.txt_phone, "1234567890");
			} else {
				logger.info("Edit phone number flag is disable.");
			}

			if (emailEditFlag == true) {
				String emailArray[] = { "plainaddress", "#@%^%#$@#$@#.com", "@example.com" };
				for (int email = 0; email < emailArray.length; email++) {
					seleniumUtil.setText(myProfile.txt_email, emailArray[email]);
					Thread.sleep(1000);
					seleniumUtil.click(myProfile.btn_enable_saveChanges);
					Thread.sleep(1000);
					seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg),
							consts.val_msg_email_format);
				}
			} else {
				logger.info("Edit email flag is disable.");
			}

			if (phoneNumberEditFlag == true && emailEditFlag == true) {
				seleniumUtil.clearText(myProfile.txt_phone);
				seleniumUtil.setText(myProfile.txt_phone, "123456789");
				seleniumUtil.click(myProfile.btn_enable_saveChanges);
				seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg),
						consts.val_msg_phoneNo_ten_digits + "\n" + consts.val_msg_email_format);

			} else {
				logger.info("Edit phone number and email flag is disable.");
				seleniumUtil.click(myProfile.btn_enable_saveChanges);
			}

		} else {
			logger.error(ProfileResponse_message_content);

		}
		
		
	}

	@Test(priority = 6)
	private void MyProfile_TC06() throws Exception {
		logger = extent.createTest("To verify update contact information by make change popup.")
				.assignCategory(getClass().getSimpleName());
		if (ProfileResponse == true) {
			seleniumUtil.clickByJS(myProfile.lnk_makeChanges);
			random_address_1 = "Updated address_1_" + seleniumUtil.generateString(3);
			random_address_2 = "Updated address_2_" + seleniumUtil.generateString(3);
			random_address_3 = "Updated address_3_" + seleniumUtil.generateString(3);
			random_city = "Updated city_" + seleniumUtil.generateString(3);
			phone = Integer.toString(seleniumUtil.generateMobile());

			if (addressEditFlag == true) {
				seleniumUtil.setText(myProfile.txt_address_1, random_address_1);
				seleniumUtil.setText(myProfile.txt_address_2, random_address_2);
				seleniumUtil.setText(myProfile.txt_address_3, random_address_3);
				seleniumUtil.setText(myProfile.txt_city, random_city);
				seleniumUtil.selectByValue(myProfile.ddl_state, properties.getProperty("N_State"));
				seleniumUtil.setText(myProfile.txt_zipCode, properties.getProperty("N_ZipCode"));
			} else {
				logger.info("Edit address flag is disable.");

			}

			if (phoneNumberEditFlag == true) {
				seleniumUtil.setText(myProfile.txt_phone, phone);
			} else {
				logger.info("Edit phone number flag is disable.");

			}

			if (emailEditFlag == true) {
				seleniumUtil.setText(myProfile.txt_email, g_email);

			} else {
				logger.info("Edit email flag is disable.");

			}
			seleniumUtil.clickByJS(myProfile.btn_enable_saveChanges);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
					consts.succ_msg_profile_update_succ);
		} else {
			logger.error(ProfileResponse_message_content);

		}
		
		

	}

	@Test(priority = 7)
	private void MyProfile_TC07() throws Exception {
		logger = extent.createTest("To verify updated contact information details at my profile landing page.")
				.assignCategory(getClass().getSimpleName());
		if (ProfileResponse == true) {
			if (addressEditFlag == true) {
				seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_1), random_address_1);
				seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_2), random_address_2);
				seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_3), random_address_3);
				seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_addressLine_4),
						random_city + ", " + properties.getProperty("N_State") + " " + properties.getProperty("N_ZipCode"));
			} else {
				logger.info("Edit address flag is disable.");

			}

			if (phoneNumberEditFlag == true) {
				seleniumUtil.verify(seleniumUtil.getOnlyDigits(seleniumUtil.getText(myProfile.lbl_phone)), phone);
			} else {
				logger.info("Edit phone number flag is disable.");

			}

			if (emailEditFlag == true) {

				seleniumUtil.verify(seleniumUtil.getText(myProfile.lbl_email), g_email);
			} else {
				logger.info("Edit email flag is disable.");

			}
		} else {
			logger.error(ProfileResponse_message_content);

		}
		
		
	}

	@Test(priority = 8)
	private void MyProfile_TC08() throws Exception {
		logger = extent.createTest("To verify updated contact information details at make change popup.")
				.assignCategory(getClass().getSimpleName());

		if (ProfileResponse == true) {
			seleniumUtil.clickByJS(myProfile.lnk_makeChanges);
			seleniumUtil.waitForElementVisible(myProfile.txt_address_1);

			if (addressEditFlag == true) { 
				seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_address_1, "value"), random_address_1);
				seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_address_2, "value"), random_address_2);
				seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_address_3, "value"), random_address_3);
				seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_city, "value"), random_city);
				seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(myProfile.ddl_state),
						properties.getProperty("N_State"));
				seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_zipCode, "value"),
						properties.getProperty("N_ZipCode"));
			}else {
				logger.info("Edit address flag is disable.");

			}
			
			if (phoneNumberEditFlag == true) {
				seleniumUtil.verify(seleniumUtil.getOnlyDigits(seleniumUtil.getAttributeValue(myProfile.txt_phone, "value")),
						phone);
			}else {
				logger.info("Edit phone number is disable.");

			}
			
			if (emailEditFlag == true) {
				seleniumUtil.verify(seleniumUtil.getAttributeValue(myProfile.txt_email, "value"), g_email);

			}else {
				logger.info("Edit email flag is disable.");

			}

			seleniumUtil.click(myProfile.btn_cancel);
			seleniumUtil.refresh();
		} else {
			logger.error(ProfileResponse_message_content);

		}
		
	
	}

	@Test(priority = 9)
	public void MyProfile_TC09() throws Exception {
		logger = extent.createTest("To verify password policy validation for password at change password popup.")
				.assignCategory(getClass().getSimpleName());

		if (ProfileResponse == true) {

			seleniumUtil.waitForElementVisible(myProfile.lnk_changePassword);
			seleniumUtil.clickByJS(myProfile.lnk_changePassword);
			seleniumUtil.waitForElementVisible(myProfile.txt_newPassword);

			logger.info("********** Verify invalid validation. **********");

			seleniumUtil.setText(myProfile.txt_newPassword, "ABCde12");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(myProfile.lbl_invalid_msg_8_char_len), true);
			seleniumUtil.setText(myProfile.txt_newPassword, "ABCD1234");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(myProfile.lbl_invalid_msg_letters_a_z), true);
			seleniumUtil.setText(myProfile.txt_newPassword, "abcd1234");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(myProfile.lbl_invalid_msg_letters_A_Z), true);
			seleniumUtil.setText(myProfile.txt_newPassword, "abcdEFGH");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(myProfile.lbl_invalid_msg_numbers_0_9), true);
			seleniumUtil.setText(myProfile.txt_newPassword, "abcdEF12");
			seleniumUtil.setText(myProfile.txt_confirmPassword, "abcdEFGH");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(myProfile.lbl_invalid_msg_match_pass), true);

			logger.info("********** Verify valid validation. **********");

			seleniumUtil.setText(myProfile.txt_newPassword, "ABCde123");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(myProfile.lbl_valid_msg_8_char_len), true);
			seleniumUtil.setText(myProfile.txt_newPassword, "aa");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(myProfile.lbl_valid_msg_letters_a_z), true);
			seleniumUtil.setText(myProfile.txt_newPassword, "AA");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(myProfile.lbl_valid_msg_letters_A_Z), true);
			seleniumUtil.setText(myProfile.txt_newPassword, "123");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(myProfile.lbl_valid_msg_numbers_0_9), true);
			seleniumUtil.setText(myProfile.txt_newPassword, "abcdEF12");
			seleniumUtil.setText(myProfile.txt_confirmPassword, "abcdEF12");
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_valid_msg_match_pass), true);
			
		} else {
			logger.error(ProfileResponse_message_content);

		}
		

	}

	@Test(priority = 10)
	public void MyProfile_TC10() throws Exception {
		logger = extent.createTest("To verify new Password can not be same as your current password.")
				.assignCategory(getClass().getSimpleName());

		if (ProfileResponse == true) {
			seleniumUtil.setText(myProfile.txt_oldPassword, g_userPassword);
			seleniumUtil.setText(myProfile.txt_newPassword, g_userPassword);
			seleniumUtil.setText(myProfile.txt_confirmPassword, g_userPassword);
			seleniumUtil.click(myProfile.btn_enable_savePasswordChanges);
			
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_val_msg);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg),
					consts.val_msg_new_password_not_same_current_password);
		} else {
			logger.error(ProfileResponse_message_content);

		}
		

	}

	@Test(priority = 11)
	public void MyProfile_TC11() throws Exception {
		logger = extent.createTest("To verify old Password not same as your current password.")
				.assignCategory(getClass().getSimpleName());
		if (ProfileResponse == true) {
			
			seleniumUtil.setText(myProfile.txt_oldPassword, "utkarsh@#$%");
			seleniumUtil.setText(myProfile.txt_newPassword, properties.getProperty("N_NewPassword"));
			seleniumUtil.setText(myProfile.txt_confirmPassword, properties.getProperty("N_NewPassword"));
			seleniumUtil.click(myProfile.btn_enable_savePasswordChanges);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_val_msg);
			Thread.sleep(2000);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg),
					consts.val_msg_old_password_not_same_current_password);
		} else {
			logger.error(ProfileResponse_message_content);

		}
		

	}

	@Test(priority = 12)
	public void MyProfile_TC12() throws Exception {
		logger = extent.createTest("To verify new password change.").assignCategory(getClass().getSimpleName());

		if (ProfileResponse == true) {
			
			seleniumUtil.setText(myProfile.txt_oldPassword, g_userPassword);
			seleniumUtil.setText(myProfile.txt_newPassword, properties.getProperty("N_NewPassword"));
			seleniumUtil.setText(myProfile.txt_confirmPassword, properties.getProperty("N_NewPassword"));
			seleniumUtil.click(myProfile.btn_enable_savePasswordChanges);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
			Thread.sleep(1500);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
					consts.succ_password_update_succ);
		} else {
			logger.error(ProfileResponse_message_content);

		}
		

	}

	@Test(priority = 13)
	public void MyProfile_TC13() throws Exception {
		logger = extent.createTest("To verify logged in with new password.").assignCategory(getClass().getSimpleName());
		
		if (ProfileResponse == true) {

			try {
				seleniumUtil.clickByJS(commonObjectRepositories.lnk_logout);
				Thread.sleep(1000);
				seleniumUtil.waitForElementPresent(login.txt_userName);
				commonMethods.userLogin(g_userName, properties.getProperty("N_NewPassword"));
				seleniumUtil.verify(seleniumUtil.isElementDisplayed(dashboard.lbl_welcomeText), true);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				seleniumUtil.openURL(g_url + consts.url_myProfile);
				seleniumUtil.waitForElementVisible(myProfile.lnk_changePassword);
				seleniumUtil.click(myProfile.lnk_changePassword);
				seleniumUtil.waitForElementVisible(myProfile.txt_oldPassword);
				seleniumUtil.setText(myProfile.txt_oldPassword, properties.getProperty("N_NewPassword"));
				seleniumUtil.setText(myProfile.txt_newPassword, g_userPassword);
				seleniumUtil.setText(myProfile.txt_confirmPassword, g_userPassword);
				seleniumUtil.click(myProfile.btn_enable_savePasswordChanges);
				seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
			}

			
		} else {
			logger.error(ProfileResponse_message_content);

		}
		
	}

	@Test(priority = 14)
	public void MyProfile_TC14() throws Exception {
		logger = extent.createTest("To verify security question and update security answer.")
				.assignCategory(getClass().getSimpleName());
		if (ProfileResponse == true) {
			seleniumUtil.refresh();
			seleniumUtil.waitForElementVisible(myProfile.lnk_change);
			seleniumUtil.click(myProfile.lnk_change);
			seleniumUtil.waitForElementVisible(myProfile.txt_answer);
			seleniumUtil.verify(seleniumUtil.getDropdownSelectedValue(myProfile.ddl_selectedQuestion), g_securityQuestion);
			seleniumUtil.setText(myProfile.txt_answer, g_securityAnswer);
			seleniumUtil.click(myProfile.btn_enable_saveChanges);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_succ_msg);
			Thread.sleep(1500);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_succ_msg),
					consts.succ_msg_security_update_succ);
		} else {
			logger.error(ProfileResponse_message_content);

		}

	}

	@AfterTest
	public void postCondition() {
		seleniumUtil.quitBrowser();
		System.out.println("***** End Test Suite: " + getClass().getSimpleName() + " *****");

	}

}
