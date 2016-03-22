package ${serviceImplPackageName};

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.query.MapQueryParam;
import ${domainPackageName}.${entityName?replace("Entity","")}QueryParam;
import ${domainPackageName}.entity.${entityName};
import ${repositoryPackageName}.${entityName?replace("Entity","")}Repository;
/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class ${entityName?replace("Entity","")}ServiceImpl {

	@Autowired
	${entityName?replace("Entity","")}Repository ${entityName?replace("Entity","")?uncap_first}Repository;
	
	@RequestMapping(value="/page")
	@ControllerLogExeTime(description="分页查询${name}",log = false)
	public Page${left}${entityName}>  queryPage(${entityName?replace("Entity","")}QueryParam ${entityName?replace("Entity","")?uncap_first}QueryParam,Pageable pageable){
	
		Page${left}${entityName}> page = ${entityName?replace("Entity","")?uncap_first}Repository.findPage("${entityName?replace("Entity","")?uncap_first}.findPage${entityName?replace("Entity","")}", new MapQueryParam(${entityName?replace("Entity","")?uncap_first}QueryParam), pageable);
		return page;
	}
	
	@ControllerLogExeTime(description="获取${name}",log = false)
	@RequestMapping(value="/get")
	public ${entityName}  get(String id){
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
	    ${entityName?replace("Entity","")?uncap_first}Repository.save(${entityName?uncap_first});
        return ${entityName?uncap_first};
	}
	
	@RequestMapping(value="/update")
	@ControllerLogExeTime(description="修改${name}")
	public void  update(${entityName} ${entityName?uncap_first}){
        ${entityName?replace("Entity","")?uncap_first}Repository.save(${entityName?uncap_first});
	}
}
