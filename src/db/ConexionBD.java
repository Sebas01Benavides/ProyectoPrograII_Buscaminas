/*
 * Esta clase se encarga de toda la lógica de conexión y
 * operación con la base de datos, en este caso para PostgreSQL.
 */
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
    // Configuración de la base de datos 
    private static final String URL = "jdbc:postgresql://localhost:5432/bd_buscaminas"; // URL de la base de datos.
    private static final String USER = "postgres"; // Usuario de la base de datos.
    private static final String PASSWORD = "admin"; // Contraseña del usuario.

    /*
     * Método estático para obtener una conexión a la base de datos.
     * Lanza una SQLException si la conexión falla.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /*
     * Guarda la puntuación de un jugador en la tabla 'mejores_puntuaciones'.
     * Utiliza un PreparedStatement para prevenir inyecciones SQL.
     */
    public static void guardarPuntuacion(String playerName, int score, String difficulty) {
        // SQL para insertar una nueva fila.
        String sql = "INSERT INTO mejores_puntuaciones (nombre_jugador, puntuacion, dificultad) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection(); // Intenta establecer una conexión.
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Asigna los valores a los placeholders (?) del PreparedStatement.
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.setString(3, difficulty);
            
            // Ejecuta la consulta.
            pstmt.executeUpdate();
            System.out.println("Puntuacion guardada correctamente");
            
        } catch (SQLException e) { // Captura cualquier error de SQL.
            System.err.println("Error al guardar la puntuacion " + e.getMessage());
        }
    }
}
