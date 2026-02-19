package com.ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/InsertarUsuario")
public class InsertarUsuarioServlet extends HttpServlet {

    // GELMAN OJO AQUÍ: En Docker usamos el nombre del servicio 'db', no 'localhost'
    private static final String URL = "jdbc:mariadb://db:3306/mi_bd";
    private static final String USER = "root";
    private static final String PASS = "rootpassword";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String nombre = request.getParameter("nombre");

        try (PrintWriter out = response.getWriter()) {
            // Cargar Driver
            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                out.println("Error: No encuentro el driver. " + e.getMessage());
                return;
            }

            // Conectar e Insertar
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                String sql = "INSERT INTO usuarios (nombre) VALUES (?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nombre);
                    int filas = stmt.executeUpdate();
                    
                    out.println("<!DOCTYPE html><html><body>");
                    if (filas > 0) {
                        out.println("<h2 style='color:green'>¡El usuario '" + nombre + "' se guardó!</h2>");
                    } else {
                        out.println("<h2 style='color:red'>Falló la vuelta.</h2>");
                    }
                    out.println("<a href='index.html'>Volver</a></body></html>");
                }
            } catch (SQLException e) {
                out.println("Error de BD: " + e.getMessage());
                e.printStackTrace(out);
            }
        }
    }
}