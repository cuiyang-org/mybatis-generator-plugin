package org.cuiyang.mybatis.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * 代码生成器
 *
 * @author cuiyang
 */
public class CodeGenerator {
    protected Configuration configuration;
    protected String targetProject;
    protected String targetPackage;

    public CodeGenerator(String targetProject, String targetPackage) {
        this.targetProject = targetProject;
        this.targetPackage = targetPackage;
        this.init();
    }

    /**
     * 初始化
     */
    protected void init() {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setClassLoaderForTemplateLoading(CodeGenerator.class.getClassLoader(), "ftl");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    /**
     * 生成代码
     * @param dataModel 数据模型
     * @param template 模板
     * @param targetFile 目标文件
     * @throws IOException 模板不存在
     * @throws TemplateException 模板异常
     */
    public void generate(Object dataModel, String template, String targetFile) throws IOException, TemplateException {
        Template temp = configuration.getTemplate(template);
        File outFile = new File(new File(targetProject, targetPackage.replace(".", "/")), targetFile);
        FileUtils.forceMkdir(outFile.getParentFile());
        try (Writer out = new FileWriter(outFile)) {
            temp.process(dataModel, out);
        }
    }

    /**
     * 输出的工程
     * @return 项目路径
     */
    public String getTargetProject() {
        return targetProject;
    }

    /**
     * 输出的包名
     * @return 包名
     */
    public String getTargetPackage() {
        return targetPackage;
    }
}
