package es.soporte.informes.sigpes.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TsvSigpe
{
    public static DateTimeFormatter DATE_FORMATTER      = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private LocalDate               mesContable;
    private String                  id;
    private String                  titulo;
    private LocalDateTime           fechaEntrada;
    private LocalDateTime           fechaSalida;
    private int                     dias;
    private String                  tecnicoDrago;
    private String                  funcionalRsi;
    private boolean                 malDirigida;
    private String                  comentario;
    private Float                   horasImputables;

    public TsvSigpe()
    {
        super();
    }

    public TsvSigpe(LocalDate mesContable, String id, String titulo, LocalDateTime fechaEntrada,
            LocalDateTime fechaSalida, int dias, String tecnicoDrago, String funcionalRsi, boolean malDirigida,
            String comentario, Float horasImputables)
    {
        super();
        this.mesContable = mesContable;
        this.id = id;
        this.titulo = titulo;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.dias = dias;
        this.tecnicoDrago = tecnicoDrago;
        this.funcionalRsi = funcionalRsi;
        this.malDirigida = malDirigida;
        this.comentario = comentario;
        this.horasImputables = horasImputables;
    }

    public LocalDate getMesContable()
    {
        return mesContable;
    }

    public void setMesContable(LocalDate mesContable)
    {
        this.mesContable = mesContable;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public LocalDateTime getFechaEntrada()
    {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada)
    {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida()
    {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida)
    {
        this.fechaSalida = fechaSalida;
    }

    public int getDias()
    {
        return dias;
    }

    public void setDias(int dias)
    {
        this.dias = dias;
    }

    public String getTecnicoDrago()
    {
        return tecnicoDrago;
    }

    public void setTecnicoDrago(String tecnicoDrago)
    {
        this.tecnicoDrago = tecnicoDrago;
    }

    public String getFuncionalRsi()
    {
        return funcionalRsi;
    }

    public void setFuncionalRsi(String funcionalRsi)
    {
        this.funcionalRsi = funcionalRsi;
    }

    public boolean isMalDirigida()
    {
        return malDirigida;
    }

    public void setMalDirigida(boolean malDirigida)
    {
        this.malDirigida = malDirigida;
    }

    public String getComentario()
    {
        return comentario;
    }

    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }

    public Float getHorasImputables()
    {
        return horasImputables;
    }

    public void setHorasImputables(Float horasImputables)
    {
        this.horasImputables = horasImputables;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("\"mesContable\": \"").append(mesContable.format(DATE_FORMATTER)).append("\", ");
        sb.append("\"id\": \"").append(id).append("\", ");
        sb.append("\"titulo\": \"").append(titulo).append("\", ");
        sb.append("\"fechaEntrada\": \"").append(fechaEntrada.format(DATE_TIME_FORMATTER)).append("\", ");
        sb.append("\"fechaSalida\": \"").append(fechaSalida.format(DATE_TIME_FORMATTER)).append("\", ");
        sb.append("\"dias\": \"").append(String.valueOf(dias)).append("\", ");
        sb.append("\"tecnicoDrago\": \"").append(tecnicoDrago).append("\", ");
        sb.append("\"funcionalRsi\": \"").append(funcionalRsi).append("\", ");
        sb.append("\"malDirigida\": \"").append(String.valueOf(malDirigida)).append("\", ");
        sb.append("\"comentario\": \"").append(comentario).append("\", ");
        sb.append("\"horasImputables\": \"").append(String.valueOf(horasImputables)).append("\"");
        sb.append("}");

        return sb.toString();
    }
}
