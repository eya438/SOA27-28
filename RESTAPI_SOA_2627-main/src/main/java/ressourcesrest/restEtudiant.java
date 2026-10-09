package ressourcesrest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("etudiants")
public class restEtudiant {

    public static EtudiantBusiness etuB = new EtudiantBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        return Response.ok(etuB.getAllEtudiants()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantById(@PathParam("id") String id) {
        Etudiant e = etuB.getEtudiantByIdentifiant(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(e).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajoutEtudiant(Etudiant e) {
        if (e.getOption() == null || !etuB.addEtudiant(e)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(e).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant e) {
        if (e.getOption() != null) {
            Option o = restOption.optB.getOptionByCode(e.getOption().getCodeOption());
            if (o == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            e.setOption(o);
        }
        if (etuB.updateEtudiant(id, e)) {
            return Response.ok(e).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (etuB.deleteEtudiant(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option o = restOption.optB.getOptionByCode(codeOption);
        if (o == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Etudiant> sansOption = new ArrayList<>();
        for (Etudiant e : etuB.getEtudiantsByOption(o)) {
            sansOption.add(new Etudiant(e.getIdentifiant(), e.getNom(), e.getPrenom(),
                    null, e.getAnneeEtude(), e.getEmail()));
        }
        return Response.ok(new EtudiantList(sansOption)).build();
    }
}