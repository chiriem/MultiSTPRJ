package kopo.poly.persistance.mongodb;

import kopo.poly.dto.ManageUserDTO;

import java.util.List;

public interface IManageUserMapper {

    /**
     *
     * @param colNm 조회할 컬랙션 이름
     * @return 공지사항 리스트
     * @throws Exception
     */
    List<ManageUserDTO> getUserList(String colNm) throws Exception;



    /**
     *
     * @param pDTO 조회할 정보
     * @param colNm 보회할 컬렉션 이름
     * @return 조회 결과
     * @throws Exception
     */
    ManageUserDTO getUserInfo(ManageUserDTO pDTO, String colNm) throws Exception;



    /**
     *
     * @param pDTO 삭제할 정보
     * @param colNm 삭제할 컬렉션 이름
     * @return 삭제 결과
     * @throws Exception
     */
    int deleteUserInfo(ManageUserDTO pDTO, String colNm) throws Exception;

}
