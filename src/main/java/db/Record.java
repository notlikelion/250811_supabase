package db;

// 받아와줄 레코드
public record Record(long id, String created_at, String question, String answer) {
}
