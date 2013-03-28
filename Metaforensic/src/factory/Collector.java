/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

/**
 *
 * @author andy737-1
 */
public class Collector {

    private String DirectorioRecoleccion;
    private String DirectorioSalida;
    private String TipoHash;
    private JFrame j;

    public void setDirectorioRecoleccion(String DirectorioRecoleccion) {
        this.DirectorioRecoleccion = DirectorioRecoleccion;
    }

    public void setDirectorioSalida(String DirectorioSalida) {
        this.DirectorioSalida = DirectorioSalida;
    }

    public void setTipoHash(String TipoHash) {
        this.TipoHash = TipoHash;
    }

    public void setTipoArchivo(JFrame j) {
        this.j = j;
    }

    public String getDirectorioRecoleccion() {
        return DirectorioRecoleccion;
    }

    public String getDirectorioSalida() {
        return DirectorioSalida;
    }

    public String getTipoHash() {
        return TipoHash;
    }

    public ArrayList getTipoArchivo() {

        Component[] arr;
        ArrayList chk = new ArrayList<>();
        if (j.getContentPane().getComponents() != null) {
            arr = j.getContentPane().getComponents();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] instanceof JCheckBox) {
                    if ((((JCheckBox) arr[i]).isSelected())) {
                        chk.add(((JCheckBox) arr[i]).getText());
                    }
                }
            }
            
                return chk;

        }
        return null;
    }
}
