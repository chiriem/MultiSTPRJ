package kopo.poly.service.impl;


import kopo.poly.dto.NoticeDTO;
import kopo.poly.persistance.mongodb.impl.NoticeMapper;
import kopo.poly.persistance.mongodb.impl.SequenceMapper;
import kopo.poly.service.INoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service("NoticeService")
public class NoticeService implements INoticeService {

    // MongoDB에 저장할 Mapper
    @Resource(name="NoticeMapper")
    private NoticeMapper noticeMapper;

    // MongoDB에 시퀸스 검색 Mapper
    @Resource(name="SequenceMapper")
    private SequenceMapper sequenceMapper;

    @Override
    public List<NoticeDTO> getNoticeList(String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getNoticeList Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<NoticeDTO> rList = new LinkedList<>();

        // 조회 결과 담기
        rList = noticeMapper.getNoticeList(colNm);

        log.info(this.getClass().getName() + ".getNoticeList End!");

        return rList;
    }

    @Override
    public int insertNoticeInfo(NoticeDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".insertNoticeInfo Start!");

        int res = 0;

        // 시퀸스 값 넣기
        pDTO.setNotice_seq(sequenceMapper.getSequence(colNm).getCol_seq());

        // 조회수 넣기
        pDTO.setRead_cnt("0");

        // 날짜 넣기
        pDTO.setReg_dt(SimpleDateFormat.getDateInstance().format(new Date()));
        pDTO.setChg_dt(SimpleDateFormat.getDateInstance().format(new Date()));

        // MongoDB에 데이터 저장하기
        int success = noticeMapper.insertNoticeInfo(pDTO, colNm);

        if (success > 0) {

            res = 1;

            // 시퀸스 값 증가
            sequenceMapper.updateSequence(colNm);

        } else {
            res = 0;
        }

        https://id.twitch.tv/oauth2/token?client_id=dvz38tsu6jppaoguvjdg3fkrq71bpf&client_secret=yhyiv6epe7rhj3rontwv2iqqkkpozr&grant_type=client_credentials

        log.info(this.getClass().getName() + ".insertNoticeInfo End!");

        return res;
    }

    @Override
    public NoticeDTO getNoticeInfo(NoticeDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getNoticeInfo Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        NoticeDTO rDTO = new NoticeDTO();

        // 조회 결과 담기
        rDTO = noticeMapper.getNoticeInfo(pDTO, colNm);

        log.info(this.getClass().getName() + ".getNoticeInfo End!");

        return rDTO;

    }

    @Override
    public int updateNoticeReadCnt(NoticeDTO pDTO,String colNm) throws Exception {

        log.info(this.getClass().getName() + ".updateNoticeReadCnt Start!");

        int res = 0;

        // MongoDB에 데이터 새로고침하기
        res = noticeMapper.updateNoticeReadCnt(pDTO, colNm);

        log.info(this.getClass().getName() + ".updateNoticeReadCnt End!");

        return res;

    }

    @Override
    public int updateNoticeInfo(NoticeDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".updateNoticeInfo Start!");

        int res = 0;

        // 최근 날짜 수정하기
        pDTO.setChg_dt(SimpleDateFormat.getDateInstance().format(new Date()));

        // MongoDB에 데이터 새로고침하기
        res = noticeMapper.updateNoticeInfo(pDTO, colNm);

        log.info(this.getClass().getName() + ".updateNoticeInfo End!");

        return res;
    }

    @Override
    public int deleteNoticeInfo(NoticeDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".deleteNoticeInfo Start!");

        int res = 0;

        // MongoDB에 데이터 새로고침하기
        res = noticeMapper.deleteNoticeInfo(pDTO, colNm);

        log.info(this.getClass().getName() + ".deleteNoticeInfo End!");

        return res;
    }
}