package org.cuiyang.mybatis.generator.plugin;

import freemarker.template.TemplateException;
import org.apache.commons.lang3.math.NumberUtils;
import org.cuiyang.mybatis.generator.EnumCodeGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 枚举自动生成插件
 * <p> eg. 喜欢(cancel 0 取消, like 1 喜欢, dislike 2 不喜欢)
 *
 * @author cuiyang
 */
public class EnumGeneratorPlugin extends BasePluginAdapter {

    private EnumCodeGenerator generator;
    private Pattern pattern = Pattern.compile("(.+)\\((.+)\\)");

    @Override
    public void init() {
        generator = new EnumCodeGenerator(properties.getProperty("targetProject", "src/main/java"),
            properties.getProperty("targetPackage", ""));
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
            IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        String name = introspectedTable.getFullyQualifiedTable() + "." + introspectedColumn.getActualColumnName();
        String enumName = properties.getProperty(name);
        if (enumName == null) {
            // 该列不需要创建枚举类型
            return true;
        }

        String remarks = introspectedColumn.getRemarks();
        Matcher matcher = pattern.matcher(remarks);
        if (!matcher.find()) {
            // 列注释不符合枚举规则
            return true;
        }

        String classRemarks = matcher.group(1).trim();
        String group = matcher.group(2).trim();
        String[] split = group.split(",");

        List<EnumCodeGenerator.EnumItem> items = new ArrayList<>();
        for (String str : split) {
            String[] split2 = str.trim().split(" ");
            if (split2.length != 3) {
                // 列注释不符合枚举规则
                return true;
            }
            EnumCodeGenerator.EnumItem item = new EnumCodeGenerator.EnumItem();
            item.setName(split2[0].toUpperCase());
            item.setCode(split2[1]);
            item.setRemark(split2[2]);
            items.add(item);
        }

        if (!items.isEmpty()) {
            EnumCodeGenerator.EnumDataModel dataModel = new EnumCodeGenerator.EnumDataModel();
            dataModel.setItems(items);
            dataModel.setClassName(enumName);
            dataModel.setAuthor(author());
            dataModel.setRemark(classRemarks);
            dataModel.setCodeType(NumberUtils.isNumber(items.get(0).getCode()) ? "int" : "String");
            try {
                // 生成枚举代码
                generator.generate(dataModel);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
