package com.seguridadnacional.seguridad.servlets;

import com.seguridadnacional.seguridad.controllers.RolUsuarioController;
import com.seguridadnacional.seguridad.controllers.StatusUsuarioController;
import com.seguridadnacional.seguridad.controllers.UsuarioController;
import com.seguridadnacional.seguridad.models.RolUsuario;
import com.seguridadnacional.seguridad.models.StatusUsuario;
import com.seguridadnacional.seguridad.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * CRUD de usuarios.
 * GET  /usuarios              -> listar
 * GET  /usuarios?action=nuevo -> formulario crear
 * GET  /usuarios?action=editar&id={id} -> formulario editar
 * POST /usuarios?action=crear  -> insertar
 * POST /usuarios?action=editar -> actualizar
 * POST /usuarios?action=eliminar&id={id} -> eliminar
 */
@WebServlet("/usuarios")
public class SvUsuario extends HttpServlet {

    private final UsuarioController      controller       = new UsuarioController();
    private final RolUsuarioController   rolController    = new RolUsuarioController();
    private final StatusUsuarioController statusController = new StatusUsuarioController();

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
            Usuario usuario = controller.buscarPorId(id);
            cargarFormulario(req, resp, usuario);
            return;
        }

        // Listar todos
        List<Usuario> lista = controller.listarTodos();
        req.setAttribute("usuarios", lista);
        req.getRequestDispatcher("/WEB-INF/views/usuario/lista.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        switch (action) {
            case "crear":   crear(req, resp);   break;
            case "editar":  editar(req, resp);  break;
            case "eliminar": eliminar(req, resp); break;
            default:
                resp.sendRedirect(req.getContextPath() + "/usuarios");
        }
    }

    // ─── Crear ────────────────────────────────────────────────────────────────
    private void crear(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Usuario usuario = construirDesdeForm(req, 0);
            controller.crearUsuario(usuario);
            req.getSession().setAttribute("mensaje", "Usuario creado correctamente");
            resp.sendRedirect(req.getContextPath() + "/usuarios");
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
            Usuario usuario = construirDesdeForm(req, id);
            controller.actualizar(usuario);
            req.getSession().setAttribute("mensaje", "Usuario actualizado correctamente");
            resp.sendRedirect(req.getContextPath() + "/usuarios");
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
            req.getSession().setAttribute("mensaje", "Usuario eliminado correctamente");
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/usuarios");
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────
    private void cargarFormulario(HttpServletRequest req, HttpServletResponse resp, Usuario usuario)
            throws ServletException, IOException {
        req.setAttribute("usuario", usuario);
        req.setAttribute("roles",   rolController.listarTodos());
        req.setAttribute("estados", statusController.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/usuario/formulario.jsp").forward(req, resp);
    }

    private Usuario construirDesdeForm(HttpServletRequest req, int id) {
        String nombreUsuario = req.getParameter("nombreUsuario");
        String contrasena    = req.getParameter("contrasena");
        int    idRol         = Integer.parseInt(req.getParameter("idRol"));
        int    idStatus      = Integer.parseInt(req.getParameter("idStatus"));

        RolUsuario   rol    = rolController.buscarPorId(idRol);
        StatusUsuario status = statusController.buscarPorId(idStatus);

        Usuario u = new Usuario(nombreUsuario, contrasena, status, rol);
        if (id > 0) u.setIdUsuario(id);
        return u;
    }
}