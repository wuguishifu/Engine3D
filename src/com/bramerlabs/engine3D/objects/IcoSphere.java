package com.bramerlabs.engine3D.objects;

import com.bramerlabs.engine3D.graphics.mesh.Mesh;
import com.bramerlabs.engine3D.math.vector.Vector3f;
import com.bramerlabs.engine3D.math.vector.Vector4f;

public class IcoSphere extends RenderObject {

    public IcoSphere(Vector3f position, Vector4f color, float radius) {
        super(generateMesh(color), position, new Vector3f(0), new Vector3f(radius));
    }

    public static Mesh generateMesh(Vector4f color) {
        return null;
    }

}
