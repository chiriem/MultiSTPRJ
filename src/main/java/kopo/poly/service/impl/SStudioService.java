package kopo.poly.service.impl;

import kopo.poly.dto.SStudioDTO;
import kopo.poly.persistance.mongodb.impl.SStudioMapper;
import kopo.poly.persistance.mongodb.impl.SequenceMapper;
import kopo.poly.service.ISStudioService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service("SStudioService")
public class SStudioService implements ISStudioService {

    // MongoDB에 저장할 Mapper
    @Resource(name = "SStudioMapper")
    private SStudioMapper sStudioMapper;

    // MongoDB에 시퀸스 검색 Mapper
    @Resource(name = "SequenceMapper")
    private SequenceMapper sequenceMapper;

    @Override
    public int insertYtaddress(SStudioDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".insertYtaddress start!");

        // 입력 성공 : 1, 기타 에러 발생 : 0
        int res = 0;


        // controller에서 값이 정상적으로 못 넘어오는 경우를 대비하기 위해 사용함
        if (pDTO == null) {
            pDTO = new SStudioDTO();
        }

        // 주소 중복 방지를 위해 DB에서 데이터 조회
        SStudioDTO rDTO = sStudioMapper.getYtExists(pDTO, colNm);

        // mapper에서 값이 정상적으로 못 넘어오는 경우를 대비하기 위해 사용함
        if (rDTO == null) {
            rDTO = new SStudioDTO();
        }

        if (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")) {
            res = 2;

            // 중복이 아니므로, 정보 기입 진행함
        } else {

            // 문제 없으면 시퀸스 증가와 함께 넣기
            // 시퀸스 값 넣기
            pDTO.setYt_seq(sequenceMapper.getSequence(colNm).getCol_seq());

            // 정보입력
            int success = sStudioMapper.insertYtAddress(pDTO, colNm);

            // db에 데이터가 등록되었다면(
            if (success > 0) {
                res = 1;

                sequenceMapper.updateSequence(colNm);

            } else {
                res = 0;

            }

        }
        return res;
    }

    @Override
    public List<SStudioDTO> getYtaddress(SStudioDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getYtaddress start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<SStudioDTO> rList = new LinkedList<>();

        // 조회 결과 담기
        rList = sStudioMapper.getYtaddress(pDTO, colNm);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getYtaddress End!");

        return rList;
    }

    @Override
    public SStudioDTO getYoutubeInfo(SStudioDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getYoutubeInfo Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        SStudioDTO rDTO = new SStudioDTO();

        // 조회 결과 담기
        rDTO = sStudioMapper.getYoutubeInfo(pDTO, colNm);

        String yt_seq = rDTO.getYt_seq();
        String yt_addrress = rDTO.getYt_address();

        log.info("yt_seq : " + yt_seq);
        log.info("yt_addrress : " + yt_addrress);

        log.info(this.getClass().getName() + ".getYoutubeInfo End!");

        return rDTO;

    }

}