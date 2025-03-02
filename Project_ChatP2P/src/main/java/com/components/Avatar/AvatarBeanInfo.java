package com.components.avatar;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class AvatarBeanInfo extends SimpleBeanInfo {
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor cornerRadius = new PropertyDescriptor("cornerRadius", Avatar.class);
            cornerRadius.setDisplayName("Corner Radius");
            
            PropertyDescriptor imagePath = new PropertyDescriptor("imagePath", Avatar.class);
            imagePath.setDisplayName("Image Path");
            imagePath.setPropertyEditorClass(javax.swing.JFileChooser.class);
            
            return new PropertyDescriptor[] { cornerRadius, imagePath };
        } catch (IntrospectionException e) {
            return null;
        }
    }
}