package net.kazuha.bbh;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CheckWordSensitive {
    public static String CheckWords(String raw) {
        if (lkq2.config.getString("anti-badword.mode").equals("off")) {
            return raw;
        }
        if(lkq2.config.getString("anti-badword.mode").equals("combine")){
            String fina = CheckString(raw);
            fina = CheckWithNetEase(fina);
            return fina;
        }
        if(lkq2.config.getString("anti-badword.mode").equals("cloud")){
            String fina = CheckWithNetEase(raw);
            return fina;
        }
        if(lkq2.config.getString("anti-badword.mode").equals("cloud")){
            String fina = CheckWithNetEase(raw);
            return fina;
        }
        return raw;
    }
    public static String CheckWithNetEase(String omg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content",omg);
        String retred = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost("https://g79apigatewayobt.nie.netease.com/mc-server/check-words-sensitive");
            StringEntity entity = new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            retred = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(retred == null){
            return omg;
        }
        JSONObject ret = new JSONObject(retred);
        if (ret.getInt("code") == 0) {
            return omg;
        }else {
            return omg.replace(ret.getString("details"),String.join(" ",Collections.nCopies(retred.length(),lkq2.config.getString("anti-badword.replace"))));
        }

    }

    public static String CheckString(String omg){
        List<String> badwords = lkq2.config.getStringList("anti-badword.bad-word-list");
        for(String e : badwords){
            if(omg.contains(e)){
                omg = omg.replace(e, Collections.nCopies(e.length(),lkq2.config.getString("anti-badword.replace")).toString());
            }
        }
        return omg;
    }
}
