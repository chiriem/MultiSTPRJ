package kopo.poly.service.impl;


import kopo.poly.dto.ManageUserDTO;
import kopo.poly.persistance.mongodb.impl.ManageUserMapper;
import kopo.poly.persistance.mongodb.impl.SequenceMapper;
import kopo.poly.service.IManageUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service("ManageUserService")
public class ManageUserService implements IManageUserService {

    // MongoDB에 저장할 Mapper
    @Resource(name="ManageUserMapper")
    private ManageUserMapper manageUserMapper;

    // MongoDB에 시퀸스 검색 Mapper
    @Resource(name="SequenceMapper")
    private SequenceMapper sequenceMapper;

    @Override
    public List<ManageUserDTO> getUserList(String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getUserList Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<ManageUserDTO> rList = new LinkedList<>();

        // 조회 결과 담기
        rList = manageUserMapper.getUserList(colNm);

        log.info(this.getClass().getName() + ".getUserList End!");

        return rList;
    }



    @Override
    public ManageUserDTO getUserInfo(ManageUserDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getUserInfo Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        ManageUserDTO rDTO = new ManageUserDTO();

        // 조회 결과 담기
        rDTO = manageUserMapper.getUserInfo(pDTO, colNm);

        log.info(this.getClass().getName() + ".getUserInfo End!");

        return rDTO;

    }


    @Override
    public int deleteUserInfo(ManageUserDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".deleteUserInfo Start!");

        int res = 0;

        // MongoDB에 데이터 새로고침하기
        res = manageUserMapper.deleteUserInfo(pDTO, colNm);

        log.info(this.getClass().getName() + ".deleteUserInfo End!");

        return res;
    }
}