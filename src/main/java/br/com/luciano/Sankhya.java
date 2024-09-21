package br.com.luciano;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class Sankhya {
    private String usuario;
    private String senha;
    private String token;
    private String appKey;
    private String bearerToken;

    public Sankhya(String usuario, String senha, String token, String appKey){
        this.usuario = usuario;
        this.senha = senha;
        this.token = token;
        this.appKey = appKey;
    }

    public boolean login() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.sankhya.com.br/login")
                .method("POST", body)
                .addHeader("token", getToken())
                .addHeader("appkey", getAppKey())
                .addHeader("username", getUsuario())
                .addHeader("password", getSenha())
                .build();
        Response response = client.newCall(request).execute();
        JSONObject responseBody = new JSONObject(response.body().string());

        if (!responseBody.getBoolean("error")){
            setBearerToken(responseBody.getString("bearerToken"));
            return true;
        }
        else{
            return false;
        }
    }

    private String getUsuario() {
        return usuario;
    }
    private void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    private String getSenha() {
        return senha;
    }
    private void setSenha(String senha) {
        this.senha = senha;
    }

    private String getToken() {
        return token;
    }
    private void setToken(String token) {
        this.token = token;
    }

    private String getAppKey() {
        return appKey;
    }
    private void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    private void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    private String getBearerToken() {
        return bearerToken;
    }
}
