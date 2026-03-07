package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.PersonaController;
import com.seguridadnacional.seguridad.controllers.PersonalAdministrativoController;
import com.seguridadnacional.seguridad.models.Persona;
import com.seguridadnacional.seguridad.models.PersonalAdministrativo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * CRUD de personal administrativo.
 * GET  /personal                         -> listar
 * GET  /personal?action=nuevo            -> formulario crear
 * GET  /personal?action=editar&id={id}   -> formulario editar
 * POST /personal?action=crear            -> insertar
 * POST /personal?action=editar           -> actualizar
 * POST /personal?action=eliminar&id={id} -> eliminar
 */
@WebServlet("/personal")
public class SvPersonalAdministrativo extends HttpServlet {

    private final PersonalAdministrativoController controller  = new PersonalAdministrativoController();
    private final PersonaController                personaCtrl = new PersonaController();

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

        List<PersonalAdministrativo> lista = controller.listarTodos();
        req.setAttribute("personalList", lista);
        req.getRequestDispatcher("/WEB-INF/views/personal/lista.jsp").forward(req, resp);
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
                resp.sendRedirect(req.getContextPath() + "/personal");
        }
    }

    private void crear(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            PersonalAdministrativo pa = construirDesdeForm(req, 0);
            controller.crear(pa);
            req.getSession().setAttribute("mensaje", "Personal administrativo creado correctamente");
            resp.sendRedirect(req.getContextPath() + "/personal");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            cargarFormulario(req, resp, null);
        }
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            PersonalAdministrativo pa = construirDesdeForm(req, id);
            controller.actualizar(pa);
            req.getSession().setAttribute("mensaje", "Personal administrativo actualizado correctamente");
            resp.sendRedirect(req.getContextPath() + "/personal");
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
            req.getSession().setAttribute("mensaje", "Personal administrativo eliminado correctamente");
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/personal");
    }

    private void cargarFormulario(HttpServletRequest req, HttpServletResponse resp,
                                  PersonalAdministrativo pa)
            throws ServletException, IOException {
        req.setAttribute("personal",  pa);
        req.setAttribute("personas",  personaCtrl.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/personal/formulario.jsp").forward(req, resp);
    }

    private PersonalAdministrativo construirDesdeForm(HttpServletRequest req, int id) {
        String cargo     = req.getParameter("cargo");
        int    idPersona = Integer.parseInt(req.getParameter("idPersona"));
        Persona persona  = personaCtrl.buscarPorId(idPersona);

        PersonalAdministrativo pa = new PersonalAdministrativo(cargo, persona);
        if (id > 0) pa.setIdPersonalAdministrativo(id);
        return pa;
    }
}