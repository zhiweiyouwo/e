package ${domainPackageName};

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.Op;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class ${entityName?replace("Entity","")}QueryParam {

<#list conditionColumns as condition>  
   @ConditionParam(name="${condition.fieldName}")
   private String ${condition.fieldName};
</#list>  

<#list conditionColumns as condition>  
   public String get${condition.fieldName?cap_first}() {
       return ${condition.fieldName};
   }

   public void set${condition.fieldName?cap_first}(String ${condition.fieldName}) {
        this.${condition.fieldName} = ${condition.fieldName};
   }
</#list> 	
	
}