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
import metadata.ElapsedTime;
import metadata.FileFea;
import metadata.ProcessFile;
import metadata.Hash;
import metadata.InfoCompu;
import metadata.OutFileLog;

/**
 *
 * @author andy737-1
 */
public class CollectorFiles extends FileName implements ProcessFile {

    private Collector values;
    private OperationViewer op;
    private CollectorGUI gui;
    private OperationViewer opr;
    private OutFileLog out;
    private FileFea fif;
    private AbstractChecksum checksum;
    private Hash hashob;
    private List<Hash> very;
    private List<Hash> fail;
    private ElapsedTime et;
    private int error;
    private int subdir;
    private int pdf;
    private int jpg;
    private int png;
    private int docx;
    private int xlsx;
    private int pptx;
    private int doc;
    private int xls;
    private int ppt;
    private int odt;
    private int ods;
    private int odp;

    public CollectorFiles(CollectorGUI gui) {
        this.gui = gui;
        InitVar();
    }

    private void InitViewer() {
        opr = new OperationViewer(gui);
        opr.setVisible(true);
    }

    private void InitVar() {
        values = Collector.getInstance();
        InitViewer();
        hashob = new Hash();
        very = new ArrayList<>();
        fail = new ArrayList<>();
        et = new ElapsedTime();
        fif = fif.getInstance();
        pdf = 0;
        error = 0;
        subdir = 0;
        jpg = 0;
        png = 0;
        docx = 0;
        xlsx = 0;
        pptx = 0;
        doc = 0;
        xls = 0;
        ppt = 0;
        odt = 0;
        ods = 0;
        odp = 0;
    }

    public final void OutLog() {
        out = new OutFileLog();
        fif.setPath(values.getDirectorioSalida());
        fif.setNameFile(DateTime.getDate().toString().replace("-", "") + "_" + DateTime.getTimeMilli().toString().replace(":", "") + "_" + InfoCompu.getUser());
        fif.setFrame(op);
        out.CreateFile();

    }

    public void setConsole(OperationViewer op) {
        this.op = op;
    }

    private void TypeFile() {
        SetProcessTxt("Tipos de archivo [recolección]:");
        for (int i = 0; i < values.getTipoArchivo().size(); i++) {
            SetProcessTxt(" " + values.getTipoArchivo().get(i).toString() + " |");
        }
        SetProcessTxt("\n");
        SetProcessTxt("Tipo hash [checksum]: " + values.getTipoHash() + "\n");
    }

    private void GeneralData(String rc) {
        SetProcessTxt("Iniciando recolección " + rc + "de metadatos.....\n\n"
                + "Directorio Raiz [recolección]: " + values.getDirectorioRecoleccion() + "\n");
        TypeFile();
        SetProcessTxt("Nombre del equipo: " + InfoCompu.getPCName() + "\n");
        SetProcessTxt("Nombre del usuario: " + InfoCompu.getUser() + "\n");
        SetProcessTxt("Sistema operativo: " + InfoCompu.getSO() + "\n");
        SetProcessTxt("SO versión: " + InfoCompu.getSOVer() + "\n");
        SetProcessTxt("Arquitectura: " + InfoCompu.getSOAq() + "\n\n");
    }

    public void InitAction() {
        String rc = "";
        OutLog();
        SetProcessTxt(OutFileLog.titulo);
        if (values.getRecursivo()) {
            rc = "recursiva ";
        }
        GeneralData(rc);
    }

    public void ActionPerformed() {
        et.StartAll();
        InitAction();
        Find(new File(values.getDirectorioRecoleccion()), values.getTipoArchivo());
        PrintTot();
        et.StopAll();
        SetProcessTxt("Tiempo total transcurrido: " + et.getElapsedTimeAll() + " segundos aprox.\n");
        out.CloseFile();
        opr.setExit(true);
        opr.setExitButtonEnabled(true);
        gui.CleanGUIDirect();
    }

    private void WriteFile(String txt) {
        out.setText(txt);
        out.WriteFile();
    }

    private void Find(File raiz, ArrayList tipo) {
        String ext;
        File[] archivos = null;
        int i = 0;
        archivos = raiz.listFiles();
        for (i = 0; i < archivos.length; i++) {
            try {
                File archivo = archivos[i];
                if (archivo.isDirectory() && values.getRecursivo()) {
                    subdir++;
                    Find(archivo, tipo);
                } else {
                    ext = extension(archivo);
                    if (tipo.contains(ext)) {
                        if (!opr.getPanic()) {
                            ProcessFile(ext, archivo);
                        } else {
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                error++;
                SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [ERROR]:[DIR/FILE] " + archivos[i].toString() + "\\ \n");
                continue;
            }
        }
    }

    private void ProcessFile(String ext, File archivo) {
        String check;
        SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [START]:[PROCESS]\n");
        et.Start();
        SumType(ext);
        check = CreateChecksum(archivo);
        FeaturesFile(archivo, ext, check);
        VerifyChecksum(archivo);
        SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [END]:[PROCESS]\n");
        et.Stop();
        SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [ELAPSED]:[TIME] " + et.getElapsedTime() + " segundos aprox.\n\n");
    }

    private void SumType(String ext) {
        switch (ext) {
            case "pdf":
                pdf++;
                break;
            case "jpg":
                jpg++;
                break;
            case "png":
                png++;
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
            case "doc":
                doc++;
                break;
            case "xls":
                xls++;
                break;
            case "ppt":
                ppt++;
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

    private void DefineExtView() {
        if (pdf > 0) {
            SetProcessTxt("Número de archivos pdf: " + pdf + "\n");
        }
        if (jpg > 0) {
            SetProcessTxt("Número de archivos jpg: " + jpg + "\n");
        }
        if (png > 0) {
            SetProcessTxt("Número de archivos png: " + png + "\n");
        }
        if (docx > 0) {
            SetProcessTxt("Número de archivos docx: " + docx + "\n");
        }
        if (xlsx > 0) {
            SetProcessTxt("Número de archivos xlsx: " + xlsx + "\n");
        }
        if (pptx > 0) {
            SetProcessTxt("Número de archivos pptx: " + pptx + "\n");
        }
        if (doc > 0) {
            SetProcessTxt("Número de archivos doc: " + doc + "\n");
        }
        if (xls > 0) {
            SetProcessTxt("Número de archivos xls: " + xls + "\n");
        }
        if (ppt > 0) {
            SetProcessTxt("Número de archivos ppt: " + ppt + "\n");
        }
        if (odt > 0) {
            SetProcessTxt("Número de archivos odt: " + odt + "\n");
        }
        if (ods > 0) {
            SetProcessTxt("Número de archivos ods: " + ods + "\n");
        }
        if (odp > 0) {
            SetProcessTxt("Número de archivos odp: " + odp + "\n");
        }
    }

    private void ValMsgOpAll() {
        if (opr.getPanic()) {
            SetProcessTxt("\nProceso cancelado.....\n\n");
        } else {
            if (fail.size() > 0 || error > 0 && very.size() > 0) {
                SetProcessTxt("\nRecolección finalizada con errores.....\n\n");
            } else {
                if (very.size() > 0) {
                    SetProcessTxt("\nRecolección finalizada con exito.....\n\n");
                } else {
                    SetProcessTxt("\nNo se encontraron archivos.....\n\n");
                }
            }
        }
    }

    private void PrintTot() {
        ValMsgOpAll();
        SetProcessTxt("Total de archivos sometidos a recolección: " + very.size() + "\n");
        SetProcessTxt("Total de directorios sometidos a recolección: " + (subdir + 1) + "\n");
        SetProcessTxt("Total de errores de recolección: " + error + "\n");
        SetProcessTxt("Total de errores de integridad: " + fail.size() + "\n");
        DefineExtView();
    }

    private double SizeFile(File archivo) {
        double bytes = archivo.length();
        double kb = bytes / 1024;
        return kb;
    }

    private void SetProcessTxt(String txt) {
        opr.setText(txt);
        opr.Append();
        WriteFile(txt);
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
        StringTokenizer st;
        try {
            checksum = JacksumAPI.getChecksumInstance(values.getTipoHash());

        } catch (NoSuchAlgorithmException ex) {
            SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [ERROR]:[TYPE]:[CHECKSUM] No se pudo ejecutar el algoritmo elegido.\n");
        }
        try {
            checksum.readFile(archivo.toString());
        } catch (IOException ex) {
            SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [ERROR]:[FILE]:[CHECKSUM] EL archivo no pudo ser firmado.\n");
        }
        st = new StringTokenizer(checksum.toString());
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
        StringTokenizer st;
        try {
            checksum = JacksumAPI.getChecksumInstance(values.getTipoHash());

        } catch (NoSuchAlgorithmException ex) {
            SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [ERROR]:[FAIL]:[CHECKSUM] No se pudo ejecutar el algoritmo elegido.\n");
        }
        try {
            checksum.readFile(archivo.toString());
        } catch (IOException ex) {
            SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[FAIL]:[FILE] El archivo no pudo ser verificado.\n");
        }
        st = new StringTokenizer(checksum.toString());
        hash = st.nextElement().toString();
        hashob.setFile(archivo);
        hashob.setHash(hash);
        if (very.contains(hashob)) {
            SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [VERIFY]:[PASSED]:[FILE] El archivo paso la prueba de integridad.\n");
            return true;
        } else {
            SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [VERIFY]:[FAIL]:[FILE] El archivo no paso la prueba de integridad.\n");
            fail.add(hashob);
            return false;
        }
    }

    public void FeaturesFile(File archivo, String ext, String check) {
        SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [SCAN]:[DIR] " + path(archivo) + "\\" + "\n");
        SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [FOUND]:[FILE] " + filename(archivo) + "." + ext + "\n");
        SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [FILE]:[TYPE] " + ext + "\n");
        SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [FILE]:[SIZE] " + SizeFile(archivo) + " KB\n");
        SetProcessTxt("[" + DateTime.getDate() + " " + DateTime.getTimeMilli() + "] [CHECKSUM]:[FILE] " + check + " \n");
    }
}
