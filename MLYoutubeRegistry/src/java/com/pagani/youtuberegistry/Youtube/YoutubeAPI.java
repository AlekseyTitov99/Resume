package com.pagani.youtuberegistry.Youtube;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author Kelvin Santiago
 */

public class YoutubeAPI {

    public final String API_KEY = "SUACHAVE";

    private YouTube youtube;

    /**
     * Método responsável por buscar dados do video passando o ID.
     *
     * @param idVideo
     * @return
     */
    public Video buscarPeloIdVideo(String idVideo) {
        try {
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("appAmaYoutube").build();

            // Definindo objeto com os atributos de retorno da busca.
            YouTube.Videos.List busca = youtube.videos().list("id,snippet,statistics");

            // Setando ID do video
            busca.setId(idVideo);

            // Setando KEY do Youtube.
            busca.setKey(API_KEY);

            // Executando busca.
            List<Video> respostaBuscaYoutube = busca.execute().getItems();

            // Imprimindo dado de resposta
            System.out.println(respostaBuscaYoutube);

            return respostaBuscaYoutube.get(0);

        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    /**
     * Método responsável por retornar apenas o ID da URL do Youtube
     *
     * @param URL
     * @return
     */
    public String splitURL(String URL) {
        try {
            return URL.split("v=")[1];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método responsável por converter imagem de URL para Byte[]
     * @param linkURL
     * @return
     */
    public byte[] getBytesImagemURL(String linkURL) {
        URL imageURL = null;
        try {
            imageURL = new URL(linkURL);
            // Lendo imagem diretamente da URL
            BufferedImage originalImage = ImageIO.read(imageURL);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            return imageBytes;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
