import java.io.Serializable;

public class Player implements Serializable {
    private String nickname;
    private int puntuacion;

    public Player(String nickname, int puntuacion) {
        this.nickname = nickname;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nickname;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    @Override
    public String toString() {
        return nickname + " - " + puntuacion + " puntos";
    }

    public String getNickname() {
        return nickname;
    }
}

