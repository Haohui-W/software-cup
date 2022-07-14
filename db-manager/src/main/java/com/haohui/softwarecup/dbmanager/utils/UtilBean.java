package com.haohui.softwarecup.dbmanager.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Haohui
 * 创建时间 2022/7/14/18:15
 */
@Component
@Data
public class UtilBean {
    @Value("${app.aTimeStr}")
    private String aTimeStr;

    @Value("${app.zipFileLocation}")
    private Resource resource;

    private final Random random = new Random();
}
