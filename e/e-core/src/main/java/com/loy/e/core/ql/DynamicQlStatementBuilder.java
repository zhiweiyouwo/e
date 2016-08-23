package com.loy.e.core.ql;

import java.io.IOException;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public interface DynamicQlStatementBuilder {
    public StatementTemplate get(String key);
    public void init() throws IOException;  
}