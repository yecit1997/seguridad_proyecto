package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.UsuarioController;
import com.seguridadnacional.seguridad.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Maneja autenticación: login y logout.
 * GET  /auth  -> redirige a login.jsp
 * POST /auth  -> procesa credenciales
 * POST /auth?action=logout -> cierra sesión
 */
@WebServlet("/auth")
public class SvAuth extends HttpServlet {

    private final UsuarioController controller = new UsuarioController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Si ya tiene sesión activa, va al dashboard
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("usuarioSesion") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("logout".equals(action)) {
            cerrarSesion(req, resp);
            return;
        }

        iniciarSesion(req, resp);
    }

    // ─── Login ────────────────────────────────────────────────────────────────
    private void iniciarSesion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nombreUsuario = req.getParameter("nombreUsuario");
        String contrasena    = req.getParameter("contrasena");

        if (esVacio(nombreUsuario) || esVacio(contrasena)) {
            req.setAttribute("error", "Usuario y contraseña son obligatorios");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
            return;
        }

        try {
            Usuario usuario = controller.login(nombreUsuario.trim(), contrasena.trim());

            HttpSession session = req.getSession(true);
            session.setAttribute("usuarioSesion", usuario);
            session.setAttribute("rolSesion", usuario.getRolUsuario().getNombreRolUsuario());
            session.setMaxInactiveInterval(30 * 60); // 30 minutos

            resp.sendRedirect(req.getContextPath() + "/dashboard");

        } catch (RuntimeException e) {
            req.setAttribute("error", "Credenciales incorrectas. Intente de nuevo.");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }

    // ─── Logout ───────────────────────────────────────────────────────────────
    private void cerrarSesion(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/auth");
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}