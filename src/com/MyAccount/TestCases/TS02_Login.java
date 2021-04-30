package com.MyAccount.TestCases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS02_Login extends TestBase {

	@BeforeTest
	public void preCondition() throws Exception {
		System.out.println("***** Start Test Suite: " + getClass().getSimpleName() + " *****");
		seleniumUtil.openBrowser();
		seleniumUtil.openURL(g_url);
	}

	
	 @Test(priority = 1)
	private void Login_TC01() throws Exception {
		logger = extent.createTest("To verify invalid username.").assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(login.txt_userName, "test");
		seleniumUtil.setText(login.txt_password, g_userPassword);
		seleniumUtil.waitForElementPresent(login.btn_login_enable);
		seleniumUtil.click(login.btn_login_enable);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg),
				consts.val_msg_login_incorrect_credentials);
	}

	 @Test(priority = 2)
	private void Login_TC02() throws Exception {
		logger = extent.createTest("To verify invalid password.").assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(login.txt_userName, g_userName);
		seleniumUtil.setText(login.txt_password, "TestAutomationPassword");
		seleniumUtil.waitForElementPresent(login.btn_login_enable);
		seleniumUtil.click(login.btn_login_enable);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg),
				consts.val_msg_login_incorrect_credentials);
	}

	 @Test(priority = 3)
	private void Login_TC03() throws Exception {
		logger = extent.createTest("To verify invalid username and password.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(login.txt_userName, "TestAutomationUserName");
		seleniumUtil.setText(login.txt_password, "TestAutomationPassword");
		seleniumUtil.waitForElementPresent(login.btn_login_enable);
		seleniumUtil.click(login.btn_login_enable);
		seleniumUtil.verify(seleniumUtil.getText(commonObjectRepositories.lbl_val_msg),
				consts.val_msg_login_incorrect_credentials);
	}

	 @Test(priority = 4)
	public void Login_TC04() throws Exception {
		logger = extent.createTest("To verify user successfully login and it's redirect to home page.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.setText(login.txt_userName, g_userName);
		seleniumUtil.setText(login.txt_password, g_userPassword);
		seleniumUtil.waitForElementPresent(login.btn_login_enable);
		seleniumUtil.click(login.btn_login_enable);
		seleniumUtil.waitForElementPresent(commonObjectRepositories.lnk_logout);
		seleniumUtil.clickByJS(commonObjectRepositories.lnk_logout);
		seleniumUtil.waitForElementPresent(login.txt_userName);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(login.txt_userName), true);
	}

	 @Test(priority = 5)
	private void Login_TC05() throws Exception {
		logger = extent.createTest("To verify 'Remember Me' checkbox at login.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
		seleniumUtil.waitForElementPresent(login.txt_userName);
		seleniumUtil.setText(login.txt_userName, g_userName);
		seleniumUtil.setText(login.txt_password, g_userPassword);
		seleniumUtil.clickByJS(login.chk_rememberCheck);
		seleniumUtil.waitForElementVisible(login.btn_login_enable);
		seleniumUtil.click(login.btn_login_enable);
		seleniumUtil.waitForElementVisible(commonObjectRepositories.lnk_logout);
		seleniumUtil.clickByJS(commonObjectRepositories.lnk_logout);
		seleniumUtil.waitForElementVisible(login.txt_userName);
		String strUserName = seleniumUtil.getAttributeValue(login.txt_userName, "value");
		seleniumUtil.verify(strUserName, g_userName);
	}

	 @Test(priority = 6)
	private void Login_TC06() throws Exception {
		logger = extent.createTest("To verify 'Forgot username?' link at login.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.waitForElementInVisible(commonObjectRepositories.img_loading);
		seleniumUtil.waitForElementPresent(login.lnk_forgot_username);
		seleniumUtil.click(login.lnk_forgot_username);
		seleniumUtil.waitForElementPresent(forgotUsername.txt_planNumber);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(forgotUsername.txt_planNumber), true);

	}

	 @Test(priority = 7)
	private void Login_TC07() throws Exception {
		logger = extent.createTest("To verify 'Forgot password?' link at login.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.openURL(g_url);
		seleniumUtil.waitForElementPresent(login.lnk_forgot_password);
		seleniumUtil.click(login.lnk_forgot_password);
		seleniumUtil.waitForElementVisible(forgotPassword.txt_username);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(forgotPassword.txt_username), true);
	}

	 @Test(priority = 8)
	private void Login_TC08() throws Exception {
		logger = extent.createTest("To verify 'Register' button at login.").assignCategory(getClass().getSimpleName());
		seleniumUtil.openURL(g_url);
		seleniumUtil.waitForElementPresent(login.btn_register);
		seleniumUtil.clickByJS(login.btn_register);
		seleniumUtil.waitForElementPresent(register.txt_planNumber);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(register.txt_planNumber), true);
	}

	@AfterTest
	public void postCondition() {
		System.out.println("***** End Test Suite: " + getClass().getSimpleName() + " *****");
		seleniumUtil.quitBrowser();
	}
}
