package com.haohui.softwarecup.softwarecupapi.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class AppConfig {
    @Id
    private long ACId;
    private long timePullNews;
}
