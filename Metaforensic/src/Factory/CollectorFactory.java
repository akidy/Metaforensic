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
package Factory;

import metadata.Image;
import metadata.Ofimatico;
import metadata.Pdf;

/**
 * Clase factory
 *
 * @author andy737-1
 * @version 1.0
 */
public class CollectorFactory implements CollectorFactoryMethod {

    private Boolean estado;

    /**
     * Inicialización de variables
     */
    public CollectorFactory() {
        estado = false;
    }

    /**
     *
     * @param ext extención del archivo a recolectar
     * @return estado de errores en recolección (true=error)
     */
    @Override
    public Boolean InitCollector(String ext) {
        switch (ext) {
            case "docx":
            case "xlsx":
            case "pptx":
            case "doc":
            case "xls":
            case "ppt":
            case "ods":
            case "odt":
            case "odp":
                Ofimatico offi = new Ofimatico();
                if (offi.CreateFile() && offi.WriteFile() && offi.CloseFile()) {
                    estado = true;
                }
                break;
            case "png":
            case "jpg":
                Image img = new Image();
                if (img.CreateFile() && img.WriteFile() && img.CloseFile()) {
                    estado = true;
                }
                break;
            case "pdf":
                Pdf pdf = new Pdf();
                if (pdf.CreateFile() && pdf.WriteFile() && pdf.CloseFile()) {
                    estado = true;
                }
                break;
        }
        return estado;
    }
}
