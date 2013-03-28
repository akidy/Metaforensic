/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andy737-1
 */
public class FindFiles {

    private List<File> resultados = null;

    private void ActionPerformed(Collector values) {

        resultados = new ArrayList<>();
        buscar(new File(values.getDirectorioRecoleccion()), values.getTipoArchivo());
        mostrarResultados();
    }

//imprime los archivos encontrados
    private void mostrarResultados() {
        for (File archivo : resultados) {
           // System.out.println(getAbsolutePath());
        }
    }

//agrega un archivo a la lista de resultados
    private void AddFind(File archivo) {
        resultados.add(archivo);
    }

    private void buscar(File raiz, ArrayList tipo) {
        
        File[] archivos = raiz.listFiles();
        for (int i = 0; i < archivos.length; i++) {
            File archivo = archivos[i];

            //si es directorio comenzamos la busqueda en ese directorio
            if (archivo.isDirectory()) {
             //   buscar(archivo, sentencia);
            }

            //solo compara contra el nombre del archivo
//            if (archivo.getName().match(sentencia)) {
                addResultado(archivo);
            }
        }

    }
