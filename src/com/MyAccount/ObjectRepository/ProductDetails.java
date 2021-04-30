package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class ProductDetails {

	public By lbl_productName = By
			.xpath("//h1[starts-with(normalize-space(),'arrow_back')][contains(@class,'mainHeadingT')]");

	// InsuranceProduct: "N"

	public By lbl_planNumber = By.xpath("//span[text()='PLAN NUMBER']/following-sibling::span");
	public By lbl_planType = By.xpath("//span[text()='PLAN TYPE']/following-sibling::span");
	public By lbl_paidThrough = By.xpath("//span[text()='PAID THROUGH']/following-sibling::span");
	public By lbl_owner = By.xpath("//span[text()='OWNER']/following-sibling::span");
	public By lbl_annualProductCost = By.xpath("//span[text()='ANNUAL PRODUCT COST']/following-sibling::span");

	//  InsuranceProduct: "Y"
	
	public By lbl_certificateNumber = By.xpath("//span[text()='CERTIFICATE NUMBER']/following-sibling::span");
	public By lbl_coverageType = By.xpath("//span[text()='COVERAGE TYPE']/following-sibling::span");
	public By lbl_paymentDueDate = By.xpath("//span[text()='PAYMENT DUE DATE']/following-sibling::span");
	public By lbl_baseBenefitAmount = By.xpath("//span[text()='BASE BENEFIT AMOUNT']/following-sibling::span");
	public By lbl_dailyHospitalBenefit = By.xpath("//span[text()='DAILY HOSPITAL BENEFIT']/following-sibling::span");
	public By lbl_annualPremium = By.xpath("//span[text()='ANNUAL PREMIUM']/following-sibling::span");
	
	// Common 
	public By lbl_status = By.xpath("//span[text()='STATUS']/following-sibling::span");
	public By lbl_coverageEffectiveDate = By.xpath("//span[text()='COVERAGE EFFECTIVE DATE']/following-sibling::span");
	public By lbl_paymentFrequency = By.xpath("//span[text()='PAYMENT FREQUENCY']/following-sibling::span");

	
	public By btn_arrowBack = By.xpath("//a[text()='arrow_back']");
	
	public By embedPlugin = By.xpath("//div[@id='content']");
	

}
