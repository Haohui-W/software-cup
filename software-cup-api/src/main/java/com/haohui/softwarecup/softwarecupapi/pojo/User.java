package com.haohui.softwarecup.softwarecupapi.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class User {
    @Id
    private UUID uuid;
    private String nickName;
    private String username;
    private String userPassword;
    private List<String> keyWords;
    private LocalDate birthDay;
}
