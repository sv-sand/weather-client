import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sanddev.WeatherClient.Exception.WeatherException;

import java.util.Locale;

/**
 * @author sand <sve.snd@gmail.com>
 * @since 22.10.2023
 */

public class LanguageTest extends WeatherClientMockedTest {
    @Test
    public void changeLanguage() {
        setLocale(new Locale("en"));
        setLocale(new Locale("ru"));
    }

    @Test
    public void wrongLanguage() {
        try {
            client.setLocale(Locale.ITALY);
        } catch (WeatherException e) {
            return;
        }
        Assertions.fail();
    }
}
