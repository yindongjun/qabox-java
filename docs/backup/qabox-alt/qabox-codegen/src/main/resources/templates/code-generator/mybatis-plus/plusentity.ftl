package ${packageName}.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @description ${classInfo.classComment}
 * @author ${authorName}
 * @date ${.now?string('yyyy-MM-dd')}
 */
@Data<#if swagger?exists && swagger==true>
@ApiModel("${classInfo.classComment}")</#if>
@TableName(value="${classInfo.tableName}", autoResultMap=true)
public class ${classInfo.className} implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
    /**
    * ${fieldItem.fieldComment}
    */<#if swagger?exists && swagger==true>
    @ApiModelProperty("${fieldItem.fieldComment}")</#if>
    @TableField("${fieldItem.columnName}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};

</#list>
    public ${classInfo.className}() {
    }
</#if>

}
