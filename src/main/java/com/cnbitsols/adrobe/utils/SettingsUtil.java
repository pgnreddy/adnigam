/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.utils;

import com.cnbitsols.adrobe.dao.AdrobeHibernateDAO;
import com.cnbitsols.adrobe.entities.Configuration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author santosh.r
 */
@Component
public class SettingsUtil implements ApplicationContextAware {

    private static ApplicationContext ac;
    public static List list;
    public static Map configMap;

    @Override
    public void setApplicationContext(ApplicationContext ac) {
        this.ac = ac;
        configList();
    }

    public static void configList() {
        list = ((AdrobeHibernateDAO) ac.getBean("adrobeHibernateDAOImpl")).getConfiguration();
        configMap=new HashMap();
        System.out.println("list:" + list);
        for (int i = 0; i < list.size(); i++) {
            Configuration c = (Configuration) list.get(i);
            configMap.put(c.getName(), c.getValue());
        }
    }

    public static String getSettings(String key) {
        return (String) configMap.get(key);
    }
    
    public static Map getConfigMap(){
        return configMap;
    }
}
