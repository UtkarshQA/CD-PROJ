package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class ResetPassword {
	
	public By txt_new_password  = By.xpath("//input[@name='newPassword']");
	public By txt_confirmpassword  = By.xpath("//input[@name='confirmPassword']");
	
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

	public By btn_submit_enable = By.xpath("//button[@value='Submit'][contains(@class,'false')]");
	public By btn_submit_disabled = By.xpath("//button[@value='Submit'][contains(@class,'disabled')]");
}
