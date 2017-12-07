package com.tsb.utils;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author zkl
 * @version 1.0
 * @date 2014-7-17 下午3:48:06
 */
public class PropertyUtil {

//	static Logger logger = Logger.getLogger(PropertyUtil.class.getName());

    public static final String DEFAULT_PROPERTY_FILE = "conf/conf.properties";

    private static Properties props = new Properties();

    static {
        load(DEFAULT_PROPERTY_FILE);
    }

    private static void load(String name) {
        InputStream is = PropertyUtil.class.getResourceAsStream("/" + name);
        try {
            try {
                props.load(is);
            } catch (Exception e) {
//				logger.error("Cannot loading|" + name + "|"
//						+ e.getMessage());
                System.out.println("Cannot loading|" + name + "|"
                        + e.getMessage());
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public static String getProperty(String key) {
        String result = props.getProperty(key);
        if (result != null && result.startsWith("@")) {
            result = getProperty(result.substring(1));
        }
        return result;
    }

    public static String getProperty(String key, String defaultValue) {
        String result = props.getProperty(key, defaultValue);
        if (result != null && result.startsWith("@")) {
            result = getProperty(result.substring(1), defaultValue);
        }
        return result;
    }

    public static void putValue(String key, String value) {
        props.put(key, value);
    }

    public static void saveProperty(String key, String value) {
//		///保存属性到b.properties文件
//		FileOutputStream oFile;
//		try {
//			//InputStream is = PropertyUtil.class.getResourceAsStream("/" + DEFAULT_PROPERTY_FILE);
//			oFile = new FileOutputStream(DEFAULT_PROPERTY_FILE, true);
//			//true表示追加打开
//			props.setProperty(key, value);
//			props.store(oFile, "Save properties file");
//			oFile.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

        try {
            OutputStream fos = new FileOutputStream(DEFAULT_PROPERTY_FILE);
            props.setProperty(key, value);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, "Update '" + key + value);
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }

    public static void updateProperty(String key, String value) {
        try {
            //props.load(new FileInputStream(DEFAULT_PROPERTY_FILE));  
            load(DEFAULT_PROPERTY_FILE);
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。   
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。   
            OutputStream fos = new FileOutputStream(DEFAULT_PROPERTY_FILE);
            props.setProperty(key, value);
            // 以适合使用 load 方法加载到 Properties 表中的格式，   
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流   
            //props.store(fos, "Update '" + key + "' value");   
            props.store(fos, "Save properties file");
            fos.flush();
            fos.close();
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }

    public static void main(String[] args) {
//		String username="89910003218";
        String username = "89910003205";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(java.util.Calendar.getInstance().getTime());
//		String password = com.tsb.ischool.utils.MD5Util.MD5(username + day + "tsb");
//        System.out.printf("password=" + password.toUpperCase());
//		PropertyUtil.updateProperty("SSCHOOL_INDEXED_USER_IINUM", "88888");
        //PropertyUtil.saveProperty("SSCHOOL_INDEXED_USER_IINUM", "88888");
        String testIp = PropertyUtil.getProperty("accessApiUrl");
        System.out.println("写属性文件成功！" + testIp);
//			　　Properties p = new Properties();
//			　　p.setProperty("id", "user1");
//			　　p.setProperty("password", "123456");
//			　　try{
//			　　    PrintStream stm = new PrintStream(new File("e:\test.properties"));
//			　　    p.list(stm);
//			   } catch (IOException e) {
//			　　    e.printStackTrace();
//			　　}
    }

}
