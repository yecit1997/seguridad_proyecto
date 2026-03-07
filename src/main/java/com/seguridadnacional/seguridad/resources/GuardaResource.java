package com.seguridadnacional.seguridad.resources;

import com.seguridadnacional.seguridad.controllers.GuardaController;
import com.seguridadnacional.seguridad.models.Guarda;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/guardas")
public class GuardaResource {

    private final GuardaController controller = new GuardaController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        try {
            List<Guarda> lista = controller.listarTodos();
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
            Guarda g = controller.buscarPorId(id);
            if (g == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(g).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/supervisor/{supervisorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorSupervisor(@PathParam("supervisorId") int supervisorId) {
        try {
            List<Guarda> lista = controller.listarPorSupervisor(supervisorId);
            return Response.ok(lista).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Guarda g) {
        try {
            // PK de Guarda es usuario_rol_id — debe venir en el body
            controller.crear(g);
            return Response.status(Response.Status.CREATED).entity(g).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Guarda g) {
        try {
            // id = usuarioRolId — se ignora si ya viene en el objeto
            controller.actualizar(g);
            return Response.ok(g).build();
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