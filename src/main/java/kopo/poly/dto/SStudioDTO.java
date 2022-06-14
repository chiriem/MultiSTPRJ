package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 정보 DTO
 * 사용자의 정보를 저장, 조회, 수정, 삭제를 관여합니다.
 */

/**
 * lombok은 코딩을 줄이기 위해 @어노테이션을 통한 자동 코드 완성기능임
 * 값이 0인 것을 방지함.
 * @Getter => getter 함수를 작성하지 않았지만, 자동 생성
 * @Setter => setter 함수를 작성하지 않았지만, 자동 생성
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
@Getter
@Setter
public class SStudioDTO {

    String yt_seq; // 유튜브주소 번호
    String user_id; // 아이디
    String yt_address; // 유튜브 주소

    // 정보 입력 시, 중복가입을 방지를 위해 사용할 변수
    // DB를 조회해서 회원이 존재하면 Y값을 반환함.
    // DB테이블에 존재하지 않는 가상의 컬럼(ALIAS)
    String exists_yn;

}