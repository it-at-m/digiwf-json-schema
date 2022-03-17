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
    public void validateComplexObjectSchema() throws IOException, URISyntaxException {
        final String rawSchema = this.getSchemaString("/org/everit/json/schema/extension/complexObjectReadonlyValidationSchemaMultiple.json");

        final Schema schema = createSchema(rawSchema);
        final boolean b = schema.isReadOnlyProperty("#/fahrzeugdaten/weiteresFahrzeug");

        assertTrue(b);
    }

    @Test
    public void validateArray() throws IOException, URISyntaxException {
        final String rawSchema = this.getSchemaString("/org/everit/json/schema/extension/complexObjectReadonlyValidationSchemaMultiple.json");

        final Schema schema = createSchema(rawSchema);
        final boolean fahrzeuge = schema.isReadOnlyProperty("#/fahrzeugdaten/fahrzeuge");
        assertTrue(fahrzeuge);

        final boolean fahrzeugeProp = schema.isReadOnlyProperty("#/fahrzeugdaten/fahrzeuge/any/deutschesKennzeichen");
        assertTrue(fahrzeugeProp);
    }

    @Test
    public void validateSimpleObjectSchema() throws IOException, URISyntaxException {
        final String rawSchema = this.getSchemaString("/org/everit/json/schema/extension/simpleObjectReadOnlySchema.json");

        final Schema schema = createSchema(rawSchema);
        final boolean readOnlyProp = schema.isReadOnlyProperty("#/readOnlyProp");
        assertTrue(readOnlyProp);

        final boolean readOnlySection = schema.isReadOnlyProperty("#/readOnlySection");
        assertTrue(readOnlySection);

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
