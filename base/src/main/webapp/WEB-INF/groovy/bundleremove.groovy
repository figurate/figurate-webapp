
def bundleId = request.getParameter('id')
if (bundleId) {
    context.getAttribute('osgi').bundleContext.getBundle(bundleId as long)?.uninstall()
}
redirect("bundles.html")
