package com.MyAccount.ObjectRepository;

import org.openqa.selenium.By;

public class Liferay {
	
	public By txt_screenName = By.id("_com_liferay_login_web_portlet_LoginPortlet_login");
	public By txt_password = By.id("_com_liferay_login_web_portlet_LoginPortlet_password");
	public By btn_sigin = By.xpath("//button/span[text()='Sign In']");
	public By lnk_controlPanel = By.xpath("//span[contains(@class,'category-name')][text()='Control Panel']");
	public By lnk_configuration = By.xpath("//a[@href='#panel-manage-control_panel_configuration']");
	public By lnk_serverAdminPortlet = By.id("_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_portlet_com_liferay_server_admin_web_portlet_ServerAdminPortlet");
	public By lnk_script = By.xpath("//li[@class='nav-item']//a/span[text()='Script']");
	public By ddl_language = By.id("_com_liferay_server_admin_web_portlet_ServerAdminPortlet_language");
	public By txt_script = By.id("_com_liferay_server_admin_web_portlet_ServerAdminPortlet_script");
	public By btn_execute = By.xpath("//button[@data-cmd='runScript']/span[text()='Execute']");
	public By btn_i_agree = By.xpath("//span[text()='I Agree']/parent::button");
	public By txt_password1 = By.id("password1");
	public By txt_password2 = By.id("password2");
	public By btn_save = By.xpath("//span[text()='Save']/parent::button");
	public By ddl_personal_menu_dropdown = By.xpath("//div[contains(@id,'personal_menu_dropdown')]");

	

}