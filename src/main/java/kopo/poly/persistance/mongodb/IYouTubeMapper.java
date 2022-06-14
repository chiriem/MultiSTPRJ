package kopo.poly.persistance.mongodb;

import kopo.poly.dto.YouTubeDTO;

import java.util.List;

public interface IYouTubeMapper {

    /**
     * 유튜브 주소 입력하기
     *
     * @param pDTO 저장할 정보
     * @param colNm 저장할 컬랙션 이름
     * @return 저장 성공 여부
     * @throws Exception
     */
    int insertYtAddress(YouTubeDTO pDTO, String colNm) throws Exception;

    YouTubeDTO getYtExists(YouTubeDTO pDTO, String colNm) throws Exception;

    List<YouTubeDTO> getYtaddress(YouTubeDTO pDTO, String colNm) throws Exception;

    YouTubeDTO getYoutubeInfo(YouTubeDTO pDTO, String colNm) throws Exception;

}