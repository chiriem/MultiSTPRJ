package kopo.poly.service;

import kopo.poly.dto.SStudioDTO;

import java.util.List;

public interface ISStudioService {
   // SStudioDTO get();

    /**
     * 유튜브 주소 등록하기
     *
     * @param pDTO  저장할 정보
     * @param colNm 참조할 컬렉션 이름
     * @return 저장 성공 여부
     * @throws Exception
     */
    int insertYtaddress(SStudioDTO pDTO, String colNm) throws Exception;

    List<SStudioDTO> getYtaddress(SStudioDTO pDTO, String colNm) throws Exception;

    List<SStudioDTO> getAllYtaddress(SStudioDTO pDTO, String colNm, String LcolNm) throws Exception;

    /**
     * 유튜브 주소만 받기
     */
    SStudioDTO getYoutubeInfo(SStudioDTO pDTO, String colNm) throws Exception;

    int deleteYt(SStudioDTO pDTO, String colNm) throws Exception;

    int deleteAllYt(SStudioDTO pDTO, String colNm, String LcolNm) throws Exception;
}