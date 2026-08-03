package com.example.demo.repository.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DummyTest {

  @Test
  void setterAndGetter_shouldExposeId() {
    Dummy dummy = new Dummy();
    assertNotNull(dummy);

    dummy.setId("id1");

    assertEquals("id1", dummy.getId());
  }
}
