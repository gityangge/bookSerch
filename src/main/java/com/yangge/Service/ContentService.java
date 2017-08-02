package com.yangge.Service;

import com.yangge.domain.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;

/**
 * Created by yangge on 2017/8/2 0002.
 */
@Service
public class ContentService {
    /*通用方法，文本id为content*/
    public  Page getOnePageContent(String pageName,String pageUrl){
        Page page=new Page();
        Document doc = null;
        try {
            doc = Jsoup.connect(pageUrl)
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element content = doc.getElementById("content");
        String text=content.html();
        text=text.replaceAll("</.*?>","\n");
        text=text.replaceAll("<.*?>","");
//        System.out.println(text);
        page.setPageName(pageName);
        page.setPageContent(text);
        return page;
    }

}
