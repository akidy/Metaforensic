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
package Process;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Clase encarga de la conversion de time y date util a time y date sql
 *
 * @author andy737-1
 * @version 1.1
 */
public class DateTime {

    /**
     *
     * @return la fecha actual en formato sql
     */
    public static Date getDate() {
        Calendar cal = Calendar.getInstance();
        Date utilDate = new Date();
        cal.setTime(utilDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime());
        return sqlDate;
    }

    /**
     *
     * @return la hora actual en formato sql
     */
    public static Time getTime() {
        int hora, minutos, segundos;
        Calendar cal = new GregorianCalendar();
        hora = cal.get(Calendar.HOUR_OF_DAY);
        minutos = cal.get(Calendar.MINUTE);
        segundos = cal.get(Calendar.SECOND);
        Time sqlTime = Time.valueOf(hora + ":" + minutos + ":" + segundos);
        return sqlTime;
    }

    /**
     *
     * @return la hora actual con milisegundos en formato string
     */
    public static String getTimeMilli() {
        int hora, minutos, segundos, mili;
        Calendar cal = new GregorianCalendar();
        hora = cal.get(Calendar.HOUR_OF_DAY);
        minutos = cal.get(Calendar.MINUTE);
        segundos = cal.get(Calendar.SECOND);
        mili = cal.get(Calendar.MILLISECOND);
        String sqlTime = (hora + ":" + minutos + ":" + segundos + ":" + mili);
        return sqlTime;
    }
}
