package org.mnode.figurate.base

import groovy.servlet.ServletBinding;
import groovy.servlet.TemplateServlet
import groovy.util.logging.Slf4j;


@Slf4j
class FigurateTemplateServlet extends TemplateServlet {

    def extensionsLastModified = [:]
    
    ConfigObject extensions = []
    
    @Override
    protected void setVariables(ServletBinding binding) {
        super.setVariables(binding);
        
        final defaultScriptContentLoader = { String path ->
            def file = new File(path)
            if(file.exists()){
                return file.text
            }
            InputStream is = Thread.currentThread().contextClassLoader.getResourceAsStream(path)
            if(!is) return ""
            is.text
        }
        
        // extension dir..
        File extensionDir = [binding.context.getResource('/WEB-INF/extensions').toURI()]
        extensionDir.listFiles().each { extension ->
            if (!extensionsLastModified[extension] || extension.lastModified() > extensionsLastModified[extension]) {
//                String extensionScript = binding.context.getResource('/WEB-INF/extensions/figurate.groovy').text
                String extensionScript = extension.text
                log.info "Extension Script: $extensionScript"
                ConfigSlurper extensionLoader = []
                extensions.merge(extensionLoader.parse(extensionScript))
                extensionsLastModified[extension] = extension.lastModified()
            }
        }
        extensions.entrySet().each {
            log.info "Binding variable: $it.key, $it.value"
            binding.setVariable(it.key, it.value)
        }
    }
}
