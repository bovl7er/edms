package com.cmxv.weblayer.util;


import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {
    
    private static final Logger log = Logger.getLogger(AuthorizationFilter.class);

//--------------------------------------------------------------------------------------------------------------------
    public AuthorizationFilter() {
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
//--------------------------------------------------------------------------------------------------------------------
/**
     * Фильтрация сервлета
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            
            
            String reqURI = reqt.getRequestURI();
           
            if (reqURI.contains("/login.xhtml")
                    || (ses != null && ses.getAttribute("sessionUser") != null)
                    || reqURI.contains("/public/")
                    || reqURI.contains("javax.faces.resource"))
            {
            	chain.doFilter(request, response);
            }
            else {
            	if(isAjax(reqt)) {
                    response.getWriter().print(xmlPartialRedirectToPage(reqt, "/login.xhtml"));
                    response.flushBuffer();
            	} else {
            		resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
            	}        
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace(System.out);

        }
    }
//--------------------------------------------------------------------------------------------------------------------

    private String xmlPartialRedirectToPage(HttpServletRequest request, String page) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        sb.append("<partial-response><redirect url=\"").append(request.getContextPath()).append(request.getServletPath()).append(page).append("\"/></partial-response>");
        return sb.toString();
    }
    
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
    
    @Override
    public void destroy() {

    }
//--------------------------------------------------------------------------------------------------------------------    
}
