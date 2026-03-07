package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.PersonaController;
import com.seguridadnacional.seguridad.models.Persona;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * CRUD de personas.
 * GET  /personas                         -> listar
 * GET  /personas?action=nuevo            -> formulario crear
 * GET  /personas?action=editar&id={id}   -> formulario editar
 * POST /personas?action=crear            -> insertar
 * POST /personas?action=editar           -> actualizar
 * POST /personas?action=eliminar&id={id} -> eliminar
 */
@WebServlet("/personas")
public class SvPersona extends HttpServlet {

    private final PersonaController controller = new PersonaController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("nuevo".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/persona/formulario.jsp").forward(req, resp);
            return;
        }

        if ("editar".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("persona", controller.buscarPorId(id));
            req.getRequestDispatcher("/WEB-INF/views/persona/formulario.jsp").forward(req, resp);
            return;
        }

        List<Persona> lista = controller.listarTodos();
        req.setAttribute("personas", lista);
        req.getRequestDispatcher("/WEB-INF/views/persona/lista.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        switch (action) {
            case "crear":    crear(req, resp);    break;
            case "editar":   editar(req, resp);   break;
            case "eliminar": eliminar(req, resp);  break;
            default:
                resp.sendRedirect(req.getContextPath() + "/personas");
        }
    }

    private void crear(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Persona persona = construirDesdeForm(req, 0);
            controller.crearPersona(persona);
            req.getSession().setAttribute("mensaje", "Persona creada correctamente");
            resp.sendRedirect(req.getContextPath() + "/personas");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/persona/formulario.jsp").forward(req, resp);
        }
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Persona persona = construirDesdeForm(req, id);
            controller.actualizar(persona);
            req.getSession().setAttribute("mensaje", "Persona actualizada correctamente");
            resp.sendRedirect(req.getContextPath() + "/personas");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/persona/formulario.jsp").forward(req, resp);
        }
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            controller.eliminar(id);
            req.getSession().setAttribute("mensaje", "Persona eliminada correctamente");
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/personas");
    }

    private Persona construirDesdeForm(HttpServletRequest req, int id) {
        String nombre   = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String correo   = req.getParameter("correo");
        String telefono = req.getParameter("telefono");

        Persona p = new Persona(nombre, apellido, correo, telefono);
        if (id > 0) p.setIdPersona(id);
        return p;
    }
}