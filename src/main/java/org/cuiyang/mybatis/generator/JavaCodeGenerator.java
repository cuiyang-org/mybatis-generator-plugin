package org.cuiyang.mybatis.generator;

import freemarker.template.TemplateException;
import lombok.Data;

import java.io.IOException;

/**
 * Java代码生成器
 *
 * @author cuiyang
 */
public class JavaCodeGenerator extends CodeGenerator {

    public JavaCodeGenerator(String targetProject, String targetPackage) {
        super(targetProject, targetPackage);
    }

    public void generate(ClassDataModel dataModel) throws IOException, TemplateException {
        generate(dataModel, "Java.ftl");
    }

    public void generate(ClassDataModel dataModel, String template) throws IOException, TemplateException {
        dataModel.setPackageName(targetPackage);
        super.generate(dataModel, template, dataModel.getClassName() + ".java");
    }

    /**
     * Java类数据模型
     */
    @Data
    public static class ClassDataModel {
        /** 包名 */
        private String packageName;
        /** 类的类型 class enum interface */
        private String classType;
        /** 类名 */
        private String className;
        /** 作者 */
        private String author;
        /** 注释 */
        private String remark;
    }
}
