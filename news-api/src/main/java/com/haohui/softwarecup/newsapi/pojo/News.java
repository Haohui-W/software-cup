package com.haohui.softwarecup.newsapi.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class News {
    private Long nid;
    private String title;
    private String content;
    private String type;
    private LocalDateTime publishedTime;
}
