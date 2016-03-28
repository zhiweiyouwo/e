package ${domainPackageName};

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.Op;
<#list importParamClassNames as c> 
${c};
</#list>

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class ${entityName?replace("Entity","")}QueryParam {

<#list conditionColumns as condition>  
 <#if condition.count ==1>
 @ConditionParam(name="${condition.combineFieldName}")
   private ${condition.returnClazz} ${condition.combineFieldName};
 <#else>
 @ConditionParam(name="${condition.combineFieldName}")
   @DateTimeFormat(pattern="yyyy-MM-dd")
   private Date ${condition.combineFieldName}Start;
 
 @ConditionParam(name="${condition.combineFieldName}")
   @DateTimeFormat(pattern="yyyy-MM-dd")
   private Date ${condition.combineFieldName}End;
 </#if>
</#list>  

<#list conditionColumns as condition>  
<#if condition.count ==1>
   public ${condition.returnClazz} get${condition.combineFieldName?cap_first}() {
       return ${condition.combineFieldName};
   }

   public void set${condition.combineFieldName?cap_first}(${condition.returnClazz} ${condition.combineFieldName}) {
        this.${condition.combineFieldName} = ${condition.combineFieldName};
   }
 <#else>
   public Date get${condition.combineFieldName?cap_first}Start() {
       return ${condition.combineFieldName}Start;
   }

   public void set${condition.combineFieldName?cap_first}Start(Date ${condition.combineFieldName}Start) {
        this.${condition.combineFieldName}Start = ${condition.combineFieldName}Start;
   }
   
   public Date get${condition.combineFieldName?cap_first}End() {
       return ${condition.combineFieldName}End;
   }

   public void set${condition.combineFieldName?cap_first}End(Date ${condition.combineFieldName}End) {
        this.${condition.combineFieldName}End = ${condition.combineFieldName}End;
   }
 </#if>
</#list> 	
	
}