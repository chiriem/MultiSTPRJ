package kopo.poly.persistance.mongodb;

import kopo.poly.dto.SStudioDTO;

import java.util.List;

public interface ISStudioMapper {

    /**
     * 유튜브 주소 입력하기
     *
     * @param pDTO 저장할 정보
     * @param colNm 저장할 컬랙션 이름
     * @return 저장 성공 여부
     * @throws Exception
     */
    int insertYtAddress(SStudioDTO pDTO, String colNm) throws Exception;

    List<SStudioDTO> getYtaddress(SStudioDTO pDTO, String colNm) throws Exception;

    SStudioDTO getYoutubeInfo(SStudioDTO pDTO, String colNm) throws Exception;

    SStudioDTO getYtExists(SStudioDTO pDTO, String colNm) throws Exception;

}