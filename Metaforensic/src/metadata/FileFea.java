/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import javax.swing.JFrame;

/**
 *
 * @author andy737-1
 */
public class FileFea {

    private String nombre;
    private String path;
    private JFrame f;
    private static FileFea instance = new FileFea();

    /**
     * Inicia variables
     */
    public FileFea() {
        nombre = "";
        path = "";
        f = null;
    }

    public static FileFea getInstance() {
        return instance;
    }

    /**
     *
     * @param nombre del archivo sometido a recolección
     */
    public void setNameFile(String nombre) {
        this.nombre = nombre;
    }

    public String getNameFile() {
        return nombre;
    }

    /**
     *
     * @param path ruta del archivo sometido a recolección
     */
    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    /**
     *
     * @param f frame padre
     */
    public void setFrame(JFrame f) {
        this.f = f;
    }

    public JFrame getFrame() {
        return f;
    }
}
