package kopo.poly.service.impl;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.Video;
import kopo.poly.dto.SStudioDTO;
import kopo.poly.dto.YouTubeDTO;
import kopo.poly.persistance.mongodb.impl.SStudioMapper;
import kopo.poly.persistance.mongodb.impl.SequenceMapper;
import kopo.poly.service.ISStudioService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;
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

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;
    private static YouTube youtube;


    private int prettyPrint(Iterator<Video> iteratorSearchResults, SStudioDTO pDTO, String colNm) throws Exception {

        System.out.println("\n=============================================================");
        System.out.println("=============================================================\n");

        int res = 0;

        SStudioDTO rDTO = new SStudioDTO();

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            Video singleVideo = iteratorSearchResults.next();

            // Double checks the kind is video.
            if (singleVideo.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

                System.out.println(" Video Id : " + singleVideo.getId());
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out
                        .println(" contentDetails Duration: " + singleVideo.getContentDetails().getDuration());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println("\n-------------------------------------------------------------\n");

                // 시퀀스 증가 및 시퀀스 넣기
                pDTO.setYt_seq(sequenceMapper.getSequence(colNm).getCol_seq());

                sequenceMapper.updateSequence(colNm);

                rDTO.setThumbnailPath(thumbnail.getUrl());  //썸네일 url
                rDTO.setTitle(singleVideo.getSnippet().getTitle()); //제목
                rDTO.setYt_address(pDTO.getYt_address()); //video_id
                rDTO.setUser_id(pDTO.getUser_id()); //사용자 아이디
                rDTO.setYt_seq(pDTO.getYt_seq());

            }
        }

        res = sStudioMapper.insertYtAddress(rDTO, colNm);

        return res;
    }

    @Override
    public int insertYtaddress(SStudioDTO pDTO, String colNm) throws Exception {

        int res = 0;

        try {
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-video-duration-get").build();

            //내가 원하는 정보 지정할 수 있어요. 공식 API문서를 참고해주세요.
            YouTube.Videos.List videos = youtube.videos().list("id,snippet,contentDetails");
            videos.setKey("AIzaSyAfJQyw0LqcMkaJi0hCw35NUPyjV7Br-4g");
            videos.setId(pDTO.getYt_address());
            videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED); //조회 최대 갯수.
            List<Video> videoList = videos.execute().getItems();

//            for (Video i : videoList){
//                System.out.println(i);
//            } //리스트에 뭐가 담아있는지 확인할려고 사용
            //      System.out.println(videoList);

            if (videoList != null) {
                res = prettyPrint(videoList.iterator(), pDTO, colNm);


            }

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        log.info("res : " + res);

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