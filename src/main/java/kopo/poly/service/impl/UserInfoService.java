package kopo.poly.service.impl;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mongodb.impl.SequenceMapper;
import kopo.poly.persistance.mongodb.impl.UserInfoMapper;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service("UserInfoService")
public class UserInfoService implements IUserInfoService {

    // MongoDB에 저장할 Mapper
    @Resource(name = "UserInfoMapper")
    private UserInfoMapper userInfoMapper;

    // MongoDB에 시퀸스 검색 Mapper
    @Resource(name="SequenceMapper")
    private SequenceMapper sequenceMapper;

    @Override
    public int insertUserInfo(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".insertUserInfo start!");

        // 회원가입 성공 : 1, 아이디 중복으로인한 가입 취소 : 2, 기타 에러 발생 : 0
        int res = 0;

        // 권한 넣기
        pDTO.setUser_auth("user");

        // 수정자 넣기
        pDTO.setReg_id(pDTO.getUser_id());
        pDTO.setChg_id(pDTO.getUser_id());

        // 날짜 넣기
        pDTO.setReg_dt(SimpleDateFormat.getDateInstance().format(new Date()));
        pDTO.setChg_dt(SimpleDateFormat.getDateInstance().format(new Date()));


        // controller에서 값이 정상적으로 못 넘어오는 경우를 대비하기 위해 사용함
        if (pDTO == null) {
            pDTO = new UserInfoDTO();
        }

        // 회원 가입 중복 방지를 위해 DB에서 데이터 조회
        UserInfoDTO rDTO = userInfoMapper.getUserExists(pDTO, colNm);

        // mapper에서 값이 정상적으로 못 넘어오는 경우를 대비하기 위해 사용함
        if (rDTO == null) {
            rDTO = new UserInfoDTO();
        }

        // 중복된 회원정보가 있는 경우, 결과값을 2로 변경하고, 더 이상 작업을 진행하지 않음
        if (CmmUtil.nvl(rDTO.getExists_yn()).equals("Y")) {
            res = 2;

            // 회원가입이 중복이 아니므로, 회원가입 진행함
        } else {

            // 문제 없으면 시퀸스 증가와 함께 넣기
            // 시퀸스 값 넣기
            pDTO.setUser_seq(sequenceMapper.getSequence(colNm).getCol_seq());

            // 회원가입
            int success = userInfoMapper.insertUserInfo(pDTO, colNm);

            // db에 데이터가 등록되었다면(회원가입 성공했다면....
            if (success > 0) {
                res = 1;

                sequenceMapper.updateSequence(colNm);

            } else {
                res = 0;

            }

        }

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".insertUserInfo End!");

        return res;
    }

    /**
     * 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기
     *
     * @param pDTO UserInfoDTO 로그인을 위한 회원아이디, 비밀번호
     * @param colNm 조회할 컬렉션 이름
     * @return UserInfoDTO 로그인된 회원아이디 정보
     */
    @Override
    public UserInfoDTO getUserLoginCheck(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getUserLoginCheck start!");

        // 로그인 성공 : 1, 실패 : 0
        int res = 0;

        // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 mapper 호출하기
        UserInfoDTO rDTO = userInfoMapper.getUserLoginCheck(pDTO, colNm);

        if (rDTO == null) {
            rDTO = new UserInfoDTO();

        }

        /*
         * #######################################################
         *                    로그인 성공 여부 체크 시작!!
         * #######################################################
         */

        /*
         * userInfoMapper로 부터 SELECT 쿼리의 결과로 회원아이디를 받아왔다면, 로그인 성공!!
         *
         * DTO의 변수에 값이 있는지 확인하기 처리속도 측면에서 가장 좋은 방법은 변수의 길이를 가져오는 것이다.
         * 따라서  .length() 함수를 통해 회원아이디의 글자수를 가져와 0보다 큰지 비교한다.
         * 0보다 크다면, 글자가 존재하는 것이기 때문에 값이 존재한다.
         */
        if (CmmUtil.nvl(rDTO.getUser_id()).length()>0) {
            res = 1;
        }

        /*
         * #######################################################
         *                    로그인 성공 여부 체크 끝!!
         * #######################################################
         */

        log.info("res : " + res);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getUserLoginCheck End!");

        return rDTO;
    }

    @Override
    public UserInfoDTO getUserIdCheck(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getUserIdCheck start!");

        // 로그인 성공 : 1, 실패 : 0
        int res = 0;

        // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 mapper 호출하기
        UserInfoDTO rDTO = userInfoMapper.getUserIdCheck(pDTO, colNm);

        if (rDTO == null) {
            rDTO = new UserInfoDTO();

        }

        /*
         * #######################################################
         *                    성공 여부 체크 시작!!
         * #######################################################
         */

        /*
         * userInfoMapper로 부터 SELECT 쿼리의 결과로 회원아이디를 받아왔다면, 로그인 성공!!
         *
         * DTO의 변수에 값이 있는지 확인하기 처리속도 측면에서 가장 좋은 방법은 변수의 길이를 가져오는 것이다.
         * 따라서  .length() 함수를 통해 회원아이디의 글자수를 가져와 0보다 큰지 비교한다.
         * 0보다 크다면, 글자가 존재하는 것이기 때문에 값이 존재한다.
         */
        if (CmmUtil.nvl(rDTO.getUser_id()).length()>0) {
            res = 1;
        }

        /*
         * #######################################################
         *                    성공 여부 체크 끝!!
         * #######################################################
         */

        log.info("res : " + res);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getUserIdCheck End!");

        return rDTO;
    }

    @Override
    public int updateUserInfo(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".updateUserInfo Start!");

        int res = 0;

        // 수정자 넣기
        pDTO.setChg_id(pDTO.getUser_id());

        log.info("chg_id : " + pDTO.getChg_id() );

        // 날짜 넣기
        pDTO.setChg_dt(SimpleDateFormat.getDateInstance().format(new Date()));

        log.info("chg_dt : " + pDTO.getChg_dt() );

        // controller에서 값이 정상적으로 못 넘어오는 경우를 대비하기 위해 사용함
        if (pDTO == null) {
            pDTO = new UserInfoDTO();
        }

        // 회원가입
        int success = userInfoMapper.updateUserInfo(pDTO, colNm);

        // db에 데이터가 등록되었다면(회원가입 성공했다면....
        if (success > 0) {

            res = 1;

        } else {

            res = 0;

        }

//        }
        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".updateUserInfo End!");

        return res;
    }

    @Override
    public int deleteUserInfo(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".deleteUser Start!");

        int res = 0;


        // controller에서 값이 정상적으로 못 넘어오는 경우를 대비하기 위해 사용함
        if (pDTO == null) {
            pDTO = new UserInfoDTO();
        }

        // 삭제 성공 여부
        int success = userInfoMapper.deleteUserInfo(pDTO, colNm);

        // 삭제 성공했다면....
        if (success > 0) {

            res = 1;

        } else {

            res = 0;

        }

//        }
        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".deleteUser End!");

        return res;

    }

}