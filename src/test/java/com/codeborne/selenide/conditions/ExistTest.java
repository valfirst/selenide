package com.codeborne.selenide.conditions;

import com.codeborne.selenide.Driver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.CheckResult.Verdict.ACCEPT;
import static com.codeborne.selenide.CheckResult.Verdict.REJECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class ExistTest {
  private final Driver driver = mock(Driver.class);
  private final Exist condition = new Exist();
  private final WebElement element = mock(WebElement.class);

  @Test
  void negate() {
    assertThat(condition.missingElementSatisfiesCondition()).isFalse();
    assertThat(condition.negate().missingElementSatisfiesCondition()).isTrue();
  }

  @Test
  void name() {
    assertThat(condition.getName()).isEqualTo("exist");
    assertThat(condition.negate().getName()).isEqualTo("not exist");
  }

  @Test
  void satisfied_if_element_is_visible() {
    when(element.isDisplayed()).thenReturn(true);
    assertThat(condition.check(driver, element).verdict()).isEqualTo(ACCEPT);
  }

  @Test
  void satisfied_if_element_exists_even_if_invisible() {
    when(element.isDisplayed()).thenReturn(false);
    assertThat(condition.check(driver, element).verdict()).isEqualTo(ACCEPT);
  }

  @Test
  void not_satisfied_if_element_is_stolen() {
    when(element.isDisplayed()).thenThrow(StaleElementReferenceException.class);
    assertThat(condition.check(driver, element).verdict()).isEqualTo(REJECT);
  }
}
