package com.funnymiai.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串工具类
 */
public final class StringUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
	/** Private Constructor **/
	private StringUtil(){
	}
	
	/***
	 * 
	 * @Description:  字符集
	 * @ClassName ENCODING
	 * @author: qinzheng
	 * @Created 2015年9月10日 上午9:48:26
	 */
	public interface ENCODING {
		String UTF_8 = "UTF-8";
		String GBK = "GBK";
		String ISO_8858_1 = "ISO-8859-1";
		String GB2312 = "GB2312";
	}
	
	/**
	 * 判断字符串是否非null && 非空字符 
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNotEmpty(String param) {
		return param != null && !"".equals(param.trim());
	}

	/**
	 * 判断字符串是否为null || 空字符串
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param) {
		return param == null || "".equals(param.trim());
	}
	
	/**
	 * 判断是否为数字
	 * @param str
	 * @return True为数字
	 */
	public static boolean isNum(String str) {
		String regex = "^(-?\\d+)(\\.\\d+)?$";
		return matchRegex(str, regex);
	}
	
	public static String trim2Empty(String str) {
		return isEmpty(str) ? "" : str.trim();
	}	
	
	private static boolean matchRegex(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	/**
	 * 
	 * @Description: 生成UUID
	 * @return
	 * @ReturnType String
	 * @author: qinzheng
	 * @Created 2015年8月31日 上午11:31:30
	 */
	public static String uuid(){
		return UUID.randomUUID().toString();
	}
	
	/**
	  * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	  */
	 public static String uuid32() {
	  return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	 }
	 
	 /**生成40位的UUID
	  * @return
	  */
	 public static String uuid40(){
	  
	  return java.util.UUID.randomUUID().toString().replaceAll("-", "")+java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0,8);
	 }

	/**
	 * int字符串装int值
	 * @Description: 
	 * @param intstr
	 * @return
	 * @ReturnType int
	 * @author: qinzheng
	 * @Created 2015年8月21日 上午10:44:01
	 */
	public static int toInt(String intstr){
		try {
			return Integer.parseInt(intstr);
		} catch (Exception e) {
			LOGGER.error("", "", e);
			return 0;
		}
	}
	
	/**
	 * 
	 * @Description: 字符串字符转换
	 * @param str
	 * @param encoding
	 * @param toEncoding
	 * @return
	 * @throws DedicException
	 * @ReturnType String
	 * @author: qinzheng
	 * @throws UnsupportedEncodingException 
	 * @Created 2015年9月10日 上午9:52:45
	 */
	public static String convertCharacter(String str, String encoding, String toEncoding) throws UnsupportedEncodingException{
		return new String(str.getBytes(encoding), toEncoding);
	}
	
	/**
	 * 两个字段取值是否变动-带非空验证
	 * @Description: 
	 * @param a 
	 * @param b
	 * @return
	 * @ReturnType boolean
	 * @author: Chengfang.Su
	 * @Created 2016年8月23日 上午10:44:01
	 */
	public static boolean isChanged(String a, String b){
		if(null == a && null == b){
			return false;
		}else if(null == a || null == b){
			return true;
		}else if(a.equals(b)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 获取市级区域编码处理
	 * @Description: 
	 * @param regnCode
	 * @return
	 * @ReturnType String
	 * @author: Chengfang.Su
	 * @Created 2016年10月21日 上午10:20:17
	 */
	public static String subRegnCode4Str(String regnCode){
		String str = (isEmpty(regnCode)||"null".equals(regnCode))?"":regnCode;
		if(isNotEmpty(str)&&str.length()>4){
			str=str.substring(0, 4).concat("00000000");
		}
		return str;
	}
	
	/**
	 * 脱敏规则——手机号码(手机号码隐藏中间4位数)
	 * @Description: 
	 * @param telnum
	 * @return
	 * @ReturnType String
	 * @author: Chengfang.Su
	 * @Created 2017年5月31日 上午10:25:10
	 */
	public static String desensitizationPhone(String telnum){
		String rtnStr = telnum;
		if(isEmpty(rtnStr)||rtnStr.trim().length()<7){
			return rtnStr;
		}
		rtnStr = rtnStr.trim();
		// 校验是否为电话号码
		String regExp = "^([0-9]{3,4}-)?[0-9]{7,8}$";
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(rtnStr);  
        if(m.matches()){// 若判断为固话
        	rtnStr = replace(rtnStr,rtnStr.length()-3,"****");
        }else{
        	rtnStr = replace(rtnStr,4,"****");
        }
        return rtnStr;
	}
	
	/**
	 * 脱敏规则——身份证(18／20位：隐藏7—14位)
	 * @Description: 
	 * @param credNum
	 * @return
	 * @ReturnType String
	 * @author: Chengfang.Su
	 * @Created 2017年5月31日 上午10:25:10
	 */
	public static String desensitizationCredNum(String credNum){
		String rtnStr = credNum;
		if(isEmpty(rtnStr)||rtnStr.trim().length()<15){
			return rtnStr;
		}else{
			return replace(rtnStr.trim(), 7, "********");
		}
	}
	
	/**
	 * 脱敏规则——银行卡号(保留末4位)
	 * @Description: 
	 * @param opaccBankAcct
	 * @return
	 * @ReturnType String
	 * @author: Chengfang.Su
	 * @Created 2017年5月31日 上午10:25:10
	 */
	public static String desensitizationBankAcct(String opaccBankAcct){
		String rtnStr = opaccBankAcct;
		if(isEmpty(rtnStr)||rtnStr.trim().length()<4){
			return rtnStr;
		}else{
			rtnStr = rtnStr.trim();
			StringBuilder newChar = new StringBuilder("");
			for(int i=0;i<rtnStr.length()-4;i++){
				newChar.append("*");
			}
			return replace(rtnStr, 1, newChar.toString());
		}
	}
	
	/**
	 * 脱敏规则
	 * @Description: 
	 * @param str(需脱敏的字符串) 
	 * @param n(脱敏开始位)
	 * @param newChar(脱敏掩码)
	 * @return
	 * @ReturnType String
	 * @author: Chengfang.Su
	 * @Created 2017年5月31日 上午10:25:10
	 */
	public static String replace (String str,int n,String newChar){ 
		String s1=""; 
		String s2=""; 
		try{
			s1=str.substring(0,n-1); 
			s2=str.substring(n-1+newChar.length(),str.length());
			return s1+newChar+s2;
		}catch(Exception ex){ 
			LOGGER.error("", "", ex);
			LOGGER.error("", "", "替换的位数小于1或大于字符串的位数");
			return str;
		} 
	}
	/**
	* @author 黄承文
	* @Title: outOfSensitiveInformation
	* @Description: 脱敏 提取公共方法
	* @param @param list
	* @param @param typeMap    设定文件
	* @return void    返回类型
	* @throws
	*/
	public static void outOfSensitiveInformation(List<Map<String,String>> list,Map<String,String> typeMap){
		if(null == list ||list.isEmpty()){
			return;
		}
		for(Entry<String,String> entry  : typeMap.entrySet()){
			for(Map<String,String> pMap : list){
				if(StringUtil.isNotEmpty(pMap.get(entry.getKey()))){
					switch (entry.getValue()){
					case "1"://电话号码,固话，手机
						String telNum = desensitizationPhone(pMap.get(entry.getKey()));
						pMap.put(entry.getKey(),telNum);
						break;
					case "2"://身份证号
						String credNum = desensitizationCredNum(pMap.get(entry.getKey()));
						pMap.put(entry.getKey(),credNum);
						break;
					case "3"://银行号
						String bankAcct = desensitizationBankAcct(pMap.get(entry.getKey()));
						pMap.put(entry.getKey(),bankAcct);
						break;
					default:
						break;
					}
				}
			}
		}
	}
	/**
	* @author 黄承文
	* @Title: outOfSensitiveInformation
	* @Description: 脱敏 提取公共方法
	* @param @param pMap
	* @param @param typeMap    设定文件
	* @return void    返回类型
	* @throws
	*/
	public static void outOfSensitiveInformation(Map<String,String> pMap,Map<String,String> typeMap){
		if(null == pMap ||pMap.isEmpty()){
			return;
		}
		for(Entry<String,String> entry  : typeMap.entrySet()){
				if(StringUtil.isNotEmpty(pMap.get(entry.getKey()))){
					switch (entry.getValue()){
					case "1"://电话号码,固话，手机
						String telNum = desensitizationPhone(pMap.get(entry.getKey()));
						pMap.put(entry.getKey(),telNum);
						break;
					case "2"://身份证号
						String credNum = desensitizationCredNum(pMap.get(entry.getKey()));
						pMap.put(entry.getKey(),credNum);
						break;
					case "3"://银行号
						String bankAcct = desensitizationBankAcct(pMap.get(entry.getKey()));
						pMap.put(entry.getKey(),bankAcct);
						break;
					default:
						break;
					}
			}
		}
	}
	public static void outOfSensitiveInformation(Object object,Map<String,String> typeMap){
		if(null == object){
			return;
		}
		//将object换Map
		@SuppressWarnings("unchecked")
		Map<String,Object> pMap = (Map<String, Object>) object;
		for(Entry<String,String> entry  : typeMap.entrySet()){
			if(pMap.containsKey(entry.getKey()) && StringUtil.isNotEmpty(String.valueOf(pMap.get(entry.getKey())))){
				switch (entry.getValue()){
				case "1"://电话号码,固话，手机
					String telNum = desensitizationPhone(String.valueOf(pMap.get(entry.getKey())));
					pMap.put(entry.getKey(),telNum);
					break;
				case "2"://身份证号
					String credNum = desensitizationCredNum(String.valueOf(pMap.get(entry.getKey())));
					pMap.put(entry.getKey(),credNum);
					break;
				case "3"://银行号
					String bankAcct = desensitizationBankAcct(String.valueOf(pMap.get(entry.getKey())));
					pMap.put(entry.getKey(),bankAcct);
					break;
				default:
					break;
				}
			}
		}
	}
	/**
	 * @author: qiwanjia
	 * @Description: 处理身份证号转换为出生年月（15：老身份证号、18：新身份证号、20：残疾人证号）
	 * @param str
	 * @return
	 * @date 2017年8月21日 上午11:36:09
	 */
	public static String handleIdNo(String str){
		String rtnStr = str;
		if(rtnStr.length() == 15){
			rtnStr = new StringBuilder("19").append(rtnStr.substring(6, 8)).append("-").append(rtnStr.substring(8, 10)).append("-").append(rtnStr.substring(10,12)).toString();
		}else if(rtnStr.length() == 18 || rtnStr.length() == 20){
			rtnStr = new StringBuilder(rtnStr.substring(6, 10)).append("-").append(str.substring(10, 12)).append("-").append(str.substring(12,14)).toString();
		}
		return rtnStr;
	}
	
	/**
	 * 替换传递参数里面的特殊字符，
	 * 
	 * @param param 需要处理的字符串
	 * @param specialChar  被替换的特殊字符 
	 * @return
	 */
	public static String replaceChar(String param,String specialChar) {
		String newStr = param;//替换过后的字符；
		if(!StringUtil.isEmpty(param)){
			String replaceStr = param.replace(specialChar, "','");
			newStr = new StringBuilder().append("'").append(replaceStr).append("'").toString();
		}
		return newStr;
	}
	
	/**
	 * 
	 * @Title concatStr
	 * @Description: 字符串拼接
	 * @param: str 原字符串
	 * @param: tempStr 拼接字符串
	 * @return String
	 * @throws
	 * @author Chengfang.Su
	 * @Date 2017年11月7日   上午10:59:08
	 */
	public static String concatStr(String str, String tempStr){
		return concatStr(str, ",", tempStr);
	}
	
	/**
	 * 
	 * @Title concatStr
	 * @Description: 字符串拼接
	 * @param: str 原字符串
	 * @param: splStr 拼接符
	 * @param: tempStr 拼接字符串
	 * @return String
	 * @throws
	 * @author Chengfang.Su
	 * @Date 2017年11月7日   上午10:59:08
	 */
	public static String concatStr(String str, String splStr, String tempStr){
		if(isEmpty(str))return tempStr;
		if(null==tempStr)tempStr="";
		if(null==splStr)splStr=",";
		return new StringBuilder(str).append(splStr).append(tempStr).toString();
	}
	/*public static void main(String[] args) throws Exception{
		//DataSourcePasswordCrypto DataSourcePasswordCrypto =new DataSourcePasswordCrypto();
		//String s = DataSourcePasswordCrypto.decrypt("rO0ABXciABAXlLeTSkG6ksapXoZLbZ60s4IEQQJIaBbws67USNtU+w==");
		//System.err.print(s);
		//String t= DataSourcePasswordCrypto.encrypt("$dfxrXF64");
		//System.err.print(t); //0pX8xz!Qn
		//JedisUtil.getRedisCache().setString("111", "222");
		//System.err.print(JedisUtil.getRedisCache().getString("111"));
	}*/
	/**
     * 生成随机码
     * @param numberFlag
     * @param length
     * @return
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int len = strTable.length();
        boolean bDone = true;
        do {
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int)Math.floor(dblR);
                char c = strTable.charAt(intR);
                if ('0' <= c && c <= '9') {
                    count++;
                }
                retStr += Character.toString(strTable.charAt(intR));
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }
    
    /**
     * 去除开头或结尾的0
     * @param tempString
     * @return
     */
	public static String deleteZero(String tempString, int type) {
        int initlen = tempString.length(); // 串的初始长度
        int finallen = initlen; // 串的最终长度
        int start = 0; // 串的开始位置
        int off = 0; // 串的偏移位置
        char[] val = new char[initlen];
        tempString.getChars(0, finallen, val, 0); // 保存原数据，用于判断字符
        if (type == 1) { //去除开头的0
        	// 找到以'0'开头的后一位
        	while ((start < finallen) && (val[off + start] == '0')) {
        		start++;
        	}
        }
        if (type == 2) { //去除结尾的0
	        // 找到以'0'结尾的前一位
	        while ((start < finallen) && (val[off + finallen - 1] == '0')) {
	            finallen--;
	        }
        }
        return ((start > 0) || (finallen < initlen)) ? tempString.substring(start, finallen): tempString;
    }
	
	/**
	 * 自定义名称+日期生成名称
	 * @param name 文件名
	 * @param ext 扩展名
	 * @return
	 */
	public static String randomName(String name, String ext) {
		String nowTime = DateUtil.date2String(new Date(), DateUtil.DATE_PATTERN.YYYYMMDDHHMMSSSSS);
		String backName;
		if (isNotEmpty(name)) {
			backName = concatStr(name, "_", nowTime);
		} else {
			backName = concatStr("default", "_", nowTime);
		}
		if (isNotEmpty(ext)) {
			backName = concatStr(backName, ".", ext);
		}
		return backName;
	}
	
	/**
     * 校验特殊字符
     * @param str
     * @return
     */
    public static boolean xssCheck(String str) {
        boolean flag = false;
        String strParam = ">,<,\',\",\\";
        String[] arrParam = strParam.split(",");
        for (String element : arrParam) {
            if (str.indexOf(element) >= 0) {
                flag = true;
            }
        }
        return flag;
    }
    
    /**
     * 接口字符串校验
     * @param param 需要校验的字符串
     * @param paramNm 参数说明
     * @param empty 是否非空校验
     * @param length 是否长度校验
     * @param lengtNum 校验长度
     * @throws NgesopControlException
     */
    public static String checkString(String param, String paramNm, boolean empty, boolean length, int lengtNum) {
        String strParam = ">,<,(,),\',\",\\,.,（，）,。";
        String[] arrParam = strParam.split(",");
        String resultStr = null;
        if (empty) {
            if (isEmpty(param)) {
            	resultStr = paramNm + "不能为空";
            	return resultStr;
            }
        }
        if (StringUtil.isNotEmpty(param)) {
            for (String element : arrParam) {
                if (param.indexOf(element) >= 0) {
                	resultStr = paramNm + "不能包含特殊字符";
                	return resultStr;
                }
            }
        }
        if (length) {
            if (StringUtil.isNotEmpty(param) && param.length() > lengtNum) {
            	resultStr = paramNm + "不能超过" + lengtNum + "位";
            	return resultStr;
            }
        }
        return resultStr;
        
    }
}
