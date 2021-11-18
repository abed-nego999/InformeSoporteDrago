package es.soporte.informes.sigpes.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DAOSigpe
{
    private Connection          conn;

    private static final String INSERT_SIGPE           = "INSERT INTO sigpes.sigpes (id,titulo) VALUES (?,?)";
    private static final String INSERT_ACTUACION_SIGPE = "INSERT INTO sigpes.actuacion_sigpe (mes_contable,id_sigpe,fecha_entrada,fecha_salida,dias,tecnico_drago,funcional_rsi,mal_dirigida,comentario,horas_imputables) VALUES (?,?,?,?,?,?,?,?,?,?)";

    public DAOSigpe()
    {
    }

    public void insertSigpe(TsvSigpe tsvSigpe)
    {
        insertSigpe(tsvSigpe.getId(), tsvSigpe.getTitulo());
        insertActuacionSigpe(tsvSigpe.getMesContable(), tsvSigpe.getId(), tsvSigpe.getFechaEntrada(), tsvSigpe.getFechaSalida(), tsvSigpe.getDias(), tsvSigpe.getTecnicoDrago(), tsvSigpe.getFuncionalRsi(), tsvSigpe.isMalDirigida(), tsvSigpe.getComentario(), tsvSigpe.getHorasImputables());
    }

    private void insertActuacionSigpe(LocalDate mesContable, String idSigpe, LocalDateTime fechaEntrada,
            LocalDateTime fechaSalida, int dias, String tecnicoDrago, String funcionalRsi, boolean malDirigida,
            String comentario, Float horasImputables)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try
        {
            conn = getConnection();
            conn.setAutoCommit(false);
            pstmt = getConnection().prepareStatement(INSERT_ACTUACION_SIGPE);
            pstmt.setDate(1, Date.valueOf(mesContable));
            pstmt.setString(2, idSigpe);
            pstmt.setTimestamp(3, Timestamp.valueOf(fechaEntrada));
            pstmt.setTimestamp(4, Timestamp.valueOf(fechaSalida));
            pstmt.setInt(5, dias);
            pstmt.setString(6, tecnicoDrago);
            pstmt.setString(7, funcionalRsi);
            pstmt.setInt(8, malDirigida ? 0 : 1);
            pstmt.setString(9, comentario);
            if (horasImputables != null)
            {
                pstmt.setFloat(10, horasImputables);
            }
            else
            {
                pstmt.setNull(10, Types.FLOAT);
            }

            int rowCount = pstmt.executeUpdate();

            if (rowCount == 1)
            {
                conn.commit();
            }
            else
            {
                conn.rollback();
                throw new SQLException("Error: El insert de la sigpe " + idSigpe + " con fecha de entrada "
                        + fechaEntrada + " ha afectado a [" + rowCount + "] filas. Se hace ROLLBACK.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                conn.setAutoCommit(true);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            closeAllObjects(conn, pstmt, rs);
        }
    }

    private void insertSigpe(String idSigpe, String titulo)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try
        {
            conn = getConnection();
            conn.setAutoCommit(false);
            pstmt = getConnection().prepareStatement(INSERT_SIGPE);
            pstmt.setString(1, idSigpe);
            pstmt.setString(2, titulo);

            int rowCount = pstmt.executeUpdate();

            if (rowCount == 1)
            {
                conn.commit();
            }
            else
            {
                conn.rollback();
                throw new SQLException("Error: El insert de la sigpe " + idSigpe + " con título " + titulo
                        + " ha afectado a [" + rowCount + "] filas. Se hace ROLLBACK.");
            }
        }
        catch (SQLException e)
        {
            if (!(e instanceof SQLIntegrityConstraintViolationException))
            {
                e.printStackTrace();
            }
        }
        finally
        {
            try
            {
                conn.setAutoCommit(true);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            closeAllObjects(conn, pstmt);
        }
    }

    private Connection getConnection() throws SQLException
    {
        if (conn == null || conn.isClosed())
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sigpes", "root", "root");
        }

        return conn;
    }

    private void closeAllObjects(Connection conn, Statement stmt)
    {
        closeAllObjects(conn, stmt, null);
    }

    private void closeAllObjects(Connection conn, Statement stmt, ResultSet rs)
    {
        if (rs != null)
            try
            {
                rs.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        if (stmt != null)
            try
            {
                stmt.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        if (conn != null)
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
    }
}
