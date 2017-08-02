package com.yangge.Controller;

import com.yangge.Service.PackageService;
import com.yangge.Service.SerchService;
import com.yangge.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangge on 2017/8/2 0002.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/list")
public class SerchController {
    @Autowired
    private SerchService serchService;
    @Autowired
    private PackageService packageService;
    @RequestMapping(value = "serch",method = RequestMethod.POST)
    public List<Book> getBookList(@RequestParam(value = "bookName",required = true) String bookName,@RequestParam(value = "url",required = true) String url){
//        return serchService.getPageList(bookName,url);
    return  serchService.getBookFromMiaoBiGe(bookName);
    }
    @RequestMapping(value = "test")
    public void test(@RequestParam(value = "bookName") String bookName){
        packageService.packageBook(bookName,0);
    }
}
