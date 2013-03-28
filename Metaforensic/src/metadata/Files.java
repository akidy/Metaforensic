/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author andy737-1
 */
public abstract class Files implements Metadata{
    
    protected String hash;
    protected String tipo_hash;
    protected String nombre;
    protected String directorio;
    protected String id_archivo;
    protected String tipo;
    protected Date fecha_recoleccion;
    protected Time hora_recoleccion;
    
    public abstract void CollectorAlgorithm();
    
    
    public void GenerateId(){
    
    }
        
    public void CreateChecksum(){
    
    }
    
    public void VerifyChecksum(){
    
    }
    
    public void OpenFile(){
     
    }
    
    public void CloseFile(){
        
    }
    
    
}
