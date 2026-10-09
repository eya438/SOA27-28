package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class restOption {

   public static OptionBusiness optB = new OptionBusiness();

   // GET /options  et  GET /options?domaine=Mathématiques
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response getAllOptions(@QueryParam("domaine") String d) {
      if (d == null) {
         return Response.ok(optB.getListeOptions()).build();
      }
      List<Option> l = optB.getOptionsByDomaine(d);
      if (l.isEmpty()) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok(l).build();
   }

   // GET /options/1
   @GET
   @Path("{code}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getOptionByCode(@PathParam("code") int code) {
      Option o = optB.getOptionByCode(code);
      if (o == null) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok(o).build();
   }

   // POST /options
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response ajoutOption(Option op) {
      if (optB.addOption(op)) {
         return Response.ok(op).build();
      }
      return Response.status(Response.Status.NOT_FOUND).build();
   }

   // PUT /options/1
   @PUT
   @Path("{code}")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response updateOption(@PathParam("code") int code, Option op) {
      if (optB.updateOption(code, op)) {
         return Response.ok(op).build();
      }
      return Response.status(Response.Status.NOT_FOUND).build();
   }

   // DELETE /options/2
   @DELETE
   @Path("{code}")
   public Response deleteOption(@PathParam("code") int code) {
      if (optB.deleteOption(code)) {
         return Response.noContent().build();   // 204, sans corps
      }
      return Response.status(Response.Status.NOT_FOUND).build();
   }
}