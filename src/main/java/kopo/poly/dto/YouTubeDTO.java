package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class YouTubeDTO {

    String yt_seq; // 유튜브주소 번호
    String user_id; // 아이디
    String title; // 동영상 제목
    String videoId; // 동영상 식별 ID //yt_address와 대응
    String thumbnailPath; //동영상 썸네일 경로

    // 정보 입력 시, 중복 방지를 위해 사용할 변수
    // DB를 조회해서 중복 값이 존재하면 Y값을 반환함.
    // DB테이블에 존재하지 않는 가상의 컬럼(ALIAS)
    String exists_yn;

}
