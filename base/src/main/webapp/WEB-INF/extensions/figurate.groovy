import java.net.InetAddress

import org.apache.commons.io.FileUtils;

figurate = [
    version: '1.0',
    copyrightYear: '2012',
    hostInfo: { InetAddress.localHost.hostAddress },
    memory: [total: {FileUtils.byteCountToDisplaySize(Runtime.runtime.totalMemory())}, free: {FileUtils.byteCountToDisplaySize(Runtime.runtime.freeMemory())}]
]
