package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.AlertaController;
import com.seguridadnacional.seguridad.controllers.UsuarioController;
import com.seguridadnacional.seguridad.controllers.UsuarioRolController;
import com.seguridadnacional.seguridad.models.Alerta;
import com.seguridadnacional.seguridad.models.Usuario;
import com.seguridadnacional.seguridad.models.UsuarioRol;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/auth")
public class SvAuth extends HttpServlet {

    private final UsuarioController    usuarioCtrl    = new UsuarioController();
    private final UsuarioRolController usuarioRolCtrl = new UsuarioRolController();
    private final AlertaController     alertaCtrl     = new AlertaController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("logout".equals(action)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
        } else {
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String nombreUsuario = req.getParameter("nombreUsuario");
        String contrasena    = req.getParameter("contrasena");

        try {
            Usuario usuario = usuarioCtrl.login(nombreUsuario, contrasena);

            // Cargar roles del usuario
            List<UsuarioRol> roles = usuarioRolCtrl.listarPorUsuario(usuario.getIdUsuario());
            // Determinar rol principal (primer rol asignado)
            String rolPrincipal = roles.isEmpty() ? "SIN_ROL" : roles.get(0).getRol().getNombre();

            // Cargar alertas no leídas
            List<Alerta> alertasNoLeidas = alertaCtrl.listarNoLeidas(usuario.getIdUsuario());

            HttpSession session = req.getSession(true);
            session.setAttribute("usuarioSesion", usuario);
            session.setAttribute("rolesSesion", roles);
            session.setAttribute("rolPrincipal", rolPrincipal);
            session.setAttribute("alertasNoLeidas", alertasNoLeidas);
            session.setMaxInactiveInterval(30 * 60); // 30 minutos

            resp.sendRedirect(req.getContextPath() + "/dashboard");

        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }
}