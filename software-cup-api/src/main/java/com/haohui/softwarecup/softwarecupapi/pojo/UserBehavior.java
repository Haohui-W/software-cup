package com.haohui.softwarecup.softwarecupapi.pojo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@Builder
public class UserBehavior {
    @Id
    private long UBId;
    private UUID uuid;
    private long nid;
    private long duration;
}
