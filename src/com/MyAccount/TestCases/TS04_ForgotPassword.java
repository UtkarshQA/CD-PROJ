package com.MyAccount.TestCases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS04_ForgotPassword extends TestBase {

	@BeforeTest
	public void preCondition() throws InterruptedException {
		seleniumUtil.openURL(g_url + consts.url_forgotPassword);
	}

	@Test(priority = 1)
	public void ForgotPassword_TC01() throws Exception {
		logger = extent.createTest("To verify 'Return to Login' button.").assignCategory(getClass().getSimpleName());
		seleniumUtil.clickByJS(forgotPassword.btn_return_to_login);
		seleniumUtil.waitForElementVisible(login.txt_userName);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(login.txt_userName), true);
	}

	@Test(priority = 2)
	public void ForgotPassword_TC02() throws Exception {
		logger = extent.createTest("To verify submit button is disable.").assignCategory(getClass().getSimpleName());
		seleniumUtil.openURL(g_url + consts.url_forgotPassword);
		seleniumUtil.waitForElementVisible(forgotPassword.btn_submit_disabled);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(forgotPassword.btn_submit_disabled), true);
	}

	@Test(priority = 3)
	public void ForgotPassword_TC03() throws Exception {
		logger = extent.createTest("To verify submit button is enable.").assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(forgotPassword.txt_username, "a");
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(forgotPassword.btn_submit_enable), true);
		Thread.sleep(2000);
	}

	@Test(priority = 4)
	public void ForgotPassword_TC04() throws Exception {
		logger = extent.createTest("To verify username is not registred.").assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(forgotPassword.txt_username, "TestAutomationUserName");
		seleniumUtil.click(forgotPassword.btn_submit_enable);
		seleniumUtil.waitForElementPresent(commonObjectRepositories.lbl_notification);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification), consts.val_msg_forgot_password_incorrect_credentials);
	}

	@Test(priority = 5)
	public void ForgotPassword_TC05() throws Exception {
		logger = extent.createTest("To verify screen redirect to security question screen.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(forgotPassword.txt_username, g_userName);
		seleniumUtil.click(forgotPassword.btn_submit_enable);
		seleniumUtil.waitForElementPresent(forgotPassword.txt_answer);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(forgotPassword.txt_answer), true);

	}

	@Test(priority = 6)
	public void ForgotPassword_TC06() throws Exception {
		logger = extent.createTest("To verify user security question.").assignCategory(getClass().getSimpleName());
		seleniumUtil.verify(seleniumUtil.getText(forgotPassword.lbl_question), g_securityQuestion);
	}

	@Test(priority = 7)
	public void ForgotPassword_TC07() throws Exception {
		logger = extent.createTest("To verify submit button is disable at security question screen.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(forgotPassword.btn_submit_disabled), true);
	}

	@Test(priority = 8)
	public void ForgotPassword_TC08() throws Exception {
		logger = extent.createTest("To verify invalid security answer validation.")
				.assignCategory(getClass().getSimpleName());

		seleniumUtil.setText(forgotPassword.txt_answer, "TestAnswer");
		seleniumUtil.click(forgotPassword.btn_submit_enable);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_notification), consts.val_msg_forgot_password_incorrect_security_ans);
	}

	@Test(priority = 9)
	public void ForgotPassword_TC09() throws Exception {
		logger = extent.createTest("To verify valid security answer with success message screen.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(forgotPassword.txt_answer, g_securityAnswer);
		seleniumUtil.click(forgotPassword.btn_submit_enable);
		seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_success_msg);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(commonObjectRepositories.lbl_success_msg), true);
	}

	@Test(priority = 10)
	public void ForgotPassword_TC10() throws Exception {
		logger = extent.createTest(
				"To verify 'Return to Log In' link at success message screen and verify user can logged in successfully.")
				.assignCategory(getClass().getSimpleName());
		try {
			seleniumUtil.click(forgotPassword.btn_return_to_login);
			seleniumUtil.setText(login.txt_userName, g_userName);
			seleniumUtil.setText(login.txt_password, g_userPassword);
			seleniumUtil.click(login.btn_login_enable);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lnk_logout);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(commonObjectRepositories.lnk_logout), true);
		} finally {
			seleniumUtil.click(commonObjectRepositories.lnk_logout);
		}

	}

}
