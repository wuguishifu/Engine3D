package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.file_util.FileLoader;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class Material {

    // paths
    private String texturePath, specularPath, normalPath;
    private static final String FORMAT = "PNG";

    // dimensions
    private float width, height;

    // texture IDs
    public int textureID, specularID, normalID;
    private Texture texture, specular, normal;

    public Material(String texturePath, String specularPath, String normalPath) {
        this.texturePath = texturePath;
        this.specularPath = specularPath;
        this.normalPath = normalPath;
        this.create();
    }

    public Material(String texturePath) {
        this.texturePath = texturePath;
        this.create();
    }

    public void create() {
        try {
            texture = TextureLoader.getTexture(FORMAT,
                    FileLoader.class.getModule().getResourceAsStream(texturePath), GL20.GL_NEAREST);
            specular = TextureLoader.getTexture(FORMAT,
                    FileLoader.class.getModule().getResourceAsStream(specularPath), GL20.GL_NEAREST);
            normal = TextureLoader.getTexture(FORMAT,
                    FileLoader.class.getModule().getResourceAsStream(normalPath), GL20.GL_NEAREST);
        } catch (IOException e) {
            e.printStackTrace();
            if (texture == null) {
                System.err.println("Could not load file at " + texturePath);
            }
            if (specular == null) {
                System.err.println("Could not load file at " + specularPath);
            }
            if (normal == null) {
                System.err.println("Could not load file at " + normalPath);
            }
        }

        width = texture.getWidth();
        height = texture.getHeight();

        textureID = texture.getTextureID();
        specularID = specular.getTextureID();
        normalID = normal.getTextureID();
    }

    public void destroy() {
        GL20.glDeleteTextures(textureID);
        GL20.glDeleteTextures(specularID);
        GL20.glDeleteTextures(normalID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }

    public int getSpecularID() {
        return specularID;
    }

    public int getNormalID() {
        return normalID;
    }
}
