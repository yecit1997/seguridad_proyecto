package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.ConductorController;
import com.seguridadnacional.seguridad.controllers.VehiculoController;
import com.seguridadnacional.seguridad.models.Conductor;
import com.seguridadnacional.seguridad.models.Vehiculo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * CRUD de vehículos.
 * GET  /vehiculos                         -> listar
 * GET  /vehiculos?action=nuevo            -> formulario crear
 * GET  /vehiculos?action=editar&id={id}   -> formulario editar
 * GET  /vehiculos?action=ver&id={id}      -> detalle
 * POST /vehiculos?action=crear            -> insertar
 * POST /vehiculos?action=editar           -> actualizar
 * POST /vehiculos?action=eliminar&id={id} -> eliminar
 */
@WebServlet("/vehiculos")
public class SvVehiculo extends HttpServlet {

    private final VehiculoController  controller    = new VehiculoController();
    private final ConductorController conductorCtrl = new ConductorController();

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
            req.setAttribute("vehiculo", controller.buscarPorId(id));
            req.getRequestDispatcher("/WEB-INF/views/vehiculo/detalle.jsp").forward(req, resp);
            return;
        }

        List<Vehiculo> lista = controller.listarTodos();
        req.setAttribute("vehiculos", lista);
        req.getRequestDispatcher("/WEB-INF/views/vehiculo/lista.jsp").forward(req, resp);
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
                resp.sendRedirect(req.getContextPath() + "/vehiculos");
        }
    }

    private void crear(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Vehiculo vehiculo = construirDesdeForm(req);
            controller.registrarVehiculo(vehiculo);
            req.getSession().setAttribute("mensaje", "Vehículo registrado correctamente");
            resp.sendRedirect(req.getContextPath() + "/vehiculos");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            cargarFormulario(req, resp, null);
        }
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Vehiculo vehiculo = construirDesdeForm(req);
            controller.actualizar(vehiculo);
            req.getSession().setAttribute("mensaje", "Vehículo actualizado correctamente");
            resp.sendRedirect(req.getContextPath() + "/vehiculos");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            cargarFormulario(req, resp, null);
        }
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            controller.eliminar(id);
            req.getSession().setAttribute("mensaje", "Vehículo eliminado correctamente");
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/vehiculos");
    }

    private void cargarFormulario(HttpServletRequest req, HttpServletResponse resp, Vehiculo vehiculo)
            throws ServletException, IOException {
        req.setAttribute("vehiculo",   vehiculo);
        req.setAttribute("conductores", conductorCtrl.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/vehiculo/formulario.jsp").forward(req, resp);
    }

    private Vehiculo construirDesdeForm(HttpServletRequest req) {
        int    id          = Integer.parseInt(req.getParameter("idVehiculo"));
        String placa       = req.getParameter("placa");
        int    idConductor = Integer.parseInt(req.getParameter("idConductor"));

        Conductor conductor = conductorCtrl.buscarPorId(idConductor);
        return new Vehiculo(id, placa, conductor);
    }
}