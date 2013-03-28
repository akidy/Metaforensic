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

import factory.Collector;

/**
 * Clase encargada de validar la información ingresada por el usuario
 *
 * @author andy737-1
 */
public class ValidateInfo {

    /**
     *
     * @param values
     * @return el estado de la validacion true correcto y false para error
     */
    public boolean GeneralValidate(Collector values) {

        boolean estado = false;

        if (!"".equals(values.getDirectorioRecoleccion()) || !"".equals(values.getDirectorioSalida()) || (values.getTipoArchivo().isEmpty() != true) || values.getTipoHash() != null) {
            estado = true;
        }

        return estado;
    }

    /**
     *
     * @param values que contiene al obejto colector para recibir los parametros asignados en el GUI
     * @return el valor del error generado
     */
    public int EspecificValidate(Collector values) {

        if (!"".equals(values.getDirectorioRecoleccion())) {
            if (!"".equals(values.getDirectorioSalida())) {
                if (values.getTipoHash() != null) {
                    if ((values.getTipoArchivo().isEmpty() != true)) {
                        if (!values.getDirectorioRecoleccion().equalsIgnoreCase(values.getDirectorioSalida())) {
                            return 6;
                        } else {
                            return 5;
                        }
                    } else {
                        return 4;
                    }
                } else {
                    return 3;
                }

            } else {
                return 2;
            }

        } else {
            return 1;
        }
    }
}
