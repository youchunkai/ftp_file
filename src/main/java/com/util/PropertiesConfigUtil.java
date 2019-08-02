package com.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertiesConfigUtil {

	/**
	 * 根据输入的value值读取配置文件的内容
	 * @param key key值
	 * @return 返回key对应的value字符串
	 */
	public static String getConfigByKey(String key){
		ResourceBundle rs = getResourceBundle();// 获取本地化配置
		String result=rs.getString(key);
		return result;
	}
	
	/**
	 * 获取ResourceBundle对象
	 * @return
	 */
	public static ResourceBundle getResourceBundle(){
		Locale currentLocale = Locale.getDefault();//根据 Locale加载资源包
		return ResourceBundle.getBundle("config/config", currentLocale);
	}
	
	public static void main(String[] args){
		String originalAir = PropertiesConfigUtil.getConfigByKey("originalAir");
		String originalWater = PropertiesConfigUtil.getConfigByKey("originalWater");
		String analyseAir = PropertiesConfigUtil.getConfigByKey("analyseAir");
		String analyseWater = PropertiesConfigUtil.getConfigByKey("analyseWater");
		System.out.println(originalAir + "   " +analyseAir+ "   "+originalWater+ "   "+analyseWater);
	}
	
}
