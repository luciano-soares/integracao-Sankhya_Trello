package br.com.luciano;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

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

    public boolean login() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url("https://api.sankhya.com.br/login")
                .method("POST", body)
                .addHeader("token", getToken())
                .addHeader("appkey", getAppKey())
                .addHeader("username", getUsuario())
                .addHeader("password", getSenha())
                .build();
        try{
            Response response = client.newCall(request).execute();
            JSONObject responseBody = new JSONObject(response.body().string());
            System.out.println(responseBody);
            System.out.println(responseBody.getString("error"));

            if (Objects.equals(responseBody.getString("error"), "null")){
                setBearerToken(responseBody.getString("bearerToken"));
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            return false;
        }
    }

    public void buscaDadosFlow() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String sql = "SELECT pdv.idinstprn AS instancia\n" +
                "                        , TO_CHAR(pdv.objet) AS solicitacao\n" +
                "                        , NVL(INITCAP(parsolic.nomeparc), INITCAP(ususolic.nomeusu)) AS solicitante\n" +
                "                        , NVL(INITCAP(pardono.nomeparc), INITCAP(usudono.nomeusu)) AS dono\n" +
                "                        , ele.nome AS tarefa\n" +
                "                        , 'Solicitação de PDV' AS nomeProcesso\n" +
                "                    FROM twfitar tar\n" +
                "                    INNER JOIN ad_ppdv pdv     ON pdv.idinstprn    = tar.idinstprn\n" +
                "                    INNER JOIN tsiusu ususolic ON ususolic.codusu  = tar.codususolicitante\n" +
                "                    LEFT  JOIN tgfpar parsolic ON parsolic.codparc = ususolic.codparc\n" +
                "                    INNER JOIN tsiusu usudono  ON usudono.codusu   = tar.codusudono\n" +
                "                    LEFT  JOIN tgfpar pardono  ON pardono.codparc  = usudono.codparc\n" +
                "                    INNER JOIN twfele ele      ON ele.idelemento   = tar.idelemento\n" +
                "                    WHERE dhconclusao IS NULL\n" +
                "                    AND ele.versao = (SELECT MAX(versao) FROM twfele WHERE idelemento = ele.idelemento)\n" +
                "\n" +
                "                    UNION ALL\n" +
                "\n" +
                "                    SELECT imp.idinstprn AS instancia\n" +
                "                        , imp.apelido AS solicitacao\n" +
                "                        , NVL(INITCAP(parsolic.nomeparc), INITCAP(ususolic.nomeusu)) AS solicitante\n" +
                "                        , NVL(INITCAP(pardono.nomeparc), INITCAP(usudono.nomeusu)) AS dono\n" +
                "                        , ele.nome AS tarefa\n" +
                "                        , 'Solicitação de impressão' AS nomeProcesso\n" +
                "                    FROM twfitar tar\n" +
                "                    INNER JOIN ad_pimp imp     ON imp.idinstprn = tar.idinstprn\n" +
                "                    INNER JOIN tsiusu ususolic ON ususolic.codusu  = tar.codususolicitante\n" +
                "                    LEFT  JOIN tgfpar parsolic ON parsolic.codparc = ususolic.codparc\n" +
                "                    INNER JOIN tsiusu usudono  ON usudono.codusu   = tar.codusudono\n" +
                "                    LEFT  JOIN tgfpar pardono  ON pardono.codparc  = usudono.codparc\n" +
                "                    INNER JOIN twfele ele      ON ele.idelemento   = tar.idelemento\n" +
                "                    WHERE dhconclusao IS NULL\n" +
                "                    AND ele.versao = (SELECT MAX(versao) FROM twfele WHERE idelemento = ele.idelemento)";
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{" +
                "    \"serviceName\": \"DbExplorerSP.executeQuery\"," +
                "    \"requestBody\": {" +
                "        \"sql\": \" " + sql + " \"" +
                "    }" +
                "}");
        Request request = new Request.Builder()
                .url("https://api.sankhya.com.br/gateway/v1/mge/service.sbr?serviceName=DbExplorerSP.executeQuery&outputType=json")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("appkey", getAppKey())
                .addHeader("Authorization", "Bearer " + getBearerToken())
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    public void logout() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("https://api.sankhya.com.br/gateway/v1/mge/service.sbr?serviceName=MobileLoginSP.logout&outputType=json")
                .addHeader("appkey", getAppKey())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + getBearerToken())
                .build();
        Response response = client.newCall(request).execute();
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
