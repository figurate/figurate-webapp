package org.mnode.figurate.base

import groovy.util.logging.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener

import org.apache.camel.CamelContext
import org.apache.camel.impl.DefaultCamelContext
import org.osgi.framework.launch.Framework

@Slf4j
class CamelLoaderListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent e) {
        CamelContext camelContext = new DefaultCamelContext()
        
        e.servletContext.setAttribute('camel', camelContext)
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent e) {
        CamelContext camel = e.servletContext.getAttribute('camel')
        log.info "Shutting down camel"
        try {
            camel.stop()
        }
        catch (Exception ex) {
            ex.printStackTrace()
        }
    }
}