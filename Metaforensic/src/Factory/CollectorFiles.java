/**
 * *****************************************************************************
 *
 * Metaforensic version 1.0 - Análisis forense de metadatos en archivos
 * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez,
 * All Rights Reserved, https://github.com/andy737
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
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
package Factory;

import GUI.Collector;
import GUI.CollectorGUI;
import GUI.OperationViewer;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import metadata.DateTime;
import metadata.FileI;
import metadata.Hash;
import metadata.InfoCompu;
import metadata.OutFileLog;

/**
 *
 * @author andy737-1
 */
public class CollectorFiles extends FileName implements FileI {

    private Collector values;
    private OperationViewer op;
    private CollectorGUI gui;
    private OperationViewer opr;
    private OutFileLog out;
    private AbstractChecksum checksum;
    private Hash hashob;
    private List<Hash> very;
    private List<Hash> fail;
    private int error;
    private int subdir;
    private int pdf;
    private int jpg;
    private int docx;
    private int xlsx;
    private int pptx;
    private int odt;
    private int ods;
    private int odp;

    public CollectorFiles(CollectorGUI gui) {
        this.gui = gui;
        values = null;
        opr = new OperationViewer(gui);
        opr.setVisible(true);
        hashob = new Hash();
        very = new ArrayList<>();
        fail = new ArrayList<>();
        pdf = 0;
        error = 0;
        subdir = 0;
        jpg = 0;
        docx = 0;
        xlsx = 0;
        pptx = 0;
        odt = 0;
        ods = 0;
        odp = 0;

    }

    public final void OutLog() {
        out = new OutFileLog();
        out.setPath(values.getDirectorioSalida());
        out.setNameFile(DateTime.getDate().toString().replace("-", "") + "_" + DateTime.getTime().toString().replace(":", "") + "_" + InfoCompu.getUser());
        out.setFrame(op);
        out.CreateFile();

    }

    public void setValues(Collector values) {
        this.values = values;
    }

    public void setConsole(OperationViewer op) {
        this.op = op;
    }

    private void TypeFile() {
        opr.setText("Tipos de archivo [recolección]:");
        opr.Append();
        WriteFile("Tipos de archivo [recolección]:");
        for (int i = 0; i < values.getTipoArchivo().size(); i++) {
            opr.setText(" " + values.getTipoArchivo().get(i).toString() + " |");
            opr.Append();
            WriteFile(" " + values.getTipoArchivo().get(i).toString() + " |");
        }
        opr.setText("\n");
        opr.Append();
        WriteFile("\n");
        opr.setText("Tipo hash [checksum]: " + values.getTipoHash() + "\n");
        opr.Append();
        WriteFile("Tipo hash [checksum]: " + values.getTipoHash() + "\n");
    }

    public void InitAction() {

        String rc = "";
        OutLog();
        opr.setText(OutFileLog.titulo);
        opr.Append();
        WriteFile(OutFileLog.titulo);
        if (values.getRecursivo()) {
            rc = "recursiva ";
        }
        opr.setText("Iniciando recolección " + rc + "de metadatos.....\n\n"
                + "Directorio Raiz [recolección]: " + values.getDirectorioRecoleccion() + "\n");
        opr.Append();
        WriteFile("Iniciando recolección " + rc + "de metadatos...\n\n"
                + "Directorio Raiz [recolección]: " + values.getDirectorioRecoleccion() + "\n");
        TypeFile();
        opr.setText("Nombre del equipo: " + InfoCompu.getPCName() + "\n");
        opr.Append();
        WriteFile("Nombre del equipo: " + InfoCompu.getPCName() + "\n");
        opr.setText("Nombre del usuario: " + InfoCompu.getUser() + "\n");
        opr.Append();
        WriteFile("Nombre del usuario: " + InfoCompu.getUser() + "\n");
        opr.setText("Sistema operativo: " + InfoCompu.getSO() + "\n");
        opr.Append();
        WriteFile("Sistema operativo: " + InfoCompu.getSO() + "\n");
        opr.setText("SO versión: " + InfoCompu.getSOVer() + "\n");
        opr.Append();
        WriteFile("SO versión: " + InfoCompu.getSOVer() + "\n");
        opr.setText("Arquitectura: " + InfoCompu.getSOAq() + "\n\n");
        opr.Append();
        WriteFile("Arquitectura: " + InfoCompu.getSOAq() + "\n\n");
    }

    public void ActionPerformed() {
        InitAction();
        Find(new File(values.getDirectorioRecoleccion()), values.getTipoArchivo());
        PrintTot();
        out.closeFile();
        opr.setExit(true);
        opr.setExitButtonEnabled(true);
        gui.CleanGUIDirect();
    }

    private void WriteFile(String txt) {
        out.setText(txt);
        out.WriteFile();
    }

    private void Find(File raiz, ArrayList tipo) {
        String ext, check;
        File[] archivos = null;
        int i = 0;
        archivos = raiz.listFiles();
        for (i = 0; i < archivos.length; i++) {
            try {

                //System.out.println(i + " " + (archivos.length-1));
                File archivo = archivos[i];
                if (archivo.isDirectory() && values.getRecursivo()) {
                    subdir++;
                    Find(archivo, tipo);
                } else {
                    ext = extension(archivo);
                    if (tipo.contains(ext)) {
                        //opr.setColor(Color.white);
                        SumType(ext);
                        check = CreateChecksum(archivo);
                        FeaturesFile(archivo, ext, check);
                        VerifyChecksum(archivo);
                    }
                }
            } catch (Exception ex) {
                //opr.setColor(Color.red);
                error++;
                opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[DIR/FILE] " + archivos[i].toString() + "\n");
                opr.Append();
                WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[DIR/FILE] " + archivos[i].toString() + "\n");
                continue;
            }
        }
    }

    private void SumType(String ext) {
        switch (ext) {
            case "pdf":
                pdf++;
                break;
            case "jpg":
                jpg++;
                break;
            case "docx":
                docx++;
                break;
            case "xlsx":
                xlsx++;
                break;
            case "pptx":
                pptx++;
                break;
            case "odt":
                odt++;
                break;
            case "ods":
                ods++;
                break;
            case "odp":
                odp++;
                break;
        }
    }

    private void PrintTot() {

        opr.setText("Recolección finalizada con exito.....\n\n");
        opr.Append();
        WriteFile("Total de archivos sometidos a recolección: " + very.size() + "\n");
        opr.setText("Total de archivos sometidos a recolección: " + very.size() + "\n");
        opr.Append();
        WriteFile("Total de archivos sometidos a recolección: " + very.size() + "\n");
        opr.setText("Total de directorios sometidos a recolección: " + (subdir + 1) + "\n");
        opr.Append();
        WriteFile("Total de directorios sometidos a recolección: " + (subdir + 1) + "\n");
        opr.setText("Total de errores de recolección: " + error + "\n");
        opr.Append();
        WriteFile("Total de errores de recolección: " + error + "\n");
        opr.setText("Total de errores de integridad: " + fail.size() + "\n");
        opr.Append();
        WriteFile("Total de errores de integridad: " + fail.size() + "\n");
        if (pdf > 0) {
            opr.setText("Número de archivos pdf: " + pdf + "\n");
            opr.Append();
            WriteFile("Número de archivos pdf: " + pdf + " \n");
        }
        if (jpg > 0) {
            opr.setText("Número de archivos jpg: " + jpg + "\n");
            opr.Append();
            WriteFile("Número de archivos jpg: " + jpg + " \n");
        }

        if (docx > 0) {
            opr.setText("Número de archivos docx: " + docx + "\n");
            opr.Append();
            WriteFile("Número de archivos docx: " + docx + " \n");
        }
        if (xlsx > 0) {
            opr.setText("Número de archivos xlsx: " + xlsx + "\n");
            opr.Append();
            WriteFile("Número de archivos xlsx: " + xlsx + " \n");
        }
        if (pptx > 0) {
            opr.setText("Número de archivos pptx: " + pptx + "\n");
            opr.Append();
            WriteFile("Número de archivos pptx: " + pptx + " \n");
        }
        if (odt > 0) {
            opr.setText("Número de archivos odt: " + odt + "\n");
            opr.Append();
            WriteFile("Número de archivos odt: " + odt + " \n");
        }
        if (ods > 0) {
            opr.setText("Número de archivos ods: " + ods + "\n");
            opr.Append();
            WriteFile("Número de archivos ods: " + ods + " \n");
        }
        if (odp > 0) {
            opr.setText("Número de archivos odp: " + odp + "\n");
            opr.Append();
            WriteFile("Número de archivos odp: " + odp + " \n");
        }
    }

    @Override
    public void CollectorAlgorithm() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*
     * Implementacion de la API jacksum ver. 1.7.0 (Licencia GNU) para firmado de archivos
     * 
     * Credits to: http://www.jonelo.de/java/jacksum
     */

    @Override
    public String CreateChecksum(File archivo) {

        String hash;
        try {
            checksum = JacksumAPI.getChecksumInstance(values.getTipoHash());

        } catch (NoSuchAlgorithmException ex) {
            opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[TYPE]:[CHECKSUM] No se pudo ejecutar el algoritmo elegido.\n");
            opr.Append();
            WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[TYPE]:[CHECKSUM] No se pudo ejecutar el algoritmo elegido.\n");
        }
        try {
            checksum.readFile(archivo.toString());
        } catch (IOException ex) {
            opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[FILE]:[CHECKSUM] EL archivo no pudo ser firmado.\n");
            opr.Append();
            WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[FILE]:[CHECKSUM] EL archivo no pudo ser firmado.\n");
        }

        StringTokenizer st = new StringTokenizer(checksum.toString());
        hash = st.nextElement().toString();
        hashob.setFile(archivo);
        hashob.setHash(hash);
        very.add(hashob);

        return hash;

    }
    /*
     * Implementacion de la API jacksum ver. 1.7.0 (Licencia GNU) para verificación de integridad de archivos
     * 
     * Credits to: http://www.jonelo.de/java/jacksum
     */

    @Override
    public Boolean VerifyChecksum(File archivo) {
        String hash;
        try {
            checksum = JacksumAPI.getChecksumInstance(values.getTipoHash());

        } catch (NoSuchAlgorithmException ex) {
            opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[FAIL]:[CHECKSUM] No se pudo ejecutar el algoritmo elegido.\n");
            opr.Append();
            WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[FAIL]:[CHECKSUM] No se pudo ejecutar el algoritmo elegido.\n");
        }
        try {
            checksum.readFile(archivo.toString());
        } catch (IOException ex) {
            opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[FAIL]:[FILE] El archivo no pudo ser verificado.\n");
            opr.Append();
            WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[FAIL]:[FILE] El archivo no pudo ser verificado.\n");
        }

        StringTokenizer st = new StringTokenizer(checksum.toString());
        hash = st.nextElement().toString();
        hashob.setFile(archivo);
        hashob.setHash(hash);
        if (very.contains(hashob)) {
            opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [VERIFY]:[PASSED]:[FILE] El archivo paso la prueba de integridad.\n\n");
            opr.Append();
            WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [VERIFY]:[PASSED]:[FILE] El archivo paso la prueba de integridad.\n\n");
            return true;
        } else {
            opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [VERIFY]:[FAIL]:[FILE] El archivo no paso la prueba de integridad.\n\n");
            opr.Append();
            WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [VERIFY]:[FAIL]:[FILE] El archivo no paso la prueba de integridad.\n\n");
            fail.add(hashob);
            return false;
        }

    }

    @Override
    public void OpenFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void CloseFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void FeaturesFile(File archivo, String ext, String check) {

        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [SCAN]:[DIR] " + path(archivo) + "\\" + "\n");
        opr.Append();
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FOUND]:[FILE] " + filename(archivo) + "." + ext + "\n");
        opr.Append();
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FILE]:[TYPE] " + ext + "\n");
        opr.Append();
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FILE]:[SIZE] " + SizeFile(archivo) + " KB\n");
        opr.Append();
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [CHECKSUM]:[FILE] " + check + " \n");
        opr.Append();
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [SCAN]:[DIR] " + path(archivo) + "\\" + "\n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FOUND]:[FILE] " + filename(archivo) + "." + ext + "\n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FILE]:[TYPE] " + ext + "\n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FILE]:[SIZE] " + SizeFile(archivo) + " KB\n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [TYPE]:[CHECKSUM] " + values.getTipoHash() + " \n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [CHECKSUM]:[FILE] " + check + " \n");
    }

    private double SizeFile(File archivo) {

        double bytes = archivo.length();
        double kb = bytes / 1024;
        return kb;
    }
}
