import db.RecordAddDTO;
import db.RecordRepository;

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
    }
}
