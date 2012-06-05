import groovy.util.logging.Slf4j;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

@Slf4j
class Activator implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
        // TODO Auto-generated method stub
        log.info 'RSS bundle started'
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        // TODO Auto-generated method stub
        log.info 'RSS bundle stopped'
    }
}
