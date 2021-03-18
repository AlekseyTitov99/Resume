package com.pagani.manopla.objeto;

public class ManoplaUser {

    private String user;
    private boolean joia1;
    private boolean joia2;
    private boolean joia3;
    private boolean joia4;
    private boolean joia5;
    private boolean joia6;

    public ManoplaUser(String usuario) {
        this.user = usuario;
        this.joia1 = false;
        this.joia2 = false;
        this.joia3 = false;
        this.joia4 = false;
        this.joia5 = false;
        this.joia6 = false;
    }


    public boolean isJoia1() {
        return joia1;
    }

    public void setJoia1(boolean joia1) {
        this.joia1 = joia1;
    }

    public boolean isJoia2() {
        return joia2;
    }

    public void setJoia2(boolean joia2) {
        this.joia2 = joia2;
    }

    public boolean isJoia3() {
        return joia3;
    }

    public void setJoia3(boolean joia3) {
        this.joia3 = joia3;
    }

    public boolean isJoia4() {
        return joia4;
    }

    public void setJoia4(boolean joia4) {
        this.joia4 = joia4;
    }

    public boolean isJoia5() {
        return joia5;
    }

    public void setJoia5(boolean joia5) {
        this.joia5 = joia5;
    }

    public boolean isJoia6() {
        return joia6;
    }

    public void setJoia6(boolean joia6) {
        this.joia6 = joia6;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}