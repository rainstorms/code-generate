package ${entityPackage};


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "${table.name}")
public class ${entity}Entity {
<#-- 循环属性名称 -->
<#list table.fields as field>
    <#if field.comment??>
        @ApiModelProperty(value = "<#if field.comment!="">${field.comment}<#else >主键</#if>")
    </#if>
    private <#if field.propertyType="Date"> Integer<#else >${field.propertyType}</#if> ${field.propertyName};

</#list>

@TableField(fill = FieldFill.INSERT)
public Long createTime;

@JsonIgnore
@TableField(fill = FieldFill.INSERT_UPDATE)
public Long updateTime;

@JsonIgnore
@TableLogic
public Long deleteTime;
}
