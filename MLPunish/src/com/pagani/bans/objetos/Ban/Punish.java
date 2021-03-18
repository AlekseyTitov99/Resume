package com.pagani.bans.objetos.Ban;

import com.pagani.bans.objetos.PunishType;

import java.time.LocalDateTime;

public class Punish {

    private String user;
    private String author;
    private Long time;
    private Long FinishAt;
    private PunishType tipo;
    private String prova;
    private LocalDateTime date;
    private String motivo;
    private int ID;

    public Punish(String usuario,String autor,Long tempo,PunishType type,String prove, LocalDateTime data, Long finish, String reason, int IDS){
        super();
        this.user = usuario;
        this.author = autor;
        this.time = tempo;
        this.tipo = type;
        this.prova = prove;
        this.date = data;
        this.FinishAt = finish;
        this.motivo = reason;
        this.ID = IDS;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public PunishType getTipo() {
        return tipo;
    }

    public void setTipo(PunishType tipo) {
        this.tipo = tipo;
    }

    public String getProva() {
        return prova;
    }

    public void setProva(String prova) {
        this.prova = prova;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getFinishAt() {
        return FinishAt;
    }

    public void setFinishAt(Long finishAt) {
        FinishAt = finishAt;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}