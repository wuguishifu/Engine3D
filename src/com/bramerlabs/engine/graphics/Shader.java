package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.file_util.FileLoader;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Shader {

    private String vertex, fragment;
    private int vertexID, fragmentID, programID;

    public Shader(String vertexPath, String fragmentPath) {
        this.vertex = FileLoader.load(vertexPath);
        this.fragment = FileLoader.load(fragmentPath);
        create();
    }

    public void create() {
        programID = GL20.glCreateProgram();
        vertexID = createShader(GL20.GL_VERTEX_SHADER, vertex);
        fragmentID = createShader(GL20.GL_FRAGMENT_SHADER, fragment);
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);
        this.linkProgram(programID);
        this.validateProgram(programID);
    }

    private static int createShader(int shaderType, String shaderFile) {
        int shaderID = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shaderID, shaderFile);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            System.err.println("Could not compile shader at " + shaderFile);
            System.err.println("Shader: " + GL20.glGetShaderInfoLog(shaderID));
            throw new RuntimeException();
        }
        return shaderID;
    }

    public void linkProgram(int programID) {
        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            System.err.println("Shader program could not be linked");
            System.err.println("Program: " + GL20.glGetProgramInfoLog(programID));
            throw new RuntimeException();
        }
    }

    public void validateProgram(int programID) {
        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL20.GL_FALSE) {
            System.err.println("Shader program could not be validated");
            System.err.println("Program: " + GL20.glGetProgramInfoLog(programID));
            throw new RuntimeException();
        }
    }

    public int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    public void setUniform(String name, float value) {
        GL20.glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform(String name, Vector2f value) {
        GL20.glUniform2f(getUniformLocation(name), value.x, value.y);
    }

    public void setUniform(String name, Vector3f value) {
        GL20.glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }

    public void setUniform(String name, Vector4f value) {
        GL20.glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    public void setUniform(String name, Matrix4f value) {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE * Matrix4f.SIZE);
        matrix.put(value.getAll()).flip();
        GL20.glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }

    public void bind() {
        GL20.glUseProgram(programID);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void destroy() {
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
        GL20.glDeleteShader(programID);
    }

}
