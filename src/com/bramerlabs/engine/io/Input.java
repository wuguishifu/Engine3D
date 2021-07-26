package com.bramerlabs.engine.io;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL46;

public class Input {

    private GLFWKeyCallback keyboard;
    private GLFWMouseButtonCallback mouse;
    private GLFWCursorPosCallback cursor;
    private GLFWWindowSizeCallback windowSize;
    private GLFWWindowPosCallback windowPos;
    private GLFWScrollCallback scrollWheel;

    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private float mouseX, mouseY;
    private float prevMouseX, prevMouseY;
    private float scrollX, scrollY;

    private int windowHeight, windowWidth;
    private int windowX, windowY;
    private boolean resized;

    public Input() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };

        mouse = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

        cursor = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                prevMouseX = mouseX;
                prevMouseY = mouseY;
                mouseX = (float) xpos;
                mouseY = (float) ypos;
            }
        };

        windowSize = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                windowWidth = width;
                windowHeight = height;
                GL46.glViewport(0, 0, width, height);
                resized = true;
            }
        };

        windowPos = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                windowX = xpos;
                windowY = ypos;
            }
        };

        scrollWheel = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public void destroy() {
        keyboard.free();
        mouse.free();
        cursor.free();
        scrollWheel.free();
        windowSize.free();
        windowPos.free();
    }

    public boolean isKeyDown(int key) {
        return keys[key];
    }

    public boolean isButtonDown(int button) {
        return buttons[button];
    }

    public GLFWKeyCallback getKeyboard() {
        return keyboard;
    }

    public GLFWMouseButtonCallback getMouse() {
        return mouse;
    }

    public GLFWCursorPosCallback getCursor() {
        return cursor;
    }

    public GLFWWindowSizeCallback getWindowSize() {
        return windowSize;
    }

    public GLFWWindowPosCallback getWindowPos() {
        return windowPos;
    }

    public GLFWScrollCallback getScrollWheel() {
        return scrollWheel;
    }

    public boolean[] getKeys() {
        return keys;
    }

    public boolean[] getButtons() {
        return buttons;
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    public float getPrevMouseX() {
        return prevMouseX;
    }

    public float getPrevMouseY() {
        return prevMouseY;
    }

    public float getScrollX() {
        return scrollX;
    }

    public float getScrollY() {
        return scrollY;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowX() {
        return windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public boolean isResized() {
        return resized;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }
}
