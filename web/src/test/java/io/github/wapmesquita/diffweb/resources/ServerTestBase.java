package io.github.wapmesquita.diffweb.resources;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

import javax.ws.rs.core.Application;

import io.github.wapmesquita.diffweb.config.ApplicationConfig;

public class ServerTestBase extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.CONTAINER_FACTORY);
        return new ApplicationConfig();
    }

    private void stopContainer() throws Exception {
        super.tearDown();
    }

    protected String loadFile1() {
        return loadFileEncoded("f1.txt");
    }

    protected String loadFile2() {
        return loadFileEncoded("f2.txt");
    }

    protected String loadFileBig() {
        return loadFileEncoded("big.txt");
    }

    protected String loadFileEncoded(String filename) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename)))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line+"\n");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }
}
