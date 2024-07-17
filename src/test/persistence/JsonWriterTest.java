package persistence;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest {
    JSONObject dummyJSON;

    @BeforeEach
    void runBefore() {
        dummyJSON = new JSONObject();
        dummyJSON.put("1", 1);
        dummyJSON.put("2", 2);
    }

    @Test
    void testWriterInvalidPath() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriter() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriter.json");
            writer.open();
            writer.write(dummyJSON);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriter.json");
            JSONObject jsonRead = reader.read();
            assertEquals(dummyJSON.getInt("1"), jsonRead.getInt("1"));
            assertEquals(dummyJSON.getInt("2"), jsonRead.getInt("2"));
        } catch (IOException e) {
            fail("Failed to write");
        }
    }
}