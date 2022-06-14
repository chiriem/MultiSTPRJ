package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class YouTubeDTO {

    String title; // 동영상 제목
    String thumbnailPath; //동영상 썸네일 경로
    String videoId; // 동영상 식별 ID

//    @Builder(toBuilder = true)
//    public YouTubeDTO(String title, String thumbnailPath, String videoId) {
//        this.title = title;
//        this.thumbnailPath = thumbnailPath;
//        this.videoId = videoId;
//    }
}
