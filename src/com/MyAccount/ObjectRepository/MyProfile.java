package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class MyProfile {
	
	public By lnk_profile  = By.linkText("Profile");
	public By lbl_fullName = By.xpath("//div[@class='profileName']//label[2]");
	public By lbl_dob = By.xpath("//div[@class='dob']//label[2]");
	public By lnk_makeChanges = By.xpath("//div[contains(@class,'btn-links')][text()='Make Changes']");
	public By lbl_addressLine_1 = By.xpath("//span[text()='ADDRESS']/following-sibling::span[@class='value']/span[1]");
	public By lbl_addressLine_2 = By.xpath("//span[text()='ADDRESS']/following-sibling::span[@class='value']/span[2]");
	public By lbl_addressLine_3 = By.xpath("//span[text()='ADDRESS']/following-sibling::span[@class='value']/span[3]");
	public By lbl_addressLine_4 = By.xpath("//span[text()='ADDRESS']/following-sibling::span[@class='value']/span[4]");
	public By lbl_phone = By.xpath("//span[text()='PHONE']/following-sibling::span[@class='value']");
	public By lbl_email = By.xpath("//span[text()='EMAIL']/following-sibling::span[@class='value']");
	public By lnk_changePassword  = By.linkText("Change Password");
	public By lnk_change  = By.linkText("Change");	

	public By txt_address_1 = By.xpath("//input[@name='address1']");
	public By txt_address_2 = By.xpath("//input[@name='address2']");
	public By txt_address_3 = By.xpath("//input[@name='address3']");
	public By txt_city = By.xpath("//input[@name='city']");
	public By ddl_state = By.xpath("//select[@name='_state']");
	public By txt_zipCode = By.xpath("//input[@name='zip']");
	public By txt_phone = By.xpath("//input[@name='phone']");
	public By txt_email = By.xpath("//input[@name='email']");
	public By btn_disable_saveChanges = By.xpath("//button[@disabled=''][text()='Save changes']");
	public By btn_enable_saveChanges = By.xpath("//button[text()='Save changes']");
	
	public By txt_oldPassword = By.xpath("//input[@name='oldPassword']");
	public By txt_newPassword = By.xpath("//input[@name='newPassword']");
	public By txt_confirmPassword = By.xpath("//input[@name='confirmPassword']");
	public By btn_disable_savePasswordChanges = By.xpath("//button[@disabled=''][text()='Save Password changes']");
	public By btn_enable_savePasswordChanges = By.xpath("//button[text()='Save Password changes']");
	
	public By lbl_invalid_msg_8_char_len = By.xpath("//span[contains(text(),'At least 8 characters in length')][@class='invalid']");
	public By lbl_invalid_msg_letters_a_z = By.xpath("//span[contains(text(),'Should contain lowercase letters (a-z)')][@class='invalid']");
	public By lbl_invalid_msg_letters_A_Z = By.xpath("//span[contains(text(),'Should contain upper case letters (A-Z)')][@class='invalid']");
	public By lbl_invalid_msg_numbers_0_9 = By.xpath("//span[contains(text(),'Should contain numbers (i.e. 0-9)')][@class='invalid']");
	public By lbl_invalid_msg_match_pass = By.xpath("//span[contains(text(),'New and Confirm password should match')][@class='invalid']");

	public By lbl_valid_msg_8_char_len = By.xpath("//span[contains(text(),'At least 8 characters in length')][@class='valid']");
	public By lbl_valid_msg_letters_a_z = By.xpath("//span[contains(text(),'Should contain lowercase letters (a-z)')][@class='valid']");
	public By lbl_valid_msg_letters_A_Z = By.xpath("//span[contains(text(),'Should contain upper case letters (A-Z)')][@class='valid']");
	public By lbl_valid_msg_numbers_0_9 = By.xpath("//span[contains(text(),'Should contain numbers (i.e. 0-9)')][@class='valid']");
	public By lbl_valid_msg_match_pass = By.xpath("//span[contains(text(),'New and Confirm password should match')][@class='valid']");
	
	public By ddl_selectedQuestion = By.xpath("//select[@name='selectedQuestion']");
	public By txt_answer = By.xpath("//input[@name='selectedAnswer']");
	public By btn_cancel = By.xpath("//button[text()='Cancel']");

	
	
}
