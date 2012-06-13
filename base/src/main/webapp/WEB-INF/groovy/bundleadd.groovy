import org.apache.commons.fileupload.disk.DiskFileItemFactory
import org.apache.commons.fileupload.servlet.ServletFileUpload

if (request.method == 'POST') {
    if (ServletFileUpload.isMultipartContent(request)) {
        // Create a new file upload handler
        ServletFileUpload upload = [new DiskFileItemFactory()]
                
//                def bundleLocation
                def items = upload.getItemIterator(request)
                while (items.hasNext()) {
                    def item = items.next()
//                            if (item.formField && item.fieldName == 'location') {
//                                bundleLocation = item.openStream().text
//                            }
//                            else {
                                context.getAttribute('osgi').bundleContext.installBundle(item.name, item.openStream())
//                            }
                }
    }
}

redirect("bundles.html")
