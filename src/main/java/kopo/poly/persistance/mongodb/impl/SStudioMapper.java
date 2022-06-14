package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import kopo.poly.dto.SStudioDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.ISStudioMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("SStudioMapper")
public class SStudioMapper extends AbstractMongoDBComon implements ISStudioMapper {

    // 유튜브 주소 입력하기
    @Override
    public int insertYtAddress(SStudioDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".insertYtAddress Start!");

        int res = 0;

        // 컬렉션이 없다면 생성하기
        if (!mongodb.collectionExists(colNm)) {

            // 컬렉션 생성
            super.createCollection(colNm);
            // 인덱스 생성
            mongodb.getCollection(colNm).createIndex(Indexes.ascending("yt_seq"));

        }

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        log.info(pDTO.getUser_id());

        // DTO를 Map 데이터타입으로 변경한 뒤, 변경된 Map 데이터 타입을 Document로 변경하기
        col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));

        res = 1;

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".insertYtAddress End!");

        return res;
    }

    @Override
    public SStudioDTO getYtExists(SStudioDTO pDTO, String colNm) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getYtExists Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        SStudioDTO rDTO = new SStudioDTO();

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("yt_address", CmmUtil.nvl(pDTO.getYt_address()));

        // 결과 값을 카운트한다.
        long ll = col.countDocuments(projection);

        // 비교 시작
        rDTO.setExists_yn(ll > 0 ? "Y" : "N");

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getYtExists End!");

        return rDTO;
    }

    @Override
    public List<SStudioDTO> getYtaddress(SStudioDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getYtaddress Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<SStudioDTO> rList = new LinkedList<>();

        // 컬렉션이 없다면 생성하기
        if (!mongodb.collectionExists(colNm)) {

            // 컬렉션 생성
            super.createCollection(colNm);
            // 인덱스 생성
            mongodb.getCollection(colNm).createIndex(Indexes.ascending("notice_seq"));

        }

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 찾아야 할 쿼리값 생성
        Document query = new Document();
        query.append("user_id", CmmUtil.nvl(pDTO.getUser_id()));

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("yt_seq", "$yt_seq");
        projection.append("user_id", "$user_id");
        projection.append("yt_address", "$yt_address");

//        // MongoDB는 무조건 ObjectID가 자동생성되며, ObjectID는 사용하지 않을 때, 조회할 필요가 없음.
//        projection.append("_id", 0);

        // MongoDB의 find 명령어를 통해 조회할 경우 사용함
        // 조회하는 데이터의 양이 적은 경우, find를 사용하고, 데이터양이 많은 경우 무조건 Aggregate 사용한다.
        FindIterable<Document> rs = col.find(query).projection(projection);

        for (Document doc : rs) {

            if (doc == null) {

                doc = new Document();

            }

            // 조회 테스트
            String yt_seq = CmmUtil.nvl(doc.getString("yt_seq"));
            String user_id = CmmUtil.nvl(doc.getString("user_id"));
            String yt_address = CmmUtil.nvl(doc.getString("yt_address"));
            log.info("yt_seq : " + yt_seq);
            log.info("user_id : " + user_id);
            log.info("yt_address : " + yt_address);

            SStudioDTO rDTO = new SStudioDTO();

            rDTO.setYt_seq(yt_seq);
            rDTO.setUser_id(user_id);
            rDTO.setYt_address(yt_address);

            // 레코드 결과를 List에 저장하기
            rList.add(rDTO);

        }

        log.info(this.getClass().getName() + ".gettaddress End!");

        return rList;
    }

    @Override
    public SStudioDTO getYoutubeInfo(SStudioDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getYoutubeInfo Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        SStudioDTO rDTO = new SStudioDTO();

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("yt_seq", "$yt_seq");
        projection.append("yt_address", "$yt_address");

        // MongoDB는 무조건 ObjectID가 자동생성되며, ObjectID는 사용하지 않을 때, 조회할 필요가 없음.
        projection.append("_id", 0);

        // MongoDB의 find 명령어를 통해 조회할 경우 사용함
        // 조회하는 데이터의 양이 적은 경우, find를 사용하고, 데이터양이 많은 경우 무조건 Aggregate 사용한다.
        FindIterable<Document> rs = col.find(new Document("yt_seq", pDTO.getYt_seq())).projection(projection);

        // 결과값은 하나니까 첫번째 값만 가져옴.
        Document doc = rs.first();

        // 조회 테스트
        String yt_seq = CmmUtil.nvl(doc.getString("yt_seq"));
        String yt_address = CmmUtil.nvl(doc.getString("yt_address"));

        log.info("yt_seq : " + yt_seq);
        log.info("yt_address : " + yt_address);

        // rDTO에 값 집어넣기
        rDTO.setYt_seq(yt_seq);
        rDTO.setYt_address(yt_address);

        log.info(this.getClass().getName() + ".getYoutubueInfo End!");

        return rDTO;

    }

}
