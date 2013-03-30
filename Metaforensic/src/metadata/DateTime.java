/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author andy737-1
 */
public class DateTime {

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

    public static Time getTime() {
        int hora, minutos, segundos;
        Calendar cal = new GregorianCalendar();
        hora = cal.get(Calendar.HOUR_OF_DAY);
        minutos = cal.get(Calendar.MINUTE);
        segundos = cal.get(Calendar.SECOND);
        Time sqlTime = Time.valueOf(hora + ":" + minutos + ":" + segundos);

        return sqlTime;

    }
}
