package com.yangge.Service;

import com.yangge.Util.ChineseNumber;
import com.yangge.domain.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangge on 2017/8/2 0002.
 */
@Service
public class SerchService {
    /*这个方法是网站的通用方法，但是无法处理分页*/
    public List<Book> getPageList(String bookName,String url){
        String serchURL="http://www.baidu.com/s?wd="+bookName+"&ct=2097152&si="+url+"&sts="+url+"&submit=%E6%90%9C%E7%B4%A2";
        System.out.println(serchURL);
        Document doc = null;
        try {
            doc = Jsoup.connect(serchURL)
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .get();
            Element content = doc.getElementById("content_left").getElementById("1");
            Elements links = content.getElementsByTag("a");
            if (links.isEmpty()) return null;
            String firstUrl = links.get(0).attr("href");
            Document bookListDoc = Jsoup.connect(firstUrl)
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .get();
            Element bookListContent = bookListDoc.body();
            Elements booklinks = bookListContent.getElementsByTag("a");
            List<Book> pageList=sortPageList(booklinks);
            return  pageList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*针对妙笔阁的搜索方法*/
    public List<Book> getBookFromMiaoBiGe(String bookName){
        String serchURL="http://www.baidu.com/s?wd="+bookName+"&ct=2097152&si=www.miaobige.com&sts=www.miaobige.com&submit=%E6%90%9C%E7%B4%A2";
        Document doc = null;
        try {
            doc = Jsoup.connect(serchURL)
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .get();

            Element content = doc.getElementById("content_left").getElementById("1");
            Elements links = content.getElementsByTag("a");
            if (links.isEmpty()) return null;
            String firstUrl = links.get(0).attr("href");
            Document bookListDoc = Jsoup.connect(firstUrl)
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .get();
            Element bookListContent = bookListDoc.body();
            Elements booklinks = bookListContent.getElementsByTag("a");
            /*加入分页*/
            Elements pagesNum=bookListContent.getElementsByClass("pages");
            if(!pagesNum.isEmpty()){
                Elements pageLinks=pagesNum.get(0).getElementsByTag("a");
                for(Element element:pageLinks){
                    String linkHref = element.attr("abs:href");
                    Document bookListDocPage = Jsoup.connect(linkHref)
                            .userAgent("Mozilla")
                            .timeout(3000)
                            .get();
                    Element bookListContentPage = bookListDocPage.body();
                    Elements booklinksPage = bookListContentPage.getElementsByTag("a");
                    booklinks.addAll(booklinksPage);
                }
            }
            List<Book> pageList=sortPageList(booklinks);
            return  pageList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private List<Book> sortPageList(Elements booklinks){
        List<Book> pageList=new ArrayList<>(booklinks.size());
        Pattern pagePattern=Pattern.compile("第(.*?)章.*?");
        for (Element link : booklinks) {
            String linkHref = link.attr("abs:href");
            String linkText = link.text();
            Matcher pageMatcher=pagePattern.matcher(linkText);
            if(pageMatcher.matches()){
                String pageNum=pageMatcher.group(1);
                int pageInt;
                if(Pattern.compile("[0-9]+").matcher(pageNum).matches()){
                    pageInt=Integer.parseInt(pageNum);
                }else{
                    pageInt= ChineseNumber.chineseNumber2Int(pageNum);
                }
                Book pageBook=new Book();
                pageBook.setPageID(pageInt);
                pageBook.setPageName(linkText);
                pageBook.setPageURL(linkHref);
                pageList.add(pageBook);
            }
        }
        pageList.sort(new Comparator<Book>(){
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getPageID()>o2.getPageID()?1:(o1.getPageID()==o2.getPageID()?0:-1);
            }
        });
        return pageList;
    }
}
