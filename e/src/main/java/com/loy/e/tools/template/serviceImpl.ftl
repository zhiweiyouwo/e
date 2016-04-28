package ${serviceImplPackageName};
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.MapQueryParam;
import com.loy.e.core.util.TableToExcelUtil;
import ${domainPackageName}.${entityName?replace("Entity","")}QueryParam;
import ${domainPackageName}.entity.${entityName};
<#list  importClassNames as importClassName>
${importClassName};
</#list>
import ${repositoryPackageName}.${entityName?replace("Entity","")}Repository;
<#if sortable>
import com.loy.e.core.query.Direction;
import org.apache.commons.lang.ArrayUtils;
</#if>
/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@RestController
@RequestMapping(value = "/${entityName?replace("Entity","")?uncap_first}",method={RequestMethod.POST,RequestMethod.GET})
@Transactional
public class ${entityName?replace("Entity","")}ServiceImpl {

	@Autowired
	${entityName?replace("Entity","")}Repository ${entityName?replace("Entity","")?uncap_first}Repository;
	<#assign hasSelect = false>
	<#list editColumns as col> 
	    <#if col.inputName?contains(".")?string == 'true'>
		    <#if col.type == 'select'>
		    <#assign hasSelect = true>
	        </#if>
        </#if>
    </#list> 
    <#if hasSelect>
    @Autowired
    DictionaryRepository  dictionaryRepository;
    </#if>
    
	@RequestMapping(value="/page")
	@ControllerLogExeTime(description="分页查询${name}",log = false)
	public Page${left}${entityName}>  queryPage(${entityName?replace("Entity","")}QueryParam ${entityName?replace("Entity","")?uncap_first}QueryParam,Pageable pageable){
       <#list conditionColumns as condition>
       <#if condition.count==2 && condition.type =='date'>
		if(${entityName?replace("Entity","")?uncap_first}QueryParam != null){
		    <#if sortable>
	        processSort(${entityName?replace("Entity","")?uncap_first}QueryParam);
	        </#if>
			Date ${condition.combineFieldName}End = ${entityName?replace("Entity","")?uncap_first}QueryParam.get${condition.combineFieldName?cap_first}End();
			if(${condition.combineFieldName}End != null){
				${condition.combineFieldName}End = DateUtil.addOneDay(dateEnd);
				${entityName?replace("Entity","")?uncap_first}QueryParam.set${condition.combineFieldName?cap_first}End(${condition.combineFieldName}End);
			}
		}
		</#if>
		</#list>
		Page${left}${entityName}> page = ${entityName?replace("Entity","")?uncap_first}Repository.findPage("${modelName}.${entityName?replace("Entity","")?uncap_first}.findPage${entityName?replace("Entity","")}", new MapQueryParam(${entityName?replace("Entity","")?uncap_first}QueryParam), pageable);
		return page;
	}
	
	@ControllerLogExeTime(description="获取${name}",log = false)
	@RequestMapping(value="/get")
	public ${entityName}  get(String id){
		${entityName} ${entityName?uncap_first} = ${entityName?replace("Entity","")?uncap_first}Repository.get(id);
		return ${entityName?uncap_first};
	}
	
	@ControllerLogExeTime(description="查看${name}",log = false)
	@RequestMapping(value="/detail")
	public ${entityName}  detail(String id){
		${entityName} ${entityName?uncap_first} = ${entityName?replace("Entity","")?uncap_first}Repository.get(id);
		return ${entityName?uncap_first};
	}
	
	@ControllerLogExeTime(description="删除${name}")
	@RequestMapping(value="/del")
	public void  del(String id){
		if(StringUtils.isNotEmpty(id)){
			String[] idsArr = id.split(",");
			if(idsArr != null){
				for(String idd : idsArr){
					${entityName} ${entityName?uncap_first} = ${entityName?replace("Entity","")?uncap_first}Repository.get(idd);
					if(${entityName?uncap_first} != null){
						${entityName?replace("Entity","")?uncap_first}Repository.delete(${entityName?uncap_first});
					}
				}
			}
		}
	}
	
	@RequestMapping(value="/save")
	@ControllerLogExeTime(description="保存${name}")
	public ${entityName}  save(${entityName} ${entityName?uncap_first}){
	    <#list editColumns as col> 
	    <#if col.inputName?contains(".")?string == 'true'>
	    <#if col.type == 'select'>
	    DictionaryEntity ${col.fieldName} = ${entityName?uncap_first}.get${col.fieldName?replace("Entity","")?cap_first}();
		<#else>
		${col.fieldName?cap_first}Entity ${col.fieldName} = ${entityName?uncap_first}.get${col.fieldName?replace("Entity","")?cap_first}();
		</#if>
		if(${col.fieldName} != null){
		    String ${col.fieldName}Id = ${col.fieldName}.getId();
			if(StringUtils.isEmpty(${col.fieldName}Id)){
				${col.fieldName} = null;
			}
			<#if col.type == 'select'>
			else{
		      ${col.fieldName} = dictionaryRepository.get(${col.fieldName}Id);
			}
			</#if>
		}
		${entityName?uncap_first}.set${col.fieldName?cap_first}(${col.fieldName}); 
	    </#if>
	    </#list>
	    ${entityName?replace("Entity","")?uncap_first}Repository.save(${entityName?uncap_first});
        return ${entityName?uncap_first};
	}
	
	@RequestMapping(value="/update")
	@ControllerLogExeTime(description="修改${name}")
	public void  update(${entityName} ${entityName?uncap_first}){
	<#list editColumns as col> 
	    <#if col.inputName?contains(".")?string == 'true'>
	    <#if col.type == 'select'>
		      DictionaryEntity ${col.fieldName} = ${entityName?uncap_first}.get${col.fieldName?replace("Entity","")?cap_first}();
		<#else>
		    ${col.fieldName?cap_first}Entity ${col.fieldName} = ${entityName?uncap_first}.get${col.fieldName?replace("Entity","")?cap_first}();
		</#if>
		if(${col.fieldName} != null){
		    String ${col.fieldName}Id = ${col.fieldName}.getId();
			if(StringUtils.isEmpty(${col.fieldName}Id)){
				${col.fieldName} = null;
			}
			<#if col.type == 'select'>
			else{
		      ${col.fieldName} = dictionaryRepository.get(${col.fieldName}Id);
			}
			</#if>
		}
		${entityName?uncap_first}.set${col.fieldName?cap_first}(${col.fieldName}); 
	    </#if>
	    </#list>
	    ${entityName?replace("Entity","")?uncap_first}Repository.save(${entityName?uncap_first});
	}
	
	@RequestMapping(value="/excel",method={RequestMethod.POST})
	@ControllerLogExeTime(description="导出${name}",log=false)
    public void  excel(String html ,HttpServletResponse response) throws IOException{
		response.setContentType("application/msexcel;charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=${entityName?replace("Entity","")?uncap_first}s.xls");
		OutputStream out = response.getOutputStream();
		TableToExcelUtil.createExcelFormTable("${entityName?replace("Entity","")?uncap_first}", html, 1, out);
		out.flush();
		out.close();
	}
	
	
	<#if sortable>
	private void processSort(${entityName?replace("Entity","")}QueryParam ${entityName?replace("Entity","")?uncap_first}QueryParam){
		    String orderProperity = ${entityName?replace("Entity","")?uncap_first}QueryParam.getOrderProperty();
			if(StringUtils.isNotEmpty(orderProperity)){
				String[] orderProperties = {<#list orderFields as orderField>"${orderField}"<#if orderField_has_next> ,</#if></#list>};
				if(ArrayUtils.contains(orderProperties, orderProperity)){
					String direction = ${entityName?replace("Entity","")?uncap_first}QueryParam.getDirection();
					if(!Direction.ASC.getInfo().equalsIgnoreCase(direction) && 
							!Direction.DESC.getInfo().equalsIgnoreCase(direction)){
						${entityName?replace("Entity","")?uncap_first}QueryParam.setDirection(Direction.DESC.getInfo());
					}
				}
			}else{
				${entityName?replace("Entity","")?uncap_first}QueryParam.setOrderProperty("");
				${entityName?replace("Entity","")?uncap_first}QueryParam.setDirection("");
			}
	}
	</#if>	
}
