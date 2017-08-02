package com.yangge.Service;

import com.yangge.Util.BookFileUtil;
import com.yangge.domain.Book;
import com.yangge.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangge on 2017/8/2 0002.
 */
@Service
public class PackageService {
    @Autowired
    private SerchService serchService;
    @Autowired
    private ContentService contentService;
    public void packageBook(String bookName,int stationID){
        PackageThread packageThread=new PackageThread(bookName,stationID);
        packageThread.start();
    }
    private String packageBookInner(String bookNmae,int stationID){
        List<Book> pageList=serchService.getBookFromMiaoBiGe(bookNmae);
        for(Book book:pageList){
            Page page=contentService.getOnePageContent(book.getPageName(),book.getPageURL());
            BookFileUtil.writeBook(bookNmae+"-"+stationID,page);
            System.out.println("write "+book.getPageName());
        }
        return bookNmae+"-"+stationID;
    }
    class PackageThread extends Thread{
        private String bookName;
        private int stationID;
        PackageThread(){
        }
        PackageThread(String bookName,int stationID){
            this.bookName=bookName;
            this.stationID=stationID;
        }
        @Override
        public void run() {
            super.run();
            packageBookInner(bookName,stationID);
        }
    }
}
