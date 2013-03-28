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
package Run;

import factory.CollectorGUI;

/**
 * Clase encargada de iniciar la aplicación "Metaforensic"
 *
 *
 * @author andy737-1
 * @version 1.0
 */
public class Run {

    /**
     *
     * @param args (valor default)
     */
    public static void main(String[] args) {

        splash.RunSplash();
        CollectorGUI cl = new CollectorGUI();
        cl.setVisible(true);

    }
}
