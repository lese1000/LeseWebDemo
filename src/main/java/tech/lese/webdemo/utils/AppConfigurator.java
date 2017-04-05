package tech.lese.webdemo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfigurator {
	private static AppConfigurator property;
	private Properties pro = new Properties();

	private AppConfigurator() {
		// 加载 classes下 appConfigurator.properties 属性文件
		
		InputStream inStream = AppConfigurator.this.getClass().getClassLoader().getResourceAsStream("system.properties");
		try {
			if (inStream == null) {
				throw new NullPointerException("classes \u76EE\u5F55\u4E0B\u627E\u4E0D\u5230 appConfigurator.properties \u5C5E\u6027\u6587\u4EF6");
			}
			pro.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""));	//项目路径
//		filePath = filePath.replaceAll("file:/", "");
//		filePath = filePath.replaceAll("%20", " ");
//		filePath = filePath.trim() + "system.properties";
//		try {
//			pro.load(new FileInputStream(filePath));   ;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private static AppConfigurator getInstance() {
		property = new AppConfigurator();
		return property;
	}

	public static final String getProperty(String propertyKey) {
		return getInstance().pro.getProperty(propertyKey);
	}

}
