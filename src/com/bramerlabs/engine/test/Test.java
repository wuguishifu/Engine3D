package com.bramerlabs.engine.test;

import com.bramerlabs.engine.io.Input;
import com.bramerlabs.engine.io.Window;
import com.bramerlabs.engine.io.WindowConstants;
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
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.R, window.G, window.B, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        System.arraycopy(keysDown, 0, keysDownOld, 0, keysDown.length);
        System.arraycopy(input.getKeys(), 0, keysDown, 0, input.getKeys().length);
    }

    public void render() {

    }

    public void close() {
        window.destroy();
    }

}
