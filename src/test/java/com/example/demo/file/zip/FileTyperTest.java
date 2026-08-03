package com.example.demo.file.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class FileTyperTest {

  @Test
  void apply_shouldDetectTextPlain() throws Exception {
    File file = File.createTempFile("note", ".txt");
    Files.writeString(file.toPath(), "hello world");

    MediaType mediaType = new FileTyper().apply(file);

    assertEquals("text/plain", mediaType.toString());
  }
}
