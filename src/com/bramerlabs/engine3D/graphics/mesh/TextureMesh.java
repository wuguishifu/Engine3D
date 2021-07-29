package com.bramerlabs.engine3D.graphics.mesh;

import com.bramerlabs.engine3D.graphics.Material;
import com.bramerlabs.engine3D.graphics.vertex.TextureVertex;
import com.bramerlabs.engine3D.math.vector.Vector2f;
import com.bramerlabs.engine3D.math.vector.Vector3f;
import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class TextureMesh extends Mesh {

    private Material material;

    public TextureMesh(TextureVertex[] vertices, int[] indices, Material material) {
        super(vertices, indices);
        this.material = material;
    }

    @Override
    public void create() {
        material.create();
        vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);
        makePositionBuffer();
        makeNormalBuffer();
        makeTextureBuffer();
        makeTangentBuffers();
        makeIndexBuffer();
    }

    public void makeTextureBuffer() {
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            TextureVertex vertex = (TextureVertex) vertices[i];
            textureData[2 * i    ] = vertex.getTextureCoord().x;
            textureData[2 * i + 1] = vertex.getTextureCoord().y;
        }
        textureBuffer.put(textureData).flip();
        tbo = storeData(textureBuffer, TEXTURE_COORD, 2);
    }

    public void makeTangentBuffers() {
        for (int i = 0; i < indices.length; i += 3) {
            // get the vertices in this triangle
            TextureVertex v1 = (TextureVertex) vertices[indices[i    ]];
            TextureVertex v2 = (TextureVertex) vertices[indices[i + 1]];
            TextureVertex v3 = (TextureVertex) vertices[indices[i + 2]];

            // calculate the edge vectors
            Vector3f edge1 = Vector3f.subtract(v2.getPosition(), v1.getPosition());
            Vector3f edge2 = Vector3f.subtract(v3.getPosition(), v1.getPosition());

            // calculate the difference in texture coord
            Vector2f deltaUV1 = Vector2f.subtract(v2.getTextureCoord(), v1.getTextureCoord());
            Vector2f deltaUV2 = Vector2f.subtract(v3.getTextureCoord(), v1.getTextureCoord());

            // calculate fractional f
            float f = 1.0f / (deltaUV1.getX() * deltaUV2.getY() - deltaUV2.getX() * deltaUV1.getY());

            Vector3f tangent = new Vector3f(
                    f * (deltaUV2.y * edge1.x - deltaUV1.y * edge2.x),
                    f * (deltaUV2.y * edge1.y - deltaUV1.y * edge2.y),
                    f * (deltaUV2.y * edge1.z - deltaUV1.y * edge2.z));
            Vector3f bitangent = new Vector3f(
                    f * (-deltaUV2.x * edge1.x + deltaUV1.x * edge2.x),
                    f * (-deltaUV2.x * edge1.y + deltaUV1.x * edge2.y),
                    f * (-deltaUV2.x * edge1.z + deltaUV1.x * edge2.z));

            // set the tangents
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

        // preallocate memory
        FloatBuffer tangentBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        FloatBuffer bitangentBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);

        // create new temp array to store data
        float[] tangentData = new float[vertices.length * 3];
        float[] bitangentData = new float[vertices.length * 3];

        // add all the tangent and bitangent ata
        for (int i = 0; i < vertices.length; i ++) {
            TextureVertex vertex = (TextureVertex) vertices[i];
            tangentData[i * 3    ] = vertex.getTangent().getX();
            tangentData[i * 3 + 1] = vertex.getTangent().getY();
            tangentData[i * 3 + 2] = vertex.getTangent().getZ();
            bitangentData[i * 3    ] = vertex.getBitangent().getX();
            bitangentData[i * 3 + 1] = vertex.getBitangent().getY();
            bitangentData[i * 3 + 2] = vertex.getBitangent().getZ();
        }

        // flip the data to make it handleable by OpenGL
        tangentBuffer.put(tangentData).flip();
        bitangentBuffer.put(bitangentData).flip();

        // store the data in the buffer object
        this.tan = storeData(tangentBuffer, TANGENT, 3);
        this.bbo = storeData(bitangentBuffer, BITANGENT, 3);
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void destroy() {
        super.destroy();
        material.destroy();
    }

}
