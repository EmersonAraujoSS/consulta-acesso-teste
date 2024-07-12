package br.com.avancard.application;

import br.com.avancard.repository.FilaRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class main {

    private static final String DB_URL = "jdbc:sqlserver://FNXAMDB01;databaseName=DB_FUPRES01_ARBI_HOM;user=dev;password=dev;encrypt=true;trustServerCertificate=true";

    public static void main(String[] args) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                FilaRepository filaRepository = new FilaRepository(connection);
                checkSpot checkSpot = new checkSpot(filaRepository);
                checkSpot.processarFila();
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Agendar a tarefa para rodar a cada 1 minuto (60 segundos)
        executor.scheduleAtFixedRate(task, 0, 60, TimeUnit.SECONDS);

        //hook para encerrar o executor ao finalizar a aplicação
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }));
    }
}
