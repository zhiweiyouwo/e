<?xml version="1.0" encoding="utf-8"?>  
<!DOCTYPE dynamic-ql-statement PUBLIC "-//Loy/HOP Ql Dynamic Statement DTD 1.0//EN"  
"http://www.loy.com/dtd/dynamic-ql-statement-1.0.dtd">  
<dynamic-ql-statement>  
    <hql-query name="${modelName}.${entityName?replace("Entity","")?uncap_first}.findPage${entityName?replace("Entity","")}">  
    <![CDATA[ 
        from ${entityName} x where 1=1 
        <#list conditionColumns as condition>
        <#if condition.count ==1>
        <#if condition.type =='select'>
         ${left}@notEmpty name="${condition.combineFieldName}Id">
          and x.${condition.fieldName}.id = :${condition.combineFieldName}Id 
        ${left}/@notEmpty>
        <#else>
         ${left}@notEmpty name="${condition.combineFieldName}">
          and x.${condition.fieldName} = :${condition.combineFieldName} 
         ${left}/@notEmpty>
        </#if>
       
        <#else>
        ${left}@notEmpty name="${condition.combineFieldName}Start">
          and x.${condition.fieldName} >= :${condition.combineFieldName}Start 
        ${left}/@notEmpty>
        ${left}@notEmpty name="${condition.combineFieldName}End">
          and x.${condition.fieldName} <= :${condition.combineFieldName}End 
        ${left}/@notEmpty>
        </#if>
		</#list> 
		<#if sortable>${left}@notEmpty name="orderProperty"> order by x.${orderProperty} ${direction} ${left}/@notEmpty></#if>
    ]]>   
    </hql-query>  
</dynamic-ql-statement> 