package com.example.demo.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;

class EmailConfTest {

  @Test
  void constructor_shouldExposeSource() {
    EmailConf conf = new EmailConf("noreply@poja.io", Region.EU_WEST_3);

    assertEquals("noreply@poja.io", conf.getSesSource());
  }

  @Test
  void getSesClient_shouldBuildClient() {
    EmailConf conf = new EmailConf("noreply@poja.io", Region.EU_WEST_3);

    assertNotNull(conf.getSesClient());
  }
}
