package kopo.poly.service;

import kopo.poly.dto.ManageUserDTO;

import java.util.List;


public interface IManageUserService {

	/**
	 * 사용자 리스트 불러오기
	 */
	List<ManageUserDTO> getUserList(String colNm) throws Exception;


	/**
	 * 사용자 상세보기
	 */
	ManageUserDTO getUserInfo(ManageUserDTO pDTO, String colNm) throws Exception;

	/**
	 * 사용자 삭제하기
	 */
	int deleteUserInfo(ManageUserDTO pDTO, String colNm) throws Exception;
	
}

