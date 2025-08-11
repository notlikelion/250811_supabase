import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Part;
import db.RecordAddDTO;
import db.RecordRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        RecordRepository repository = new RecordRepository();
        System.out.println(repository.getRecords());
        repository.addRecord(new RecordAddDTO(
                "질문",
                "답변"
        ));
        // Scanner -> GenAI -> 질문/답변 (반복)
        // add(insert)와 연결.
        // Supabase 기능들 조금 보면... (밥 먹고 나서 할 일)
        Scanner sc = new Scanner(System.in);
        Dotenv dotenv = Dotenv.load();
        // https://aistudio.google.com/apikey
        String GOOGLE_API_KEY = dotenv.get("GOOGLE_API_KEY");

        while (true) {
            System.out.print("질문을 입력해주세요 🥹 : ");
            String input = sc.nextLine();
            if (input.equals("종료")) {
                break;
            }
            Client client = Client.builder()
                    .apiKey(GOOGLE_API_KEY)
                    .build();
            Content systemInstruction = Content.builder()
                    .parts(Part.builder()
                        .text("100자 이내에 한글로 작성된 평문"))
                    .build();
            String output = client.models.generateContent   ("gemini-2.0-flash",
                    input, GenerateContentConfig.builder()
                            .systemInstruction(systemInstruction).build())
                    .text();
            System.out.println(output);
        }
    }
}
