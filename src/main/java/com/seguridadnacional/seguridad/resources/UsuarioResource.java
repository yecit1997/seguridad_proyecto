package com.seguridadnacional.seguridad.resources;

import com.seguridadnacional.seguridad.controllers.UsuarioController;
import com.seguridadnacional.seguridad.controllers.PersonaController;
import com.seguridadnacional.seguridad.models.Usuario;
import com.seguridadnacional.seguridad.models.Persona;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
public class UsuarioResource {

    private final UsuarioController controller = new UsuarioController();
    private final PersonaController personaController = new PersonaController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        try {
            List<Usuario> lista = controller.listarTodos();
            return Response.ok(lista).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Usuario u = controller.buscarPorId(id);
            if (u == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(u).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Usuario u) {
        try {
            // Asegura que la persona exista
            if (u.getPersona() != null && u.getPersonaId() > 0) {
                Persona persona = personaController.buscarPorId(u.getPersonaId());
                u.setPersona(persona);
            }
            Usuario creado = controller.crear(u);
            return Response.status(Response.Status.CREATED).entity(creado).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Usuario u) {
        try {
            u.setIdUsuario(id);
            controller.actualizar(u);
            return Response.ok(u).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") int id) {
        try {
            controller.eliminar(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}