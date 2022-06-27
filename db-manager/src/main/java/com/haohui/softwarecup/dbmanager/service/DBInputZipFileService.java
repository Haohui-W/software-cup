package com.haohui.softwarecup.dbmanager.service;

import com.haohui.softwarecup.dbmanager.dao.NewsDao;
import com.haohui.softwarecup.dbmanager.pojo.News;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class DBInputZipFileService {
    private final NewsDao newsDao;
    private final Random random = new Random();
    private final LocalDateTime threeYearsAgo = LocalDateTime.of(2019, 5, 31, 0, 0, 0);
    private int countSave = 0;

    /**
     * 获取存储的新闻个数
     *
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
     *
     * @param title   新闻标题
     * @param content 新闻内容
     * @param type    新闻类型
     */
    private void saveInDB(String title, StringBuffer content, String type) {
        // 随机一个10年内的秒
        int aSecondInThreeYears = random.nextInt(10 * 365 * 24 * 60 * 60);

        // 构造一条新闻，其标题、内容和类型为方法参数，发布时间为三年前+一个随机的十年内的秒。其中content去除掉前后的空白字符
        News news = News
                .builder()
                .title(title)
                .content(content.toString().strip())
                .type(type)
                .publishedTime(threeYearsAgo.plusSeconds(aSecondInThreeYears))
                .build();

        // 调用newsDao存储下这个新闻
        Mono<News> newsMono = newsDao.save(news);

        // 让mono产生订阅，否则插入不生效。
        newsMono.block();

        // 保存成功之后，保存的新闻数量+1
        countSave++;
    }

    @Value("${app.zipFileLocation}")
    private Resource resource;

    /**
     * 读取压缩包的每个zipEntry
     * 如果是新闻文件，第一行作为标题，后面作为内容，
     * 从entry的名字中获取类型，存到数据库中
     *
     * @throws Exception 异常
     */
    public void inputAndSave() throws Exception {
        if(!resource.exists()) throw new RuntimeException("文件不存在");
        try(ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(resource.getInputStream()),StandardCharsets.UTF_8)) {

            ZipEntry nextEntry;

            // 如果还有下一个entry就一直循环
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                String entryName = nextEntry.getName(); //获取当前entry的名字，实际为路径+文件名

//                System.out.println(entryName);

                // 如果entry是不是目录且不是放置新闻的那个文件夹里的内容，就读取
                if (!nextEntry.isDirectory() && !entryName.startsWith("__MAC")) {

                    // 构造Reader读取字符串
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));

                    // 第一行作为新闻标题
                    String title = reader.readLine();

                    // 新闻类型夹在zipEntry的name的两个 '/' 之间
                    String type = entryName.substring(entryName.indexOf('/') + 1, entryName.lastIndexOf('/'));

                    // 使用StringBuffer构造新闻内容
                    StringBuffer content = new StringBuffer();
                    // 将剩下的行转为stream，遍历所有行，如是空字符串或者都是空格等不可见字符就跳过。否则全部存到content中
                    reader.lines().forEach((e) -> {
                        if (e.isBlank()) return;
                        content.append(e.strip()).append("\n");
                    });

                    // 存储到数据库中
                    saveInDB(title, content, type);
                }
//                Thread.sleep(1000);
            }
        }
    }
}
