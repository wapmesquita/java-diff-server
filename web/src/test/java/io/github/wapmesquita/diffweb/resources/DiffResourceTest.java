package io.github.wapmesquita.diffweb.resources;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.wapmesquita.diffservice.model.DiffResult;
import io.github.wapmesquita.diffweb.dto.DiffDtoClient;
import io.github.wapmesquita.diffweb.dto.UploadDto;
import io.github.wapmesquita.diffweb.enums.DiffOption;
import io.github.wapmesquita.diffweb.utils.Constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DiffResourceTest extends ServerTestBase {

    private static final Logger log = LoggerFactory.getLogger(DiffResourceTest.class);

    @Test
    public void getDiffNotFound() {
        String id = UUID.randomUUID().toString();
        Response response = target(UrlPatterns.getRequestDiffPatter(id)).request().get();
        log.info("Response: {}", response);

        assertEquals(Constants.HTTP_NOT_FOUND, response.getStatus());
    }

    @Test
    public void testDiffEqual() {
        String id = UUID.randomUUID().toString();
        UploadDto uploadDtoLeft = new UploadDto(loadFile1());
        Response responseLeft = uploadDiffPart(id, DiffOption.LEFT, uploadDtoLeft);
        assertEquals(Constants.HTTP_SUCCESS, responseLeft.getStatus());

        UploadDto uploadDtoRight = new UploadDto(loadFile1());
        Response responseRight = uploadDiffPart(id, DiffOption.RIGHT, uploadDtoRight);
        assertEquals(Constants.HTTP_SUCCESS, responseRight.getStatus());

        DiffDtoClient diffDto = target(UrlPatterns.getRequestDiffPatter(id)).request().get(DiffDtoClient.class);
        log.info("Response: {}", diffDto);
        assertEquals(DiffResult.EQUAL, diffDto.diffResult);
        assertTrue(diffDto.differentLines.isEmpty());
    }

    @Test
    public void testDiffDifferentSize() {
        String id = UUID.randomUUID().toString();
        UploadDto uploadDtoLeft = new UploadDto(loadFile1());
        Response responseLeft = uploadDiffPart(id, DiffOption.LEFT, uploadDtoLeft);
        assertEquals(Constants.HTTP_SUCCESS, responseLeft.getStatus());

        UploadDto uploadDtoRight = new UploadDto(loadFileBig());
        Response responseRight = uploadDiffPart(id, DiffOption.RIGHT, uploadDtoRight);
        assertEquals(Constants.HTTP_SUCCESS, responseRight.getStatus());

        DiffDtoClient diffDto = target(UrlPatterns.getRequestDiffPatter(id)).request().get(DiffDtoClient.class);
        log.info("Response: {}", diffDto);
        assertEquals(DiffResult.NOT_EQUAL_SIZE, diffDto.diffResult);
        assertTrue(diffDto.differentLines.isEmpty());
    }

    @Test
    public void testDiffSameSize() {
        String id = UUID.randomUUID().toString();
        UploadDto uploadDtoLeft = new UploadDto(loadFile1());
        Response responseLeft = uploadDiffPart(id, DiffOption.LEFT, uploadDtoLeft);
        assertEquals(Constants.HTTP_SUCCESS, responseLeft.getStatus());

        UploadDto uploadDtoRight = new UploadDto(loadFile2());
        Response responseRight = uploadDiffPart(id, DiffOption.RIGHT, uploadDtoRight);
        assertEquals(Constants.HTTP_SUCCESS, responseRight.getStatus());

        DiffDtoClient diffDto = target(UrlPatterns.getRequestDiffPatter(id)).request().get(DiffDtoClient.class);
        log.info("Response: {}", diffDto);
        assertEquals(DiffResult.SAME_SIZE, diffDto.diffResult);
        assertEquals(2, diffDto.differentLines.size());
        assertTrue(diffDto.differentLines.stream().anyMatch(l -> l.getLineNumber().equals(4)));
        assertTrue(diffDto.differentLines.stream().anyMatch(l -> l.getLineNumber().equals(5)));
    }

    @Test
    public void testDiffWithOnlyOnePart() {
        String id = UUID.randomUUID().toString();
        UploadDto uploadDtoLeft = new UploadDto(loadFile1());
        Response responseLeft = uploadDiffPart(id, DiffOption.LEFT, uploadDtoLeft);
        assertEquals(Constants.HTTP_SUCCESS, responseLeft.getStatus());

        DiffDtoClient diffDto = target(UrlPatterns.getRequestDiffPatter(id)).request().get(DiffDtoClient.class);
        log.info("Response: {}", diffDto);
        assertEquals(DiffResult.NOT_EQUAL_SIZE, diffDto.diffResult);
        assertTrue(diffDto.differentLines.isEmpty());
    }

    @Test
    public void testUploadWithInvalidId() {
        String id = "";
        UploadDto uploadDtoLeft = new UploadDto(loadFile1());
        Response responseLeft = uploadDiffPart(id, DiffOption.LEFT, uploadDtoLeft);
        assertEquals(Constants.HTTP_NOT_FOUND, responseLeft.getStatus());
    }

    private Response uploadDiffPart(String id, DiffOption option, UploadDto uploadDto) {Entity<UploadDto> uploadDtoEntity = Entity.entity(uploadDto, MediaType.APPLICATION_JSON_TYPE);
        Response response = target(UrlPatterns.getUploadDiffPattern(id, option))
                .request()
                .put(uploadDtoEntity);
        log.info("Response: {}", response);
        return response;
    }

}
