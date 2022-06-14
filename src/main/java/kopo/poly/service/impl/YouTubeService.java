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
import kopo.poly.service.IYouTubeService;
import kopo.poly.dto.YouTubeDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class YouTubeService implements IYouTubeService {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;
    private static YouTube youtube;

    private static void prettyPrint(Iterator<Video> iteratorSearchResults, YouTubeDTO youTubeDto) {

        System.out.println("\n=============================================================");
        System.out.println("=============================================================\n");

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            Video singleVideo = iteratorSearchResults.next();

            // Double checks the kind is video.
            if (singleVideo.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

                System.out.println(" Video Id" + singleVideo.getId());
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out
                        .println(" contentDetails Duration: " + singleVideo.getContentDetails().getDuration());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println("\n-------------------------------------------------------------\n");

                youTubeDto.setThumbnailPath(thumbnail.getUrl());
                youTubeDto.setTitle(singleVideo.getSnippet().getTitle());
                youTubeDto.setVideoId(singleVideo.getId());

            }
        }
    }

    @Override
    public YouTubeDTO get() {
        YouTubeDTO youTubeDto = new YouTubeDTO();

        try {
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-video-duration-get").build();

            //내가 원하는 정보 지정할 수 있어요. 공식 API문서를 참고해주세요.
            YouTube.Videos.List videos = youtube.videos().list("id,snippet,contentDetails");
            videos.setKey("AIzaSyAfJQyw0LqcMkaJi0hCw35NUPyjV7Br-4g");
            videos.setId("WMweEpGlu_U");
            videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED); //조회 최대 갯수.
            List<Video> videoList = videos.execute().getItems();

            if (videoList != null) {
                prettyPrint(videoList.iterator(), youTubeDto);
            }

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return youTubeDto;
    }
}
