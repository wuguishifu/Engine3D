package com.bramerlabs.engine3D.objects;

import com.bramerlabs.engine3D.graphics.Material;
import com.bramerlabs.engine3D.graphics.mesh.ColorMesh;
import com.bramerlabs.engine3D.graphics.mesh.TextureMesh;
import com.bramerlabs.engine3D.graphics.vertex.ColorVertex;
import com.bramerlabs.engine3D.graphics.vertex.TextureVertex;
import com.bramerlabs.engine3D.math.vector.Vector3f;
import com.bramerlabs.engine3D.math.vector.Vector4f;

public class Cube extends RenderObject {

    public Cube(Vector3f position, Vector3f rotation, Vector3f scale, Vector4f color) {
        super(new ColorMesh(new ColorVertex[] {
                new ColorVertex(-0.5f, -0.5f,  0.5f,  0,  0,  1, color),
                new ColorVertex( 0.5f, -0.5f,  0.5f,  0,  0,  1, color),
                new ColorVertex( 0.5f,  0.5f,  0.5f,  0,  0,  1, color),
                new ColorVertex(-0.5f,  0.5f,  0.5f,  0,  0,  1, color),
                new ColorVertex(-0.5f, -0.5f, -0.5f,  0,  0, -1, color),
                new ColorVertex( 0.5f, -0.5f, -0.5f,  0,  0, -1, color),
                new ColorVertex( 0.5f,  0.5f, -0.5f,  0,  0, -1, color),
                new ColorVertex(-0.5f,  0.5f, -0.5f,  0,  0, -1, color),
                new ColorVertex( 0.5f, -0.5f,  0.5f,  1,  0,  0, color),
                new ColorVertex( 0.5f,  0.5f,  0.5f,  1,  0,  0, color),
                new ColorVertex( 0.5f, -0.5f, -0.5f,  1,  0,  0, color),
                new ColorVertex( 0.5f,  0.5f, -0.5f,  1,  0,  0, color),
                new ColorVertex(-0.5f, -0.5f,  0.5f, -1,  0,  0, color),
                new ColorVertex(-0.5f,  0.5f,  0.5f, -1,  0,  0, color),
                new ColorVertex(-0.5f, -0.5f, -0.5f, -1,  0,  0, color),
                new ColorVertex(-0.5f,  0.5f, -0.5f, -1,  0,  0, color),
                new ColorVertex( 0.5f,  0.5f,  0.5f,  0,  1,  0, color),
                new ColorVertex(-0.5f,  0.5f,  0.5f,  0,  1,  0, color),
                new ColorVertex( 0.5f,  0.5f, -0.5f,  0,  1,  0, color),
                new ColorVertex(-0.5f,  0.5f, -0.5f,  0,  1,  0, color),
                new ColorVertex(-0.5f, -0.5f,  0.5f,  0, -1,  0, color),
                new ColorVertex( 0.5f, -0.5f,  0.5f,  0, -1,  0, color),
                new ColorVertex(-0.5f, -0.5f, -0.5f,  0, -1,  0, color),
                new ColorVertex( 0.5f, -0.5f, -0.5f,  0, -1,  0, color),
        }, new int[] {
                0, 1, 2, 2, 3, 0, 5, 4, 7, 5, 7, 6, 8, 10, 11, 8, 11, 9,
                14, 12, 13, 14, 13, 15, 17, 16, 18, 17, 18, 19, 20, 22, 23, 20, 23, 21,
        }), position, rotation, scale);
    }

    public Cube(Vector3f position, Vector3f rotation, Vector3f scale, Material material) {
        super(new TextureMesh(new TextureVertex[] {
                new TextureVertex(-0.5f, -0.5f,  0.5f,  0,  0,  1, 0, 1),
                new TextureVertex( 0.5f, -0.5f,  0.5f,  0,  0,  1, 1, 1),
                new TextureVertex( 0.5f,  0.5f,  0.5f,  0,  0,  1, 1, 0),
                new TextureVertex(-0.5f,  0.5f,  0.5f,  0,  0,  1, 0, 0),
                new TextureVertex(-0.5f, -0.5f, -0.5f,  0,  0, -1, 0, 1),
                new TextureVertex( 0.5f, -0.5f, -0.5f,  0,  0, -1, 1, 1),
                new TextureVertex( 0.5f,  0.5f, -0.5f,  0,  0, -1, 1, 0),
                new TextureVertex(-0.5f,  0.5f, -0.5f,  0,  0, -1, 0, 0),
                new TextureVertex( 0.5f, -0.5f,  0.5f,  1,  0,  0, 0, 1),
                new TextureVertex( 0.5f,  0.5f,  0.5f,  1,  0,  0, 0, 0),
                new TextureVertex( 0.5f, -0.5f, -0.5f,  1,  0,  0, 1, 1),
                new TextureVertex( 0.5f,  0.5f, -0.5f,  1,  0,  0, 1, 0),
                new TextureVertex(-0.5f, -0.5f,  0.5f, -1,  0,  0, 1, 1),
                new TextureVertex(-0.5f,  0.5f,  0.5f, -1,  0,  0, 1, 0),
                new TextureVertex(-0.5f, -0.5f, -0.5f, -1,  0,  0, 0, 1),
                new TextureVertex(-0.5f,  0.5f, -0.5f, -1,  0,  0, 0, 0),
                new TextureVertex( 0.5f,  0.5f,  0.5f,  0,  1,  0, 1, 1),
                new TextureVertex(-0.5f,  0.5f,  0.5f,  0,  1,  0, 0, 1),
                new TextureVertex( 0.5f,  0.5f, -0.5f,  0,  1,  0, 1, 0),
                new TextureVertex(-0.5f,  0.5f, -0.5f,  0,  1,  0, 0, 0),
                new TextureVertex(-0.5f, -0.5f,  0.5f,  0, -1,  0, 1, 1),
                new TextureVertex( 0.5f, -0.5f,  0.5f,  0, -1,  0, 0, 1),
                new TextureVertex(-0.5f, -0.5f, -0.5f,  0, -1,  0, 1, 0),
                new TextureVertex( 0.5f, -0.5f, -0.5f,  0, -1,  0, 0, 0),
        }, new int[] {
                0, 1, 2, 2, 3, 0, 5, 4, 7, 5, 7, 6, 8, 10, 11, 8, 11, 9,
                14, 12, 13, 14, 13, 15, 17, 16, 18, 17, 18, 19, 20, 22, 23, 20, 23, 21,
        }, material), position, rotation, scale);
    }

}
