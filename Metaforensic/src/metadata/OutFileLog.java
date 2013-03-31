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
package metadata;

import Windows.ModalDialog;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author andy737-1
 * @version 1.0
 */
public class OutFileLog extends FilesCommon {

    private FileOutputStream log;
    private OutputStreamWriter outlog;
    private BufferedWriter outfinal;
    private String txt;
    private ModalDialog md;
    private FileFea fif;
    /**
     * Encabezado de log
     */
    public static String titulo = "************************************************************************************\n"
            + " * Metaforensic version 1.0 - Análisis forense de metadatos en archivos\n"
            + " * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez, TSU Idania Aquino Cruz,\n"
            + " * All Rights Reserved, https://github.com/andy737\n"
            + " * [Recolector] Log de operaciones y eventos\n"
            + "************************************************************************************\n\n";

    /**
     * Inicia variables
     */
    public OutFileLog() {
        md = new ModalDialog();
        log = null;
        outlog = null;
        outfinal = null;
        txt = "";
        fif = fif.getInstance();
    }

    /**
     *
     * @param txt proceso que se efectuo sobre el archivo
     */
    public void setText(String txt) {

        this.txt = txt;
    }

    /**
     * Crea un archivo de extención .log
     */
    @Override
    public void CreateFile() {
        try {
            log = new FileOutputStream(fif.getPath() + "\\" + fif.getNameFile() + ".log");
            outlog = new OutputStreamWriter(log, "UTF-8");
            outfinal = new BufferedWriter(outlog);
        } catch (IOException ex) {
            md.setDialogo("No se pudo crear el archivo " + fif.getNameFile() + ".log" + " en la carpeta: \n" + fif.getPath());
            md.setTitulo("Error de archivo");
            md.setFrame(fif.getFrame());
            md.DialogErr();
        }

    }

    /**
     * Escribe los procesos sobre el archivo .log
     */
    @Override
    public void WriteFile() {
        try {
            outfinal.write(txt);
            outfinal.flush();
        } catch (IOException ex) {
            md.setDialogo("Error de escritura en el archivo " + fif.getNameFile() + ".log" + "ubicado en la carpeta: \n" + fif.getPath());
            md.setTitulo("Error de archivo");
            md.setFrame(fif.getFrame());
            md.DialogErr();
        }

    }

    /**
     * Finaliza la escritura sobre el archivo .log
     */
    @Override
    public void CloseFile() {
        try {
            outfinal.close();
        } catch (IOException ex) {
            md.setDialogo("Error al cerrar el archivo " + fif.getNameFile() + ".log" + "ubicado en la carpeta: \n" + fif.getPath());
            md.setTitulo("Error de archivo");
            md.setFrame(fif.getFrame());
            md.DialogErr();
        }
    }
}
