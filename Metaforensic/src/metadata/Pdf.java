/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import Process.Collector;
import Process.FileFea;
import Process.Hash;
import Process.InfoCompu;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author andy737-1
 */
public class Pdf extends Metadatas {

    public Pdf() {
        entrada = null;
        metadatos = null;
        handler = null;
        parser = null;
        outfinal = null;
        metaout = null;
        outmeta = "";
        test = null;
        mt = null;
        txt = "";
        fim = FileMeta.getInstance();
        fif = FileFea.getInstance();
        cll = Collector.getInstance();
        metadatosN = null;
        hash = Hash.getInstance();
    }

    @Override
    public Boolean WriteFile() {
        try {
            entrada = new FileInputStream(fim.getNameFile());
            metadatos = new Metadata();
            handler = new BodyContentHandler(-1);
            parser = new AutoDetectParser();
            parser.parse(entrada, handler, metadatos);
            metadatosN = metadatos.names();
            outfinal.write("******************************************************************************************************\n");
            outfinal.flush();
            outfinal.write("[host:Name]:" + InfoCompu.getPCName() + "\n");
            outfinal.flush();
            outfinal.write("[host:User]:" + InfoCompu.getUser() + "\n");
            outfinal.flush();
            outfinal.write("[host:OS]:" + InfoCompu.getSO() + "\n");
            outfinal.flush();
            outfinal.write("[Host:VerOS]:" + InfoCompu.getSOVer() + "\n");
            outfinal.flush();
            outfinal.write("[host:Arq]:" + InfoCompu.getSOAq() + "\n");
            outfinal.flush();
            outfinal.write("[file:Name]:" + fim.getNameFile().toString() + "\n");
            outfinal.flush();
            outfinal.write("[file:Size]:" + SizeFile() + " KB\n");
            outfinal.flush();
            outfinal.write("[checksum:Type]:" + cll.getTipoHash() + " KB\n");
            outfinal.flush();
            outfinal.write("[checksum:Hash]:" + hash.getHash() + " KB\n");
            outfinal.flush();
            for (String name : metadatosN) {
                outfinal.write("[" + name + "]" + ":" + metadatos.get(name) + "\n");
                outfinal.flush();
            }
            return true;
        } catch (IOException | SAXException | TikaException ex) {
            return false;
        }

    }

    @Override
    public Boolean CloseFile() {
        try {
            outfinal.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public Boolean CreateFile() {
        test = new File(NameFileC());
        try {
            if (!test.exists()) {
                mt = new FileOutputStream(NameFileC());
                metaout = new OutputStreamWriter(mt, "UTF-8");
                outfinal = new BufferedWriter(metaout);
                return true;
            } else {
                mt = new FileOutputStream(NameFileC(), true);
                metaout = new OutputStreamWriter(mt, "UTF-8");
                outfinal = new BufferedWriter(metaout);
                return true;
            }
        } catch (IOException ex) {
            return false;
        }

    }

    @Override
    public double SizeFile() {
        double bytes = fim.getNameFile().length();
        double kb = bytes / 1024;
        return kb;
    }

    private String NameFileC() {
        outmeta = fif.getPath() + "\\" + fif.getNameFile() + ".afa";
        return outmeta;
    }
}
