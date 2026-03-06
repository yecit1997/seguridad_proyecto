package com.seguridadnacional.seguridad.resources;

import com.seguridadnacional.seguridad.controllers.UsuarioController;
import com.seguridadnacional.seguridad.models.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioController controller = new UsuarioController();

    /** GET /usuarios */
    @GET
    public Response listarTodos() {
        try {
            List<Usuario> lista = controller.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** GET /usuarios/{id} */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Usuario u = controller.buscarPorId(id);
            return Response.ok(u).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage()).build();
        }
    }

    /** POST /usuarios */
    @POST
    public Response crear(Usuario usuario) {
        try {
            Usuario creado = controller.crearUsuario(usuario);
            return Response.status(Response.Status.CREATED).entity(creado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** PUT /usuarios/{id} */
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") int id, Usuario usuario) {
        try {
            usuario.setIdUsuario(id);
            Usuario actualizado = controller.actualizar(usuario);
            return Response.ok(actualizado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** DELETE /usuarios/{id} */
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        try {
            controller.eliminar(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage()).build();
        }
    }

    /** POST /usuarios/login */
    @POST
    @Path("/login")
    public Response login(Usuario credenciales) {
        try {
            Usuario u = controller.login(credenciales.getNombreUsuario(), credenciales.getContrasena());
            return Response.ok(u).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity(e.getMessage()).build();
        }
    }
}