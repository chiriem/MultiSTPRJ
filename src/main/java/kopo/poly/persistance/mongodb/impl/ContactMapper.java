package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import kopo.poly.dto.ContactDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IContactMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("ContactMapper")
public class ContactMapper extends AbstractMongoDBComon implements IContactMapper {

    @Override
    public List<ContactDTO> getContactList(ContactDTO pDTO,String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getContactList Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<ContactDTO> rList = new LinkedList<>();

        // 컬렉션이 없다면 생성하기
        if(!mongodb.collectionExists(colNm)) {

            // 컬렉션 생성
            super.createCollection(colNm);
            // 인덱스 생성
            mongodb.getCollection(colNm).createIndex(Indexes.ascending("Contact_seq"));

        }

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 찾아야 할 쿼리값 생성
        Document query = new Document();
        query.append("user_id", CmmUtil.nvl(pDTO.getUser_id()));

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("contact_seq", "$contact_seq");
        projection.append("title", "$title");
        projection.append("read_cnt", "$read_cnt");
        projection.append("reg_id", "$reg_id");
        projection.append("reg_dt", "$reg_dt");

        // MongoDB는 무조건 ObjectID가 자동생성되며, ObjectID는 사용하지 않을 때, 조회할 필요가 없음.
        projection.append("_id", 0);

        // MongoDB의 find 명령어를 통해 조회할 경우 사용함
        // 조회하는 데이터의 양이 적은 경우, find를 사용하고, 데이터양이 많은 경우 무조건 Aggregate 사용한다.
        FindIterable<Document> rs = col.find(query).projection(projection);

        for (Document doc : rs) {

            if (doc == null) {

                doc = new Document();

            }

            // 조회 테스트
            String contact_seq = CmmUtil.nvl(doc.getString("contact_seq"));
            String title = CmmUtil.nvl(doc.getString("title"));
            String read_cnt = CmmUtil.nvl(doc.getString("read_cnt"));
            String reg_id = CmmUtil.nvl(doc.getString("reg_id"));
            String reg_dt = CmmUtil.nvl(doc.getString("reg_dt"));

            log.info("contact_seq : " + contact_seq);
            log.info("title : " + title);
            log.info("read_cnt : " + read_cnt);
            log.info("reg_id : " + reg_id);
            log.info("reg_dt : " + reg_dt);

            ContactDTO rDTO = new ContactDTO();

            rDTO.setContact_seq(contact_seq);
            rDTO.setTitle(title);
            rDTO.setReg_id(reg_id);
            rDTO.setReg_dt(reg_dt);

            // 레코드 결과를 List에 저장하기
            rList.add(rDTO);

        }

        log.info(this.getClass().getName() + ".getContactList End!");

        return rList;
    }

    @Override
    public List<ContactDTO> getContactListforadmin(String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getContactListforadmin Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<ContactDTO> rList = new LinkedList<>();

        // 컬렉션이 없다면 생성하기
        if(!mongodb.collectionExists(colNm)) {

            // 컬렉션 생성
            super.createCollection(colNm);
            // 인덱스 생성
            mongodb.getCollection(colNm).createIndex(Indexes.ascending("Contact_seq"));

        }

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 찾아야 할 쿼리값 생성
        Document query = new Document();

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("contact_seq", "$contact_seq");
        projection.append("title", "$title");
        projection.append("read_cnt", "$read_cnt");
        projection.append("user_id", "$user_id");
        projection.append("reg_id", "$reg_id");
        projection.append("reg_dt", "$reg_dt");

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
            String contact_seq = CmmUtil.nvl(doc.getString("contact_seq"));
            String title = CmmUtil.nvl(doc.getString("title"));
            String user_id = CmmUtil.nvl(doc.getString("user_id"));
            String reg_id = CmmUtil.nvl(doc.getString("reg_id"));
            String reg_dt = CmmUtil.nvl(doc.getString("reg_dt"));

            log.info("contact_seq : " + contact_seq);
            log.info("title : " + title);
            log.info("user_id : " + user_id);
            log.info("reg_id : " + reg_id);
            log.info("reg_dt : " + reg_dt);

            ContactDTO rDTO = new ContactDTO();

            rDTO.setContact_seq(contact_seq);
            rDTO.setTitle(title);
            rDTO.setUser_id(user_id);
            rDTO.setReg_id(reg_id);
            rDTO.setReg_dt(reg_dt);

            // 레코드 결과를 List에 저장하기
            rList.add(rDTO);

        }

        log.info(this.getClass().getName() + ".getContactList End!");

        return rList;
    }

    @Override
    public int insertContactInfo(ContactDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".insertContactInfo Start!");

        int res = 0;

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // DTO를 Map 데이터타입으로 변경한 뒤, 변경된 Map 데이터타입을 Document로 변경하기
        col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));

        res = 1;

        log.info(this.getClass().getName() + ".insertContactInfo End!");

        return res;
    }

    @Override
    public ContactDTO getContactInfo(ContactDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".getContactInfo Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        ContactDTO rDTO = new ContactDTO();

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("contact_seq", "$contact_seq");
        projection.append("title", "$title");
        projection.append("contents", "$contents");
        projection.append("user_id", "$user_id");
        projection.append("reg_id", "$reg_id");
        projection.append("reg_dt", "$reg_dt");
        projection.append("chg_id", "$chg_id");
        projection.append("chg_dt", "$chg_dt");

        // MongoDB는 무조건 ObjectID가 자동생성되며, ObjectID는 사용하지 않을 때, 조회할 필요가 없음.
        projection.append("_id", 0);

        // MongoDB의 find 명령어를 통해 조회할 경우 사용함
        // 조회하는 데이터의 양이 적은 경우, find를 사용하고, 데이터양이 많은 경우 무조건 Aggregate 사용한다.
        FindIterable<Document> rs = col.find(new Document("contact_seq", pDTO.getContact_seq())).projection(projection);

        for (Document doc : rs) {

            if (doc == null) {

                doc = new Document();

            }

            // 조회 테스트
            String contact_seq = CmmUtil.nvl(doc.getString("contact_seq"));
            String title = CmmUtil.nvl(doc.getString("title"));
            String contents = CmmUtil.nvl(doc.getString("contents"));
            String user_id = CmmUtil.nvl(doc.getString("user_id"));
            String reg_id = CmmUtil.nvl(doc.getString("reg_id"));
            String reg_dt = CmmUtil.nvl(doc.getString("reg_dt"));
            String chg_id = CmmUtil.nvl(doc.getString("chg_id"));
            String chg_dt = CmmUtil.nvl(doc.getString("chg_dt"));

            log.info("Contact_seq : " + contact_seq);
            log.info("title : " + title);
            log.info("reg_id : " + reg_id);
            log.info("reg_dt : " + reg_dt);

            // rDTO에 값 집어넣기
            rDTO.setContact_seq(contact_seq);
            rDTO.setTitle(title);
            rDTO.setContents(contents);
            rDTO.setUser_id(user_id);
            rDTO.setReg_id(reg_id);
            rDTO.setReg_dt(reg_dt);
            rDTO.setChg_id(chg_id);
            rDTO.setChg_dt(chg_dt);

        }

        log.info(this.getClass().getName() + ".getContactInfo End!");

        return rDTO;
    }



    @Override
    public int updateContactInfo(ContactDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".updateContactInfo Start!");

        int res = 0;

        // 조회 결과를 전달하기 위한 객체 생성하기
        ContactDTO rDTO = new ContactDTO();

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함)
        Document projection = new Document();
        projection.append("contact_seq", "$contact_seq");

        // MongoDB의 find 명령어를 통해 조회할 경우 사용함
        // 조회하는 데이터의 양이 적은 경우, find를 사용하고, 데이터양이 많은 경우 무조건 Aggregate 사용한다.
        FindIterable<Document> rs = col.find(new Document("contact_seq", pDTO.getContact_seq())).projection(projection);

        // 한줄로 append해서 수정할 필드 추가해도 되지만, 가독성이 떨어져 줄마다 append 함
        Document updateDoc = new Document();
        updateDoc.append("title", CmmUtil.nvl(pDTO.getTitle())); // 기존 필드 수정
        updateDoc.append("contents", CmmUtil.nvl(pDTO.getContents())); // 기존 필드 수정
        updateDoc.append("user_id", CmmUtil.nvl(pDTO.getUser_id())); // 기존 필드 수정
        updateDoc.append("chg_id", CmmUtil.nvl(pDTO.getUser_id())); // 기존 필드 수정
        updateDoc.append("chg_dt", CmmUtil.nvl(pDTO.getChg_dt())); // 기존 필드 수정

        rs.forEach(doc -> col.updateOne(doc, new Document("$set", updateDoc)));

        log.info(this.getClass().getName() + ".updateContactInfo End!");

        return res;

    }

    @Override
    public int deleteContactInfo(ContactDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".deleteContactInfo Start!");

        int res = 0;

        // 조회 결과를 전달하기 위한 객체 생성하기
        ContactDTO rDTO = new ContactDTO();

        // MongoDB 컬렉션 지정하기
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회할 조건
        Document query = new Document();
        query.append("contact_seq", CmmUtil.nvl(pDTO.getContact_seq()));

        // MongoDB 데이터 삭제는 반드시 컬렉션으 조회하고, 조회된 ObjectID를 기반으로 데이터를 삭제함
        // MongoDB 환경은 분산환경(Sharding)으로 구성될 수 있기 때문에 정확한 PX에 매핑하기 위해서임
        FindIterable<Document> rs = col.find(query);

        // 전체 컬랙션에 있는 데이터를 삭제하기
        rs.forEach(doc -> col.deleteOne(doc));

        log.info(this.getClass().getName() + ".deleteContactInfo End!");

        return res;

    }
}