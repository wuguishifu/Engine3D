package com.bramerlabs.engine3D.test;

import com.bramerlabs.engine3D.graphics.Camera;
import com.bramerlabs.engine3D.graphics.Material;
import com.bramerlabs.engine3D.graphics.Shader;
import com.bramerlabs.engine3D.graphics.io.window.Input;
import com.bramerlabs.engine3D.graphics.io.window.Window;
import com.bramerlabs.engine3D.graphics.io.window.WindowConstants;
import com.bramerlabs.engine3D.graphics.renderers.Renderer;
import com.bramerlabs.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.engine3D.math.vector.Vector3f;
import com.bramerlabs.engine3D.objects.ObjectLoader;
import com.bramerlabs.engine3D.objects.RenderObject;
import org.lwjgl.opengl.GL46;

import java.awt.*;
import java.util.ArrayList;

public class Test implements Runnable {

    private final Input input;
    private final Window window;
    private final Camera camera;

    private Shader shader;
    private Renderer renderer;

    private RenderObject object;

    ArrayList<Matrix4f> models = new ArrayList<>();

    public static void main(String[] args) {
        new Test().start();
    }

    public Test() {
        input = new Input();
        window = new Window(new WindowConstants("Test", new Color(204, 232, 220)), input);
        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
    }

    public void start() {
        Thread main = new Thread(this, "Test Thread");
        main.start();
    }

    public void run() {
        this.init();
        while (!window.shouldClose()) {
            this.update();
            this.render();
        }
        this.close();
    }

    private void init() {
        window.create();
        camera.setFocus(new Vector3f(0, 0, 0));

//        shader = new Shader("shaders/cel/vertex.glsl", "shaders/cel/fragment.glsl");
        shader = new Shader("shaders/textured/vertex.glsl", "shaders/textured/fragment.glsl");
        shader.create();

        renderer = new Renderer(window, new Vector3f(-5, 20, 10));

        Material birch = new Material(
                "textures/birch/base.png",
                "textures/birch/specular.png",
                "textures/birch/normal.png");
        birch.create();

        object = ObjectLoader.parseTexture("resources/objects/birch.obj", birch);
        object.setScale(new Vector3f(1, 1, 1));
        object.createMesh();

        for (int i = -5; i < 5; i++) {
            for (int j = -5; j < 5; j++) {
                Vector3f position = new Vector3f((float) (i + Math.random()), -2, (float) (j + Math.random()));
                Vector3f scale = new Vector3f(10, 10, 10);
                Vector3f rotation = new Vector3f(0, (float) (360 * Math.random()), 0);
                models.add(Matrix4f.transform(position, rotation, scale));
            }
        }
    }

    private void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.updateArcball();
    }

    private void render() {
        for (Matrix4f model : models) {
            renderer.renderInstancedTexturedMesh(object, camera, shader, model);
        }
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
        object.destroy();
    }

}
