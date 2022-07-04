package com.haohui.softwarecup.softwarecupapi.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserLog {
    @Id
    private long ULId;
    private UUID uuid;
    private LocalDateTime loginTime;
    private long duration;
    private LocalDateTime logoutTime;
}
