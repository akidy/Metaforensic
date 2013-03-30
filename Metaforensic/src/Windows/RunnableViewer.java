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
package Windows;

import Factory.CollectorFiles;
import GUI.Collector;
import GUI.CollectorGUI;

/**
 * Clase que implemnta runnable para el visor de operaciones
 *
 * @author andy737-1
 * @version 1.0
 */
public class RunnableViewer implements Runnable {

    private CollectorGUI gui;
    private Collector values;

    /**
     * Constructor que inicializa el objeto runnableviewer
     *
     * @param gui frame padre
     * @param values valores definidos por el usuario
     */
    public RunnableViewer(CollectorGUI gui, Collector values) {
        this.gui = gui;
        this.values = values;
    }

    @Override
    public void run() {
        CollectorFiles ff = new CollectorFiles(gui);
        ff.setValues(values);
        ff.ActionPerformed();
    }
}
