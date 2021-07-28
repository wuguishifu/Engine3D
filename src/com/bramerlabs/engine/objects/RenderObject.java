package com.bramerlabs.engine.objects;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;

public class RenderObject {

    public Vector3f position, rotation, scale;
    public Mesh mesh;

    public RenderObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public void createMesh() {
        mesh.create();
    }

    public void destroy() {
        mesh.destroy();
    }

    public Matrix4f getModelMatrix() {
        return Matrix4f.transform(position, rotation, scale);
    }

}
