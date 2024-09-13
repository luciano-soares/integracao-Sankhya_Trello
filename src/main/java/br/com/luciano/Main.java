package br.com.luciano;
import java.io.*;
import java.util.Properties;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;

public class Main implements ScheduledAction{
    public static Properties getProp() throws IOException {
        Properties props = new Properties();
        BufferedReader file = new BufferedReader(new FileReader("config.properties"));
        props.load(file);
        return props;
    }

    public static void main(String[] args) throws IOException {
        Properties prop = getProp();
        String token = prop.getProperty("sankhya.token");
        System.out.println("Hello world!, " + token);
    }

    @Override
    public void onTime(ScheduledActionContext scheduledActionContext) {
        System.out.println("Hello :)");
    }
}