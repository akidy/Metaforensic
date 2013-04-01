/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

/**
 *
 * @author andy737-1
 */
public class ErroCollectorMeta {

    private List<File> error;
    private FileFea fif;
    private OutputStreamWriter ercod;
    private FileOutputStream er;
    private BufferedWriter outfinal;

    public ErroCollectorMeta(List<File> error) {
        this.error = error;
        fif = FileFea.getInstance();
        ercod = null;
        er = null;
        outfinal = null;
    }

    public Boolean WriteErrorFiles() {
        try {
            outfinal.write("******************************************************************************************************\n");
            outfinal.flush();
            for (int i = 0; i < error.size()-1; i++) {
                outfinal.write("[file:Fail]:" + error.get(i) + "\n");
            outfinal.flush();
            }
            
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public Boolean CloseFile() {
        try {
            outfinal.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public Boolean OpenFile() {
        try {
            er = new FileOutputStream(NameFileC(), true);
            ercod = new OutputStreamWriter(er, "UTF-8");
            outfinal = new BufferedWriter(ercod);
            return true;
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            return false;
        }
    }

    private String NameFileC() {
        String filerror = fif.getPath() + "\\" + fif.getNameFile() + ".afa";
        return filerror;
    }
}
