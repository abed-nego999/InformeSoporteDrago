package es.soporte.informes.sigpes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import es.soporte.informes.sigpes.calendario.A_CalendarioLaboral;
import es.soporte.informes.sigpes.calendario.CalendarioMadrid2021;
import es.soporte.informes.sigpes.db.DAOSigpe;
import es.soporte.informes.sigpes.db.TsvSigpe;

public class GeneraInformes
{
    private static A_CalendarioLaboral calendario = new CalendarioMadrid2021();

    private File                       tsvFile;
    private Date                       mesContable;
    private DAOSigpe                   daoSigpe   = new DAOSigpe();

    public static void main(String[] args) throws Exception
    {
        for (String arg : args)
        {
            System.out.println(arg);
        }

        Date mesContable = new SimpleDateFormat("dd/MM/yyyy").parse("01/" + args[0]);
        File tsvFile = new File(args[1]);

        if (tsvFile.exists())
        {
            GeneraInformes generaInformes = new GeneraInformes(mesContable, tsvFile);
            generaInformes.insertInfo();
        }
    }

    public GeneraInformes(Date mesContable, File tsvFile)
    {
        this.tsvFile = tsvFile;
        this.mesContable = mesContable;
    }

    private void insertInfo() throws FileNotFoundException, IOException
    {
        List<TsvSigpe> sigpesFichero = new ArrayList<TsvSigpe>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tsvFile), "UTF-8")))
        {
            // Obtiene las líneas del TSV
            String line;
            while ((line = br.readLine()) != null)
            {
                try
                {
                    // Obtiene los campos de la línea
                    List<String> lineFields = Arrays.asList(line.split("\t", -1));

                    // Convierte el mes contable en LocalDate
                    LocalDate mesContable = this.mesContable.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    // Asigna cada campo a una variable
                    String id = getStringValue(lineFields.get(0));
                    String titulo = getStringValue(lineFields.get(2));
                    LocalDateTime fechaEntrada = getLocalDateTimeValue(lineFields.get(3));
                    LocalDateTime fechaSalida = getLocalDateTimeValue(lineFields.get(4));
                    int dias = getDias(fechaEntrada, fechaSalida);
                    String tecnicoDrago = getStringValue(lineFields.get(5));
                    String funcionalRsi = getStringValue(lineFields.get(6));
                    boolean malDirigida = getBooleanValue(lineFields.get(7));
                    String comentario = getStringValue(lineFields.get(9));
                    Float horasImputables = getFloatValue(lineFields.get(10));

                    if (id != null && mesContable != null && fechaEntrada != null)
                    {
                        // Añade la nueva sigpe al listado
                        System.out.println("SIGPE " + id + " se inserta con " + dias + " dias de duración y "
                                + (horasImputables == null ? 0 : horasImputables) + "h imputables");
                        sigpesFichero.add(new TsvSigpe(mesContable, id, titulo, fechaEntrada, fechaSalida, dias, tecnicoDrago, funcionalRsi, malDirigida, comentario, horasImputables));
                    }
                }
                catch (Exception e)
                {
                    System.out.println("ERROR: Sucedió un error al procesar la siguiente línea del fichero \"" + tsvFile
                            + "\": " + line);
                    e.printStackTrace();
                }
            }
        }

        System.out.println("TRAZA");

        for (TsvSigpe sigpe : sigpesFichero)
        {
            try
            {
                daoSigpe.insertSigpe(sigpe);
                System.out.println("CORRECTO: Al insertar en BBDD el sigpe: " + sigpe);
            }
            catch (Exception e)
            {
                System.out.println("ERROR: Al insertar en BBDD el sigpe: " + sigpe);
                e.printStackTrace();
            }
        }
    }

    private int getDias(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) throws Exception
    {
        if (fechaEntrada.isAfter(fechaSalida))
        {
            throw new Exception("La fecha de entrada del SIGPE es posterior a la de salida");
        }

        return calendario.getDiasEntreFechas(fechaEntrada, fechaSalida);
    }

    private String getStringValue(String field)
    {
        field = field.trim();

        return field.isEmpty() ? null : field;
    }

    private LocalDateTime getLocalDateTimeValue(String field) throws DateTimeParseException
    {
        field = field.trim();

        return field.isEmpty() ? null : LocalDateTime.parse(field, TsvSigpe.DATE_TIME_FORMATTER);
    }

    private Float getFloatValue(String field) throws NumberFormatException
    {
        field = field.trim();

        return field.isEmpty() ? null : new Float(field.trim());
    }

    private boolean getBooleanValue(String field)
    {
        field = field.trim().toLowerCase().replaceAll("[íìïî]", "i");

        return "si".equals(field);
    }
}
