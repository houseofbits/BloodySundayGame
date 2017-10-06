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
    float[] vertices = null;
    short[] indices = null;
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
        gameObject.getSceneManager().assetsManager.load(modelName, Model.class);
    }

    public void create(){
        gameObject.getSceneManager().assetsManager.load(modelName, Model.class);
    }

    public void init(){
        if(gameObject.getSceneManager().assetsManager.isLoaded(modelName)) {
            Model model = gameObject.getSceneManager().assetsManager.get(modelName, Model.class);
            model.calculateBoundingBox(boundingBox);

            model.calculateTransforms();

            if(model.meshes.size > 0)LoadMeshes(model.meshes.get(0).copy(true), model.nodes.get(0).globalTransform);

        }else{
            System.out.println("IntersectionMesh:init asset not loaded "+modelName);
        }
    }

    public void LoadMeshes(Mesh mesh, Matrix4 transform){

        vertexSize = mesh.getVertexSize() / 4;
        vertices = new float[mesh.getNumVertices() * mesh.getVertexSize() / 4];
        mesh.transform(transform.cpy());
        mesh.getVertices(vertices);
        indices = new short[mesh.getNumIndices()];
        mesh.getIndices(indices);
    }

    public boolean IntersectRay(Ray ray, Vector3 point){

        if(vertices == null || vertexSize == 0)return false;

        Ray r = ray.cpy();
        r.mul(transform.cpy().inv());
        if(Intersector.intersectRayTriangles(r, vertices, indices, vertexSize, point)) {
            point.mul(transform);
            return true;
        }
        return false;
    }

}
