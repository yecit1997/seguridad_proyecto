package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.PersonaController;
import com.seguridadnacional.seguridad.models.Persona;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/personas")
public class SvPersona extends HttpServlet {

    private final PersonaController ctrl = new PersonaController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("editar".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("persona", ctrl.buscarPorId(id));
            req.getRequestDispatcher("/WEB-INF/views/persona/formulario.jsp").forward(req, resp);
        } else {
            req.setAttribute("personas", ctrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/persona/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            switch (action) {
                case "crear" -> {
                    Persona p = new Persona(
                        req.getParameter("dni"),
                        req.getParameter("nombre"),
                        req.getParameter("apellido"),
                        req.getParameter("correo"),
                        req.getParameter("telefono")
                    );
                    ctrl.crear(p);
                    req.getSession().setAttribute("mensaje", "Persona creada correctamente");
                }
                case "actualizar" -> {
                    Persona p = new Persona(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("dni"),
                        req.getParameter("nombre"),
                        req.getParameter("apellido"),
                        req.getParameter("correo"),
                        req.getParameter("telefono")
                    );
                    ctrl.actualizar(p);
                    req.getSession().setAttribute("mensaje", "Persona actualizada correctamente");
                }
                case "eliminar" -> {
                    ctrl.eliminar(Integer.parseInt(req.getParameter("id")));
                    req.getSession().setAttribute("mensaje", "Persona eliminada correctamente");
                }
            }
        } catch (RuntimeException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/personas");
    }
}