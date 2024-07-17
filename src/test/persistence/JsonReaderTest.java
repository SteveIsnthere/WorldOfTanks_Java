package persistence;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/ubcTheBest.json");
        try {
            JSONObject json = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderJSON() {
        JsonReader reader = new JsonReader("./data/testReader.json");
        try {
            JSONObject json = reader.read();
            assertEquals(1, json.getInt("1"));
            assertEquals(2, json.getInt("2"));
        } catch (IOException e) {
            fail("Read file failed");
        }
    }
}