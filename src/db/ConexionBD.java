package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sebas
 */
public class ConexionBD {
    // Configuracion de la base de datos 
    private static final String URL = "jdbc:postgresql://localhost:5432/bd_buscaminas"; //base de datos local
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void guardarPuntuacion(String playerName, int score, String difficulty) {
        // Con este codigo se hacen los inserts a la tabla de la base de datos
        String sql = "INSERT INTO mejores_puntuaciones (nombre_jugador, puntuacion, dificultad) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();//Usamos try catch para probar primero la conexion con la base de datos, si da error tira un error en consola. para depuracion
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.setString(3, difficulty);
            
            pstmt.executeUpdate();
            System.out.println("Puntuacion guardada correctamente");
            
        } catch (SQLException e) {
            System.err.println("Error al guardar la puntuacion " + e.getMessage());
        }
    }
}
