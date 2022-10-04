package kopo.poly.persistance.mongodb;

import kopo.poly.dto.ContactDTO;

import java.util.List;

public interface IContactMapper {

    /**
     *
     * @param colNm 조회할 컬랙션 이름
     * @return 공지사항 리스트
     * @throws Exception
     */
    List<ContactDTO> getContactList(ContactDTO pDTO, String colNm) throws Exception;

    List<ContactDTO> getContactListforadmin(String colNm) throws Exception;

    /**
     *
     * @param pDTO 저장될 정보
     * @param colNm 저장할 컬렉션 이름
     * @return 저장 결과
     * @throws Exception
     */
    int insertContactInfo(ContactDTO pDTO, String colNm) throws Exception;

    /**
     *
     * @param pDTO 조회할 정보
     * @param colNm 보회할 컬렉션 이름
     * @return 조회 결과
     * @throws Exception
     */
    ContactDTO getContactInfo(ContactDTO pDTO, String colNm) throws Exception;



    /**
     *
     * @param pDTO 수정할 정보
     * @param colNm 수정할 컬렉션 이름
     * @return 수정 결과
     * @throws Exception
     */
    int updateContactInfo(ContactDTO pDTO, String colNm) throws Exception;

    /**
     *
     * @param pDTO 삭제할 정보
     * @param colNm 삭제할 컬렉션 이름
     * @return 삭제 결과
     * @throws Exception
     */
    int deleteContactInfo(ContactDTO pDTO, String colNm) throws Exception;

}
