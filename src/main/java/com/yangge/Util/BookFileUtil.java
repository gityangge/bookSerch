package com.yangge.Util;

import com.yangge.domain.Page;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by yangge on 2017/8/2 0002.
 */
public class BookFileUtil {
    public static void writeBook(String fileName,Page page){
        File file=new File(fileName);
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter writer=new FileWriter(file+".txt",true);
            writer.write(page.getPageName()+"\n");
            writer.write(page.getPageContent()+"\n\n\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
