package com.MyAccount.TestCases;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import com.MyAccount.Utilities.TestBase;
public class HttpCodeResponse extends TestBase{
   public static void main(String[] args) throws
   MalformedURLException, IOException {
      driver = new ChromeDriver();
      driver.get("https://www.tutorialspoint.com/questions/indexrw.php");
      // wait of 5 seconds
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      // establish and open connection with URL
      HttpURLConnection c=
      (HttpURLConnection)new
      URL("https://www.tutorialspoint.com/questions/indexrw.php")
      .openConnection();
      // set the HEAD request with setRequestMethod
      c.setRequestMethod("HEAD");
      // connection started and get response code
      c.connect();
      int r = c.getResponseCode();
      System.out.println("Http response code: " + r);
      driver.close();
   }
}
