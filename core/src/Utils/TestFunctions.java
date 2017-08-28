package Utils;

/**
 * Created by KristsPudzens on 28.08.2017.
 */

public class TestFunctions {

    public static void RandDistrib(){

        RandomDistribution<String> dstr = new RandomDistribution<String>();

        dstr.add("A", 0.1f);
        dstr.add("B", 0.1f);
        dstr.add("C", 0.1f);
        dstr.add("D", 0.1f);
        dstr.add("E", 0.1f);
        dstr.add("F", 0.1f);
        dstr.add("G", 0.1f);
        dstr.add("H", 0.1f);
        dstr.add("I", 0.1f);

        for(int i = 0; i<200; i++) {
            RandomDistribution<String>.Node g = dstr.get();
            if(g != null){
                dstr.saveDebugLine(g);
            }
        }

        dstr.saveDebugFile("dstr_debug.html");

    }

}
