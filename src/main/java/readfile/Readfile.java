package readfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Readfile {
    private String filename;
    public Readfile(String filename){
        this.filename = filename;
    }
    public ArrayList read(){
        ArrayList result = new ArrayList();
        FileInputStream fprop;
        Properties property = new Properties();
        try {
            fprop = new FileInputStream(filename);
            property.load(fprop);
            result.add(property.getProperty("ip"));
            result.add(property.getProperty("db"));
            result.add(property.getProperty("login"));
            result.add(property.getProperty("pass"));
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        return result;
    }
}
