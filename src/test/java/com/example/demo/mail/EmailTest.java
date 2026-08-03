package com.example.demo.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

class EmailTest {

  @Test
  void record_shouldExposeAllComponents() throws Exception {
    InternetAddress to = new InternetAddress("to@example.com");
    InternetAddress cc = new InternetAddress("cc@example.com");
    File attachment = File.createTempFile("att", ".txt");

    Email email =
        new Email(to, List.of(cc), List.of(), "subject", "<b>hi</b>", List.of(attachment));

    assertEquals(to, email.to());
    assertEquals(List.of(cc), email.cc());
    assertEquals(List.of(), email.bcc());
    assertEquals("subject", email.subject());
    assertEquals("<b>hi</b>", email.htmlBody());
    assertEquals(List.of(attachment), email.attachments());
  }
}
