package MemoryLeakTest;

import java.net.URL;
import java.util.Scanner;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class App {
	public static void main(String[] args) {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.APP, getAut());
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, "justsoso");
		dc.setCapability(MobileCapabilityType.NO_RESET, true);
		dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 3600);

		AndroidDriver<WebElement> driver = null;
		Scanner s = new Scanner(System.in);
		int i = 0;
		
		try {
			driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
			System.out.println("wait...");
			s.nextLine();
			for (String c : driver.getContextHandles()) {
				if (c.startsWith("WEBVIEW")) {
					driver.context(c);
					System.out.println("switched to " + c);
					break;
				}
			}
			System.out.println(driver.getCurrentUrl());

			for (i = 0; i < 1000; ++i) {
				driver.navigate().refresh();
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("current loop: " + i);
		} finally {
			s.close();
			if (driver != null)
				driver.quit();
		}
	}

	public static String getAut() {
		String aut = App.class.getResource("/aut.apk").getFile();
		return aut.substring(1);
	}
}
