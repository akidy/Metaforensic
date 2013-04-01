/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Factory;

import metadata.Image;
import metadata.Ofimatico;
import metadata.Pdf;

/**
 *
 * @author andy737-1
 */
public class CollectorFactory implements CollectorFactoryMethod {

    @Override
    public Boolean InitCollector(String ext) {
        Boolean estado = false;
        switch (ext) {
            case "docx":
            case "xlsx":
            case "pptx":
            case "doc":
            case "xls":
            case "ppt":
            case "ods":
            case "odt":
            case "odp":
                Ofimatico offi = new Ofimatico();
                if (offi.CreateFile() && offi.WriteFile() && offi.CloseFile()) {
                    estado = true;
                }
                break;

            case "png":
            case "jpg":
                Image img = new Image();
                if (img.CreateFile() && img.WriteFile() && img.CloseFile()) {
                    estado = true;
                }
                break;
            case "pdf":
                Pdf pdf = new Pdf();
                if (pdf.CreateFile() && pdf.WriteFile() && pdf.CloseFile()) {
                    estado = true;
                }
                break;
        }
        return estado;
    }
}
