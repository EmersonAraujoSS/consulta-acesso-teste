package br.com.avancard.repository;

import br.com.avancard.enttity.Fila;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilaRepository {
    private Connection connection;


    public FilaRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Fila> findByStatus(String status) throws SQLException {
        List<Fila> filas = new ArrayList<>();
        String sql = "SELECT id, cpf, matricula, status,mst,msr,msd,mct,mcr,mcd,situacaoTextBox FROM fila WHERE status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Fila fila = new Fila();
                    fila.setId((long) resultSet.getInt("id"));
                    fila.setCpf(resultSet.getString("cpf"));
                    fila.setMatricula(resultSet.getString("matricula"));
                    fila.setStatus(resultSet.getString("status"));
                    fila.setMst(resultSet.getString("mst"));
                    fila.setMsr(resultSet.getString("msr"));
                    fila.setMsd(resultSet.getString("msd"));
                    fila.setMct(resultSet.getString("mct"));
                    fila.setMcr(resultSet.getString("mcr"));
                    fila.setMcd(resultSet.getString("mcd"));
                    fila.setSituacaoTextBox(resultSet.getString("situacaoTextBox"));
                    filas.add(fila);
                }
            }
        }
        return filas;
    }

    public void save(Fila fila) throws SQLException {
        String sql = "UPDATE fila SET status = ?, mst = ?, msr = ?, msd = ?, mct = ?, mcr = ?, mcd = ?, situacaoTextBox = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fila.getStatus());
            statement.setString(2, fila.getMst());
            statement.setString(3, fila.getMsr());
            statement.setString(4, fila.getMsd());
            statement.setString(5, fila.getMct());
            statement.setString(6, fila.getMcr());
            statement.setString(7, fila.getMcd());
            statement.setString(8, fila.getSituacaoTextBox());
            statement.setInt(9, fila.getId());
            statement.executeUpdate();
        }
    }

}
