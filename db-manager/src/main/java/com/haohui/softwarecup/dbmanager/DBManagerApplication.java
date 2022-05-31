package com.haohui.softwarecup.dbmanager;

import com.haohui.softwarecup.dbmanager.service.DBInputZipFileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class DBManagerApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DBManagerApplication.class, args);

        DBInputZipFileService dbInputZipFileService = context.getBean(DBInputZipFileService.class);
        try {
            dbInputZipFileService.inputAndSave();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("存储"+dbInputZipFileService.getCountSave()+"个新闻。");
        }
    }
}
