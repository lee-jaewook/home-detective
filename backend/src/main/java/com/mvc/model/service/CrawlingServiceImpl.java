package com.mvc.model.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CrawlingServiceImpl implements CrawlingService{

    @Override
    public String getImgSource(String path) throws Exception {
        String[] filter = {"ad","AD","refresh", "logo", "LOGO", "common", ".gif", "rss", "banner", "gija", "title","TITLE", "url.","button", "bottom","icon","ico","배너", "ban/", "scs-phinf", "journalist", "top", "sns","homepage", "ic-","font","nad","user","profile", "btn", "txt_"};


        Connection conn = Jsoup.connect(path);
        String resultOfCrawling = "";

        try {
            Document document = conn.get();
            Elements elements = document.select("img");
//            System.out.println("elements :");
//            System.out.println(elements);
            for (Element element : elements) {
                String imgPath = element.attr("src", element.attr("abs:src")).attr("src");
                if(imgPath =="") imgPath = element.attr("data-src", element.attr("abs:data-src")).attr("data-src");
//                System.out.println("imgPath : "+imgPath);
                boolean isContain = false;
                for (String s : filter) {
                    if(imgPath.contains(s)){
                        isContain = true;
                        break;
                    }
                }
                if (isContain) continue;
//                System.out.println("변환 전!!!!");
//                System.out.println(element.attr("src"));
//                System.out.println("일치 여부!!!!");
//                System.out.println(element.attr("src").equals(element.attr("abs:src")));
//                System.out.println("변환 후!!!!");
//                System.out.println(element.attr("src", element.attr("abs:src")));
//                System.out.println("url 만!!!!");
//                System.out.println(element.attr("src", element.attr("abs:src")).attr("src"));
//                System.out.println("================================================");
//                System.out.println(element);
//                System.out.println("path :"+path);
//                System.out.println(imgPath);
                resultOfCrawling = imgPath;
//                System.out.println("================================================");
                return resultOfCrawling;
            }
            return null;
        } catch (IOException ignored) {
            return null;
        }
    }
}
