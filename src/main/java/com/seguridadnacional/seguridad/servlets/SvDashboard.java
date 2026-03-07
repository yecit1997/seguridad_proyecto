package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.ReporteController;
import com.seguridadnacional.seguridad.controllers.GuardaController;
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
 * Dashboard principal — carga resumen según el rol del usuario en sesión.
 * GET /dashboard
 */
@WebServlet("/dashboard")
public class SvDashboard extends HttpServlet {

    private final ReporteController  reporteCtrl = new ReporteController();
    private final GuardaController   guardaCtrl  = new GuardaController();
    private final UsuarioController  usuarioCtrl = new UsuarioController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // Protección: si no hay sesión activa, redirigir al login
        if (session == null || session.getAttribute("usuarioSesion") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");
        String  rol     = (String)  session.getAttribute("rolSesion");

        // Cargar datos según el rol
        switch (rol.toUpperCase()) {
            case "GUARDA":
                // Solo ve sus propios reportes
                req.setAttribute("misReportes",
                    reporteCtrl.listarPorUsuario(usuario.getIdUsuario()));
                break;

            case "SUPERVISOR":
                // Ve todos los reportes y su lista de guardas
                req.setAttribute("todosReportes", reporteCtrl.listarTodos());
                req.setAttribute("misGuardas",
                    guardaCtrl.listarPorSupervisor(
                        Integer.parseInt(req.getParameter("idSupervisor") != null
                            ? req.getParameter("idSupervisor") : "0")));
                break;

            case "ADMIN":
            case "PERSONAL_ADMINISTRATIVO":
                // Vista completa
                req.setAttribute("todosReportes", reporteCtrl.listarTodos());
                req.setAttribute("totalUsuarios", usuarioCtrl.listarTodos().size());
                req.setAttribute("totalGuardas",  guardaCtrl.listarTodos().size());
                break;

            default:
                req.setAttribute("todosReportes", reporteCtrl.listarTodos());
                break;
        }

        req.setAttribute("usuario", usuario);
        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}