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
    ${item.name}(${item.code})<#if item_has_next>,<#else>;</#if>
    </#list>

    /** code */
    private ${codeType} code;

    ${className}(${codeType} code) {
        this.code = code;
    }

    public ${codeType} getCode() {
        return this.code;
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
