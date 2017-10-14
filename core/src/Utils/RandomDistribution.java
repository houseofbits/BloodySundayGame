package Utils;

import com.badlogic.gdx.math.Interpolation;
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
        public float initWeightMap[] = null;
        public float initWeight = 0.0f;
        public float weight = 0.0f;
        public T data;
        public float count = 1.0f;
        public float part = 0;
        public float factor = 0;
        public Node(T d, float w){data = d; initWeight = w;}
        public Node(T d, float w[]){data = d; initWeightMap = w;}
    }

    protected Array<Node> nodes = new Array<Node>();
    protected float treshold = 1.1f;
    protected Random rand = new Random();
    protected Node previous = null;

    public StringBuilder debugString = new StringBuilder();

    public RandomDistribution(){}

    public void add(T data, float weight){
        remove(data);
        nodes.add(new Node(data, weight));
        normalizeWeights();
    }
    public void add(T data, float[] weight){
        remove(data);
        nodes.add(new Node(data, weight));
        normalizeWeights();
    }
    public void set(T data, float weight){
        for(int i=0; i<nodes.size; i++){
            if(data == nodes.get(i).data){
                nodes.get(i).initWeight = weight;
            }
        }
        normalizeWeights();
    }

    public void map(T data, float fa){
        for(int i=0; i<nodes.size; i++){
            if(data == nodes.get(i).data){
                Node n = nodes.get(i);
                if(n.initWeightMap != null && n.initWeightMap.length > 0){

                    fa = Math.min(1.0f, Math.max(0.0f, fa));

                    int ia = (int)Math.floor(fa * (float)(n.initWeightMap.length-1));
                    int ib = (int)Math.ceil(fa * (float)(n.initWeightMap.length-1));

                    if(ia == ib){
                        n.initWeight = n.initWeightMap[ia];
                    }else{
                        float f = (fa * (float)(n.initWeightMap.length-1)) - (float)ia;
                        n.initWeight = Interpolation.linear.apply(n.initWeightMap[ia], n.initWeightMap[ib], f);
                    }
                }
            }
        }
        normalizeWeights();
    }
//
//    public void setFraction(T data, float weightF){
//        float tw = totalWeight();
//        if(tw > 0 && weightF > 0){
//            weightF = tw * weightF;
//        }
//        set(data, weightF);
//    }

    public void remove(T data){
        for(int i=0; i<nodes.size; i++){
            if(data == nodes.get(i).data){
                nodes.removeIndex(i);
            }
        }
        normalizeWeights();
    }

    protected float totalWeight(){
        float weightSum = 0;
        for(int i=0; i<nodes.size; i++){
            weightSum += nodes.get(i).initWeight;
        }
        return weightSum;
    }

    protected void normalizeWeights(){
        float weightSum = totalWeight();
        for(int i=0; i<nodes.size; i++){
            Node n = nodes.get(i);
            if(n.initWeight > 0 && weightSum > 0)n.weight = n.initWeight / weightSum;
            else n.weight = 0;
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
            if(n.weight > 0){
                n.count++;
                previous = n;
                return n;
            }
        }else{
            int idx = rand.nextInt(nodes.size);
            Node n = nodes.get(idx);
            n.count++;
            previous = n;
            if(n.weight > 0)return n;
        }

        return null;
    }

    public void saveDebugLine(Node n){
        debugString.append("<tr>");
        for(int i=0; i<nodes.size; i++) {
            Node nd = nodes.get(i);
            if(n != null && n.data == nd.data)debugString.append("<td style=\"text-align:center;background-color:red;border: 1px solid gray;\">");
            else debugString.append("<td style=\"border: 1px solid gray;text-align:center;\">");
            debugString.append(String.format("%.2f", nd.part)
                    +"<br/>"
                    +String.format("%.2f", nd.weight)
                    +"/"
                    +String.format("%.2f", nd.initWeight));
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
                pw.write("<td style=\"background-color:gray;text-align:center;color:white;\">");
                pw.write(""+nd.data.toString());
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
