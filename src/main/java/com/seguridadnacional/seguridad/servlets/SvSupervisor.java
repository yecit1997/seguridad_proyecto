package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.*;
import com.seguridadnacional.seguridad.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/supervisores")
public class SvSupervisor extends HttpServlet {

    private final SupervisorController supervisorCtrl = new SupervisorController();
    private final UsuarioRolController usuarioRolCtrl = new UsuarioRolController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("editar".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("supervisor", supervisorCtrl.buscarPorId(id));
            req.getRequestDispatcher("/WEB-INF/views/supervisor/formulario.jsp").forward(req, resp);
        } else if ("nuevo".equals(action)) {
            req.setAttribute("usuariosRol", usuarioRolCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/supervisor/formulario.jsp").forward(req, resp);
        } else {
            req.setAttribute("supervisores", supervisorCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/supervisor/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            switch (action) {
                case "crear" -> {
                    UsuarioRol ur = usuarioRolCtrl.buscarPorId(Integer.parseInt(req.getParameter("usuarioRolId")));
                    String fechaParam = req.getParameter("fechaAscenso");
                    LocalDate fecha = (fechaParam != null && !fechaParam.isBlank()) ? LocalDate.parse(fechaParam) : null;
                    supervisorCtrl.crear(new Supervisor(ur, fecha));
                    req.getSession().setAttribute("mensaje", "Supervisor creado correctamente");
                }
                case "actualizar" -> {
                    UsuarioRol ur = usuarioRolCtrl.buscarPorId(Integer.parseInt(req.getParameter("usuarioRolId")));
                    String fechaParam = req.getParameter("fechaAscenso");
                    LocalDate fecha = (fechaParam != null && !fechaParam.isBlank()) ? LocalDate.parse(fechaParam) : null;
                    supervisorCtrl.actualizar(new Supervisor(ur, fecha));
                    req.getSession().setAttribute("mensaje", "Supervisor actualizado correctamente");
                }
                case "eliminar" -> {
                    supervisorCtrl.eliminar(Integer.parseInt(req.getParameter("id")));
                    req.getSession().setAttribute("mensaje", "Supervisor eliminado correctamente");
                }
            }
        } catch (RuntimeException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/supervisores");
    }
}