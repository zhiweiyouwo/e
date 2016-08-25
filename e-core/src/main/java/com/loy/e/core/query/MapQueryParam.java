package com.loy.e.core.query;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.loy.e.core.exception.LoyException;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class MapQueryParam {

    protected final Log logger = LogFactory.getLog(MapQueryParam.class);
    private Map<String, Object> values;
    private Object param = null;

    public MapQueryParam(Object param) {
        this.param = param;
        this.values = new HashMap<String, Object>();
        if (param != null) {
            try {
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(param);
                for (int i = 0; i < descriptors.length; i++) {
                    String name = descriptors[i].getName();
                    if (!"class".equals(name)) {
                        Object value = propertyUtilsBean.getNestedProperty(param, name);
                        if (value != null) {
                            values.put(name, value);
                        }
                    }
                }
            } catch (Throwable e) {
                logger.error(e);
                throw new LoyException("system_error");
            }
        }
    }

    public Map<String, Object> getValues() {
        return this.values;
    }

    public Object getParam() {
        return param;
    }

}