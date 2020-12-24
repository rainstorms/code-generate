package ${entityPackage};

<#list table.fields as field>
<#if field.propertyType?index_of("BigDecimal")!=-1>
<#assign importBigDecimal=true/>
</#if>
<#if field.propertyType?index_of("Date")!=-1>
<#assign importDate=true/>
</#if>
</#list>
<#if importBigDecimal?exists>
import java.math.BigDecimal;
</#if>
<#if importDate?exists>
import java.util.Date;
</#if>
import javax.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ApiModel
@Table(name = "${table.name}")
public class ${entity} {
<#-- 循环属性名称 -->
<#list table.fields as field>
    <#if field.comment??>
        @ApiModelProperty(value = "<#if field.comment!="">${field.comment}<#else >主键</#if>")
    </#if>
    private ${field.propertyType} ${field.propertyName};

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
