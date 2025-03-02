package com.components.scrollPanel;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ScrollBarCustomBeanInfo extends SimpleBeanInfo {
    
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor scrollForeground = new PropertyDescriptor("scrollForeground", ScrollBarCustom.class);
            PropertyDescriptor scrollBackground = new PropertyDescriptor("scrollBackground", ScrollBarCustom.class);
            
            scrollForeground.setDisplayName("Scroll Foreground");
            scrollBackground.setDisplayName("Scroll Background");
            
            return new PropertyDescriptor[] { 
                scrollForeground, 
                scrollBackground 
            };
        } catch (IntrospectionException e) {
            return null;
        }
    }
    
    @Override
    public BeanDescriptor getBeanDescriptor() {
        return new BeanDescriptor(ScrollBarCustom.class);
    }
}