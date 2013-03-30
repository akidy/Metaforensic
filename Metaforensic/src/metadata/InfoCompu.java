/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

/**
 *
 * @author andy737-1
 */
public class InfoCompu {

    public static String getPCName() {
        return System.getenv("COMPUTERNAME");
    }

    public static String getUser() {
        return System.getProperty("user.name");
    }

    public static String getSO() {
        return System.getProperty("os.name");
    }
    
    public static String getSOVer() {
        return System.getProperty("os.version");
    }
    public static String getSOAq() {
        return System.getProperty("os.arch");
    }
   
}
