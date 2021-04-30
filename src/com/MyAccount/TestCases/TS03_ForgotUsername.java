package com.MyAccount.TestCases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class TS03_ForgotUsername extends TestBase {

	@BeforeMethod
	private void preCondition() throws Exception {
		seleniumUtil.openBrowser();
		seleniumUtil.openURL(g_url + consts.url_forgotUsername);
		seleniumUtil.pageLoadTime();
		Thread.sleep(3000);
		seleniumUtil.waitForElementPresent(forgotUsername.txt_planNumber);

	}

	
	public void setForgotUsernameDetails(String planNumber, String firstName, String lastName, String email) throws Exception {
		seleniumUtil.setText(forgotUsername.txt_planNumber, planNumber);
		seleniumUtil.setText(forgotUsername.txt_firstName, firstName);
		seleniumUtil.setText(forgotUsername.txt_lastName, lastName);
		seleniumUtil.setText(forgotUsername.txt_email, email);
		seleniumUtil.waitForElementPresent(forgotUsername.btn_submit_enable);
		seleniumUtil.clickByJS(forgotUsername.btn_submit_enable);
	}

	@Test(priority = 1)
	public void ForgotUsername_TC01() throws Exception {
		logger = extent.createTest("To verify submit button is disable.").assignCategory(getClass().getSimpleName());
		setForgotUsernameDetails(g_planNumber, g_firstName, g_lastName, g_email);

		//seleniumUtil.verify(seleniumUtil.isElementDisplayed(forgotUsername.btn_submit_disabled), true);

	}
	
	//@Test(priority = 2)
	public void ForgotUsername_TC02() throws Exception {
		logger = extent
				.createTest("To verify validation of valid 'Plan Number' and invalid 'First name, Last name, Email'.")
				.assignCategory(getClass().getSimpleName());

		setForgotUsernameDetails("1", "TestFirstName", "TestLastName",
				"testemail@abcwer.com");
		
		String strGetMsg = seleniumUtil.getText(commonObjectRepositories.lbl_notification);
		seleniumUtil.verify(strGetMsg, consts.val_msg_planNo_ten_digits);
	}
	
	//@Test(priority = 3)
	public void ForgotUsername_TC03() throws Exception {
		logger = extent
				.createTest("To verify validation of valid 'Plan Number' and invalid 'First name, Last name, Email'.")
				.assignCategory(getClass().getSimpleName());

		setForgotUsernameDetails(g_planNumber, "TestFirstName", "TestLastName",
				"testemail@abcwer.com");
		
		String strGetMsg = seleniumUtil.getText(commonObjectRepositories.lbl_notification);
		seleniumUtil.verify(strGetMsg, consts.val_msg_forgot_username_incorrect_credentials);
	}

	//@Test(priority = 4)
	public void ForgotUsername_TC04() throws Exception {
		logger = extent
				.createTest("To verify validation of valid 'First name' and invalid 'Plan Number, Last name, Email'.")
				.assignCategory(getClass().getSimpleName());

		setForgotUsernameDetails("6789012345", g_firstName, "TestLastName",
				"testemail@abcwer.com");
		String strGetMsg = seleniumUtil.getText(commonObjectRepositories.lbl_notification);
		seleniumUtil.verify(strGetMsg, consts.val_msg_forgot_username_incorrect_credentials);

	}

	//@Test(priority = 5)
	public void ForgotUsername_TC05() throws Exception {
		logger = extent
				.createTest("To verify validation of valid 'Last name' and invalid 'Plan Number, First name, Email'.")
				.assignCategory(getClass().getSimpleName());

		setForgotUsernameDetails("6789012345", "TestFirstName", g_lastName,
				"testemail@abcwer.com");
		String strGetMsg = seleniumUtil.getText(commonObjectRepositories.lbl_notification);
		seleniumUtil.verify(strGetMsg, consts.val_msg_forgot_username_incorrect_credentials);
	}

	//@Test(priority = 6)
	public void ForgotUsername_TC06() throws Exception {
		logger = extent
				.createTest("To verify validation of valid 'Email' and invalid 'Plan Number, First name, Last name'.")
				.assignCategory(getClass().getSimpleName());

		setForgotUsernameDetails("6789012345", "TestFirstName", "TestLastName", g_email);
		String strGetMsg = seleniumUtil.getText(commonObjectRepositories.lbl_notification);
		seleniumUtil.verify(strGetMsg, consts.val_msg_forgot_username_incorrect_credentials);
	}
	
	//@Test(priority = 7)
	public void ForgotUsername_TC07() throws Exception {
		logger = extent
				.createTest("To verify validation of invalid 'Plan Number, First name, Last name and email'.")
				.assignCategory(getClass().getSimpleName());
		setForgotUsernameDetails("6789012345", "TestFirstName", "TestLastName", g_email);
		String strGetMsg = seleniumUtil.getText(commonObjectRepositories.lbl_notification);
		seleniumUtil.verify(strGetMsg, consts.val_msg_forgot_username_incorrect_credentials);
	}

	//@Test(priority = 8)
	public void ForgotUsername_TC08() throws Exception {
		logger = extent.createTest("To verify success message after click on submit button.")
				.assignCategory(getClass().getSimpleName());
		setForgotUsernameDetails(g_planNumber, g_firstName,
				g_lastName, g_email);
		seleniumUtil.waitForElementVisible(commonObjectRepositories.lbl_success_msg);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(commonObjectRepositories.lbl_success_msg), true);
	}
	
	//@Test(priority = 9)
	public void ForgotUsername_TC09() throws Exception {
		logger = extent.createTest("To verify 'Return to Log In' link at success message screen and verify user can logged in successfully.").assignCategory(getClass().getSimpleName());
		seleniumUtil.clickByJS(forgotUsername.btn_return_to_login);
		seleniumUtil.waitForElementPresent(login.txt_userName);
		try {
			seleniumUtil.setText(login.txt_userName, g_userName);
			seleniumUtil.setText(login.txt_password, g_userPassword);
			seleniumUtil.waitForElementVisible(login.btn_login_enable);
			seleniumUtil.clickByJS(login.btn_login_enable);
			seleniumUtil.waitForElementVisible(commonObjectRepositories.lnk_logout);
			seleniumUtil.verify(seleniumUtil.isElementDisplayed(commonObjectRepositories.lnk_logout), true);
		} finally {
			seleniumUtil.click(commonObjectRepositories.lnk_logout);
		}	}
	

	//@Test(priority = 10)
	public void ForgotUsername_TC10() throws Exception {
		logger = extent.createTest("To verify 'Where to find your Certificate/Plan Number?' link.")
				.assignCategory(getClass().getSimpleName());


	}

	//@Test(priority = 11)
	public void ForgotUsername_TC11() throws Exception {
		logger = extent.createTest("To verify 'Return to Log In' link at forgot username page.")
				.assignCategory(getClass().getSimpleName());
		seleniumUtil.waitForElementVisible(forgotUsername.btn_return_to_login);
		seleniumUtil.clickByJS(forgotUsername.btn_return_to_login);
		seleniumUtil.verify(seleniumUtil.isElementDisplayed(login.txt_userName), true);	

	}
	
	//@AfterMethod
	public void postCondition () {
		seleniumUtil.refresh();

	} 

}
