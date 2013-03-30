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
package metadata;

/**
 * Clase que retorna diversos valores del entorno sometido a recolección
 *
 * @author andy737-1
 * @verision 1.0
 */
public class InfoCompu {

    /**
     *
     * @return nombre del equipo
     */
    public static String getPCName() {
        return System.getenv("COMPUTERNAME");
    }

    /**
     *
     * @return usuario logueado del equipo
     */
    public static String getUser() {
        return System.getProperty("user.name");
    }

    /**
     *
     * @return nombre del SO
     */
    public static String getSO() {
        return System.getProperty("os.name");
    }

    /**
     *
     * @return versión del SO
     */
    public static String getSOVer() {
        return System.getProperty("os.version");
    }

    /**
     *
     * @return arquitectura del SO
     */
    public static String getSOAq() {
        return System.getProperty("os.arch");
    }
}
