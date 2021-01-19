package pl.jkanclerz.payu;

import org.junit.Test;
import pl.jkanclerz.payu.exceptions.PayUException;
import pl.jkanclerz.payu.http.JavaHttpPayUApiClient;
import pl.jkanclerz.payu.model.Buyer;
import pl.jkanclerz.payu.model.CreateOrderResponse;
import pl.jkanclerz.payu.model.OrderCreateRequest;
import pl.jkanclerz.payu.model.Product;

import java.util.Arrays;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

public class PayUTest {
    @Test
    public void itAllowsToRegisterOrder() throws PayUException {
        //Arrange
        var payu = thereIsPayU();
        var mySystemOrderId = UUID.randomUUID().toString();
        var exampleOrderCreateRequest = thereIsExampleOrderCreate(mySystemOrderId);

        //Act
        CreateOrderResponse r = payu.handle(exampleOrderCreateRequest);

        //Assert
        assertThat(r.getExtOrderId()).isEqualTo(mySystemOrderId);
        assertThat(r.getOrderId()).isNotNull();
        assertThat(r.getRedirectUri()).isNotNull();
    }

    private PayU thereIsPayU() {
        return new PayU(
                PayUCredentials.sandbox(),
                new JavaHttpPayUApiClient()
        );
    }

    private OrderCreateRequest thereIsExampleOrderCreate(String mySystemOrderId) {
        return OrderCreateRequest.builder()
                .notifyUrl("https://your.eshop.com/notify")
                .customerIp("127.0.0.1")
                .description("RTV market")
                .currencyCode("PLN")
                .totalAmount(21000)
                .extOrderId(mySystemOrderId)
                .buyer(Buyer.builder()
                        .email("john.doe@example.com")
                        .phone("654111654")
                        .firstName("John")
                        .lastName("Doe")
                        .language("pl")
                        .build())
                .products(Arrays.asList(
                        new Product("Wireless Mouse for Laptop", 15000, 1),
                        new Product("Battery AAA", 1000, 2)
                ))
                .build();
    }
}
