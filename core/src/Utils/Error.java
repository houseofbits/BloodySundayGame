package Utils;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.soap.SAAJMetaFactory;

/**
 * Created by T510 on 9/15/2017.
 */

public class Error {

    public static void log(String str){
        Gdx.app.log("log",str);
    }

    public static void logToFile(String line, String filename){
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(
                    new File(filename),
                    true /* append = true */));
            //pw.write(line+"\n");
            pw.println(line);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
