package com.seguridadnacional.seguridad.resources;

import com.seguridadnacional.seguridad.controllers.ReporteController;
import com.seguridadnacional.seguridad.models.Reporte;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/reportes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReporteResource {

    private final ReporteController controller = new ReporteController();

    /** GET /reportes */
    @GET
    public Response listarTodos() {
        try {
            List<Reporte> lista = controller.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** GET /reportes/{id} */
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            return Response.ok(controller.buscarPorId(id)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage()).build();
        }
    }

    /** GET /reportes/usuario/{idUsuario} */
    @GET
    @Path("/usuario/{idUsuario}")
    public Response listarPorUsuario(@PathParam("idUsuario") int idUsuario) {
        try {
            return Response.ok(controller.listarPorUsuario(idUsuario)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** GET /reportes/tipo/{idTipo} */
    @GET
    @Path("/tipo/{idTipo}")
    public Response listarPorTipo(@PathParam("idTipo") int idTipo) {
        try {
            return Response.ok(controller.listarPorTipo(idTipo)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** GET /reportes/status/{idStatus} */
    @GET
    @Path("/status/{idStatus}")
    public Response listarPorStatus(@PathParam("idStatus") int idStatus) {
        try {
            return Response.ok(controller.listarPorStatus(idStatus)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** POST /reportes */
    @POST
    public Response crear(Reporte reporte) {
        try {
            Reporte creado = controller.crearReporte(reporte);
            return Response.status(Response.Status.CREATED).entity(creado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** PUT /reportes/{id} */
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") int id, Reporte reporte) {
        try {
            reporte.setIdReporte(id);
            return Response.ok(controller.actualizar(reporte)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** PATCH /reportes/{id}/status/{idStatus} */
    @PATCH
    @Path("/{id}/status/{idStatus}")
    public Response actualizarStatus(@PathParam("id") int id, @PathParam("idStatus") int idStatus) {
        try {
            return Response.ok(controller.actualizarStatus(id, idStatus)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** DELETE /reportes/{id} */
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
}