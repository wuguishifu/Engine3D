package com.bramerlabs.engine.objects;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;

public class Cube extends RenderObject {

    public Cube(Vector3f position, Vector3f rotation, Vector3f scale, Vector4f color) {
        super(new Mesh(new Vertex[]{
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector3f( 0,  0,  1), color), // front
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector3f( 0,  0,  1), color),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector3f( 0,  0,  1), color),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector3f( 0,  0,  1), color),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f( 0,  0, -1), color), // back
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector3f( 0,  0, -1), color),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector3f( 0,  0, -1), color),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector3f( 0,  0, -1), color),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector3f( 1,  0,  0), color), // right
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector3f( 1,  0,  0), color),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector3f( 1,  0,  0), color),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector3f( 1,  0,  0), color),
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector3f(-1,  0,  0), color), // left
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector3f(-1,  0,  0), color),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(-1,  0,  0), color),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector3f(-1,  0,  0), color),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector3f( 0,  1,  0), color), // top
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector3f( 0,  1,  0), color),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector3f( 0,  1,  0), color),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector3f( 0,  1,  0), color),
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector3f( 0, -1,  0), color), // bottom
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector3f( 0, -1,  0), color),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f( 0, -1,  0), color),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector3f( 0, -1,  0), color),
        }, new int[]{
                 0,  1,  2,  2,  3,  0, // front
                 5,  4,  7,  5,  7,  6, // back
                 8, 10, 11,  8, 11,  9, // right
                14, 12, 13, 14, 13, 15, // left
                17, 16, 18, 17, 18, 19, // top
                20, 22, 23, 20, 23, 21, // bottom
        }, null), position, rotation, scale);
    }

    public Cube(Vector3f position, Vector3f rotation, Vector3f scale, String texture, String specular, String normal) {
        super(new Mesh(new Vertex[]{
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector3f( 0,  0,  1), new Vector2f(0, 1)), // front
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector3f( 0,  0,  1), new Vector2f(1, 1)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector3f( 0,  0,  1), new Vector2f(1, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector3f( 0,  0,  1), new Vector2f(0, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f( 0,  0, -1), new Vector2f(0, 1)), // back
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector3f( 0,  0, -1), new Vector2f(1, 1)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector3f( 0,  0, -1), new Vector2f(1, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector3f( 0,  0, -1), new Vector2f(0, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector3f( 1,  0,  0), new Vector2f(0, 1)), // right
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector3f( 1,  0,  0), new Vector2f(0, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector3f( 1,  0,  0), new Vector2f(1, 1)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector3f( 1,  0,  0), new Vector2f(1, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector3f(-1,  0,  0), new Vector2f(1, 1)), // left
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector3f(-1,  0,  0), new Vector2f(1, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(-1,  0,  0), new Vector2f(0, 1)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector3f(-1,  0,  0), new Vector2f(0, 0)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector3f( 0,  1,  0), new Vector2f(1, 1)), // top
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector3f( 0,  1,  0), new Vector2f(0, 1)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector3f( 0,  1,  0), new Vector2f(1, 0)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector3f( 0,  1,  0), new Vector2f(0, 0)),
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector3f( 0, -1,  0), new Vector2f(1, 1)), // bottom
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector3f( 0, -1,  0), new Vector2f(0, 1)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f( 0, -1,  0), new Vector2f(1, 0)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector3f( 0, -1,  0), new Vector2f(0, 0)),
        }, new int[]{
                0,  1,  2,  2,  3,  0, // front
                5,  4,  7,  5,  7,  6, // back
                8, 10, 11,  8, 11,  9, // right
                14, 12, 13, 14, 13, 15, // left
                17, 16, 18, 17, 18, 19, // top
                20, 22, 23, 20, 23, 21, // bottom
        }, new Material(texture, specular, normal)), position, rotation, scale);
    }

}
