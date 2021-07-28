package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Mesh {

    private Vertex[] vertices;
    private int[] indices;

    public Material material;

    private int
    vao, // vertex array buffer
    pbo, // position buffer
    nbo, // normal buffer
    tan, // tangent buffer
    bbo, // bitangent buffer
    tbo, // texture coord buffer
    ibo, // index array buffer
    cbo; // color buffer

    public static final int
    POSITION    = 0,
    NORMAL      = 1,
    TANGENT     = 2,
    BITANGENT   = 3,
    TEXTURE     = 4,
    COLOR       = 5;

    public Mesh(Vertex[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    public Mesh(ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
        this.vertices = new Vertex[vertices.size()];
        this.indices = new int[indices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            this.vertices[i] = vertices.get(i);
        }
        for (int i = 0; i < indices.size(); i++) {
            this.indices[i] = indices.get(i);
        }
    }

    public Mesh(ArrayList<Vertex> vertices) {
        this.vertices = new Vertex[vertices.size()];
        this.indices = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            this.vertices[i] = vertices.get(i);
            this.indices[i] = i;
        }
    }

    public void create() {
        vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);
        makePositionBuffer();
        makeNormalBuffer();
        makeSubTangentBuffers();
        makeTextureBuffer();
        makeColorBuffer();
        makeIndexBuffer();
    }

    private void makePositionBuffer() {
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3); // [x, y, z]
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            positionData[i * 3] = vertices[i].getPosition().x;
            positionData[i * 3 + 1] = vertices[i].getPosition().y;
            positionData[i * 3 + 2] = vertices[i].getPosition().z;
        }
        positionBuffer.put(positionData).flip();
        pbo = storeData(positionBuffer, POSITION, 3);
    }

    private void makeNormalBuffer() {
        FloatBuffer normalBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] normalData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            normalData[i * 3] = vertices[i].getNormal().x;
            normalData[i * 3 + 1] = vertices[i].getNormal().y;
            normalData[i * 3 + 2] = vertices[i].getNormal().z;
        }
        normalBuffer.put(normalData).flip();
        nbo = storeData(normalBuffer, NORMAL, 3);
    }

    private void makeSubTangentBuffers() {
        // iterate over each triangle
        for (int i = 0; i < indices.length; i += 3) {
            // vertices of the triangle
            Vertex v1 = vertices[indices[i]];
            Vertex v2 = vertices[indices[i + 1]];
            Vertex v3 = vertices[indices[i + 2]];

            // calculate the edge vectors
            Vector3f edge1 = Vector3f.subtract(v2.getPosition(), v1.getPosition());
            Vector3f edge2 = Vector3f.subtract(v3.getPosition(), v1.getPosition());

            // calculate the difference in texture coordinate
            Vector2f deltaUV1 = Vector2f.subtract(v2.getTextureCoord(), v1.getTextureCoord());
            Vector2f deltaUV2 = Vector2f.subtract(v3.getTextureCoord(), v1.getTextureCoord());

            // calculate the f value
            float f = 1.0f / (deltaUV1.x * deltaUV2.y - deltaUV2.x * deltaUV1.y);
            Vector3f tangent = new Vector3f(
                    f * (deltaUV2.y * edge1.x - deltaUV1.y * edge2.x),
                    f * (deltaUV2.y * edge1.y - deltaUV1.y * edge2.y),
                    f * (deltaUV2.y * edge1.z - deltaUV1.y * edge2.z)
            );
            Vector3f bitangent = new Vector3f(
                    f * (-deltaUV2.x * edge1.x - deltaUV1.x * edge2.x),
                    f * (-deltaUV2.x * edge1.y - deltaUV1.x * edge2.y),
                    f * (-deltaUV2.x * edge1.z - deltaUV1.x * edge2.z)
            );

            // set tangent and bitangent vectors
            if (v1.getTangent() == null || v1.getBitangent() == null) {
                v1.setTangent(tangent);
                v1.setBitangent(bitangent);
            }
            if (v2.getTangent() == null || v2.getBitangent() == null) {
                v2.setTangent(tangent);
                v2.setBitangent(bitangent);
            }
            if (v3.getTangent() == null || v3.getBitangent() == null) {
                v3.setTangent(tangent);
                v3.setBitangent(bitangent);
            }
        }

        // store data
        FloatBuffer tangentBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        FloatBuffer bitangentBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] tangentData = new float[vertices.length * 3];
        float[] bitangentData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            tangentData[i * 3] = vertices[i].getTangent().x;
            tangentData[i * 3 + 1] = vertices[i].getTangent().y;
            tangentData[i * 3 + 2] = vertices[i].getTangent().z;

            bitangentData[i * 3] = vertices[i].getBitangent().x;
            bitangentData[i * 3 + 1] = vertices[i].getBitangent().y;
            bitangentData[i * 3 + 2] = vertices[i].getBitangent().z;
        }
        tangentBuffer.put(tangentData).flip();
        bitangentBuffer.put(bitangentData).flip();
        this.tan = storeData(tangentBuffer, TANGENT, 3);
        this.bbo = storeData(bitangentBuffer, BITANGENT, 3);
    }

    private void makeTextureBuffer() {
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            textureData[i * 2] = vertices[i].getTextureCoord().x;
            textureData[i * 2 + 1] = vertices[i].getTextureCoord().y;
        }
        textureBuffer.put(textureData).flip();
        tbo = storeData(textureBuffer, TEXTURE, 2);
    }

    private void makeColorBuffer() {
        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 4);
        float[] colorData = new float[vertices.length * 4];
        for (int i = 0; i < vertices.length; i++) {
            colorData[i * 4] = vertices[i].getColor().x;
            colorData[i * 4 + 1] = vertices[i].getColor().y;
            colorData[i * 4 + 2] = vertices[i].getColor().z;
            colorData[i * 4 + 3] = vertices[i].getColor().w;
        }
        colorBuffer.put(colorData).flip();
        cbo = storeData(colorBuffer, COLOR, 4);
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    private void makeIndexBuffer() {
        IntBuffer indexBuffer = MemoryUtil.memAllocInt(indices.length);
        indexBuffer.put(indices).flip();
        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void destroy() {
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(nbo);
        GL15.glDeleteBuffers(tan);
        GL15.glDeleteBuffers(bbo);
        GL15.glDeleteBuffers(tbo);
        GL15.glDeleteBuffers(cbo);
        GL30.glDeleteVertexArrays(vao);
        material.destroy();
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public Material getMaterial() {
        return material;
    }

    public int getVAO() {
        return vao;
    }

    public int getPBO() {
        return pbo;
    }

    public int getNBO() {
        return nbo;
    }

    public int getTAN() {
        return tan;
    }

    public int getBBO() {
        return bbo;
    }

    public int getTBO() {
        return tbo;
    }

    public int getIBO() {
        return ibo;
    }

    public int getCBO() {
        return cbo;
    }
}
