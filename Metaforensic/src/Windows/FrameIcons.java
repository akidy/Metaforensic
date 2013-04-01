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
package Windows;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Clase encargada de setear los iconos en el entorno Windows
 *
 * @author andy737-1
 * @version 1.0
 */
public class FrameIcons {

    private static FrameIcons instance = new FrameIcons();
    private ArrayList<Image> iconos;

    /**
     * Constructor inicializa arraylist de iconos;
     */
    private FrameIcons() {
        iconos = null;
    }
    /*
     * Metodo que inicializa los iconos de apliación disponibles
     */

    private void InitialIcons() {
        iconos = new ArrayList<>();
        iconos.add(new ImageIcon(getClass().getResource("/Images/icono16.png")).getImage());
        iconos.add(new ImageIcon(getClass().getResource("/Images/icono32.png")).getImage());
        iconos.add(new ImageIcon(getClass().getResource("/Images/icono48.png")).getImage());
        iconos.add(new ImageIcon(getClass().getResource("/Images/icono64.png")).getImage());
        iconos.add(new ImageIcon(getClass().getResource("/Images/icono128.png")).getImage());

    }

    /**
     *
     * @return instancia de un nuevo objeto para establecer los iconos de la app
     * en el sistema
     */
    public static FrameIcons getInstance() {
        return instance;
    }

    /**
     * Carga de iconos que serán visualizados
     */
    public void SetIcon() {

        InitialIcons();
    }

    /**
     * Retorno iconos
     *
     * @return iconos para mostrar en entorno Windows
     */
    public ArrayList<Image> GetIcon() {
        return iconos;
    }
}
