package globus.app

import java.io.File

object AppFileSystem {

  var appFilesDir: String = new File(".").getCanonicalPath
}
