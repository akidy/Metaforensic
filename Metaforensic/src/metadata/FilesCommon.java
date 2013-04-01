/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

/**
 *
 * @author andy737-1
 */
public abstract class FilesCommon {

    public abstract Boolean WriteFile();

    public abstract Boolean CloseFile();

    public Boolean CreateFile() {
        return null;
    }

    public Boolean OpenFile() {
        return null;
    }
}
