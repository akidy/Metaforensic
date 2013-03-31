/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

/**
 *
 * @author andy737-1
 */
public class FileMeta extends FileFea {

    private String archivo;
    private Boolean escribir;

    /**
     * Inicia variables
     */
    public FileMeta() {
        archivo = "";
        escribir = true;
    }

    /**
     *
     * @param nombre del archivo sometido a recolección
     */
    @Override
    public void setNameFile(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public String getNameFile() {
        return archivo;
    }

    public void setEscribir(Boolean escribir) {
        this.escribir = escribir;
    }

    public Boolean getEscribir() {
        return escribir;
    }
}
