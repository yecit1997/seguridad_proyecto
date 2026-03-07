package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.*;
import com.seguridadnacional.seguridad.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/reportes")
public class SvReporte extends HttpServlet {

    private final ReporteController      reporteCtrl      = new ReporteController();
    private final TipoReporteController  tipoCtrl         = new TipoReporteController();
    private final StatusReporteController statusCtrl      = new StatusReporteController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("editar".equals(action) || "detalle".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("reporte",  reporteCtrl.buscarPorId(id));
            req.setAttribute("tipos",    tipoCtrl.listarTodos());
            req.setAttribute("statuses", statusCtrl.listarTodos());
            String vista = "detalle".equals(action) ? "detalle.jsp" : "formulario.jsp";
            req.getRequestDispatcher("/WEB-INF/views/reporte/" + vista).forward(req, resp);
        } else if ("nuevo".equals(action)) {
            req.setAttribute("tipos",    tipoCtrl.listarTodos());
            req.setAttribute("statuses", statusCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/reporte/formulario.jsp").forward(req, resp);
        } else {
            String filtroTipo   = req.getParameter("tipo");
            String filtroStatus = req.getParameter("status");
            java.util.List<Reporte> lista;
            if (filtroTipo   != null && !filtroTipo.isBlank())   lista = reporteCtrl.listarPorTipo(Integer.parseInt(filtroTipo));
            else if (filtroStatus != null && !filtroStatus.isBlank()) lista = reporteCtrl.listarPorStatus(Integer.parseInt(filtroStatus));
            else lista = reporteCtrl.listarTodos();

            req.setAttribute("reportes", lista);
            req.setAttribute("tipos",    tipoCtrl.listarTodos());
            req.setAttribute("statuses", statusCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/reporte/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        Usuario u = (Usuario) req.getSession().getAttribute("usuarioSesion");
        try {
            switch (action) {
                case "crear" -> {
                    TipoReporte   tipo   = new TipoReporte();  tipo.setIdTipoReporte(Integer.parseInt(req.getParameter("tipoId")));
                    StatusReporte status = new StatusReporte(); status.setIdStatusReporte(Integer.parseInt(req.getParameter("statusId")));
                    Reporte r = new Reporte(null, req.getParameter("descripcion"), u, tipo, status);
                    reporteCtrl.crear(r);
                    req.getSession().setAttribute("mensaje", "Reporte creado correctamente");
                }
                case "actualizar" -> {
                    TipoReporte   tipo   = new TipoReporte();  tipo.setIdTipoReporte(Integer.parseInt(req.getParameter("tipoId")));
                    StatusReporte status = new StatusReporte(); status.setIdStatusReporte(Integer.parseInt(req.getParameter("statusId")));
                    Reporte r = new Reporte(Integer.parseInt(req.getParameter("id")), null,
                                            req.getParameter("descripcion"), u, tipo, status);
                    reporteCtrl.actualizar(r);
                    req.getSession().setAttribute("mensaje", "Reporte actualizado correctamente");
                }
                case "cambiarStatus" -> {
                    reporteCtrl.actualizarStatus(
                        Integer.parseInt(req.getParameter("id")),
                        Integer.parseInt(req.getParameter("statusId"))
                    );
                    req.getSession().setAttribute("mensaje", "Estado actualizado");
                }
                case "eliminar" -> {
                    reporteCtrl.eliminar(Integer.parseInt(req.getParameter("id")));
                    req.getSession().setAttribute("mensaje", "Reporte eliminado correctamente");
                }
            }
        } catch (RuntimeException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/reportes");
    }
}