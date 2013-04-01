/*
 * *****************************************************************************
 *    
 * Metaforensic version 1.0 - Análisis forense de metadatos en archivos
 * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez,
 * TSU. Idania Aquino Cruz, All Rights Reserved, https://github.com/andy737   
 * 
 * This file is part of Metaforensic.
 *
 * Metaforensic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Metaforensic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Metaforensic.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * E-mail: andy1818ster@gmail.com
 * 
 * *****************************************************************************
 */
package Process;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

/**
 * Clase collector para manejar los datos (get,set) ante otras clases que los re
 * quieran
 *
 * @author andy737-1
 * @since 1.0
 */
public class Collector {

    private String DirectorioRecoleccion;
    private String DirectorioSalida;
    private String TipoHash;
    private JFrame j;
    private Boolean Recursivo;
    private static Collector instance = new Collector();

    private Collector() {
    }

    /**
     * Singleton
     *
     * @return una instancia del Collector
     */
    public static Collector getInstance() {
        return instance;
    }

    /**
     * Set directorio de recolección
     *
     * @param DirectorioRecoleccion directorio raíz para recolección
     */
    public void setDirectorioRecoleccion(String DirectorioRecoleccion) {
        this.DirectorioRecoleccion = DirectorioRecoleccion;
    }

    /**
     * Set directorio de salida
     *
     * @param DirectorioSalida directorio de salida para almacenar log y archivo
     * con metadatos extraídos
     */
    public void setDirectorioSalida(String DirectorioSalida) {
        this.DirectorioSalida = DirectorioSalida;
    }

    /**
     * Set tipo de hash
     *
     * @param TipoHash para firmar cada archivo leído por la aplicación
     */
    public void setTipoHash(String TipoHash) {
        this.TipoHash = TipoHash;
    }

    /**
     * Set tipo de archivo (extención), recibe el frame actual para leer los
     * controles activados y definir los tipos de archivo seleccionados
     *
     * @param j frame padre
     */
    public void setTipoArchivo(JFrame j) {
        this.j = j;
    }

    /**
     * Get directorio de recolección
     *
     * @return el directorio raíz para iniciar recolección
     */
    public String getDirectorioRecoleccion() {
        return DirectorioRecoleccion;
    }

    /**
     * Get directorio de salida
     *
     * @return el directorio para almacenar el log y archivo .afa
     */
    public String getDirectorioSalida() {
        return DirectorioSalida;
    }

    /**
     * Get tipo de hash
     *
     * @return el nombre del algoritmo para checksum
     */
    public String getTipoHash() {
        return TipoHash;
    }

    /**
     * Set si la recolección sera recursiva
     *
     * @param Recursivo determina si se realizara recursión en la recolección
     */
    public void setRecursivo(Boolean Recursivo) {
        this.Recursivo = Recursivo;
    }

    /**
     * Get recursivo (true=si, false=no)
     *
     * @return si hay recursividad en la recolección
     */
    public Boolean getRecursivo() {
        return Recursivo;
    }

    /**
     * Get los tipos de archivo a someter a recolección
     *
     * @return un arreglo con los tipos de archivo seleccionados por el usuario
     */
    public ArrayList getTipoArchivo() {

        Component[] arr;
        ArrayList<String> chk = new ArrayList<>();
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
