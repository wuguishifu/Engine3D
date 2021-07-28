package com.bramerlabs.engine.graphics.renderers;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    private final Window window;
    public Vector3f lightPosition;

    public Vector3f lightColor = new Vector3f(1.0f, 1.0f, 1.0f);

    public Renderer(Window window, Vector3f lightPosition) {
        this.window = window;
        this.lightPosition = lightPosition;
    }

    public void renderColoredMesh(RenderObject object, Camera camera, Shader shader) {
        GL30.glBindVertexArray(object.mesh.getVAO());

        GL30.glEnableVertexAttribArray(Mesh.POSITION);
        GL30.glEnableVertexAttribArray(Mesh.NORMAL);
        GL30.glEnableVertexAttribArray(Mesh.COLOR);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.mesh.getIBO());

        shader.bind();
        shader.setUniform("model", object.getModelMatrix());
        shader.setUniform("view", camera.getViewMatrix());
        shader.setUniform("projection", window.getProjectionMatrix());
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("lightLevel", 0.3f);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("specularStrength", 1.0f);
        shader.setUniform("specularPower", 32.0f);
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        GL30.glDisableVertexAttribArray(Mesh.POSITION);
        GL30.glDisableVertexAttribArray(Mesh.NORMAL);
        GL30.glDisableVertexAttribArray(Mesh.COLOR);

        GL30.glBindVertexArray(0);
    }

    public void renderTexturedMesh(RenderObject object, Camera camera, Shader shader) {
        GL30.glBindVertexArray(object.mesh.getVAO());

        GL30.glEnableVertexAttribArray(Mesh.POSITION);
        GL30.glEnableVertexAttribArray(Mesh.NORMAL);
        GL30.glEnableVertexAttribArray(Mesh.TANGENT);
        GL30.glEnableVertexAttribArray(Mesh.BITANGENT);
        GL30.glEnableVertexAttribArray(Mesh.TEXTURE);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.mesh.getIBO());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.mesh.material.textureID);
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 1);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.mesh.material.specularID);
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 2);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.mesh.material.normalID);

        shader.bind();
        shader.setUniform("model", object.getModelMatrix());
        shader.setUniform("view", camera.getViewMatrix());
        shader.setUniform("projection", window.getProjectionMatrix());
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightColor", lightColor);
        shader.setUniform("lightLevel", 0.3f);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("specularStrength", 1.0f);
        shader.setUniform("specularPower", 32.0f);
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 1);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + 2);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        GL30.glDisableVertexAttribArray(Mesh.POSITION);
        GL30.glDisableVertexAttribArray(Mesh.NORMAL);
        GL30.glDisableVertexAttribArray(Mesh.TANGENT);
        GL30.glDisableVertexAttribArray(Mesh.BITANGENT);
        GL30.glDisableVertexAttribArray(Mesh.TEXTURE);

        GL30.glBindVertexArray(0);
    }
}