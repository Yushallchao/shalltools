package com.xiao.utils;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @Author Xiao
 * @Date 2021/12/22 8:59
 */
public class RegexUtil {

	public static void main(String[] args) {

	}


	/**
	 * 判断是否是正确的邮箱地址
	 *
	 * @param email 邮箱
	 * @return boolean 是否是邮箱，true是，false否
	 */
	public static boolean isEmail(String email) {
		if (Strings.isNullOrEmpty(email)) {
			return false;
		}
		return email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	}

	/**
	 * 判断是否是url
	 *
	 * @param url url地址
	 * @return boolean 是否是url地址，true是，false否
	 */
	public static boolean isUrl(String url) {
		String regex = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
		return url.matches(regex);
	}

	/**
	 * 判断是否是正确的IP地址
	 *
	 * @param ip ip地址
	 * @return boolean 是否是IP地址，true是，false否
	 */
	public static boolean isIp(String ip) {
		if (Strings.isNullOrEmpty(ip)) {
			return false;
		}
		//^((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$

		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		return ip.matches(regex);
	}

	/**
	 * 判断是否正整数
	 *
	 * @param number 数字字符串
	 * @return boolean 是否是正整数，true是，false否
	 */
	public static boolean isNumber(String number) {
		if (Strings.isNullOrEmpty(number)) {
			return false;
		}
		return number.matches("[0-9]*");
	}

	/**
	 * 简单判断是否是11位手机号码
	 *
	 * @param mobile 手机号
	 * @return boolean 是否11位手机号，true是，false否
	 */
	public static boolean isMobile(String mobile) {
		if (Strings.isNullOrEmpty(mobile)) {
			return false;
		}
		return mobile.matches("^1\\d{10}$");
	}

	/**
	 * 判断是否含有中文，仅适合中国汉字，不包括标点
	 *
	 * @return boolean 是否含有中文，true是，false否
	 */
	public static boolean hasChinese(String text) {
		if (Strings.isNullOrEmpty(text)) {
			return false;
		}
		Pattern p = compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(text);
		return m.find();
	}

	/**
	 * 判断是否含有特殊字符
	 *
	 * @param text 检查文本
	 * @return boolean 是否含有特殊字符，true是，false否
	 */
	public static boolean hasSpecialChar(String text) {
		if (Strings.isNullOrEmpty(text)) {
			return false;
		}
		// 除a-zA-Z0-9、中划线-、下划线_、空格、制表符、换页符等之外的字符
		return text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() != 0;
	}

	/**
	 * 判断是否身份证号
	 *
	 * @param id 身份证号
	 * @return boolean 是否正常身份证号，true是，false否
	 */
	public static boolean isIdCard(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		// 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
		String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)" +
				"\\d{3}[0-9Xx]$)|" +
				"(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
		//假设18位身份证号码:41000119910101123X  410001 19910101 123X
		//^开头
		//[1-9] 第一位1-9中的一个      4
		//\\d{5} 五位数字           10001（前六位省市县地区）
		//(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
		//\\d{2}                    91（年份）
		//((0[1-9])|(10|11|12))     01（月份）
		//(([0-2][1-9])|10|20|30|31)01（日期）
		//\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
		//[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
		//$结尾

		//假设15位身份证号码:410001910101123  410001 910101 123
		//^开头
		//[1-9] 第一位1-9中的一个      4
		//\\d{5} 五位数字           10001（前六位省市县地区）
		//\\d{2}                    91（年份）
		//((0[1-9])|(10|11|12))     01（月份）
		//(([0-2][1-9])|10|20|30|31)01（日期）
		//\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
		//$结尾

		boolean matches = id.matches(regularExpression);

		//判断第18位校验值
		if (matches) {
			if (18 == id.length()) {
				try {
					char[] charArray = id.toCharArray();
					//前十七位加权因子
					int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
					//这是除以11后，可能产生的11位余数对应的验证码
					String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
					int sum = 0;
					for (int i = 0; i < idCardWi.length; i++) {
						int current = Integer.parseInt(String.valueOf(charArray[i]));
						int count = current * idCardWi[i];
						sum += count;
					}
					char idCardLast = charArray[17];
					int idCardMod = sum % 11;

					return idCardY[idCardMod].equals(String.valueOf(idCardLast));
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return matches;
	}

}
