/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import java.io.File;

/**
 *
 * @author andy737-1
 */
public class FileName {

    public String extension(File f) {
        int dot = f.toString().lastIndexOf(".");
        return f.toString().substring(dot + 1);
    }

    public String filename(File f) {
        int dot = f.toString().lastIndexOf(".");
        int sep = f.toString().lastIndexOf("\\");
        return f.toString().substring(sep + 1, dot);
    }

    public String path(File f) {
        int sep = f.toString().lastIndexOf("\\");
        return f.toString().substring(0, sep);
    }
}
