package kopo.poly.service;

import kopo.poly.dto.ContactDTO;

import java.util.List;


public interface IContactService {

	/**
	 * 공지사항 리스트 불러오기
	 */
	List<ContactDTO> getContactList(ContactDTO pDTO, String colNm) throws Exception;

	/**
	 * 공지사항 저장하기
	 */
	int insertContactInfo(ContactDTO pDTO, String colNm) throws Exception;

	/**
	 * 공지사항 상세보기
	 */
	ContactDTO getContactInfo(ContactDTO pDTO, String colNm) throws Exception;


	/**
	 * 공지사항 수정하기
	 */
	int updateContactInfo(ContactDTO pDTO, String colNm) throws Exception;

	/**
	 * 공지사항 삭제하기
	 */
	int deleteContactInfo(ContactDTO pDTO, String colNm) throws Exception;
	
}

