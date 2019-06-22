package org.cuiyang.mybatis.generator.util;

/**
 * 单词工具类
 *
 * @author cuiyang
 */
public class WordUtils {
    /**
     * 大驼峰
     */
    public static String toCamelCase(String name) {
        String[] split = name.split("_");
        StringBuilder className = new StringBuilder();
        for (String str : split) {
            className.append(captureName(str));
        }
        return className.toString();
    }

    /**
     * 首字母大写
     */
    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
