package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class PaymentMethods {

	public By lnk_paymentMethods = By.xpath("//label[text()='Payment Methods']/parent::a");
	public By btn_addPaymentMethod = By.xpath("//a[@class='addPaymentButton']");
	public By btn_deleteMethod = By.xpath("//button[text()='Delete Method']");
	public By ddl_cardType = By.xpath("//select[@name='selectedCardType']");
	public By txt_cardHolderName = By.xpath("//input[@name='cardHolderName']");
	public By txt_cardNumber = By.xpath("//input[@name='cardNumber']");
	public By ddl_expMonth = By.xpath("//select[@name='expMonth']");
	public By ddl_expYear = By.xpath("//select[@name='expYear']");
	public By txt_address_1 = By.xpath("//input[@name='address1']");
	public By txt_address_2 = By.xpath("//input[@name='address2']");
	public By txt_address_3 = By.xpath("//input[@name='address3']");
	public By txt_city = By.xpath("//input[@name='city']");
	public By ddl_selectedState = By.xpath("//select[@name='selectedState']");
	public By txt_zipCode = By.xpath("//input[@name='zip']");
	public By btn_disable_saveChanges = By.xpath("//button[@disabled=''][text()='Save changes']");
	public By btn_enable_saveChanges = By.xpath("//button[text()='Save changes']");
	public By rbtn_creditOrDebitCard = By.xpath(
			"//input[@id='defaultUnchecked1'][@value='CREDIT_CARD']//ancestor::div[contains(@class,'custom-radio')]");
	public By rbtn_bankAccount = By.xpath("//input[@id='defaultUnchecked'][@value='BANK_ACCOUNT']");
	public By btn_cancel = By.xpath("//button[text()='Cancel']");
	public By rbtn_checking = By.id("Checking");
	public By rbtn_savings = By.id("Savings");
	public By txt_accountName = By.xpath("//input[@name='accountName']");
	public By txt_routingNumber = By.xpath("//input[@name='originalRoutingNumber']");
	public By txt_confirmRountingNumber = By.xpath("//input[@name='confirmRountingNum']");
	public By txt_accountNumber = By.xpath("//input[@name='accountNum']");
	public By txt_confirmAccountNumber = By.xpath("//input[@name='confirmAccountNum']");
	public By lbl_paymentTypeName = By.xpath("//div[@class='flexWhiteBox']//div[@class='headerColumn']//h3");

	public By lnk_delete = By.xpath("(//div[contains(@class,'flexWhiteBox')])[1]//a//span[text()='Delete']");
	public By lnk_edit = By.xpath("(//div[contains(@class,'flexWhiteBox')])[1]//a//span[text()='Edit']");
	public By lbl_1 = By.xpath("(//div[contains(@class,'flexWhiteBox')]//ul//li/span[2])[1]");
	public By lbl_2 = By.xpath("(//div[contains(@class,'flexWhiteBox')]//ul//li/span[2])[2]");
	public By lbl_3 = By.xpath("(//div[contains(@class,'flexWhiteBox')]//ul//li/span[2])[3]");
	public By lbl_4 = By.xpath("(//div[contains(@class,'flexWhiteBox')]//ul//li/span[2])[4]");

}
