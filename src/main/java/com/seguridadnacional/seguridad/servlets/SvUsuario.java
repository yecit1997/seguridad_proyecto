package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.PersonaController;
import com.seguridadnacional.seguridad.controllers.UsuarioController;
import com.seguridadnacional.seguridad.models.Persona;
import com.seguridadnacional.seguridad.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/usuarios")
public class SvUsuario extends HttpServlet {

    private final UsuarioController usuarioCtrl = new UsuarioController();
    private final PersonaController personaCtrl = new PersonaController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("editar".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("usuario",  usuarioCtrl.buscarPorId(id));
            req.setAttribute("personas", personaCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/usuario/formulario.jsp").forward(req, resp);
        } else if ("nuevo".equals(action)) {
            req.setAttribute("personas", personaCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/usuario/formulario.jsp").forward(req, resp);
        } else {
            req.setAttribute("usuarios", usuarioCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/usuario/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            switch (action) {
                case "crear" -> {
                    Persona persona = personaCtrl.buscarPorId(Integer.parseInt(req.getParameter("personaId")));
                    Usuario u = new Usuario(
                        req.getParameter("nombreUsuario"),
                        req.getParameter("contrasena"),
                        "1".equals(req.getParameter("status")),
                        persona
                    );
                    usuarioCtrl.crear(u);
                    req.getSession().setAttribute("mensaje", "Usuario creado correctamente");
                }
                case "actualizar" -> {
                    Persona persona = personaCtrl.buscarPorId(Integer.parseInt(req.getParameter("personaId")));
                    Usuario u = new Usuario(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("nombreUsuario"),
                        req.getParameter("contrasena"),
                        "1".equals(req.getParameter("status")),
                        persona
                    );
                    usuarioCtrl.actualizar(u);
                    req.getSession().setAttribute("mensaje", "Usuario actualizado correctamente");
                }
                case "eliminar" -> {
                    usuarioCtrl.eliminar(Integer.parseInt(req.getParameter("id")));
                    req.getSession().setAttribute("mensaje", "Usuario eliminado correctamente");
                }
            }
        } catch (RuntimeException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/usuarios");
    }
}