package br.com.luciano;

public class Sankhya {
    private String usuario;
    private String senha;
    private String token;
    private String appKey;

    public Sankhya(String usuario, String senha, String token, String appKey){
        this.usuario = usuario;
        this.senha = senha;
        this.token = token;
        this.appKey = appKey;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getAppKey() {
        return appKey;
    }
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
