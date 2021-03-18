package com.pagani.dragonupgrades.object;

public class User {

    private final String name;
    private boolean nivel1;
    private boolean nivel2;
    private boolean nivel3;
    private boolean nivel4;
    private boolean nivel5;
    private boolean nivel6;
    private boolean nivel7;

    public User(String usuário) {
        this.nivel1 = false;
        this.nivel2 = false;
        this.nivel3 = false;
        this.nivel4 = false;
        this.nivel5 = false;
        this.nivel6 = false;
        this.nivel7 = false;
        this.name = usuário;
    }

    public String getName() {
        return name;
    }

    public boolean isNivel1() {
        return nivel1;
    }

    public void setNivel1(boolean nivel1) {
        this.nivel1 = nivel1;
    }

    public boolean isNivel2() {
        return nivel2;
    }

    public void setNivel2(boolean nivel2) {
        this.nivel2 = nivel2;
    }

    public boolean isNivel3() {
        return nivel3;
    }

    public void setNivel3(boolean nivel3) {
        this.nivel3 = nivel3;
    }

    public boolean isNivel4() {
        return nivel4;
    }

    public void setNivel4(boolean nivel4) {
        this.nivel4 = nivel4;
    }

    public boolean isNivel5() {
        return nivel5;
    }

    public void setNivel5(boolean nivel5) {
        this.nivel5 = nivel5;
    }

    public boolean isNivel6() {
        return nivel6;
    }

    public void setNivel6(boolean nivel6) {
        this.nivel6 = nivel6;
    }

    public boolean isNivel7() {
        return nivel7;
    }

    public void setNivel7(boolean nivel7) {
        this.nivel7 = nivel7;
    }
}