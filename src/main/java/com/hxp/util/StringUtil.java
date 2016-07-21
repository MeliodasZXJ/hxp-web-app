package com.hxp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	/** 空串"" */
	public final static String EMPTY_STR = "";

	/**
	 * 判断是否是空字符串 null,""和" " 都返回 true
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 判断字符串不是空字符串，并且不是null
	 * 如果不是空字符串，返回true
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		if(str == null || str.trim().length() == 0) {
			return false;
		}else {
			return true;
		}
	}

	/**
	 * 判断输入的字符串是否为纯汉字
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为浮点数，包括double和float
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 获取文件后缀格式
	 *
	 * @param filename
	 * @return 文件后缀格式 eg: (jpg)
	 */
	public static String getFileExt2(String filename) {
		if(isEmpty(filename) || filename.lastIndexOf(".") == -1) {
			return "";
		}
		String extName = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
		return extName;
	}

	/**
	 * 判断是否为空字符串
	 * @param string
	 * @return 字符串是否为空
	 */
	public static boolean isEmpty(String string) {
		boolean result = false;
		if(string == null) {
			return true;
		}
		if("".equals(string.trim())) {
			return true;
		}
		return result;
	}

	/**
	 * 判断输入的字符串是否符合Email样式
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}

	/**
	 * 判断是否为整数
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 验证英数字符，包括下划线。与[A-Za-z0-9_]等效
	 */
	public static boolean isLetter(String str) {
		if (str == null || str.length() < 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 验证是不是合法的手机号码
	 */
	public static boolean isMobilePhone(String handset) {
		try {
			String regex = "^1[3|4|5|7|8]\\d{9}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(handset);
			return matcher.matches();

		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 验证是不是合法的密码
	 */
	public static boolean isPassWord(String handset) {
		try {
			String regex = "^[a-zA-Z]\\w{5,17}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(handset);
			return matcher.matches();

		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 判断输入的数据是否是质数
	 */
	public static boolean isPrime(int x) {
		if (x <= 7) {
			if (x == 2 || x == 3 || x == 5 || x == 7)
				return true;
		}
		int c = 7;
		if (x % 2 == 0)
			return false;
		if (x % 3 == 0)
			return false;
		if (x % 5 == 0)
			return false;
		int end = (int) Math.sqrt(x);
		while (c <= end) {
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 4;
			if (x % c == 0) {
				return false;
			}
			c += 6;
			if (x % c == 0) {
				return false;
			}
			c += 2;
			if (x % c == 0) {
				return false;
			}
			c += 6;
		}
		return true;
	}

	/**
	 * 在字符串source中，用to替换from
	 */
	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null)
			return null;
		StringBuffer str = new StringBuffer("");
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			str.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = source.indexOf(from);
		}
		str.append(source);
		return str.toString();
	}

	/**
	 * 人民币转换成大写
	 */
	public static String toBigMoney(String str) {
		double value;
		try {
			value = Double.parseDouble(str.trim());
		} catch (Exception e) {
			return null;
		}
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '元'; // 如果整数部分存在,则有元的字样
		return prefix + suffix; // 返回正确表示
	}

	/**
	 * 截取字符串　超出的字符用symbol代替 　　
	 * @param len 字符串长度　长度计量单位为一个GBK汉字  两个英文字母计算为一个单位长度
	 * @param str
	 * @param symbol
	 * @return
	 */
	public static String getLimitLengthString(String str, int len, String symbol) {
		int iLen = len * 2;
		int counterOfDoubleByte = 0;
		String strRet = EMPTY_STR;
		try {
			if (str != null) {
				byte[] b = str.getBytes("GBK");
				if (b.length <= iLen) {
					return str;
				}
				for (int i = 0; i < iLen; i++) {
					if (b[i] < 0) {
						counterOfDoubleByte++;
					}
				}
				if (counterOfDoubleByte % 2 == 0) {
					strRet = new String(b, 0, iLen, "GBK") + symbol;
					return strRet;
				} else {
					strRet = new String(b, 0, iLen - 1, "GBK") + symbol;
					return strRet;
				}
			} else {
				return EMPTY_STR;
			}
		} catch (Exception ex) {
			return str.substring(0, len);
		} finally {
			strRet = null;
		}
	}

	/**
	 * 对象去掉空格后,是否为空
	 * @param source
	 * @return
     */
	public static String trimToEmpty(Object source) {
		return source != null ? source.toString().trim() : "";
	}

	/**
	 * 截取字符串　超出的字符用symbol代替 　　
	 * @param len 字符串长度　长度计量单位为一个GBK汉字  两个英文字母计算为一个单位长度
	 * @param str
	 * @return
	 */
	public static String getLimitLengthString(String str, int len) {
		return getLimitLengthString(str, len, "...");
	}

	/**
	 * 如果是null返回"";
	 * 否则，返回原值
	 * @param str
	 * @return str or ""
	 */
	public static String null2Empty(String str) {
		if(str == null) {
			return EMPTY_STR;
		}else {
			return str;
		}
	}
	
	public static void main(String[] args) {
		String source = "abcdefgabcdefgabcdefgabcdefgabcdefgabcdefg";
		String from = "efg";
		String to = "替换了";
		System.out.println("在字符串source中，用to替换from，替换结果为："
				+ replace(from, to, source));
		System.out.println("返回指定字节长度的字符串："
				+ getLimitLengthString("字1符串abcdefgabcd", 2, EMPTY_STR));
		System.out.println("返回指定字节长度的字符串："
				+ getLimitLengthString("字1符串abcdefgabcd", 4));
		System.out.println("判断是否为整数：" + isInteger("-1"));
		System.out.println("判断是否为浮点数，包括double和float：" + isDouble("+0.36"));
		System.out.println("判断输入的字符串是否符合Email样式：" + isEmail("fhwbj@163.com"));
		System.out.println("判断输入的字符串是否为纯汉字：" + isChinese("你好！"));
		System.out.println("判断输入的数据是否是质数：" + isPrime(12));
		System.out.println("人民币转换成大写：" + toBigMoney("1020965.00"));
		System.out.println("判断是不是合法的手机号码：" + isMobilePhone("15981807340"));
		System.out.println("判断是不是英数字符，包括下划线：" + isLetter("1k2_"));
	}
}
