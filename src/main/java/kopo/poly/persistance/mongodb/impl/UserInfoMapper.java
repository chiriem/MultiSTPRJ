package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IUserInfoMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component("UserInfoMapper")
public class UserInfoMapper extends AbstractMongoDBComon implements IUserInfoMapper {

    // 회원 가입하기
    @Override
    public int insertUserInfo(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".insertUserInfo Start!");

        int res = 0;

        // 컬렉션이 없다면 생성하기
        if(!mongodb.collectionExists(colNm)) {

            // 컬렉션 생성
            super.createCollection(colNm);
            // 인덱스 생성
            mongodb.getCollection(colNm).createIndex(Indexes.ascending("user_seq"));

        }

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        log.info(pDTO.getUser_nm());

        // DTO를 Map 데이터타입으로 변경한 뒤, 변경된 Map 데이터 타입을 Document로 변경하기
        col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));

        res = 1;

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".insertUserInfo End!");

        return res;
    }

    @Override
    public UserInfoDTO getUserExists(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getUserExists Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        UserInfoDTO rDTO = new UserInfoDTO();

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("user_id", CmmUtil.nvl(pDTO.getUser_id()));

        // 결과 값을 카운트한다.
        long ll = col.countDocuments(projection);

        // 비교 시작
        rDTO.setExists_yn(ll > 0 ? "Y" : "N");

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getUserExists End!");

        return rDTO;
    }

    @Override
    public UserInfoDTO getUserLoginCheck(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getUserLoginCheck Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        UserInfoDTO rDTO = new UserInfoDTO();

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 찾아야 할 쿼리값 생성
        Document query = new Document();
        query.append("user_id", CmmUtil.nvl(pDTO.getUser_id()));
        query.append("user_pw", CmmUtil.nvl(pDTO.getUser_pw()));

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("user_id", "$user_id");
        projection.append("user_nm", "$user_nm");
        projection.append("age", "$age");

        // 조건에 맞는 값을 검색
        FindIterable<Document> rs = col.find(query).projection(projection);

        // 결과값은 하나니까 첫번째 값만 가져옴.
        Document doc = rs.first();

        // 조회 테스트
        String user_id = CmmUtil.nvl(doc.getString("user_id"));
        String user_nm = CmmUtil.nvl(doc.getString("user_nm"));
        String age = CmmUtil.nvl(doc.getString("age"));

        log.info("user_id : " + user_id);
        log.info("user_nm : " + user_nm);
        log.info("age : " + age);

        // rDTO에 값 집어넣기
        rDTO.setUser_id(user_id);
        rDTO.setUser_nm(user_nm);
        rDTO.setAge(age);

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getUserLoginCheck End!");

        return rDTO;
    }

    public int updateUserInfo(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".updateUserInfo Start!");

        int res = 0;

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("user_id", "$user_id");

        log.info("user_id : " + pDTO.getUser_id());

        // MongoDB의 find 명령어를 통해 조회할 경우 사용함
        // 조회하는 데이터의 양이 적은 경우, find를 사용하고, 데이터양이 많은 경우 무조건 Aggregate 사용한다.
        FindIterable<Document> rs = col.find(new Document("user_id", pDTO.getUser_id())).projection(projection);

        // 한줄로 append해서 수정할 필드 추가해도 되지만, 가독성이 떨어져 줄마다 append 함
        Document updateDoc = new Document();
        updateDoc.append("user_nm", CmmUtil.nvl(pDTO.getUser_nm())); // 기존 필드 수정
        updateDoc.append("user_pw", CmmUtil.nvl(pDTO.getUser_pw())); // 기존 필드 수정
        updateDoc.append("age", CmmUtil.nvl(pDTO.getAge())); // 기존 필드 수정
        updateDoc.append("chg_id", CmmUtil.nvl(pDTO.getChg_id())); // 기존 필드 수정
        updateDoc.append("chg_dt", CmmUtil.nvl(pDTO.getChg_dt())); // 기존 필드 수정

        // DTO를 Map 데이터타입으로 변경한 뒤, 변경된 Map 데이터 타입을 Document로 변경하기
        rs.forEach(doc -> col.updateOne(doc, new Document("$set", updateDoc)));

        res = 1;

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".updateUserInfo End!");

        return res;
    }

    public int deleteUserInfo(UserInfoDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".updateUserInfo End!");

        int res = 0;

        // 조회 결과를 전달하기 위한 객체 생성하기
        NoticeDTO rDTO = new NoticeDTO();

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회할 조건
        Document query = new Document();
        query.append("user_seq", CmmUtil.nvl(pDTO.getUser_seq()));

        // MongoDB 데이터 삭제는 반드시 컬렉션으 조회하고, 조회된 ObjectID를 기반으로 데이터를 삭제함
        // MongoDB 환경은 분산환경(Sharding)으로 구성될 수 있기 때문에 정확한 PX에 매핑하기 위해서임
        FindIterable<Document> rs = col.find(query);

        // 전체 컬랙션에 있는 데이터를 삭제하기
        rs.forEach(doc -> col.deleteOne(doc));

        res = 1;

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".updateUserInfo End!");
        return res;
    }


}