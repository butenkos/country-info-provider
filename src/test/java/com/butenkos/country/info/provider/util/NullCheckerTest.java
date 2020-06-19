package com.butenkos.country.info.provider.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NullCheckerTest {
  @Test
  public void whenSingleNonNullPassedThenNoExceptionThrown() {
    final Object obj = new Object();
    assertDoesNotThrow(() -> NullChecker.checkNotNull(obj));
  }

  @Test
  public void whenSingleNullPassedThenExceptionThrown() {
    assertThrows(IllegalArgumentException.class, () -> NullChecker.checkNotNull((String) null));
  }

  @Test
  public void whenMultipleNonNullPassedThenNoExceptionThrown() {
    assertDoesNotThrow(() -> NullChecker.checkNotNull(new Object(), new Object(), new Object()));
  }

  @Test
  public void whenMultipleNullPassedThenExceptionThrown() {
    assertThrows(IllegalArgumentException.class, () -> NullChecker.checkNotNull(null, null, null));
  }

  @Test
  public void whenAtLeastOneIsNullOfMultipleNonNullThenExceptionThrown() {
    assertThrows(
        IllegalArgumentException.class, () -> NullChecker.checkNotNull(new Object(), null, new Object())
    );
  }
}