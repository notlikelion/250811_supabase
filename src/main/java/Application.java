import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Application {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        // request
        String URL = "https://rnwkahrdigqmrunluhzs.supabase.co/rest/v1/RECORD?select=*";
        // https://rnwkahrdigqmrunluhzs.supabase.co/rest/v1
        // /RECORD : /{테이블명} 을 지정
        // ?select=* : 전체 열을 읽어오겠다...
        Dotenv dotenv = Dotenv.load();
        String SUPABASE_KEY = dotenv.get("SUPABASE_KEY");
        HttpRequest request = HttpRequest
                .newBuilder()
//                .GET() // 이건 생략
                // 어디로 보낼지 (URI, URL)
                // 어떻게 보낼지 (GET, POST)
                // POST -> body (Mapping)
                // 어떤 인증 정보, 요청 정보를 포함할지 (headers, header)
                .uri(URI.create(URL))
                /*
                -H "apikey: SUPABASE_KEY" \
                -H "Authorization: Bearer SUPABASE_KEY"
                */
                .headers("apikey", SUPABASE_KEY,
                        "Authorization", "Bearer %s".formatted(SUPABASE_KEY))
                .build();
        // response
        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            System.out.println(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Record? Mapping -> 출력
    }
}
