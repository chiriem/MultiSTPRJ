package kopo.poly.service.impl;


import kopo.poly.dto.ContactDTO;
import kopo.poly.persistance.mongodb.impl.ContactMapper;
import kopo.poly.persistance.mongodb.impl.SequenceMapper;
import kopo.poly.service.IContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service("ContactService")
public class ContactService implements IContactService {

    // MongoDB에 저장할 Mapper
    @Resource(name="ContactMapper")
    private ContactMapper ContactMapper;

    // MongoDB에 시퀸스 검색 Mapper
    @Resource(name="SequenceMapper")
    private SequenceMapper sequenceMapper;

    @Override
    public List<ContactDTO> getContactList(ContactDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getContactList Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<ContactDTO> rList = new LinkedList<>();

        // 조회 결과 담기
        rList = ContactMapper.getContactList(pDTO, colNm);

        log.info(this.getClass().getName() + ".getContactList End!");

        return rList;
    }

    @Override
    public int insertContactInfo(ContactDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".insertContactInfo Start!");

        int res = 0;

        // 시퀸스 값 넣기
        pDTO.setContact_seq(sequenceMapper.getSequence(colNm).getCol_seq());


        // 날짜 넣기
        pDTO.setReg_dt(SimpleDateFormat.getDateInstance().format(new Date()));
        pDTO.setChg_dt(SimpleDateFormat.getDateInstance().format(new Date()));

        // MongoDB에 데이터 저장하기
        int success = ContactMapper.insertContactInfo(pDTO, colNm);

        if (success > 0) {

            res = 1;

            // 시퀸스 값 증가
            sequenceMapper.updateSequence(colNm);

        } else {
            res = 0;
        }

//        https://id.twitch.tv/oauth2/token?client_id=dvz38tsu6jppaoguvjdg3fkrq71bpf&client_secret=yhyiv6epe7rhj3rontwv2iqqkkpozr&grant_type=client_credentials

        log.info(this.getClass().getName() + ".insertContactInfo End!");

        return res;
    }

    @Override
    public ContactDTO getContactInfo(ContactDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getContactInfo Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        ContactDTO rDTO = new ContactDTO();

        // 조회 결과 담기
        rDTO = ContactMapper.getContactInfo(pDTO, colNm);

        log.info(this.getClass().getName() + ".getContactInfo End!");

        return rDTO;

    }

    @Override
    public int updateContactInfo(ContactDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".updateContactInfo Start!");

        int res = 0;

        // 최근 날짜 수정하기
        pDTO.setChg_dt(SimpleDateFormat.getDateInstance().format(new Date()));

        // MongoDB에 데이터 새로고침하기
        res = ContactMapper.updateContactInfo(pDTO, colNm);

        log.info(this.getClass().getName() + ".updateContactInfo End!");

        return res;
    }

    @Override
    public int deleteContactInfo(ContactDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".deleteContactInfo Start!");

        int res = 0;

        // MongoDB에 데이터 새로고침하기
        res = ContactMapper.deleteContactInfo(pDTO, colNm);

        log.info(this.getClass().getName() + ".deleteContactInfo End!");

        return res;
    }
}