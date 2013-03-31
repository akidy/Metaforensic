/*
 * *****************************************************************************
 *    
 * Metaforensic version 1.0 - Análisis forense de metadatos en archivos
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

/**
 * Clase que calcula el tiempo trasncurrido entre operaciones y totaliza el
 * tiempo de todas las operaciones
 *
 * @author andy737-1
 * @version 1.0
 */
public class ElapsedTime {

    private double T1;
    private double T2;
    private double t1;
    private double t2;
    private double elapsed;

    /**
     * Inicaliza variables
     */
    public void EpalsedTime() {
        T1 = 0;
        T2 = 0;
        t1 = 0;
        t2 = 0;
        elapsed = 0;
    }

    /**
     *
     * @return el tiempo de una operacion individual
     */
    public double getElapsedTime() {
        double r = T2 - T1;
        elapsed = r / 1000000000.0;
        return elapsed;
    }

    /**
     *
     * @return el tiempo de todas las operaciones
     */
    public double getElapsedTimeAll() {
        double r = t2 - t1;
        elapsed = r / 1000000000.0;
        return elapsed;
    }

    /**
     * Recoge el la hora de inicio en nanosegundo (individual)
     */
    public void Start() {
        T1 = System.nanoTime();
    }

    /**
     * Recoge la hora final en nanosegundos (individual)
     */
    public void Stop() {
        T2 = System.nanoTime();
    }

    /**
     * Recoge la hora de inicio en nanosegundos (all)
     */
    public void StartAll() {
        t1 = System.nanoTime();
    }

    /**
     * Recoge la hora final en nanosegundos (all)
     */
    public void StopAll() {
        t2 = System.nanoTime();
    }
}
