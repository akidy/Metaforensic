/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.io.File;

/**
 *
 * @author andy737-1
 */
public interface FileI {

    public void CollectorAlgorithm();

    public String CreateChecksum(File archivo);

    public void VerifyChecksum();

    public void OpenFile();

    public void CloseFile();

    public void FeaturesFile(File archivo, String ext, String check);
}
