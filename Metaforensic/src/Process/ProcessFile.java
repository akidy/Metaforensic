/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import java.io.File;

/**
 *
 * @author andy737-1
 */
public interface ProcessFile {

    public void CollectorAlgorithm(String ext, File Archivo);

    public String CreateChecksum(File archivo);

    public void VerifyChecksum(File archivo);
}
