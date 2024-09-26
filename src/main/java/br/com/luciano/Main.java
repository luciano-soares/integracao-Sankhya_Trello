package br.com.luciano;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;
import org.json.JSONException;

import java.io.*;
import java.util.Properties;

public class Main /*implements ScheduledAction*/ {
    public static Properties getProp() throws IOException {
        Properties props = new Properties();
        BufferedReader file = new BufferedReader(new FileReader("data.properties"));
        props.load(file);
        return props;
    }

    public static void main(String[] args) throws IOException {
        Properties props = getProp();
        Sankhya s = new Sankhya(props.getProperty("sankhya.id.username"),
                props.getProperty("sankhya.id.password"),
                props.getProperty("sankhya.token"),
                props.getProperty("sankhya.appkey")
        );
        try {
            if (s.login()){
                s.buscaDadosFlow();
                System.out.println("Oi");
                s.logout();
            }
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /*@Override
    public void onTime(ScheduledActionContext scheduledActionContext) {
        Properties props = new Properties();
        Sankhya s = new Sankhya(props.getProperty("sankhya.id.username"),
                props.getProperty("sankhya.id.password"),
                props.getProperty("sankhya.token"),
                props.getProperty("sankhya.appkey")
        );
        try {
            s.login();
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }*/
}