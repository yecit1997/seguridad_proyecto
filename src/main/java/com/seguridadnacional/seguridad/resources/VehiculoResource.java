package com.seguridadnacional.seguridad.resources;

import com.seguridadnacional.seguridad.controllers.VehiculoController;
import com.seguridadnacional.seguridad.models.Vehiculo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/vehiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehiculoResource {

    private final VehiculoController controller = new VehiculoController();

    /** GET /vehiculos */
    @GET
    public Response listarTodos() {
        try {
            List<Vehiculo> lista = controller.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** GET /vehiculos/{id} */
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

    /** GET /vehiculos/placa/{placa} */
    @GET
    @Path("/placa/{placa}")
    public Response buscarPorPlaca(@PathParam("placa") String placa) {
        try {
            return Response.ok(controller.buscarPorPlaca(placa)).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage()).build();
        }
    }

    /** POST /vehiculos */
    @POST
    public Response registrar(Vehiculo vehiculo) {
        try {
            return Response.status(Response.Status.CREATED)
                           .entity(controller.registrarVehiculo(vehiculo)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** PUT /vehiculos/{id} */
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") int id, Vehiculo vehiculo) {
        try {
            vehiculo.setIdVehiculo(id);
            return Response.ok(controller.actualizar(vehiculo)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(e.getMessage()).build();
        }
    }

    /** DELETE /vehiculos/{id} */
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