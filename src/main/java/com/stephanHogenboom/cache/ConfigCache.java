package com.stephanHogenboom.cache;

import com.stephanHogenboom.config.ConfigDAO;

import java.util.HashMap;

public class ConfigCache {
    ConfigDAO dao = new ConfigDAO();
    private static HashMap<String, String> cache = null;

    public  HashMap<String, String> getConfigCache() {
        if (cache == null) {
            cache = dao.getConfigCache();
        }
        return cache;
    }

    public void resetRegistryCache() {
        cache = dao.getConfigCache();
    }
}
