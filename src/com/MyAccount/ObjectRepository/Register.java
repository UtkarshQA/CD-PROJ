package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class Register {
	
	public By tab_active_account_details = By.xpath("//div[@class='navbar-link active']//div[contains(text(),'1. Account Details')]");
	public By tab_active_personal_information = By.xpath("//div[@class='navbar-link active']//div[contains(text(),'2. Personal Information')]");
	public By tab_active_sign_in_credentials = By.xpath("//div[@class='navbar-link active']//div[contains(text(),'3. Sign-in Credentials')]");
	public By tab_account_details = By.xpath("//li[contains(@class,'nav-item')]//span[contains(text(),'1. Account Details')]");
	public By tab_personal_information = By.xpath("//li[contains(@class,'nav-item')]//span[contains(text(),'2. Personal Information')]");
	public By tab_sign_in_credentials = By.xpath("//li[contains(@class,'nav-item')]//span[contains(text(),'3. Sign-in Credentials')]");
	public By txt_planNumber = By.id("planNumber");
	public By btn_next_disabled = By.xpath("//button[contains(@class,'disabled')][@value='Next']");
	public By btn_next_enabled = By.xpath("//button[contains(@class,'false')][@value='Next']");
	public By lnk_login = By.xpath("//a[contains(text(),'Log in')]");
	public By txt_firstName  = By.id("firstName");
	public By txt_lastname  = By.id("lastname");
	public By txt_email = By.id("email");
	public By btn_create_account_enabled = By.xpath("//button[contains(@class,'false')][@value='Create Account']");
	public By btn_create_account_disabled = By.xpath("//button[contains(@class,'disabled')][@value='Create Account']");
	public By txt_username  = By.id("Username");
	public By txt_password  = By.id("password");
	public By txt_confirmpassword  = By.id("confirmpassword");
	public By ddl_securityQuestion  = By.xpath("//select[@name='selectedQuestion'][@class='questionAnswer']");
	public By txt_answer  = By.id("answer");
	public By chk_iAgree  = By.xpath("//input[@name='iAgree']");

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

	
	
	
	

	
	
}

