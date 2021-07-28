package com.bramerlabs.engine.test;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.io.window.WindowConstants;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.Cube;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

import java.awt.*;

public class Test implements Runnable {

    private final Input input = new Input();
    private final WindowConstants windowConstants = new WindowConstants("Test",
            new Color(204, 232, 220));
    private final Window window = new Window(windowConstants, input);

    boolean[] keysDown;
    boolean[] keysDownOld;

    private Cube cube;
    private Shader shader;
    private Renderer renderer;
    private Camera camera;

    public static void main(String[] args) {
        new Test().start();
    }

    public void start() {
        Thread main = new Thread(this, "Test");
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

    public void init() {
        window.create();

        keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        keysDownOld = new boolean[GLFW.GLFW_KEY_LAST];

//        cube = new Cube(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1),
//                "textures/wall/base.png", "textures/wall/normal.png", "textures/wall/specular.png");
        cube = new Cube(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1),
                new Vector4f(0.5f, 0.5f, 0.5f, 1.0f));
        cube.createMesh();

        shader = new Shader("shaders/color/vertex.glsl", "shaders/color/fragment.glsl");
        shader.create();

        renderer = new Renderer(window, new Vector3f(5, 10, 20));

        camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input);
        camera.setFocus(new Vector3f(0, 0, 0));
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.R, window.G, window.B, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        System.arraycopy(keysDown, 0, keysDownOld, 0, keysDown.length);
        System.arraycopy(input.getKeys(), 0, keysDown, 0, input.getKeys().length);

        camera.updateArcball();

        window.swapBuffers();
    }

    public void render() {
//        renderer.renderTexturedMesh(cube, camera, shader);
        renderer.renderColoredMesh(cube, camera, shader);
    }

    public void close() {
        window.destroy();
    }

}
