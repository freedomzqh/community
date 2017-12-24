package com.community.common;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class HtmlUtils {
    private static final Integer size=100;
    public  static String getText(String html){
        String text=Jsoup.parse(html).text();
        if(text!=null&&text.length()>size){
            return text.substring(0,size);
        }else {
            return text;
        }
    }

    public static  String getImgUrl(String html){
        String imgUrl="";
        Elements elements= Jsoup.parse(html).getElementsByTag("img");
        if(elements!=null&&elements.size()>0){
            imgUrl=elements.get(0).attr("src");
        }
        return imgUrl;
    }
}
