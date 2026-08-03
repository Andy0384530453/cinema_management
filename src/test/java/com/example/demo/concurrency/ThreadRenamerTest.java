package com.example.demo.concurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ThreadRenamerTest {

  @Test
  void renameWorkerThread_shouldSetWorkerPrefix() {
    Thread thread = new Thread(() -> {});

    ThreadRenamer.renameWorkerThread(thread);

    assertTrue(thread.getName().startsWith("w-"));
  }

  @Test
  void renameFrontalThread_shouldSetFrontalPrefix() {
    Thread thread = new Thread(() -> {});

    ThreadRenamer.renameFrontalThread(thread);

    assertTrue(thread.getName().startsWith("f-"));
  }

  @Test
  void renameThread_shouldSetGivenName() {
    Thread thread = new Thread(() -> {});

    ThreadRenamer.renameThread(thread, "my-name");

    assertEquals("my-name", thread.getName());
  }

  @Test
  void getRandomSubThreadNamePrefixFrom_shouldUseParentName() {
    Thread thread = new Thread(() -> {});
    thread.setName("parent");

    String prefix = ThreadRenamer.getRandomSubThreadNamePrefixFrom(thread);

    assertTrue(prefix.startsWith("parent-"));
  }
}
