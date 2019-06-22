package org.cuiyang.mybatis.generator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.Iterator;
import java.util.List;

/**
 * 业务主键插件
 *
 * @author cuiyang
 */
public class BusinessKeyPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    private String businessKeyRemark() {
        return properties.getProperty("businessKeyRemark", "业务主键");
    }

    private void handleSqlMap(XmlElement element, IntrospectedTable introspectedTable) {
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            boolean bizKey = column.getRemarks().contains(businessKeyRemark());
            if (bizKey) {
                String columnName = column.getActualColumnName();
                String property = column.getJavaProperty();
                String jdbcTypeName = column.getJdbcTypeName();
                List<Element> elements = element.getElements();
                elements.remove(elements.size() - 1);
                elements.add(new TextElement(String.format("where %s = #{%s,jdbcType=%s}", columnName, property, jdbcTypeName)));

                // update语句不替换parameterType
                if (!element.getName().contains("update")) {
                    int index = 0;
                    List<Attribute> attributes = element.getAttributes();
                    Iterator<Attribute> iterator = attributes.iterator();
                    while (iterator.hasNext()) {
                        Attribute attr = iterator.next();
                        if ("parameterType".equals(attr.getName())) {
                            iterator.remove();
                            attributes.add(index, new Attribute("parameterType", column.getFullyQualifiedJavaType().getFullyQualifiedName()));
                            break;
                        }
                        index++;
                    }
                }
                break;
            }
        }
    }

    private void handleClient(Method method, IntrospectedTable introspectedTable) {
        if (method.getName().contains("update")) {
            // update语句不替换参数
            return;
        }
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            boolean bizKey = column.getRemarks().contains(businessKeyRemark());
            if (bizKey) {
                List<Parameter> parameters = method.getParameters();
                parameters.clear();
                Parameter parameter = new Parameter(column.getFullyQualifiedJavaType(), column.getJavaProperty());
                parameters.add(parameter);
                break;
            }
        }
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        handleSqlMap(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        handleSqlMap(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        handleSqlMap(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        handleSqlMap(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        handleSqlMap(element, introspectedTable);
        return true;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        handleClient(method, introspectedTable);
        return true;
    }
}
