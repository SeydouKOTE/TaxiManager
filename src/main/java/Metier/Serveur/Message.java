package Metier.Serveur;

import java.io.Serializable;

public class Message implements Serializable {
    private String type;
    private Object contenu;
    private Profile envoyerProfile;

    public Message(String type, Object contenu, Profile envoyerProfile) {
        this.type = type;
        this.contenu = contenu;
        this.envoyerProfile = envoyerProfile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContenu() {
        return contenu;
    }

    public void setContenu(Object contenu) {
        this.contenu = contenu;
    }

    public Profile getEnvoyerProfile() {
        return envoyerProfile;
    }

    public void setEnvoyerProfile(Profile envoyerProfile) {
        this.envoyerProfile = envoyerProfile;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", contenu=" + contenu +
                ", envoyerProfile=" + envoyerProfile +
                '}';
    }
}
