package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.*;
import com.seguridadnacional.seguridad.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * CRUD de guardas.
 * GET  /guardas                          -> listar
 * GET  /guardas?action=nuevo             -> formulario crear
 * GET  /guardas?action=editar&id={id}    -> formulario editar
 * GET  /guardas?action=ver&id={id}       -> detalle
 * POST /guardas?action=crear             -> insertar
 * POST /guardas?action=editar            -> actualizar
 * POST /guardas?action=eliminar&id={id}  -> eliminar
 */
@WebServlet("/guardas")
public class SvGuarda extends HttpServlet {

    private final GuardaController      controller      = new GuardaController();
    private final PersonaController     personaCtrl     = new PersonaController();
    private final AreasController       areasCtrl       = new AreasController();
    private final SupervisorController  supervisorCtrl  = new SupervisorController();

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

        if ("ver".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("guarda", controller.buscarPorId(id));
            req.getRequestDispatcher("/WEB-INF/views/guarda/detalle.jsp").forward(req, resp);
            return;
        }

        List<Guarda> lista = controller.listarTodos();
        req.setAttribute("guardas", lista);
        req.getRequestDispatcher("/WEB-INF/views/guarda/lista.jsp").forward(req, resp);
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
                resp.sendRedirect(req.getContextPath() + "/guardas");
        }
    }

    // ─── Crear ────────────────────────────────────────────────────────────────
    private void crear(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Guarda guarda = construirDesdeForm(req, 0);
            controller.crearGuarda(guarda);
            req.getSession().setAttribute("mensaje", "Guarda registrado correctamente");
            resp.sendRedirect(req.getContextPath() + "/guardas");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            cargarFormulario(req, resp, null);
        }
    }

    // ─── Editar ───────────────────────────────────────────────────────────────
    private void editar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Guarda guarda = construirDesdeForm(req, id);
            controller.actualizar(guarda);
            req.getSession().setAttribute("mensaje", "Guarda actualizado correctamente");
            resp.sendRedirect(req.getContextPath() + "/guardas");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            int id = Integer.parseInt(req.getParameter("id"));
            cargarFormulario(req, resp, controller.buscarPorId(id));
        }
    }

    // ─── Eliminar ─────────────────────────────────────────────────────────────
    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            controller.eliminar(id);
            req.getSession().setAttribute("mensaje", "Guarda eliminado correctamente");
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/guardas");
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────
    private void cargarFormulario(HttpServletRequest req, HttpServletResponse resp, Guarda guarda)
            throws ServletException, IOException {
        req.setAttribute("guarda",      guarda);
        req.setAttribute("personas",    personaCtrl.listarTodos());
        req.setAttribute("areas",       areasCtrl.listarTodos());
        req.setAttribute("supervisores", supervisorCtrl.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/guarda/formulario.jsp").forward(req, resp);
    }

    private Guarda construirDesdeForm(HttpServletRequest req, int id) {
        int idPersona    = Integer.parseInt(req.getParameter("idPersona"));
        int idArea       = Integer.parseInt(req.getParameter("idArea"));
        int idSupervisor = Integer.parseInt(req.getParameter("idSupervisor"));

        Persona   persona    = personaCtrl.buscarPorId(idPersona);
        Areas     area       = areasCtrl.buscarPorId(idArea);
        Supervisor supervisor = supervisorCtrl.buscarPorId(idSupervisor);

        Guarda g = new Guarda(persona, area, supervisor);
        if (id > 0) g.setIdGuarda(id);
        return g;
    }
}