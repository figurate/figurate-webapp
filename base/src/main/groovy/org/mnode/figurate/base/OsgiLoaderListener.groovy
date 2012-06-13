package org.mnode.figurate.base

import groovy.util.logging.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener

import org.osgi.framework.Bundle
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework
import org.osgi.framework.launch.FrameworkFactory

@Slf4j
class OsgiLoaderListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent e) {
        FrameworkFactory osgiFactory = ServiceLoader.load(FrameworkFactory).find()
        def configMap = ['org.osgi.framework.storage': "${System.getProperty('user.home')}/.figurate/bundles" as String,
            'felix.systembundle.activators': [new BundleActivator() {
            void start(BundleContext context) throws Exception {
                log.info "System bundle started"
                
                def loadBundle = { id, file ->
                    new File(file).withInputStream {
                        Bundle b = context.installBundle(id, it)
                        b.start()
                    }
                }
                
                loadBundle('felix.obr', 'C:/m2/repository/org/apache/felix/org.osgi.service.obr/1.0.2/org.osgi.service.obr-1.0.2.jar')
            }
            void stop(BundleContext context) throws Exception {
                log.info "System bundle stopped"
            }
        }]]
        Framework osgi = osgiFactory.newFramework(configMap)
        osgi.init()
        osgi.start()
        
        e.servletContext.setAttribute('osgi', osgi)
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent e) {
        Framework osgi = e.servletContext.getAttribute('osgi')
        log.info "Shutting down"
        try {
            osgi.stop()
            osgi.waitForStop(0)
        }
        catch (Exception ex) {
            ex.printStackTrace()
        }
    }
}