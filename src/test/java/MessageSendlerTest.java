import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;

public class MessageSendlerTest {
    private final String ruIP = "172.123.12.19";
        private final String usIP = "96.1.1.1";

        @Test
        void MessageSenderTest(){
            GeoService geoService = Mockito.mock(GeoServiceImpl.class);
            LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
            MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

            Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
            Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

            Mockito.when(geoService.byIp(ruIP))
                    .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
            Mockito.when(geoService.byIp(usIP))
                    .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

            Assertions.assertEquals("Добро пожаловать",
                    messageSender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, ruIP)));
            Assertions.assertEquals("Welcome",
                    messageSender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, usIP)));

        }
}
