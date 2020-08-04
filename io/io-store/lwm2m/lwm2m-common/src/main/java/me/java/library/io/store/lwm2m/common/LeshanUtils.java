package me.java.library.io.store.lwm2m.common;

import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author : sylar
 * @fullName : me.java.library.io.store.lwm2m.common.LeshanUtils
 * @createDate : 2020/8/4
 * @description :
 * @copyRight : COPYRIGHT(c) me.iot.com All Rights Reserved
 * *******************************************************************************************
 */
public class LeshanUtils {

    public static List<ObjectModel> getModelsFromResources() {
        String[] modelFiles = getModels();
        return ObjectLoader.loadDdfResources("/models", modelFiles);
    }

    public static String[] getModels() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("models");
        if (url != null) {
            File dir = new File(url.getFile());
            File[] files = dir.listFiles(pathname -> pathname.getName().toLowerCase().endsWith(".xml"));
            if (files != null) {
                return Stream.of(files).map(File::getName).toArray(String[]::new);
            }
        }
        return new String[0];
    }
}
