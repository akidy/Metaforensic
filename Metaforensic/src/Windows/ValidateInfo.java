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

    private Collector values;
    private Boolean estado;
    private int error;

    /**
     *
     */
    public ValidateInfo() {

        values = null;
        estado = false;
        error = 0;

    }

    /**
     * Valida los campos y opciones dle frame para enviar confirmacion de
     * limpieza o salida del mismo
     */
    public void GeneralValidate() {
        if (!"".equals(values.getDirectorioRecoleccion()) || !"".equals(values.getDirectorioSalida()) || (values.getTipoArchivo().isEmpty() != true) || values.getTipoHash() != null) {
            estado = true;
        } else {
            estado = false;
        }
    }

    /**
     * Valida las opciones y campos del frame para inciar recolección
     */
    public void EspecificValidate() {

        if (!"".equals(values.getDirectorioRecoleccion())) {
            if (!"".equals(values.getDirectorioSalida())) {
                if (values.getTipoHash() != null) {
                    if ((values.getTipoArchivo().isEmpty() != true)) {
                        if (!values.getDirectorioRecoleccion().equalsIgnoreCase(values.getDirectorioSalida())) {
                            error = 6;
                        } else {
                            error = 5;
                        }
                    } else {
                        error = 4;
                    }
                } else {
                    error = 3;
                }

            } else {
                error = 2;
            }

        } else {
            error = 1;
        }
    }

    /**
     *
     * @param values que contiene los valores del objeto Collector
     */
    public void setValues(Collector values) {
        this.values = values;
    }

    /**
     *
     * @return tipo de error generado
     */
    public int getError() {
        return error;
    }

    /**
     *
     * @return false error, true correcto
     */
    public Boolean getEstado() {
        return estado;
    }
}
