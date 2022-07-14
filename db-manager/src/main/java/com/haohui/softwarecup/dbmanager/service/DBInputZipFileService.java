package com.haohui.softwarecup.dbmanager.service;

import com.haohui.softwarecup.dbmanager.dao.NewsDao;
import com.haohui.softwarecup.dbmanager.pojo.News;
import com.haohui.softwarecup.dbmanager.utils.UtilBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class DBInputZipFileService {
    private final NewsDao newsDao;

    private final UtilBean utilBean;

    private final LocalDateTime aTime;

    private final Map<String, List<News>> map = new HashMap<>();


    public DBInputZipFileService(NewsDao newsDao, UtilBean utilBean) {
        this.newsDao = newsDao;
        this.utilBean = utilBean;
        this.aTime = LocalDateTime.parse(utilBean.getATimeStr());
        map.put("体育",new LinkedList<>());
        map.put("娱乐",new LinkedList<>());
        map.put("家居",new LinkedList<>());
        map.put("彩票",new LinkedList<>());
        map.put("房产",new LinkedList<>());
        map.put("教育",new LinkedList<>());
        map.put("时尚",new LinkedList<>());
        map.put("时政",new LinkedList<>());
        map.put("星座",new LinkedList<>());
        map.put("游戏",new LinkedList<>());
        map.put("社会",new LinkedList<>());
        map.put("科技",new LinkedList<>());
        map.put("股票",new LinkedList<>());
        map.put("财经",new LinkedList<>());
    }

    public void inputAndSave() throws Exception {
        Resource resource = utilBean.getResource();
        if (!resource.exists()) throw new RuntimeException("文件不存在");
        try (ZipInputStream zipInputStream = new ZipInputStream(resource.getInputStream())) {

            ZipEntry nextEntry;

            // 如果还有下一个entry就一直循环
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                //获取当前entry的名字，实际为路径+文件名
                String entryName = nextEntry.getName();

                // 如果entry是目录 或者 是另一个不相干的文件夹，就跳过
                if (nextEntry.isDirectory() || entryName.startsWith("__MAC")) continue;

                // 构造Reader读取字符串
                BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));

                // 第一行作为新闻标题
                String title = reader.readLine();

                // 新闻类型夹在zipEntry的name的两个 '/' 之间
                String type = entryName.substring(entryName.indexOf('/') + 1, entryName.lastIndexOf('/'));

                // 使用StringBuffer构造新闻内容
                StringBuffer rawContent = new StringBuffer();

                // 将剩下的行转为stream，遍历所有行，如是空字符串或者都是空格等不可见字符就跳过。否则全部存到content中
                reader.lines().forEach((e) -> {
                    if (e.isBlank()) return;
                    rawContent.append(e.strip()).append("\n");
                });

                // 把StringBuffer转为string，并且去除前后的空字符
                String content = rawContent.toString().strip();

                // 根据内容、标题和类型构造一个news对象
                News news = News
                        .builder()
                        .title(title)
                        .content(content)
                        .type(type)
                        .publishedTime(aTime.plusSeconds(utilBean.getRandom().nextInt(5 * 30 * 24 * 60 * 60)))
                        .build();

                // 先将所有的新闻存入内存中
                map.get(type).add(news);

//                Thread.sleep(1000);
            }
            // 每种类型的新闻只留下二十分之一
            map.forEach((k,v)-> map.put(k,v.stream().limit(v.size()/20).collect(Collectors.toList())));

            // 将所有新闻存入数据库中
            List<News> newsList = map.values().stream().flatMap(Collection::stream)
                    .collect(Collectors.toList());
            Flux<News> newsFlux = newsDao.saveAll(newsList);
            newsFlux.count().block();
        }
    }
}
