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

    // 1. 추가된 Record (JPA)
    // 2. 영향받은(생성된) Row의 수 integer. // 추가하는 행위는 원래 bulk -> 여러개를 한 번에 넣는 경우가 많음. 0 / 1 / N...
    // 3. true/false boolean 성공 실패. -> 내부에서 다 로깅하거나 처리해서...
    // 4. void -> 내부에서 예외처리가 되어 있음. -> 상위에서 처리하게 하겠다
    // 3? 4? -> 추가 여부다. 근데 에러가 났다면?
    // 대부분 1 아니면 4.
    public void addRecord(RecordAddDTO dto) {
        // Request
        String data = null;
        try {
            data = mapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/RECORD")) // 본인이 만든 테이블 이름으로 바꾸기
                .POST(HttpRequest.BodyPublishers.ofString(data)) // ㅖPOST는 body를 넣어줘야함
                .headers(
                        "apikey", SUPABASE_KEY,
                        "Authorization", "Bearer %s".formatted(SUPABASE_KEY)
                )
                .build();
        // Response
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body(); // String
            System.out.println(body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
