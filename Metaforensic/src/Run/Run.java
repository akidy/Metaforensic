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
package Run;

import GUI.CollectorGUI;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Clase encargada de iniciar la aplicación "Metaforensic"
 *
 *
 * @author andy737-1
 * @version 1.0
 */
public class Run {

    static Logger logger = Logger.getLogger(Run.class);

    /**
     * Inicio de la aplicación
     *
     * @param args (valor default)
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();
        logger.info("Ingresando a la aplicación");
        Splash.RunSplash(null, false);
        CollectorGUI cl = new CollectorGUI();
        cl.setVisible(true);
        
    }
}
