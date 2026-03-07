package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.AlertaController;
import com.seguridadnacional.seguridad.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/alertas")
public class SvAlerta extends HttpServlet {

    private final AlertaController ctrl = new AlertaController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Usuario u = (Usuario) req.getSession().getAttribute("usuarioSesion");
        req.setAttribute("alertas", ctrl.listarPorUsuario(u.getIdUsuario()));
        req.getRequestDispatcher("/WEB-INF/views/alerta/lista.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        Usuario u = (Usuario) req.getSession().getAttribute("usuarioSesion");
        try {
            switch (action) {
                case "marcarLeida" -> ctrl.marcarLeida(Integer.parseInt(req.getParameter("id")));
                case "eliminar"    -> ctrl.eliminar(Integer.parseInt(req.getParameter("id")));
            }
            // Actualizar contador en sesión
            req.getSession().setAttribute("alertasNoLeidas", ctrl.listarNoLeidas(u.getIdUsuario()));
        } catch (RuntimeException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/alertas");
    }
}