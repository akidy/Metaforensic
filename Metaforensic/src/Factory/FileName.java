/**
 * *****************************************************************************
 *
 * Metaforensic version 1.0 - Análisis forense de metadatos en archivos
 * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez,
 * All Rights Reserved, https://github.com/andy737
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * E-mail: andy1818ster@gmail.com
 *
 *******************************************************************************
 */
package Factory;

import java.io.File;

/**
 * Clase encargada de procesar el nombre de un archivo
 *
 * @author andy737-1
 * @version 1.0
 */
public class FileName {

    /**
     * Metodo que procesa un nombre de archivo y devuelve su extención
     *
     * @param f archivo que es requerido para procesar sus extención
     * @return la extención del archivo
     */
    public String extension(File f) {
        int dot = f.toString().lastIndexOf(".");
        return f.toString().substring(dot + 1);
    }

    /**
     * Metodo que procesa un nombre de archivo y devuelve su nombre
     *
     * @param f archivo que es requerido para procesar sus nombre
     * @return el nombre del archivo
     */
    public String filename(File f) {
        int dot = f.toString().lastIndexOf(".");
        int sep = f.toString().lastIndexOf("\\");
        return f.toString().substring(sep + 1, dot);
    }

    /**
     * Metodo que procesa un nombre de archivo y devuelve su path
     *
     * @param f archivo que es requerido para procesar sus path
     * @return el direcorio del archivo
     */
    public String path(File f) {
        int sep = f.toString().lastIndexOf("\\");
        return f.toString().substring(0, sep);
    }
}
