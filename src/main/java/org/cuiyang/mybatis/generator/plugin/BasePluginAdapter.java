package org.cuiyang.mybatis.generator.plugin;

import org.mybatis.generator.api.PluginAdapter;

import java.util.List;
import java.util.Properties;

/**
 * 插件基类
 *
 * @author cuiyang
 */
public abstract class BasePluginAdapter extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    public String author() {
        return context.getProperties().getProperty("author", "cuiyang");
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        init();
    }

    /**
     * 初始化
     */
    public abstract void init();
}
