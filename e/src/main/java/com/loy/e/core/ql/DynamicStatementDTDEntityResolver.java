package com.loy.e.core.ql;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.internal.util.ConfigHelper;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class DynamicStatementDTDEntityResolver implements EntityResolver{  

    protected final Log LOGGER  = LogFactory.getLog(DynamicStatementDTDEntityResolver.class); 
    private static final String HOP_DYNAMIC_STATEMENT = "http://www.loy.com/dtd/";  
  
    public InputSource resolveEntity(String publicId, String systemId) {  
        InputSource source = null; // returning null triggers default behavior  
        if ( systemId != null ) {  
            if ( systemId.startsWith( HOP_DYNAMIC_STATEMENT ) ) {  
                source = resolveOnClassPath( publicId, systemId, HOP_DYNAMIC_STATEMENT );  
            }  
        }  
        return source;  
    }  
  
    private InputSource resolveOnClassPath(String publicId, String systemId, String namespace) {  
        InputSource source = null;  
        String path = systemId.substring( namespace.length() );  
        InputStream dtdStream = resolveInHibernateNamespace( path );  
        if ( dtdStream == null ) {  
            LOGGER.debug( "unable to locate [" + systemId + "] on classpath" );  
            if ( systemId.substring( namespace.length() ).indexOf( "2.0" ) > -1 ) {  
                LOGGER.error( "Don't use old DTDs, read the Hibernate 3.x Migration Guide!" );  
            }  
        }  
        else {   
            source = new InputSource( dtdStream );  
            source.setPublicId( publicId );  
            source.setSystemId( systemId );  
        }  
        return source;  
    }  
  
    protected InputStream resolveInHibernateNamespace(String path) {  
        return this.getClass().getClassLoader().getResourceAsStream( path );  
    }  
  
    protected InputStream resolveInLocalNamespace(String path) {  
        try {  
            return ConfigHelper.getUserResourceAsStream( path );  
        }  
        catch ( Throwable t ) { 
        	LOGGER.error(t);
            return null;  
        }  
    }  
}  