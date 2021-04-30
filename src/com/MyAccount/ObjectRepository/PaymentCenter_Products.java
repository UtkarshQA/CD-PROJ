package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class PaymentCenter_Products {
	public By lnk_products = By.xpath("//div[contains(@class,'innerNavHeader')]//label[text()='Products']/parent::a");
	public By tbl_products = By.xpath("//table[contains(@class,'productTable')]");
	public By total_rows_products = tbl_products.xpath("//tbody//tr");
	public By btn_payNow = By.xpath("//button[text()='Pay Now']");
	public By btn_change_auto_pay_or_frequency = By.xpath("//button[text()='Change Auto Pay or Frequency']");
	public By ddl_payNow = By.xpath("//button[text()='Pay Now']");
	public By lbl_planName = By.xpath("//div[@class='modal-body']/h2[contains(@class,'subHeading')]");
	public By lbl_planNumber = By
			.xpath("//span[contains(text(),'CERTIFICATE')]/following-sibling::span[@class='value']");
	public By lbl_paymentFrequecy = By.xpath("//span[contains(text(),'PAYMENT FREQUENCY')]/following-sibling::span[@class='value']");
	
	public By ddl_paymentFrequecy = By.xpath("//select[@name='selectedPaymentFrequecy']");
	public By ddl_paymentMethod = By.xpath("//select[@name='selectedPaymentMethod']");
	public By lbl_dueDate = By.xpath("//span[contains(text(),'DUE DATE')]/following-sibling::span[@class='value']");
	public By lbl_amountDue = By.xpath("//span[contains(text(),'AMOUNT DUE')]/following-sibling::span[@class='value']");
	public By btn_saveChange = By.xpath("//button[text()='Save changes']");
	public By btn_cancel = By.xpath("//button[text()='Cancel']");

	
}
