package com.seguridadnacional.seguridad.resources;

import com.seguridadnacional.seguridad.controllers.ReporteController;
import com.seguridadnacional.seguridad.models.Reporte;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/reportes")
public class ReporteResource {

    private final ReporteController controller = new ReporteController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        try {
            List<Reporte> lista = controller.listarTodos();
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
            Reporte r = controller.buscarPorId(id);
            if (r == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(r).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/usuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorUsuario(@PathParam("idUsuario") int idUsuario) {
        try {
            return Response.ok(controller.listarPorUsuario(idUsuario)).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/tipo/{idTipo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorTipo(@PathParam("idTipo") int idTipo) {
        try {
            return Response.ok(controller.listarPorTipo(idTipo)).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/status/{idStatus}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorStatus(@PathParam("idStatus") int idStatus) {
        try {
            return Response.ok(controller.listarPorStatus(idStatus)).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Reporte r) {
        try {
            // crear() asigna fechaCreacion=now() y retorna el Reporte con id generado
            Reporte creado = controller.crear(r);
            return Response.status(Response.Status.CREATED).entity(creado).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Reporte r) {
        try {
            r.setIdReporte(id);
            controller.actualizar(r);
            return Response.ok(r).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}/status/{idStatus}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarStatus(@PathParam("id") int id, @PathParam("idStatus") int idStatus) {
        try {
            controller.actualizarStatus(id, idStatus);
            return Response.ok().entity("{\"mensaje\":\"Estado actualizado\"}").build();
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