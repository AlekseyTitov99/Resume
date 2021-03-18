package com.pagani.youtuberegistry.Youtube;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kelvin Santiago
 */
public class Youtube {

    private static final String API_URL = "https://www.googleapis.com/youtube/v3/videos?";
    private static final String KEY = "SUACHAVE";
    private static final String PART = "snippet,contentDetails,statistics,status";

    public static void main(String[] args) throws Exception {
        Youtube youtube = new Youtube();
        RespostaYoutube respostaYoutube = youtube.obterDadosVideo("https://www.youtube.com/watch?v=jdqsiFw74Jk");
        System.out.println(respostaYoutube.items.get(0).id);
    }

    public RespostaYoutube obterDadosVideo(String URL){

        Gson gson = new Gson();
        String retornoJson = null;
        try {
            // Faz o split para obter o ID do video
            String ID = URL.split("v=")[1];
            retornoJson = lerUrl(API_URL +
                    "id="+ID +"&"+
                    "key=" + KEY + "&" +
                    "part=" + PART);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return gson.fromJson(retornoJson, RespostaYoutube.class);
    }

    private static String lerUrl(String urlString) throws Exception {
        BufferedReader leitor = null;
        try {
            URL url = new URL(urlString);
            leitor = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = leitor.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (leitor != null)
                leitor.close();
        }

    }

    public class RespostaYoutube{
        public List<Items> items = new ArrayList<Items>();
    }

    public class Items{

        public Snippet snippet;
        public Statistics statistics;
        public String id;
    }

    public class Snippet{
        public String title;
        public String description;
        public Thumbnails thumbnails;
        public List<String> tags = new ArrayList<String>();
    }

    public class Statistics{
        public long likeCount;
        public long viewCount;
    }

    public class Thumbnails{
        Medium medium;
        High high;
    }

    public class Medium{
        String url;
        long width;
        long height;
    }

    public class High{
        String url;
        long width;
        long height;
    }

}