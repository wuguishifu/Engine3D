package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    // default variables
    private Vector3f position, rotation;
    private final Input input;
    float mouseX, mouseY, mouseXOld, mouseYOld;

    // first person variables
    public float moveSpeed = 0.05f, mouseSensitivity = 0.1f, rotateSpeed = 0.02f * 360.0f;

    // arcball variables
    private Vector3f focus;
    private float scrollX, scrollY, scrollXOld, scrollYOld;
    private float verticalAngle, horizontalAngle;
    private float distance, horizontalDistance, verticalDistance;

    public Camera(Vector3f position, Vector3f rotation, Input input) {
        this.position = position;
        this.rotation = rotation;
        this.input = input;
    }

    public void updateFirstPerson() {
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();
        float dx = mouseX - mouseXOld;
        float dy = mouseY - mouseYOld;
        mouseXOld = mouseX;
        mouseYOld = mouseY;
        rotation = Vector3f.add(rotation, -dy * mouseSensitivity, -dx * mouseSensitivity, 0);
    }

    public void updateArcball() {
        mouseX = input.getMouseX();
        mouseY = input.getMouseY();

        float dx = mouseX - mouseXOld;
        float dy = mouseY - mouseYOld;

        scrollY = input.getScrollY();
        float ds = scrollY - scrollYOld;
        mouseXOld = mouseX;
        mouseYOld = mouseY;

        if (input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            verticalAngle -= dy * mouseSensitivity;
            horizontalAngle += dx * mouseSensitivity;
        }

        if (distance >= 0) {
            distance -= ds;
        } else {
            distance = 0;
        }

        horizontalDistance = distance * (float) Math.cos(Math.toRadians(verticalAngle)); // h = r cos(t_x)
        verticalDistance = distance * (float) Math.sin(Math.toRadians(verticalAngle)); // v = r sin(t_x)

        float xOffset = horizontalDistance * (float) Math.sin(Math.toRadians(-horizontalAngle));
        float zOffset = horizontalDistance * (float) Math.cos(Math.toRadians(-horizontalAngle));

        this.position = new Vector3f(focus.x + xOffset, focus.y - verticalDistance, focus.z + zOffset);
        this.rotation = new Vector3f(verticalAngle, -horizontalAngle, 0);

    }

    public void setFocus(Vector3f focus) {
        this.focus = focus;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public Matrix4f getViewMatrix() {
        return Matrix4f.view(position, rotation);
    }

}
