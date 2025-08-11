package db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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
    final private ObjectMapper mapper = new ObjectMapper();

//    public String getRecords() {
    public List<Record> getRecords() {
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
            String body = response.body(); // String
//            return mapper.readValue(body,
//                    // class -> List는 복잡한 편
//                    new TypeReference<List<Record>>() {});
            // List<Record> - Mapping
            return mapper.readValue(body,
                    // class -> List는 복잡한 편
                    new TypeReference<>() {});
            // 어차피 Return이 List<Record>니까...
            // 제너릭 -> <>
            // 메서드나 클래스 단위에서 타입을 입력해서 유동적으로 사용하게 문법
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
