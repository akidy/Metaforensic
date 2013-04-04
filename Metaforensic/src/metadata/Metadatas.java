/*
 * *****************************************************************************
 *    
 * Metaforensic version 1.1 - Análisis forense de metadatos en archivos
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

import Process.Collector;
import Process.FileFea;
import Process.Hash;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

/**
 * Clase abstracta que define las variables y metadatos utilizados por todas las
 * clases recolectoras de metadatos (Sin implemtación)
 *
 * @author andy737-1
 * @version 1.1
 */
public abstract class Metadatas extends FilesCommon {

    /**
     * Lectura de archivo
     */
    protected InputStream entrada;
    /**
     * Manejador de carateres de la api Apache Tika
     */
    protected BodyContentHandler handler;
    /**
     * Detector de metadatos de la api Apache Tika
     */
    protected AutoDetectParser parser;
    /**
     * Almacena los metadatos api Apache Tika
     */
    protected Metadata metadatos;
    /**
     * Escritura de/en archivo
     */
    protected FileOutputStream mt;
    /**
     * Buffer de escritura
     */
    protected BufferedWriter outfinal;
    /**
     * Almacena temporalmente el string a escribir en el archivo .afa
     */
    protected String txt;
    /**
     * Objeto que contiene información del archivo a recolectar
     */
    protected FileFea fif;
    /**
     * Objeto que contiene información sobre el archivo .afa
     */
    protected FileMeta fim;
    /**
     * Stream de lectura
     */
    protected OutputStreamWriter metaout;
    /**
     * Nombre del archivo .afa
     */
    protected String outmeta;
    /**
     * Variable de prueba
     */
    protected File test;
    /**
     * Almcenamiento temporal de los metadatos extraídos
     */
    protected String[] metadatosN;
    /**
     *
     */
    protected Collector cll;
    /**
     * Checksum del archivo sometido a recolección
     */
    protected Hash hash;
    /*
     * Buffer temporal de carga de datos
     */
    protected StringBuffer buffer;

    /**
     *
     * @return tamaño del archivo sometido a recolección
     */
    public abstract double SizeFile();

    /*
     * Carga de datos al buffer
     */
    public abstract Boolean LoadBuffer();
}
