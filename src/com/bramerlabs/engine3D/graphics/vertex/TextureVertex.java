package com.bramerlabs.engine3D.graphics.vertex;

import com.bramerlabs.engine3D.math.vector.Vector2f;
import com.bramerlabs.engine3D.math.vector.Vector3f;

public class TextureVertex extends Vertex {

    protected Vector2f textureCoord;
    protected Vector3f tangent, bitangent;

    public TextureVertex(Vector3f position, Vector3f normal, Vector2f textureCoord) {
        super(position, normal);
        this.textureCoord = textureCoord;
    }

    public TextureVertex(float px, float py, float pz, float nx, float ny, float nz, float tx, float ty) {
        super(new Vector3f(px, py, pz), new Vector3f(nx, ny, nz));
        this.textureCoord = new Vector2f(tx, ty);
    }

    public Vector2f getTextureCoord() {
        return this.textureCoord;
    }

    public Vector3f getTangent() {
        return this.tangent;
    }

    public Vector3f getBitangent() {
        return this.bitangent;
    }

    public void setTangent(Vector3f tangent) {
        this.tangent = tangent;
    }

    public void setBitangent(Vector3f bitangent) {
        this.bitangent = bitangent;
    }

}
