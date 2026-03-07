package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.*;
import com.seguridadnacional.seguridad.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Gestión completa de reportes.
 * GET  /reportes                              -> listar (con filtros opcionales)
 * GET  /reportes?action=nuevo                 -> formulario crear
 * GET  /reportes?action=ver&id={id}           -> detalle reporte
 * GET  /reportes?action=editar&id={id}        -> formulario editar
 * POST /reportes?action=crear                 -> insertar
 * POST /reportes?action=editar                -> actualizar
 * POST /reportes?action=cambiarStatus         -> cambiar solo el status
 * POST /reportes?action=eliminar&id={id}      -> eliminar
 */
@WebServlet("/reportes")
public class SvReporte extends HttpServlet {

    private final ReporteController       controller     = new ReporteController();
    private final UsuarioController       usuarioCtrl    = new UsuarioController();
    private final TipoReporteController   tipoCtrl       = new TipoReporteController();
    private final StatusReporteController statusCtrl     = new StatusReporteController();

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
            req.setAttribute("reporte",  controller.buscarPorId(id));
            req.setAttribute("estados",  statusCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/reporte/detalle.jsp").forward(req, resp);
            return;
        }

        // Listar con filtros opcionales
        listar(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        switch (action) {
            case "crear":         crear(req, resp);         break;
            case "editar":        editar(req, resp);        break;
            case "cambiarStatus": cambiarStatus(req, resp); break;
            case "eliminar":      eliminar(req, resp);      break;
            default:
                resp.sendRedirect(req.getContextPath() + "/reportes");
        }
    }

    // ─── Listar con filtros ───────────────────────────────────────────────────
    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String filtroTipo   = req.getParameter("filtroTipo");
        String filtroStatus = req.getParameter("filtroStatus");

        List<Reporte> lista;

        if (filtroTipo != null && !filtroTipo.isEmpty()) {
            lista = controller.listarPorTipo(Integer.parseInt(filtroTipo));
        } else if (filtroStatus != null && !filtroStatus.isEmpty()) {
            lista = controller.listarPorStatus(Integer.parseInt(filtroStatus));
        } else {
            // Guardas y personal solo ven sus propios reportes
            HttpSession session = req.getSession(false);
            Usuario usuarioSesion = (session != null) ? (Usuario) session.getAttribute("usuarioSesion") : null;
            String rolSesion      = (session != null) ? (String)  session.getAttribute("rolSesion")     : null;

            if (usuarioSesion != null && "GUARDA".equalsIgnoreCase(rolSesion)) {
                lista = controller.listarPorUsuario(usuarioSesion.getIdUsuario());
            } else {
                lista = controller.listarTodos();
            }
        }

        req.setAttribute("reportes", lista);
        req.setAttribute("tipos",    tipoCtrl.listarTodos());
        req.setAttribute("estados",  statusCtrl.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/reporte/lista.jsp").forward(req, resp);
    }

    // ─── Crear ────────────────────────────────────────────────────────────────
    private void crear(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Reporte reporte = construirDesdeForm(req, 0);
            controller.crearReporte(reporte);
            req.getSession().setAttribute("mensaje", "Reporte creado correctamente");
            resp.sendRedirect(req.getContextPath() + "/reportes");
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
            Reporte reporte = construirDesdeForm(req, id);
            controller.actualizar(reporte);
            req.getSession().setAttribute("mensaje", "Reporte actualizado correctamente");
            resp.sendRedirect(req.getContextPath() + "/reportes");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            int id = Integer.parseInt(req.getParameter("id"));
            cargarFormulario(req, resp, controller.buscarPorId(id));
        }
    }

    // ─── Cambiar status ───────────────────────────────────────────────────────
    private void cambiarStatus(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int idReporte = Integer.parseInt(req.getParameter("idReporte"));
            int idStatus  = Integer.parseInt(req.getParameter("idStatus"));
            controller.actualizarStatus(idReporte, idStatus);
            req.getSession().setAttribute("mensaje", "Status del reporte actualizado");
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/reportes");
    }

    // ─── Eliminar ─────────────────────────────────────────────────────────────
    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            controller.eliminar(id);
            req.getSession().setAttribute("mensaje", "Reporte eliminado correctamente");
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/reportes");
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────
    private void cargarFormulario(HttpServletRequest req, HttpServletResponse resp, Reporte reporte)
            throws ServletException, IOException {
        req.setAttribute("reporte",  reporte);
        req.setAttribute("usuarios", usuarioCtrl.listarTodos());
        req.setAttribute("tipos",    tipoCtrl.listarTodos());
        req.setAttribute("estados",  statusCtrl.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/reporte/formulario.jsp").forward(req, resp);
    }

    private Reporte construirDesdeForm(HttpServletRequest req, int id) {
        String descripcion = req.getParameter("descripcion");
        int    idUsuario   = Integer.parseInt(req.getParameter("idUsuario"));
        int    idTipo      = Integer.parseInt(req.getParameter("idTipo"));
        int    idStatus    = Integer.parseInt(req.getParameter("idStatus"));

        Usuario       usuario = usuarioCtrl.buscarPorId(idUsuario);
        TipoReporte   tipo    = tipoCtrl.buscarPorId(idTipo);
        StatusReporte status  = statusCtrl.buscarPorId(idStatus);

        Reporte r = new Reporte(descripcion, usuario, tipo, status);
        if (id > 0) r.setIdReporte(id);
        return r;
    }
}