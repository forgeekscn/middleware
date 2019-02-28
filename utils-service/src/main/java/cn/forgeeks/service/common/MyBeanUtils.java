package cn.forgeeks.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

/**
 * map <=> obj
 */
public class MyBeanUtils {

    static Logger log = LoggerFactory.getLogger(MyBeanUtils.class);

    /**
     * obj => map
     */
    public static  Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        TreeMap<String, Object> map = new TreeMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.equals("class")) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                map.put(key, value);
            }
        } catch (Exception e) {
            log.error("transBean2Map Error " + e);
        }
        return map;
    }


    /**
     * map => obj
     */
    public static <T> T transfer(Map<String,Object> map,Object obj){
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    Method setter = property.getWriteMethod();
                    try {
                        setter.invoke(obj, value);
                    } catch (IllegalArgumentException ex) {
                        // log.info("IllegalArgumentException[{}]");
                    }
                }
            }
        } catch (Exception ex) {
            log.info("Exception[{}]");
        }
        return (T) obj;
    }

}
