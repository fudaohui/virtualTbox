package com.fdh.simulator.utils;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 读取resources/config.properties文件中配置的属性
 *
 * @author <a href="mailto:liws@xingyuanauto.com">liws</a>
 * @version $Revision$
 * @since 2016年8月1日
 */
public class PropertiesUtils {

    private static Properties props;

    public static String getProperty(String name) {
        String result = null;
        try {
            if (props == null)
                props = PropertiesLoaderUtils.loadAllProperties("config.properties");
            result = props.getProperty(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Integer getIntegerProperty(String name) {
        Integer result = null;
        try {
            if (props == null)
                props = PropertiesLoaderUtils.loadAllProperties("config.properties");
            String property = props.getProperty(name);
            result = Integer.parseInt(property);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getRefreshProperty(String name) {
        String result = null;
        try {
            props = PropertiesLoaderUtils.loadAllProperties("config.properties");
            result = props.getProperty(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean getBooleanProperty(String name) {
        return "true".equalsIgnoreCase(getProperty(name));
    }

    public static void main(String[] args) {
        try {
            if (props == null)
                props = PropertiesLoaderUtils.loadAllProperties("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration<?> em = props.elements();
        while (em.hasMoreElements()) {
            Object object = em.nextElement();
            System.out.println(object);
        }
    }
}
