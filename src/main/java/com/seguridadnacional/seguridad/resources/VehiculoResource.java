package com.seguridadnacional.seguridad.resources;

import com.seguridadnacional.seguridad.controllers.VehiculoController;
import com.seguridadnacional.seguridad.models.Vehiculo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/vehiculos")
public class VehiculoResource {

    private final VehiculoController controller = new VehiculoController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        try {
            List<Vehiculo> lista = controller.listarTodos();
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
            Vehiculo v = controller.buscarPorId(id);
            if (v == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(v).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/placa/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorPlaca(@PathParam("placa") String placa) {
        try {
            Vehiculo v = controller.buscarPorPlaca(placa);
            if (v == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(v).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crear(Vehiculo v) {
        try {
            // crear() retorna el Vehiculo con el id generado
            Vehiculo creado = controller.crear(v);
            return Response.status(Response.Status.CREATED).entity(creado).build();
        } catch (RuntimeException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int id, Vehiculo v) {
        try {
            v.setIdVehiculo(id);
            controller.actualizar(v);
            return Response.ok(v).build();
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