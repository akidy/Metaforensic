/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

/**
 *
 * @author andy737-1
 */
public class ElapsedTime {

    private double T1;
    private double T2;
    private double t1;
    private double t2;
    private double elapsed;

    public void EpalsedTime() {
        T1 = 0;
        T2 = 0;
        t1 = 0;
        t2 = 0;
        elapsed = 0;
    }

    public double getElapsedTime() {
        double r = T2 - T1;
        elapsed = r / 1000000000.0;
        return elapsed;
    }

    public double getElapsedTimeAll() {
        double r = t2 - t1;
        elapsed = r / 1000000000.0;
        return elapsed;
    }

    public void Start() {
        T1 = System.nanoTime();
    }

    public void Stop() {
        T2 = System.nanoTime();
    }

    public void StartAll() {
        t1 = System.nanoTime();
    }

    public void StopAll() {
        t2 = System.nanoTime();
    }
}
