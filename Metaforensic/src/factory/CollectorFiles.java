/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import GUI.Collector;
import GUI.CollectorGUI;
import GUI.OperationViewer;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import metadata.DateTime;
import metadata.FileI;
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

    public CollectorFiles(CollectorGUI gui) {
        this.gui = gui;
        values = null;
        opr = new OperationViewer(gui);
        opr.setVisible(true);
    }

    public final void OutLog() {
        out = new OutFileLog();
        out.setPath(values.getDirectorioSalida());
        out.setNameFile("prueba");
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
        opr.setText("Iniciando recolección " + rc + "de metadatos...\n\n"
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
                    Find(archivo, tipo);
                } else {
                    ext = extension(archivo);
                    if (tipo.contains(ext)) {
                        //opr.setColor(Color.white);
                        check = CreateChecksum(archivo);
                        FeaturesFile(archivo, ext, check);

                    }
                }
            } catch (Exception ex) {
                //opr.setColor(Color.red);
                opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[DIR] " + archivos[i].toString() + "\n");
                opr.Append();
                WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [ERROR]:[DIR] " + archivos[i].toString() + "\n");
                continue;
            }
        }
    }

    @Override
    public void CollectorAlgorithm() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String CreateChecksum(File archivo) {
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

        return st.nextElement().toString();

    }

    @Override
    public void VerifyChecksum() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [SCAN]:[DIR] " + path(archivo) + "\n");
        opr.Append();
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FOUND]:[FILE] " + filename(archivo) + "." + ext + "\n");
        opr.Append();
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FILE]:[TYPE] " + ext + "\n");
        opr.Append();
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FILE]:[SIZE] " + (archivo.length() / 1024) + " KB\n");
        opr.Append();
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [TYPE]:[CHECKSUM] " + values.getTipoHash() + " \n");
        opr.Append();
        opr.setText("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [CHECKSUM]:[FILE] " + check + " \n");
        opr.Append();
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [SCAN]:[DIR] " + path(archivo) + "\n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FOUND]:[FILE] " + filename(archivo) + "." + ext + "\n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FILE]:[TYPE] " + ext + "\n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [FILE]:[SIZE] " + (archivo.length() / 1024) + " KB\n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [TYPE]:[CHECKSUM] " + values.getTipoHash() + " \n");
        WriteFile("[" + DateTime.getDate() + " " + DateTime.getTime() + "] [CHECKSUM]:[FILE] " + check + " \n");
    }
}
