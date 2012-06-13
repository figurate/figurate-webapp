import org.osgi.framework.Bundle

bundleState = { state ->
    switch (state) {
        case Bundle.ACTIVE: return 'Active'
        case Bundle.INSTALLED: return 'Installed'
        case Bundle.RESOLVED: return 'Resolved'
        case Bundle.STARTING: return 'Starting'
        case Bundle.STOPPING: return 'Stopping'
        case Bundle.UNINSTALLED: return 'Uninstalled'
        default: return 'Unknown'
    }
}
