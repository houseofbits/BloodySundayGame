package Utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by T510 on 8/21/2017.
 */

public class RandomDistribution<T> {

    public class Node{
        public float initWeight = 0.0f;
        public float weight = 0.0f;
        public T data;
        public float count = 1.0f;
        public float part = 0;
        public float factor = 0;
        public Node(T d, float w){data = d; initWeight = w;}
    }

    protected Array<Node> nodes = new Array<Node>();
    protected float treshold = 1.1f;
    protected Random rand = new Random();
    protected Node previous = null;


    public StringBuilder debugString = new StringBuilder();

    public RandomDistribution(){}

    public void add(T data, float weight){
        if(weight <= 0)return;
        nodes.add(new Node(data, weight));
        recalculateWeights();
    }

    public void remove(T data){
        for(int i=0; i<nodes.size; i++){
            if(data == nodes.get(i).data){
                nodes.removeIndex(i);
                break;
            }
        }
        recalculateWeights();
    }

    protected void recalculateWeights(){
        float weightSum = 0;
        for(int i=0; i<nodes.size; i++){
            weightSum += nodes.get(i).initWeight;
        }
        for(int i=0; i<nodes.size; i++){
            Node n = nodes.get(i);
            n.weight = n.initWeight / weightSum;
        }
    }

    public Node get(){

        if(nodes.size == 0)return null;

        Array<Node> nf = new Array<Node>();

        float count = 0;
        for(int i=0; i<nodes.size; i++){
            count += nodes.get(i).count;
        }
        for(int i=0; i<nodes.size; i++){
            Node n = nodes.get(i);
            n.part = n.count / count;
            n.factor = n.weight / n.part;
            if(n.factor >= treshold && (previous == null || previous.data != n.data)){
                nf.add(n);
            }
        }
        if(nf.size > 0){
            int idx = rand.nextInt(nf.size);
            Node n = nf.get(idx);
            n.count++;
            previous = n;
            return n;
        }else{
            int idx = rand.nextInt(nodes.size);
            Node n = nodes.get(idx);
            n.count++;
            previous = n;
            return n;
        }
    }

    public void saveDebugLine(Node n){
        debugString.append("<tr>");
        for(int i=0; i<nodes.size; i++) {
            Node nd = nodes.get(i);
            if(n.data == nd.data)debugString.append("<td style=\"background-color:red;\">");
            else debugString.append("<td>");
            debugString.append(nd.part);
            debugString.append("</td>\n");
        }
        debugString.append("</tr>\n");
    }
    public void saveDebugFile(String filename){
        try {
            PrintWriter pw = new PrintWriter(new File(filename));
            pw.write("<table>");
            for(int i=0; i<nodes.size; i++) {
                Node nd = nodes.get(i);
                pw.write("<td style=\"background-color:gray;\">");
                pw.write(""+nd.weight);
                pw.write("</td>\n");
            }
            pw.write(debugString.toString());
            pw.write("</table>");
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
