/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import File.FindFiles;
import factory.Collector;
import factory.CollectorGUI;

/**
 *
 * @author andy737-1
 */
public class RunnableViewer implements Runnable {

    private CollectorGUI gui;
    private Collector values;

    public RunnableViewer(CollectorGUI gui, Collector values) {
        this.gui = gui;
        this.values = values;
    }

    @Override
    public void run() {
        FindFiles ff = new FindFiles(gui);
        ff.setValues(values);
        ff.ActionPerformed();
    }
}
