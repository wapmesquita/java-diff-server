package io.github.wapmesquita.diffweb.config;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.github.wapmesquita.diffweb.exception.RestException;

@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {
    @Override
    public Response toResponse(RestException e) {
        return Response.status(e.getStatusCode()).entity(e.getMessage()).build();
    }
}
