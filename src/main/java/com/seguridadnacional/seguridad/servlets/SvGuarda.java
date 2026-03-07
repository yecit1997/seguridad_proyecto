package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.*;
import com.seguridadnacional.seguridad.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/guardas")
public class SvGuarda extends HttpServlet {

    private final GuardaController      guardaCtrl      = new GuardaController();
    private final SupervisorController  supervisorCtrl  = new SupervisorController();
    private final UsuarioRolController  usuarioRolCtrl  = new UsuarioRolController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("editar".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("guarda",      guardaCtrl.buscarPorId(id));
            req.setAttribute("supervisores", supervisorCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/guarda/formulario.jsp").forward(req, resp);
        } else if ("nuevo".equals(action)) {
            req.setAttribute("usuariosRol",  usuarioRolCtrl.listarTodos());
            req.setAttribute("supervisores", supervisorCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/guarda/formulario.jsp").forward(req, resp);
        } else {
            req.setAttribute("guardas", guardaCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/guarda/lista.jsp").forward(req, resp);
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
                    String supParam = req.getParameter("supervisorId");
                    Supervisor sup = (supParam != null && !supParam.isBlank())
                        ? supervisorCtrl.buscarPorId(Integer.parseInt(supParam)) : null;
                    guardaCtrl.crear(new Guarda(ur, req.getParameter("areaAsignada"), sup));
                    req.getSession().setAttribute("mensaje", "Guarda creado correctamente");
                }
                case "actualizar" -> {
                    UsuarioRol ur = usuarioRolCtrl.buscarPorId(Integer.parseInt(req.getParameter("usuarioRolId")));
                    String supParam = req.getParameter("supervisorId");
                    Supervisor sup = (supParam != null && !supParam.isBlank())
                        ? supervisorCtrl.buscarPorId(Integer.parseInt(supParam)) : null;
                    guardaCtrl.actualizar(new Guarda(ur, req.getParameter("areaAsignada"), sup));
                    req.getSession().setAttribute("mensaje", "Guarda actualizado correctamente");
                }
                case "eliminar" -> {
                    guardaCtrl.eliminar(Integer.parseInt(req.getParameter("id")));
                    req.getSession().setAttribute("mensaje", "Guarda eliminado correctamente");
                }
            }
        } catch (RuntimeException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/guardas");
    }
}