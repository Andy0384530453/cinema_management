package com.example.demo.file.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FileHashTest {

  @Test
  void record_shouldExposeAlgorithmAndValue() {
    FileHash hash = new FileHash(FileHashAlgorithm.SHA256, "abc");

    assertEquals(FileHashAlgorithm.SHA256, hash.algorithm());
    assertEquals("abc", hash.value());
  }
}
