package org.mnode.figurate.base

import groovy.util.logging.Slf4j;

import javax.jcr.NamespaceException;
import javax.jcr.SimpleCredentials
import javax.naming.InitialContext
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.jackrabbit.core.jndi.RegistryHelper

@Slf4j
class RepositoryLoaderListener implements ServletContextListener {

    def context = new InitialContext()
    
    void contextInitialized(ServletContextEvent e) {
        File homeDir = [System.getProperty('figurate.home', System.getProperty('user.home') + '/.figurate')]
        homeDir.mkdirs()
        log.info "figurate home: [$homeDir.absolutePath]"
        def configFile = new File(homeDir, "config.xml")
        configFile.text = RepositoryLoaderListener.getResourceAsStream("/config-persist.xml").text
        
        File repositoryLocation = [homeDir, "data"]
        
        RegistryHelper.registerRepository(context, 'figurate', configFile.absolutePath,
             repositoryLocation.absolutePath, false)
        
        def context = new InitialContext()
        def repository = context.lookup('figurate')
        def session = repository.login(new SimpleCredentials('readonly', ''.toCharArray()))
        e.servletContext.setAttribute('jcr', session)
        
        try {
            session.workspace.namespaceRegistry.registerNamespace('mn', 'http://mnode.org/namespace/1.0')
        }
        catch (NamespaceException ne) {
            log.warn ne.message
        }
    }
    
    void contextDestroyed(ServletContextEvent e) {
//        RegistryHelper.unregisterRepository(context, 'figurate')
    }
}
