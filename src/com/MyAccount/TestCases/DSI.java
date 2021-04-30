package com.MyAccount.TestCases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.MyAccount.Utilities.TestBase;

public class DSI extends TestBase {
	
	@Test
	private void check_url () {
		
		try {
			int lastRow = excelUtil.getLastRow("D:\\DSI\\Intranet Without VPN\\URL sheet\\my-d-pages.xlsx", "Squirrel SQL Export");
			System.out.println(lastRow);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

}
