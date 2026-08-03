package com.example.demo.repository.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DummyUuidTest {

  @Test
  void setterAndGetter_shouldExposeId() {
    DummyUuid dummyUuid = new DummyUuid();
    assertNotNull(dummyUuid);

    dummyUuid.setId("uuid1");

    assertEquals("uuid1", dummyUuid.getId());
  }
}
