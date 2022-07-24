package com.haohui.softwarecup.softwarecupapi.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@Builder
public class User {
    @Id
    private UUID uuid;
    private String nickName;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;
    private String keyWords;
}
