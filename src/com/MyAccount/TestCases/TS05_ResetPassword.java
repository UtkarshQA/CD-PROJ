package com.MyAccount.TestCases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS05_ResetPassword extends TestBase {
	String resetPasswordURL;
	String strUserName;

	@BeforeTest
	public void preCondition() throws Exception {

		strUserName = "\'" + g_userName + "\'";
		db.executeQuery("SELECT * FROM User_ where screenName =" + strUserName);
		String strUserId = db.selectData("userId");

		db.executeQuery("SELECT TOP 1 key_ FROM Ticket WHERE classPK =" + strUserId);
		String strKey = db.selectData("key_");

		resetPasswordURL = g_url + consts.url_reset_password + "/" + strKey;
		System.out.println(resetPasswordURL);
		seleniumUtil.openURL(resetPasswordURL);
	}

	@Test(priority = 1)
	public void ResetPassword_TC01() throws Exception {
		logger = extent.createTest("To verify submit button is disable.").assignCategory(getClass().getSimpleName());
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.btn_submit_disabled), true);
	}

	@Test(priority = 2)
	public void ResetPassword_TC02() throws Exception {
		logger = extent.createTest("To verify password policy validation for new password.")
				.assignCategory(getClass().getSimpleName());
		logger.info("********** Verify invalid validation. **********");

		seleniumUtil.setText(resetPassword.txt_new_password, "ABCde12");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_invalid_msg_8_char_len), true);
		seleniumUtil.setText(resetPassword.txt_new_password, "ABCD1234");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_invalid_msg_letters_a_z), true);
		seleniumUtil.setText(resetPassword.txt_new_password, "abcd1234");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_invalid_msg_letters_A_Z), true);
		seleniumUtil.setText(resetPassword.txt_new_password, "abcdEFGH");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_invalid_msg_numbers_0_9), true);

		logger.info("********** Verify valid validation. **********");

		seleniumUtil.setText(resetPassword.txt_new_password, "ABCde123");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_valid_msg_8_char_len), true);
		seleniumUtil.setText(resetPassword.txt_new_password, "aa");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_valid_msg_letters_a_z), true);
		seleniumUtil.setText(resetPassword.txt_new_password, "AA");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_valid_msg_letters_A_Z), true);
		seleniumUtil.setText(resetPassword.txt_new_password, "123");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_valid_msg_numbers_0_9), true);
	}

	@Test(priority = 3)
	public void ResetPassword_TC03() throws Exception {
		logger = extent.createTest("To verify validation of new password and confirm new password is not matched.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(resetPassword.txt_new_password, "abcdEF12");
		seleniumUtil.setText(resetPassword.txt_confirmpassword, "abcdEFGH");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_invalid_msg_match_pass), true);
		seleniumUtil.setText(resetPassword.txt_new_password, "abcdEF12");
		seleniumUtil.setText(resetPassword.txt_confirmpassword, "abcdEF12");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.lbl_valid_msg_match_pass), true);

	}

	@Test(priority = 4)
	public void ResetPassword_TC04() throws Exception {
		logger = extent.createTest(
				"To verify page redirect to dashboard page once new password and confirm new password is matched.")
				.assignCategory(getClass().getSimpleName());
		try {
			seleniumUtil.setText(resetPassword.txt_new_password, g_userPassword);
			seleniumUtil.setText(resetPassword.txt_confirmpassword, g_userPassword);
			seleniumUtil.click(resetPassword.btn_submit_enable);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lnk_logout);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(commonObjectRepositories.lnk_logout), true);
		} finally {
			seleniumUtil.click(commonObjectRepositories.lnk_logout);
		}

	}

	@Test(priority = 5)
	public void ResetPassword_TC05() throws Exception {
		logger = extent.createTest("To verify login with new password.").assignCategory(getClass().getSimpleName());

		try {
			seleniumUtil.setText(login.txt_userName, g_userName);
			seleniumUtil.setText(login.txt_password, g_userPassword);
			seleniumUtil.click(login.btn_login_enable);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lnk_logout);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(commonObjectRepositories.lnk_logout), true);
		} finally {
			seleniumUtil.click(commonObjectRepositories.lnk_logout);
			seleniumUtil.waitForElementVisible(login.txt_userName);
		}
	}

	// @Test(priority = 6)
	public void ResetPassword_TC06() throws Exception {
		logger = extent
				.createTest("To verify reset password link should be expired once user will done reset password.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.openURL(resetPasswordURL);
		seleniumUtil.pageLoadTime();
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(resetPassword.txt_new_password), false);
	}

}
