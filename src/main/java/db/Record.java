package db;

// 받아와줄 레코드
public record Record(
        long id, // long, interger 비슷한 타입은 호환이 된다
        String created_at,
        // 만들어주는 입장에서 -> 자동생성
        String question, // 필드명/멤버변수/속성명 자체가 달라지면...
        String answer) {
}
