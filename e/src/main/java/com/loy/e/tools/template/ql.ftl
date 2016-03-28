<?xml version="1.0" encoding="utf-8"?>  
<!DOCTYPE dynamic-ql-statement PUBLIC "-//Loy/HOP Ql Dynamic Statement DTD 1.0//EN"  
"http://www.loy.com/dtd/dynamic-ql-statement-1.0.dtd">  
<dynamic-ql-statement>  
    <hql-query name="${modelName}.${entityName?replace("Entity","")?cap_first}.findPage${entityName?replace("Entity","")}">  
    <![CDATA[ 
        from ${entityName} x where 1=1 
        <#list conditionColumns as condition>
        <#if condition.count ==1>
        ${left}@notEmpty name="${condition.combineFieldName}">
          and x.${condition.fieldName} = :${condition.combineFieldName} 
        ${left}/@notEmpty>
        <#else>
        ${left}@notEmpty name="${condition.combineFieldName}Start">
          and x.${condition.fieldName} >= :${condition.combineFieldName}Start 
        ${left}/@notEmpty>
        ${left}@notEmpty name="${condition.combineFieldName}End">
          and x.${condition.fieldName} < :${condition.combineFieldName}End 
        ${left}/@notEmpty>
        </#if>
		</#list> 
    ]]>   
    </hql-query>  
</dynamic-ql-statement> 