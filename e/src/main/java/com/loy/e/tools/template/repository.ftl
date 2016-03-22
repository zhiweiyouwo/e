package ${repositoryPackageName};

import com.loy.e.core.repository.GenericRepository;
import ${domainPackageName}.entity.${entityName};


/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public interface ${entityName?replace("Entity","")}Repository extends GenericRepository<${entityName},String>{

}