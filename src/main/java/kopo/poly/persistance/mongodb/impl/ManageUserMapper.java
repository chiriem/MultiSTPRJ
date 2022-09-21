package kopo.poly.persistance.mongodb.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import kopo.poly.dto.ManageUserDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IManageUserMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component("ManageUserMapper")
public class ManageUserMapper extends AbstractMongoDBComon implements IManageUserMapper {

    @Override
    public List<ManageUserDTO> getUserList(String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getUserList Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<ManageUserDTO> rList = new LinkedList<>();

        // 컬렉션이 없다면 생성하기
        if(!mongodb.collectionExists(colNm)) {

            // 컬렉션 생성
            super.createCollection(colNm);
            // 인덱스 생성
            mongodb.getCollection(colNm).createIndex(Indexes.ascending("user_seq"));

        }

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("user_seq", "$user_seq");
        projection.append("user_id", "$user_id");
        projection.append("user_nm", "$user_nm");
        projection.append("user_auth", "$user_auth");
        projection.append("age", "$age");

        // MongoDB는 무조건 ObjectID가 자동생성되며, ObjectID는 사용하지 않을 때, 조회할 필요가 없음.
        projection.append("_id", 0);

        // MongoDB의 find 명령어를 통해 조회할 경우 사용함
        // 조회하는 데이터의 양이 적은 경우, find를 사용하고, 데이터양이 많은 경우 무조건 Aggregate 사용한다.
        FindIterable<Document> rs = col.find(new Document()).projection(projection);

        for (Document doc : rs) {

            if (doc == null) {

                doc = new Document();

            }

            // 조회 테스트
            String user_seq = CmmUtil.nvl(doc.getString("user_seq"));
            String user_id = CmmUtil.nvl(doc.getString("user_id"));
            String user_nm= CmmUtil.nvl(doc.getString("user_nm"));
            String user_auth = CmmUtil.nvl(doc.getString("user_auth"));
            String age = CmmUtil.nvl(doc.getString("age"));

            log.info("user_seq : " + user_seq);
            log.info("user_id : " + user_id);
            log.info("user_nm : " + user_nm);
            log.info("user_auth : " + user_auth);
            log.info("age : " + age);

            ManageUserDTO rDTO = new ManageUserDTO();

            rDTO.setUser_seq(user_seq);
            rDTO.setUser_id(user_id);
            rDTO.setUser_nm(user_nm);
            rDTO.setUser_auth(user_auth);
            rDTO.setAge(age);

            // 레코드 결과를 List에 저장하기
            rList.add(rDTO);

        }

        log.info(this.getClass().getName() + ".getUserList End!");

        return rList;
    }

    @Override
    public ManageUserDTO getUserInfo(ManageUserDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getUserInfo Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        ManageUserDTO rDTO = new ManageUserDTO();

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("user_seq", "$user_seq");
        projection.append("user_id", "$user_id");
        projection.append("user_nm", "$user_nm");
        projection.append("user_auth", "$user_auth");
        projection.append("age", "$age");

        // MongoDB는 무조건 ObjectID가 자동생성되며, ObjectID는 사용하지 않을 때, 조회할 필요가 없음.
        projection.append("_id", 0);

        // MongoDB의 find 명령어를 통해 조회할 경우 사용함
        // 조회하는 데이터의 양이 적은 경우, find를 사용하고, 데이터양이 많은 경우 무조건 Aggregate 사용한다.
        FindIterable<Document> rs = col.find(new Document("user_seq", pDTO.getUser_seq())).projection(projection);

        for (Document doc : rs) {

            if (doc == null) {

                doc = new Document();

            }

            // 조회 테스트
            String user_seq = CmmUtil.nvl(doc.getString("user_seq"));
            String user_id = CmmUtil.nvl(doc.getString("user_id"));
            String user_nm = CmmUtil.nvl(doc.getString("user_nm"));
            String user_auth = CmmUtil.nvl(doc.getString("user_auth"));
            String age = CmmUtil.nvl(doc.getString("age"));

            log.info("user_seq : " + user_seq);
            log.info("user_id : " + user_id);
            log.info("user_nm : " + user_nm);
            log.info("user_auth : " + user_auth);
            log.info("age : " + age);

            // rDTO에 값 집어넣기
            rDTO.setUser_seq(user_seq);
            rDTO.setUser_id(user_id);
            rDTO.setUser_nm(user_nm);
            rDTO.setUser_auth(user_auth);
            rDTO.setAge(age);

        }

        log.info(this.getClass().getName() + ".getUserInfo End!");

        return rDTO;
    }


    @Override
    public int deleteUserInfo(ManageUserDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".deleteUserInfo Start!");

        int res = 0;

        // 조회 결과를 전달하기 위한 객체 생성하기
        ManageUserDTO rDTO = new ManageUserDTO();

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

        log.info(this.getClass().getName() + ".deleteUserInfo End!");

        return res;

    }
}