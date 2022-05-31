package com.haohui.softwarecup.dbmanager.service;

import com.haohui.softwarecup.dbmanager.dao.NewsDao;
import com.haohui.softwarecup.dbmanager.pojo.News;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class DBInputZipFileService {
    private final NewsDao newsDao;
    private final Random random=new Random();
    private final LocalDateTime threeYearsAgo = LocalDateTime.of(2019, 5, 31, 0, 0, 0);
    private int countSave=0;

    /**
     * 获取存储的新闻个数
     * @return 新闻个数
     */
    public int getCountSave() {
        return countSave;
    }

    public DBInputZipFileService(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    /**
     * 使用dao将新闻存储到数据库中
     * @param title 新闻标题
     * @param content 新闻内容
     * @param type 新闻类型
     */
    private void saveInDB(String title,StringBuffer content,String type){
        // 随机一个3年内的时间
        int aSecondInThreeYears = random.nextInt(3 * 365 * 24 * 60 * 60);

        News news = new News(title, content.toString(), type, threeYearsAgo.plusSeconds(aSecondInThreeYears));
        Mono<News> newsMono = newsDao.save(news);
        newsMono.block();
        countSave++;
    }

    /**
     * 读取压缩包的每个zipEntry
     * 如果是新闻文件，第一行作为标题，后面作为内容，
     * 从entry的名字中获取类型，存到数据库中
     * @throws Exception 异常
     */
    public void inputAndSave() throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Haohui\\软件杯\\THUCNews.zip");
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             ZipInputStream zipInputStream = new ZipInputStream(bufferedInputStream, StandardCharsets.UTF_8)) {

            ZipEntry nextEntry;

            // 如果还有下一个entry就一直循环
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                String entryName = nextEntry.getName(); //获取当前entry的名字，实际为路径+文件名

//                System.out.println(entryName);

                if(!nextEntry.isDirectory() && !entryName.startsWith("__MAC")){ // 如果entry是目录或者不是放置的那个新闻文件夹，就跳过

                    Scanner scanner = new Scanner(zipInputStream);
                    String title = scanner.nextLine(); // 第一行作为新闻标题
                    StringBuffer content=new StringBuffer(); // 构造新闻内容
                    // 新闻类型夹在两个 '/' 之间
                    String type= entryName.substring(entryName.indexOf('/')+1,entryName.lastIndexOf('/'));
//                    System.out.println(type);
                    while (scanner.hasNextLine()){
                        // 构建内容
                        content.append(scanner.nextLine()).append("\n");
                    }
                    // 存储到数据库中
                    saveInDB(title,content,type);
                }
//                Thread.sleep(1000);
            }

        }


    }

}
