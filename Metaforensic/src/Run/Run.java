package Run;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * 
 */
/**
 *
 * @author andy737-1
 */
public class Run {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        splash sp = new splash();
        sp.setVisible(true);
        sp.LoadProgressBar();             
        sp.dispose();

    }
}
