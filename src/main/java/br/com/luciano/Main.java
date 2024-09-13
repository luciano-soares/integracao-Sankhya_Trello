package br.com.luciano;
import java.io.*;
import java.util.Properties;

public class Main {
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
}