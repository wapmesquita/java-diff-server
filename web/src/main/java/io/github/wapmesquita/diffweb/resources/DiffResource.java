package io.github.wapmesquita.diffweb.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.wapmesquita.diffweb.utils.Constants;
import io.github.wapmesquita.diffweb.controllers.CalculateDiffController;
import io.github.wapmesquita.diffweb.controllers.SaveDiffController;
import io.github.wapmesquita.diffweb.dto.DiffDto;
import io.github.wapmesquita.diffweb.dto.UploadDto;
import io.github.wapmesquita.diffweb.enums.DiffOption;
import io.github.wapmesquita.diffweb.exception.RestException;

@Path("/diff/{id}")
public class DiffResource {

    private static final Logger log = LoggerFactory.getLogger(DiffResource.class);

    private CalculateDiffController calculateDiffController = new CalculateDiffController();
    private SaveDiffController saveDiffController = new SaveDiffController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DiffDto getCalculatedDiff(@PathParam("id") String id) {
        return calculateDiffController.calculateDiff(id);
    }

    @PUT
    @Path("/{option}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response upload(@PathParam("id") String id, @PathParam("option") String option, UploadDto uploadDto) {
        log.info("Uploading {} to diff with ID {}", option, id);

        DiffOption diffOption = DiffOption.fromOption(option);

        if (uploadDto.encodedString == null) {
            throw new RestException(Constants.HTTP_BAD_REQUEST, "encondeString can not be null");
        }

        saveDiffController.save(id, diffOption, getDecodedData(uploadDto.encodedString));

        return Response.ok().build();
    }

    private List<String> getDecodedData(String encodedString) {
        byte[] bytes = java.util.Base64.getDecoder().decode(encodedString);
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), "UTF-8"))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                list.add(line);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
