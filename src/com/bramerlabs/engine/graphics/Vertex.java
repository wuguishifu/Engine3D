package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;


public class Vertex {

    // vertex data
    private final Vector3f position;
    private Vector3f normal, tangent, bitangent;

    // render data
    private final Vector4f color;
    private final Vector2f textureCoord;

    public Vertex(Vector3f position, Vector3f normal, Vector2f textureCoord) {
        this.position = position;
        this.normal = normal;
        this.textureCoord = textureCoord;

        this.color = new Vector4f(0, 0, 0, 0);
    }

    public Vertex(Vector3f position, Vector3f normal, Vector4f color) {
        this.position = position;
        this.normal = normal;
        this.color = color;

        this.textureCoord = new Vector2f(0, 0);
        this.tangent = new Vector3f(0, 0, 0);
        this.bitangent = new Vector3f(0, 0, 0);
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    public void setTangent(Vector3f tangent) {
        this.tangent = tangent;
    }

    public void setBitangent(Vector3f bitangent) {
        this.bitangent = bitangent;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public Vector3f getTangent() {
        return tangent;
    }

    public Vector3f getBitangent() {
        return bitangent;
    }

    public Vector4f getColor() {
        return color;
    }

    public Vector2f getTextureCoord() {
        return textureCoord;
    }
}
