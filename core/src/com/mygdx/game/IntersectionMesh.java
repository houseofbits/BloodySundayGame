package com.mygdx.game;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;

/**
 * Created by T510 on 8/13/2017.
 */

public class IntersectionMesh {

    private GameObject gameObject = null;
    private String modelName = "";
    public BoundingBox boundingBox = new BoundingBox();
    public Matrix4  transform = new Matrix4();
    float[] vertices;
    short[] indices;
    int   vertexSize = 0;

    public IntersectionMesh(GameObject o){
        gameObject = o;
    }
    public IntersectionMesh(GameObject o, String filename){
        gameObject = o;
        modelName = filename;
    }

    public void create(String m){
        modelName = m;
        gameObject.sceneManager.assetsManager.load(modelName, Model.class);
    }

    public void create(){
        gameObject.sceneManager.assetsManager.load(modelName, Model.class);
    }

    public void init(){
        if(gameObject.sceneManager.assetsManager.isLoaded(modelName)) {
            Model model = gameObject.sceneManager.assetsManager.get(modelName, Model.class);
            model.calculateBoundingBox(boundingBox);
//
            model.calculateTransforms();
            if(model.meshes.size > 0)LoadMeshes(model.meshes.get(0), model.nodes.get(0).globalTransform);

            //System.out.println(model.nodes.get(0).globalTransform);
            //System.out.println(model.nodes.get(0).localTransform);

        }else{
            System.out.println("IntersectionMesh:init asset not loaded "+modelName);
        }
    }

    public void LoadMeshes(Mesh mesh, Matrix4 transform){

        vertexSize = mesh.getVertexSize() / 4;
        vertices = new float[mesh.getNumVertices() * mesh.getVertexSize() / 4];

       // mesh.getVertices(vertices);
        //System.out.println( vertices[2]);

        mesh.transform(transform.cpy());

        mesh.getVertices(vertices);
        System.out.println("V0:"+vertices[0]+","+vertices[1]+","+vertices[2]);


        indices = new short[mesh.getNumIndices()];
        mesh.getIndices(indices);

//        System.out.println("nv: "+mesh.getNumVertices());
//        System.out.println("ni: "+mesh.getNumIndices());
//        System.out.println("vs: "+mesh.getVertexSize());
//        System.out.println("numv: "+mesh.getNumVertices() * mesh.getVertexSize() / 4);
//        System.out.println("numi: "+(mesh.getNumIndices() * 6 - 6) * (mesh.getNumVertices() - 1));

    }

    public boolean IntersectRay(Ray ray, Vector3 point){


        if(Intersector.intersectRayTriangles(ray, vertices, indices, vertexSize, point)) {
//        if(Intersector.intersectRayTriangle(ray,
//                new Vector3(-1.000000f, -1.000000f,  0.000000f),
//                new Vector3(1.000000f, -0.864088f, -0.482376f),
//                new Vector3(1.000000f,  1.000000f,  0.000000f),
//                point))
            System.out.println(point);
            return true;
        }
        return false;
    }

}
