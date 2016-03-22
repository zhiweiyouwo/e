<?xml version="1.0" encoding="utf-8"?>  
<!DOCTYPE dynamic-ql-statement PUBLIC "-//Loy/HOP Ql Dynamic Statement DTD 1.0//EN"  
"http://www.loy.com/dtd/dynamic-ql-statement-1.0.dtd">  
<dynamic-ql-statement>  
    <hql-query name="${modelName}.${entityName?replace("Entity","")?cap_first}.findPage${entityName?replace("Entity","")}">  
    <![CDATA[ 
        from ${entityName} x where 1=1 
        <#list conditionColumns as condition> 
        ${left}@notEmpty name="${condition.fieldName}">
          and x.${condition.fieldName} = :${condition.fieldName} 
        ${left}/@notEmpty>
		</#list> 
    ]]>   
    </hql-query>  
</dynamic-ql-statement> 