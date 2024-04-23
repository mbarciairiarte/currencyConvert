import com.google.gson.Gson;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultorMonedasService {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/168043a4dc62c4e2b9369b40/latest/";

    public static Moneda consultarTasaDeCambio(String monedaOrigen) {
        try {
            String monedaOrigenEncoded = URLEncoder.encode(monedaOrigen, "UTF-8");
            URI direccion = URI.create(API_URL + monedaOrigenEncoded);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(direccion)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                return gson.fromJson(response.body(), Moneda.class);
            } else {
                throw new RuntimeException("Error al obtener la tasa de cambio. Código de estado: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la tasa de cambio: " + e.getMessage());
        }
    }
}
