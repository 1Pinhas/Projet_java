package com.ism.core.Repo.Impl;

import com.ism.core.Database.Database;
import com.ism.core.Repo.YamlRepo;

public class YamlRepoImpl implements YamlRepo {


@Override
    public Object getInstance(String repoType, String repoType2) {
        try {
            String className = Database.getActiveDatabase(repoType, repoType2);
            if (className != null) {
                Class<?> clazz = Class.forName(className);
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
