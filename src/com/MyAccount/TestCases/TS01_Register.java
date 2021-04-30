package com.MyAccount.TestCases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS01_Register extends TestBase {

	@BeforeTest
	private void preCondition() throws Exception {

		String strUserName = "'" + g_userName + "'";
		db.executeQuery("SELECT * FROM User_ where screenName =" + strUserName);
		String strCompanyId = db.selectData("companyId");

		
		if (strCompanyId != null) {
			commonMethods.deleteUser(strCompanyId, strUserName);
		}
		seleniumUtil.openURL(g_url);
		seleniumUtil.clickByJS(login.btn_register);
	}

	// @BeforeMethod
	private void pre_condition() throws Exception {
		Thread.sleep(1000);
	}

	@Test(priority = 1)
	private void Register_TC01() throws Exception {
		logger = extent.createTest("To verify login link at account details tab.")
				.assignCategory(getClass().getSimpleName());
		try {
			seleniumUtil.waitForElementVisible(register.lnk_login);
			seleniumUtil.click(register.lnk_login);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(login.txt_userName), true);
		} finally {
			seleniumUtil.openURL(g_url + consts.url_registration);
			// seleniumUtil.clickByJS(login.btn_register);

		}
	}

	// @Test(priority = 2)
	private void Register_TC02() throws Exception {
		logger = extent.createTest("To verify 'Account Details','Personal Information' and 'Sign-in Credentials' tabs.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.waitForElementVisible(register.tab_account_details);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.tab_account_details), true);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.tab_personal_information), true);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.tab_sign_in_credentials), true);
	}

	// @Test(priority = 3)
	private void Register_TC03() throws Exception {
		logger = extent.createTest("To verify next button is disable mode at account details tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.btn_next_disabled), true);

	}

	@Test(priority = 4)
	private void Register_TC04() throws Exception {
		logger = extent
				.createTest(
						"To verify 10 digits required validation for certificate/plan Number at account details tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(register.txt_planNumber, "1234");
		Thread.sleep(2000);
		seleniumUtil.click(register.btn_next_enabled);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification),
				consts.val_msg_planNo_ten_digits);
		seleniumUtil.setText(register.txt_planNumber, "12345678901");
		seleniumUtil.click(register.btn_next_enabled);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification),
				consts.val_msg_planNo_ten_digits);

	}

	@Test(priority = 5)
	private void Register_TC05() throws Exception {
		logger = extent.createTest("To verify certificate/plan number should be numeric at account details tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(register.txt_planNumber, "Test plan number");
		seleniumUtil.click(register.btn_next_enabled);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification),
				consts.val_msg_numeric_planNumber_ + "\n" + consts.val_msg_planNo_ten_digits);
	}

	@Test(priority = 6)
	private void Register_TC06() throws Exception {
		logger = extent.createTest("To verify invalid certificate/plan Number at account details tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(register.txt_planNumber, "1234567890");
		seleniumUtil.click(register.btn_next_enabled);
		Thread.sleep(2000);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification), consts.val_msg_invalid);
	}

	@Test(priority = 7)
	private void Register_TC07() throws Exception {
		logger = extent.createTest("To verify certificate/plan Number already exist.")
				.assignCategory(getClass().getSimpleName());
		
		db.executeQuery("SELECT top 1 *  from  AGIA_MARegistration");
		String planNumber = db.selectData("certificateOrPlanNumber");
		String userID = db.selectData("userID");

		db.executeQuery("SELECT  firstName from User_ where userID =" + userID);
		String firstName = db.selectData("firstName");
		String lastname = db.selectData("lastName");

		seleniumUtil.setText(register.txt_planNumber, planNumber);
		seleniumUtil.clickByJS(register.btn_next_enabled);
		seleniumUtil.waitForElementVisible(register.txt_firstName);
		seleniumUtil.setText(register.txt_firstName, firstName);
		seleniumUtil.setText(register.txt_lastname, lastname);
		seleniumUtil.setText(register.txt_email, g_email);
		seleniumUtil.clickByJS(register.btn_next_enabled);
		Thread.sleep(2000);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification), consts.val_msg_exist_user);
	}

	@Test(priority = 8)
	private void Register_TC08() throws Exception {
		logger = extent.createTest(
				"To verify next button enable and after click on next button tab redirect to personal information tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.openURL(g_url + consts.url_registration);
		seleniumUtil.setText(register.txt_planNumber, g_planNumber);
		Thread.sleep(2000);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.btn_next_enabled), true);
		seleniumUtil.click(register.btn_next_enabled);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.tab_active_personal_information), true);
	}

	@Test(priority = 9)
	private void Register_TC09() throws Exception {
		logger = extent.createTest("To verify next button is disable mode at personal information tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.btn_next_disabled), true);

	}

	@Test(priority = 10)
	private void Register_TC10() throws Exception {
		logger = extent.createTest("To verify invalid first name validation at personal information tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(register.txt_firstName, "test");
		seleniumUtil.setText(register.txt_lastname, g_lastName);
		seleniumUtil.setText(register.txt_email, g_email);

		seleniumUtil.click(register.btn_next_enabled);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification), consts.val_msg_invalid);

	}

	@Test(priority = 11)
	private void Register_TC11() throws Exception {
		logger = extent.createTest("To verify invalid last name validation at personal information tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(register.txt_firstName, g_firstName);
		seleniumUtil.setText(register.txt_lastname, "test");
		seleniumUtil.setText(register.txt_email, g_email);
		seleniumUtil.click(register.btn_next_enabled);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification), consts.val_msg_invalid);

	}

	@Test(priority = 12)
	private void Register_TC12() throws Exception {
		logger = extent.createTest("To verify email format validation at personal information tab.")
				.assignCategory(getClass().getSimpleName());
		// "email@example.web"
//		String elements[] = { "plainaddress", "#@%^%#$@#$@#.com", "@example.com", "Joe Smith <email@example.com>",
//				"email.example.com", "email@example@example.com", ".email@example.com", "email.@example.com",
//				"email..email@example.com", "�?��?��?��?��?�@example.com", "email@example.com (Joe Smith)", "email@example",
//				"email@-example.com", "email@111.222.333.44444", "email@example..com",
//				"Abc..123@example.com" };

		String elements[] = { "plainaddress", "#@%^%#$@#$@#.com", "@example.com" };

		seleniumUtil.setText(register.txt_firstName, g_firstName);
		seleniumUtil.setText(register.txt_lastname, "Test");
		for (int email = 0; email < elements.length; email++) {
			seleniumUtil.setText(register.txt_email, elements[email]);
			Thread.sleep(1000);
			seleniumUtil.click(register.btn_next_enabled);
			Thread.sleep(1000);
			seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification),
					consts.val_msg_email_format);
		}
	}

	@Test(priority = 13)
	private void Register_TC13() throws Exception {
		logger = extent.createTest(
				"To verify next button enable and after click on next button tab redirect to sign-in credentials tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(register.txt_firstName, g_firstName);
		seleniumUtil.setText(register.txt_lastname, g_lastName);
		seleniumUtil.setText(register.txt_email, g_email);
		seleniumUtil.click(register.btn_next_enabled);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.txt_username), true);
	}

	@Test(priority = 14)
	private void Register_TC14() throws Exception {
		logger = extent.createTest("To verify create account button is disable mode at sign-in credentials tab.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.btn_create_account_disabled), true);

	}

	@Test(priority = 15)
	private void Register_TC15() throws Exception {
		logger = extent.createTest("To verify password policy validation for password at sign-in credentials tab.")
				.assignCategory(getClass().getSimpleName());

		logger.info("********** Verify invalid validation. **********");

		seleniumUtil.setText(register.txt_password, "ABCde12");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_invalid_msg_8_char_len), true);
		seleniumUtil.setText(register.txt_password, "ABCD1234");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_invalid_msg_letters_a_z), true);
		seleniumUtil.setText(register.txt_password, "abcd1234");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_invalid_msg_letters_A_Z), true);
		seleniumUtil.setText(register.txt_password, "abcdEFGH");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_invalid_msg_numbers_0_9), true);
		seleniumUtil.setText(register.txt_password, "abcdEF12");
		seleniumUtil.setText(register.txt_confirmpassword, "abcdEFGH");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_invalid_msg_match_pass), true);

		logger.info("********** Verify valid validation. **********");

		seleniumUtil.setText(register.txt_password, "ABCde123");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_valid_msg_8_char_len), true);
		seleniumUtil.setText(register.txt_password, "aa");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_valid_msg_letters_a_z), true);
		seleniumUtil.setText(register.txt_password, "AA");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_valid_msg_letters_A_Z), true);
		seleniumUtil.setText(register.txt_password, "123");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_valid_msg_numbers_0_9), true);
		seleniumUtil.setText(register.txt_password, "abcdEF12");
		seleniumUtil.setText(register.txt_confirmpassword, "abcdEF12");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.lbl_valid_msg_match_pass), true);
	}

	@Test(priority = 16)
	private void Register_TC16() throws Exception {
		logger = extent.createTest("To verify username is already being used validation at sign-in credentials tab.")
				.assignCategory(getClass().getSimpleName());
		db.executeQuery("SELECT Top 1 screenName FROM User_");
		String existsUser = db.selectData("screenName");
		
		System.out.println(existsUser);
		seleniumUtil.setText(register.txt_username, existsUser);
		seleniumUtil.setText(register.txt_password, g_userPassword);
		seleniumUtil.setText(register.txt_confirmpassword, g_userPassword);
		seleniumUtil.selectByValue(register.ddl_securityQuestion, "What was your childhood nickname?");
		seleniumUtil.setText(register.txt_answer, "test");
		seleniumUtil.click(register.chk_iAgree);
		seleniumUtil.clickByJS(register.btn_create_account_enabled);
		String notification = seleniumUtil.getText(commonObjectRepositories.lbl_notification) + " "
				+ g_userName;
		seleniumUtil.verify(notification, consts.val_msg_exist_user + " " + g_userName);
	}

	@Test(priority = 17)
	private void Register_TC17() throws Exception {
		logger = extent.createTest(
				"To verify 'create an account' button is enable and redirect to home page after click on 'create an account' button.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(register.txt_username, g_userName);
		seleniumUtil.setText(register.txt_password, g_userPassword);
		seleniumUtil.setText(register.txt_confirmpassword, g_userPassword);
		seleniumUtil.selectByValue(register.ddl_securityQuestion, g_securityQuestion);
		seleniumUtil.setText(register.txt_answer, g_securityAnswer);
		if (seleniumUtil.isChecked(register.chk_iAgree) == false) {
			seleniumUtil.click(register.chk_iAgree);
		}
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.btn_create_account_enabled), true);
//		seleniumUtil.clickByJS(register.btn_create_account_enabled);
//		seleniumUtil.waitForElementVisible(login.lnk_logout);
//		seleniumUtil.verify(seleniumUtil.isElementDisplayed(login.lnk_logout), true);
	}

}
