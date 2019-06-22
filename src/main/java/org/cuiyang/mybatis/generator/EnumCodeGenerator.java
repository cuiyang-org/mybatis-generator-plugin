package org.cuiyang.mybatis.generator;

import freemarker.template.TemplateException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.List;

/**
 * 枚举代码生成器
 *
 * @author cuiyang
 */
public class EnumCodeGenerator extends JavaCodeGenerator {

    public EnumCodeGenerator(String targetProject, String targetPackage) {
        super(targetProject, targetPackage);
    }

    public void generate(EnumDataModel dataModel) throws IOException, TemplateException {
        super.generate(dataModel, "Enum.ftl");
    }

    /**
     * 枚举类数据模型
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class EnumDataModel extends JavaCodeGenerator.ClassDataModel {
        /** code类型 */
        private String codeType;
        /** 枚举 */
        private List<EnumItem> items;
    }

    /**
     * 枚举
     */
    @Data
    public static class EnumItem {
        /** 名称 */
        private String name;
        /** code */
        private String code;
        /** 注释 */
        private String remark;
    }
}
