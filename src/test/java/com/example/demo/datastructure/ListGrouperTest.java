package com.example.demo.datastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class ListGrouperTest {
  private final ListGrouper<String> listGrouper = new ListGrouper<>();

  @Test
  void apply_shouldGroupBySize() {
    List<List<String>> grouped = listGrouper.apply(List.of("a", "b", "c", "d", "e"), 2);

    assertEquals(List.of(List.of("a", "b"), List.of("c", "d"), List.of("e")), grouped);
  }

  @Test
  void apply_withEmptyList_shouldReturnEmpty() {
    assertEquals(List.of(), listGrouper.apply(List.of(), 2));
  }
}
