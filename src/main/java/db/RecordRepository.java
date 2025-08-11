package db;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RecordRepository {
    final private String SUPABASE_KEY;
    public RecordRepository() {
        Dotenv dotenv = Dotenv.load(); // .env의 환경변수를 불러와서
        SUPABASE_KEY = dotenv.get("SUPABASE_KEY");
    }
    final private String BASE_URL = "https://rnwkahrdigqmrunluhzs.supabase.co/rest/v1"; // 어디로 요청을 보내야하는지, 버전
    // BASE_URL => "https://{...}.supabase.co/rest/v1" // api docs...
    // /RECORD -> 기능.
    // 본인이 입력한 그대로. (대소문자 가림)

    final private HttpClient client = HttpClient.newHttpClient(); // nodeJS -> axios, ky -> baseURL을 지원

    public String getRecords() {
        // Request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/RECORD")) // 본인이 만든 테이블 이름으로 바꾸기
                .GET() // GET은 기본요청이라 생략 가능
                .headers(
                        "apikey", SUPABASE_KEY,
                        "Authorization", "Bearer %s".formatted(SUPABASE_KEY)
                )
                .build();
        // Response
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body(); // String
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // List<Record> - Mapping
    }
}
