package integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.isChrome;
import static org.assertj.core.api.Assumptions.assumeThat;

public class MobileEmulationTest {
  @BeforeEach
  void setUp() {
    assumeThat(isChrome()).isTrue();

    close();
    System.setProperty("chromeoptions.mobileEmulation", "deviceName=Nexus 5");
    System.setProperty("chromeoptions.args", "--diagnostics,--disable-blink-features");
  }

  @AfterEach
  void tearDown() {
    if (isChrome()) {
      close();
    }
  }

  @Test
  void canOpenBrowserInMobileEmulationMode() {
    open("https://selenide.org");
    $(".main-menu-pages").find(byText("Javadoc"))
      .shouldBe(visible)
      .shouldHave(attribute("href", "https://selenide.org/javadoc.html"));
  }
}
