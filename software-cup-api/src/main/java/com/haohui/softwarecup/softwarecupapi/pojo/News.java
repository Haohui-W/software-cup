package com.haohui.softwarecup.softwarecupapi.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class News {
    @Id
    private Long nid;
    private String title;
    private String content;
    private String type;
    private LocalDateTime publishedTime;
    private List<String> keyWords;
    private Long likes;
}
