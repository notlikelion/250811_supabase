package db;

// 받아와줄 레코드
public record RecordAddDTO(
//        long id, // long, interger 비슷한 타입은 호환이 된다
//        String created_at,
        // 위의 두 개는 주석처리하거나 삭제
        String question, // 필드명/멤버변수/속성명 자체가 달라지면...
        String answer) {
}
