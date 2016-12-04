package io.github.wapmesquita.diffweb.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import io.github.wapmesquita.diffweb.resources.DiffResource;

@ApplicationPath("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> clazz = new HashSet<>();
        clazz.add(DiffResource.class);

        clazz.add(RestExceptionMapper.class);

        return clazz;
    }
}
