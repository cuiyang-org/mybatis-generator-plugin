package ${packageName};

import java.util.Objects;

/**
 * ${remark}枚举
 *
 * @author ${author}
 */
public enum ${className} {
    <#list items as item>
    /** ${item.remark} */
    ${item.name}(${item.code}, "${item.remark}")<#if item_has_next>,<#else>;</#if>
    </#list>

    /** code */
    private ${codeType} code;
    /** text */
    private String text;

    ${className}(${codeType} code, String text) {
        this.code = code;
        this.text = text;
    }

    public ${codeType} getCode() {
        return this.code;
    }

    public String getText() {
        return this.text;
    }

    public static ${className} codeOf(${codeType} code) {
        for (${className} e : ${className}.values()) {
            if (Objects.equals(e.getCode(), code)) {
                return e;
            }
        }
        return null;
    }
}
