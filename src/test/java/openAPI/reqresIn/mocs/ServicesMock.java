package openAPI.reqresIn.mocs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServicesMock {

    interface RequestSender {
        int get(String url);
    }

    static class ApiService {
        private final RequestSender sender;

        ApiService(RequestSender sender) {
            this.sender = sender;
        }

        int getStatus(String url) {
            return sender.get(url);
        }
    }

    @Test
    void get_reqres_in_api_with_mockito_stub() {

        RequestSender sender = mock(RequestSender.class);
        String url = "https://reqres.in/api/";
        when(sender.get(url)).thenReturn(200);

        ApiService service = new ApiService(sender);
        int status = service.getStatus(url);

        assertEquals(200, status, "Expected status 200 from stubbed GET");
        verify(sender, times(1)).get(url);
        verifyNoMoreInteractions(sender);
    }
}
