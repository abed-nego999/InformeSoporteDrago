package es.soporte.informes.sigpes.calendario;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarioMadrid2021 extends A_CalendarioLaboral
{

    @Override
    protected Map<DayOfWeek, HorarioLaboral> getHorarioLaboralSemanal()
    {
        Map<DayOfWeek, HorarioLaboral> horarioLaboral = new HashMap<DayOfWeek, HorarioLaboral>();

        HorarioLaboral horarioDiario = new HorarioLaboral(LocalTime.of(9, 0), LocalTime.of(17, 30));
        HorarioLaboral horarioViernes = new HorarioLaboral(LocalTime.of(8, 0), LocalTime.of(15, 0));

        horarioLaboral.put(DayOfWeek.MONDAY, horarioDiario);
        horarioLaboral.put(DayOfWeek.TUESDAY, horarioDiario);
        horarioLaboral.put(DayOfWeek.WEDNESDAY, horarioDiario);
        horarioLaboral.put(DayOfWeek.THURSDAY, horarioDiario);
        horarioLaboral.put(DayOfWeek.FRIDAY, horarioViernes);

        return horarioLaboral;
    }

    @Override
    protected List<LocalDate> getHolidays()
    {
        List<LocalDate> holidays = new ArrayList<LocalDate>();

        holidays.add(LocalDate.of(2021, Month.JANUARY, 1));
        holidays.add(LocalDate.of(2021, Month.JANUARY, 6));
        holidays.add(LocalDate.of(2021, Month.MARCH, 19));
        holidays.add(LocalDate.of(2021, Month.APRIL, 1));
        holidays.add(LocalDate.of(2021, Month.APRIL, 2));
        holidays.add(LocalDate.of(2021, Month.MAY, 1));
        holidays.add(LocalDate.of(2021, Month.MAY, 3));
        holidays.add(LocalDate.of(2021, Month.MAY, 15));
        holidays.add(LocalDate.of(2021, Month.OCTOBER, 12));
        holidays.add(LocalDate.of(2021, Month.NOVEMBER, 1));
        holidays.add(LocalDate.of(2021, Month.NOVEMBER, 9));
        holidays.add(LocalDate.of(2021, Month.DECEMBER, 6));
        holidays.add(LocalDate.of(2021, Month.DECEMBER, 8));
        holidays.add(LocalDate.of(2021, Month.DECEMBER, 25));

        return holidays;
    }

    @Override
    protected List<DayOfWeek> getWeekendDays()
    {
        List<DayOfWeek> weekendDays = new ArrayList<DayOfWeek>();

        weekendDays.add(DayOfWeek.SATURDAY);
        weekendDays.add(DayOfWeek.SUNDAY);

        return weekendDays;
    }

    @Override
    protected int getHorasAlDia()
    {
        return 8;
    }

}
