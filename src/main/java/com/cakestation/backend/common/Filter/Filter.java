package com.cakestation.backend.common.Filter;

import javax.servlet.*;
import java.io.IOException;

public interface Filter extends javax.servlet.Filter {

    public default void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException;

    public default void destroy() {}

}
