package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.*;
import com.seguridadnacional.seguridad.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/dashboard")
public class SvDashboard extends HttpServlet {

    private final ReporteController   reporteCtrl   = new ReporteController();
    private final GuardaController    guardaCtrl    = new GuardaController();
    private final AlertaController    alertaCtrl    = new AlertaController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Usuario u = (Usuario) req.getSession().getAttribute("usuarioSesion");

        req.setAttribute("totalReportes",   reporteCtrl.listarTodos().size());
        req.setAttribute("totalGuardas",    guardaCtrl.listarTodos().size());
        req.setAttribute("alertasNoLeidas", alertaCtrl.listarNoLeidas(u.getIdUsuario()));
        req.setAttribute("ultimosReportes", reporteCtrl.listarPorUsuario(u.getIdUsuario()));

        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}