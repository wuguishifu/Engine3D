package com.bramerlabs.engine.io;

import com.bramerlabs.engine.math.matrix.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;

import java.awt.*;

public class Window {

    private String title;

    private DisplayMode displayMode;
    private int width, height;
    private final int DEFAULT_WIDTH, DEFAULT_HEIGHT;
    private long windowHandle;

    public final float R, G, B;

    private static final int FRAME_RATE_CAPPED = GLFW.GLFW_TRUE;
    private int frames;
    private long time;

    private final Input input;

    private Matrix4f projectionMatrix;

    public Window(WindowConstants windowConstants, Input input) {
        this.R = windowConstants.BACKGROUND_COLOR.getRed() / 255.0f;
        this.G = windowConstants.BACKGROUND_COLOR.getGreen() / 255.0f;
        this.B = windowConstants.BACKGROUND_COLOR.getBlue() / 255.0f;
        this.title = windowConstants.APPLICATION_NAME;

        this.input = input;

        this.displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        this.DEFAULT_WIDTH = displayMode.getWidth() / 2;
        this.DEFAULT_HEIGHT = displayMode.getHeight() / 2;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;

        this.projectionMatrix = Matrix4f.projection(70.0f, (float) width / height, 0.01f, 100f);
    }

    public void create() {
        this.time = System.currentTimeMillis();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize the GLFW window");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4); // antialiasing

        this.windowHandle = GLFW.glfwCreateWindow(width, height, title, GLFW.GLFW_FALSE, GLFW.GLFW_FALSE);

        if (windowHandle == GLFW.GLFW_FALSE) {
            throw new IllegalStateException("Unable to create the GLFW window");
        }

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        assert vidMode != null;
        GLFW.glfwSetWindowSize(windowHandle, vidMode.width() / 2, vidMode.height() / 2);
        GLFW.glfwSetWindowPos(windowHandle, vidMode.width() / 4, vidMode.height() / 4);

        GLFW.glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        // cull faces
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // only render visible vertices
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glDepthFunc(GL11.GL_LESS);

        // enable antialiasing
        GL11.glEnable(GL46.GL_MULTISAMPLE);

        // enable blending for transparency
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL46.glClearColor(R, G, B, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        GL46.glViewport(0, 0, width, height);

        GLFW.glfwSetKeyCallback(windowHandle, input.getKeyboard());
        GLFW.glfwSetMouseButtonCallback(windowHandle, input.getMouse());
        GLFW.glfwSetCursorPosCallback(windowHandle, input.getCursor());
        GLFW.glfwSetWindowSizeCallback(windowHandle, input.getWindowSize());
        GLFW.glfwSetWindowPosCallback(windowHandle, input.getWindowPos());
        GLFW.glfwSetScrollCallback(windowHandle, input.getScrollWheel());

        input.setWindowX(vidMode.width() / 4);
        input.setWindowY(vidMode.height() / 4);

        GLFW.glfwShowWindow(windowHandle);

        GLFW.glfwSwapInterval(FRAME_RATE_CAPPED);
        GLFW.glfwSetWindowTitle(windowHandle, title + " | FPS: " + frames);
    }

    public void update() {
        if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            GLFW.glfwSetWindowShouldClose(windowHandle, true);
        }

        GLFW.glfwPollEvents();

        if (input.isResized()) {
            this.width = input.getWindowWidth();
            this.height = input.getWindowHeight();
            this.projectionMatrix = Matrix4f.projection(70.0f, (float) width / height, 0.01f, 100f);
        }

        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(windowHandle, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }

    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowHandle);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void close() {
        GLFW.glfwSetWindowShouldClose(windowHandle, true);
    }

    public void destroy() {
        this.close();
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

}
