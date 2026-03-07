package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.*;
import com.seguridadnacional.seguridad.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/vehiculos")
public class SvVehiculo extends HttpServlet {

    private final VehiculoController  vehiculoCtrl  = new VehiculoController();
    private final ConductorController conductorCtrl = new ConductorController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("editar".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("vehiculo",   vehiculoCtrl.buscarPorId(id));
            req.setAttribute("conductores", conductorCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/vehiculo/formulario.jsp").forward(req, resp);
        } else if ("nuevo".equals(action)) {
            req.setAttribute("conductores", conductorCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/vehiculo/formulario.jsp").forward(req, resp);
        } else {
            req.setAttribute("vehiculos", vehiculoCtrl.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/vehiculo/lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            switch (action) {
                case "crear" -> {
                    String condParam = req.getParameter("conductorId");
                    Conductor conductor = (condParam != null && !condParam.isBlank())
                        ? conductorCtrl.buscarPorId(Integer.parseInt(condParam)) : null;
                    vehiculoCtrl.crear(new Vehiculo(req.getParameter("placa"), conductor));
                    req.getSession().setAttribute("mensaje", "Vehículo creado correctamente");
                }
                case "actualizar" -> {
                    String condParam = req.getParameter("conductorId");
                    Conductor conductor = (condParam != null && !condParam.isBlank())
                        ? conductorCtrl.buscarPorId(Integer.parseInt(condParam)) : null;
                    Vehiculo v = new Vehiculo(Integer.parseInt(req.getParameter("id")), req.getParameter("placa"), conductor);
                    vehiculoCtrl.actualizar(v);
                    req.getSession().setAttribute("mensaje", "Vehículo actualizado correctamente");
                }
                case "eliminar" -> {
                    vehiculoCtrl.eliminar(Integer.parseInt(req.getParameter("id")));
                    req.getSession().setAttribute("mensaje", "Vehículo eliminado correctamente");
                }
            }
        } catch (RuntimeException e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/vehiculos");
    }
}