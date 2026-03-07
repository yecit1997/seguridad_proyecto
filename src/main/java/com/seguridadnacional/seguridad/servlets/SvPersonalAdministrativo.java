package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.*;
import com.seguridadnacional.seguridad.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/personal")
public class SvPersonalAdministrativo extends HttpServlet {

    private final PersonalAdministrativoController personalCtrl = new PersonalAdministrativoController();
    private final UsuarioRolController usuarioRolCtrl = new UsuarioRolController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("editar".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("personal", personalCtrl.buscarPorId(id));
            req.getRequestDispatcher("/WEB-INF/views/personal/formulario.jsp").forward(req, resp);
        } else if ("nuevo".equals(action)) {
            req.setAttribute("usuariosRol", usuarioRolCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/personal/formulario.jsp").forward(req, resp);
        } else {
            req.setAttribute("personalList", personalCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/personal/lista.jsp").forward(req, resp);
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
                    personalCtrl.crear(new PersonalAdministrativo(ur, req.getParameter("cargo")));
                    req.getSession().setAttribute("mensaje", "Personal creado correctamente");
                }
                case "actualizar" -> {
                    UsuarioRol ur = usuarioRolCtrl.buscarPorId(Integer.parseInt(req.getParameter("usuarioRolId")));
                    personalCtrl.actualizar(new PersonalAdministrativo(ur, req.getParameter("cargo")));
                    req.getSession().setAttribute("mensaje", "Personal actualizado correctamente");
                }
                case "eliminar" -> {
                    personalCtrl.eliminar(Integer.parseInt(req.getParameter("id")));
                    req.getSession().setAttribute("mensaje", "Personal eliminado correctamente");
                }
            }
        } catch (RuntimeException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/personal");
    }
}