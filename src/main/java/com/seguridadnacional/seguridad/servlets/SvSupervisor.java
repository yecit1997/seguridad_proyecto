package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.PersonaController;
import com.seguridadnacional.seguridad.controllers.SupervisorController;
import com.seguridadnacional.seguridad.models.Persona;
import com.seguridadnacional.seguridad.models.Supervisor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * CRUD de supervisores.
 * GET  /supervisores                         -> listar
 * GET  /supervisores?action=nuevo            -> formulario crear
 * GET  /supervisores?action=editar&id={id}   -> formulario editar
 * POST /supervisores?action=crear            -> insertar
 * POST /supervisores?action=editar           -> actualizar
 * POST /supervisores?action=eliminar&id={id} -> eliminar
 */
@WebServlet("/supervisores")
public class SvSupervisor extends HttpServlet {

    private final SupervisorController controller   = new SupervisorController();
    private final PersonaController    personaCtrl  = new PersonaController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("nuevo".equals(action)) {
            cargarFormulario(req, resp, null);
            return;
        }

        if ("editar".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            cargarFormulario(req, resp, controller.buscarPorId(id));
            return;
        }

        List<Supervisor> lista = controller.listarTodos();
        req.setAttribute("supervisores", lista);
        req.getRequestDispatcher("/WEB-INF/views/supervisor/lista.jsp").forward(req, resp);
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
                resp.sendRedirect(req.getContextPath() + "/supervisores");
        }
    }

    private void crear(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Supervisor supervisor = construirDesdeForm(req, 0);
            controller.crear(supervisor);
            req.getSession().setAttribute("mensaje", "Supervisor creado correctamente");
            resp.sendRedirect(req.getContextPath() + "/supervisores");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            cargarFormulario(req, resp, null);
        }
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Supervisor supervisor = construirDesdeForm(req, id);
            controller.actualizar(supervisor);
            req.getSession().setAttribute("mensaje", "Supervisor actualizado correctamente");
            resp.sendRedirect(req.getContextPath() + "/supervisores");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            int id = Integer.parseInt(req.getParameter("id"));
            cargarFormulario(req, resp, controller.buscarPorId(id));
        }
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            controller.eliminar(id);
            req.getSession().setAttribute("mensaje", "Supervisor eliminado correctamente");
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/supervisores");
    }

    private void cargarFormulario(HttpServletRequest req, HttpServletResponse resp, Supervisor supervisor)
            throws ServletException, IOException {
        req.setAttribute("supervisor", supervisor);
        req.setAttribute("personas",   personaCtrl.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/supervisor/formulario.jsp").forward(req, resp);
    }

    private Supervisor construirDesdeForm(HttpServletRequest req, int id) {
        int    idPersona = Integer.parseInt(req.getParameter("idPersona"));
        Persona persona  = personaCtrl.buscarPorId(idPersona);
        Supervisor s     = new Supervisor(persona);
        if (id > 0) s.setIdSupervisor(id);
        return s;
    }
}