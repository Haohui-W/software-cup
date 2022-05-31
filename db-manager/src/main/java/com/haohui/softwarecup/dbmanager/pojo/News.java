package com.haohui.softwarecup.dbmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private Long nid;
    private String title;
    private String content;
    private String type;
    private LocalDateTime publishedTime;

    public News(String title, String content, String type, LocalDateTime publishedTime) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.publishedTime = publishedTime;
    }
}
