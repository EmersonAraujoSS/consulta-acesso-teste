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
        String sql = "SELECT id, cpf, matricula, status FROM fila WHERE status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Fila fila = new Fila();
                    fila.setId((long) resultSet.getInt("id"));
                    fila.setCpf(resultSet.getString("cpf"));
                    fila.setMatricula(resultSet.getString("matricula"));
                    fila.setStatus(resultSet.getString("status"));
                    filas.add(fila);
                }
            }
        }
        return filas;
    }

    public void save(Fila fila) throws SQLException {
        String sql = "UPDATE fila SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fila.getStatus());
            statement.setInt(2, fila.getId());
            statement.executeUpdate();
        }
    }

}
