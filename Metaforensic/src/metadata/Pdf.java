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
 * Clase encarga de recolectar metadatos en archivos pdf (Sin implemtación)
 *
 * @author andy737-1
 * @version 1.1
 */
public class Pdf extends Metadatas {

    /**
     * Inicialización de variables
     */
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
        buffer = new StringBuffer();
    }

    @Override
    public Boolean WriteFile() {
        try {
            outfinal.write(buffer.toString());
            outfinal.flush();
            return true;
        } catch (Exception ex) {
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

    @Override
    public Boolean LoadBuffer() {
        try {
            entrada = new FileInputStream(fim.getNameFile());
            metadatos = new Metadata();
            handler = new BodyContentHandler(-1);
            parser = new AutoDetectParser();
            parser.parse(entrada, handler, metadatos);
            metadatosN = metadatos.names();
            buffer.append("******************************************************************************************************\n");
            buffer.append("[host:Name]:").append(InfoCompu.getPCName()).append("\n");
            buffer.append("[host:User]:").append(InfoCompu.getUser()).append("\n");
            buffer.append("[host:OS]:").append(InfoCompu.getSO()).append("\n");
            buffer.append("[Host:VerOS]:").append(InfoCompu.getSOVer()).append("\n");
            buffer.append("[host:Arq]:").append(InfoCompu.getSOAq()).append("\n");
            buffer.append("[file:Name]:").append(fim.getNameFile().toString()).append("\n");
            buffer.append("[file:Size]:").append(SizeFile()).append(" KB\n");
            buffer.append("[checksum:Type]:").append(cll.getTipoHash()).append(" KB\n");
            buffer.append("[checksum:Hash]:").append(hash.getHash()).append("\n");
            for (String name : metadatosN) {
                buffer.append("[").append(name).append("]" + ":").append(metadatos.get(name)).append("\n");
            }
            return true;
        } catch (IOException | SAXException | TikaException ex) {
            return false;
        }
    }
}
