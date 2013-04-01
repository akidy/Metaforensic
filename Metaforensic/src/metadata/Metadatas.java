/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author andy737-1
 */
public abstract class Metadatas extends FilesCommon {

    protected InputStream entrada;
    protected BodyContentHandler handler;
    protected AutoDetectParser parser;
    protected Metadata metadatos;
    protected FileOutputStream mt;
    protected BufferedWriter outfinal;
    protected String txt;
    protected FileFea fif;
    protected FileMeta fim;
    protected OutputStreamWriter metaout;
    protected String outmeta;
    protected File test;
    protected String[] metadatosN;
    protected Collector cll;
    protected Hash hash;
    
    public abstract double SizeFile();
          
}
