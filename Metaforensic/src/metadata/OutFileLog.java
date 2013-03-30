/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import Windows.ModalDialog;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JFrame;

/**
 *
 * @author andy737-1
 */
public class OutFileLog {

    private String nombre;
    private String path;
    private FileOutputStream log;
    private OutputStreamWriter outlog;
    private BufferedWriter outfinal;
    private String txt;
    private ModalDialog md;
    private JFrame f;
    public static String titulo = "*********************************************************************************\n"
            + " * Metaforensic version 1.0 - Análisis forense de metadatos en archivos\n"
            + " * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez,\n"
            + " * All Rights Reserved, https://github.com/andy737\n"
            + " * [Recolector] Log de operaciones y eventos\n"
            + "*********************************************************************************\n\n";

    public OutFileLog() {
        nombre = "";
        path = "";
        log = null;
        outlog = null;
        outfinal = null;
        txt = "";
        md = null;
        f = null;
    }

    public void setNameFile(String nombre) {
        this.nombre = nombre;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setText(String txt) {

        this.txt = txt;
    }

    public void setFrame(JFrame f) {
        this.f = f;
    }

    public void CreateFile() {
        try {
            log = new FileOutputStream(path + "\\" + nombre + ".log");
            outlog = new OutputStreamWriter(log, "UTF-8");
            outfinal = new BufferedWriter(outlog);
        } catch (IOException ex) {
            md.setDialogo("No se pudo crear el archivo " + nombre + ".log" + " en la carpeta: \n" + path);
            md.setTitulo("Error de archivo");
            md.setFrame(f);
            md.DialogErr();
        }

    }

    public void WriteFile() {
        try {
            outfinal.write(txt);
            outfinal.flush();
        } catch (IOException ex) {
            md.setDialogo("Error de escritura en el archivo " + nombre + ".log" + "ubicado en la carpeta: \n" + path);
            md.setTitulo("Error de archivo");
            md.setFrame(f);
            md.DialogErr();
        }

    }

    public void closeFile() {
        try {
            outfinal.close();
        } catch (IOException ex) {
            md.setDialogo("Error al cerrar el archivo " + nombre + ".log" + "ubicado en la carpeta: \n" + path);
            md.setTitulo("Error de archivo");
            md.setFrame(f);
            md.DialogErr();
        }
    }
}
