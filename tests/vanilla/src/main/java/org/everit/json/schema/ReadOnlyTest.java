package org.everit.json.schema;

import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.regexp.RE2JRegexpFactory;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadOnlyTest {

    @Test
    public void validateEmptyObject() throws IOException, URISyntaxException {
        final String rawSchema = this.getSchemaString("/org/everit/json/schema/extension/complexObjectReadonlyValidationSchemaMultiple.json");

        final Schema schema = createSchema(rawSchema);
        final boolean b = schema.isReadOnlyProperty("#/fahrzeugdaten/weiteresFahrzeug");

        assertTrue(b);
    }

    private String getSchemaString(final String path) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(this.getClass().getResource(path).toURI())));
    }

    public static Schema createSchema(final String schema) {
        return SchemaLoader.builder().schemaJson(new JSONObject(schema))
                .draftV7Support()
                .regexpFactory(new RE2JRegexpFactory())
                .build()
                .load()
                .build();
    }
}
