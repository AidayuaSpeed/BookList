package com.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据图书的IBSN 在网络查询图书信息
 *     抓取豆瓣读书  https://book.douban.com/ 搜索图书的IBSN
 *
 *     解析后地址为 https://book.douban.com/isbn/xxxxx/
 *      xxxxx 为isbn 号
 * */
public class ISBNUtil {

    private static Logger log = LoggerFactory.getLogger(ISBNUtil.class);

    private static HttpClient httpClient = null;

    static {
        try{
            httpClient = HttpClientBuilder.create().build();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static JSONObject search(String isbn){
        JSONObject resultMap = new JSONObject();
        try{
            Map<String,String> headers = new HashMap<>();
            headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            headers.put("Connection","keep-alive");
            headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");
            String result = sendHttpsGet("https://book.douban.com/isbn/"+isbn+"/",headers);
            if(StringUtils.isNotEmpty(result)){
                Document doc = Jsoup.parse(result);
                //图书图片 地址
                Elements imgEle = doc.select("#content #mainpic img");
                String imgSrc = imgEle.attr("src");
                log.info("图书图片URL:{}",imgSrc);
                resultMap.put("BOOK_IMAGE",download(imgSrc));
                //图书名称
                Elements nameEle = doc.select("#wrapper h1 span");
                resultMap.put("BOOK_NAME",nameEle.html());
                //图书信息
                Elements infoEle = doc.select("#content #info");
                String html = infoEle.html();
                html = html.replaceAll("<br>","@");
                html = html.replaceAll("&nbsp;","");

                //定义HTML标签的正则表达式
                String regEx_html = "<[^>]+>";
                Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
                Matcher m_html = p_html.matcher(html);
                html = m_html.replaceAll("");

                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(html);
                html = m.replaceAll("");


                //图书简介信息
                Element bookinfo = doc.select("div.intro").first();
                String bookhtml = bookinfo.html();
                bookhtml = bookhtml.replaceAll("<br>","@");
                bookhtml = bookhtml.replaceAll("&nbsp;","");

                //定义HTML标签的正则表达式
                String regEx_bookhtml = "<[^>]+>";
                Pattern p_bookhtml = Pattern.compile(regEx_bookhtml, Pattern.CASE_INSENSITIVE);
                Matcher m_bookhtml = p_bookhtml.matcher(bookhtml);
                bookhtml = m_bookhtml.replaceAll("");

                Pattern phtml = Pattern.compile("");
                Matcher mhtml = phtml.matcher(bookhtml);
                bookhtml = mhtml.replaceAll("");
                resultMap.put("BOOK_INTRO",bookhtml);


                String[] strs = html.split("@");
                for(String s:strs){
                    String[] ss = s.split(":");
                    if("作者".equals(ss[0])){
                        resultMap.put("BOOK_AUTHOR",ss[1]);
                    }else if("出版社".equals(ss[0])){
                        resultMap.put("PUBLISHER",ss[1]);
                    }else if("出版年".equals(ss[0])){
                        resultMap.put("PUBLISHING_TIME",ss[1]);
                    }else if("定价".equals(ss[0])){
                        resultMap.put("BOOK_PRICE",ss[1]);
                    }else if("ISBN".equals(ss[0])){
                        resultMap.put("ISBN",ss[1]);
                    }
                }
            }

            resultMap.put("SUCCESS",resultMap.size()>1);
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("RETURN_MSG","未查到图书信息");
        }
        resultMap.put("SUCCESS",resultMap.size()>1);
        return resultMap;
    }


    private static String sendHttpsGet(String url, Map<String, String> headers){
        log.info("URL:{}",url);
        HttpGet get = new HttpGet();
        for(String keyh : headers.keySet()) {
            get.setHeader(keyh,headers.get(keyh));
        }

        String result ="";
        try {
            get.setURI(new URI(url));
            HttpResponse response = httpClient.execute(get);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String download(String url) {
        try {
            HttpGet request = new HttpGet();
            request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:75.0) Gecko/20100101 Firefox/75.0");

            request.setURI(new URI(url));
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();
            byte[] b = EntityUtils.toByteArray(entity);
            if (200 != statusCode) {
                log.debug("http status code " + statusCode);
            }
            return "data:image/jpg;base64,"+Base64.getEncoder().encodeToString(b);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("call [" + url + "] error!", e);
        }
    }



}
