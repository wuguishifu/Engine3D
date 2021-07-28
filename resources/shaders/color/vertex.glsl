#version 460 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 5) in vec4 color;

out vec4 passColor;
out vec3 passNormal;
out vec3 passFragPos;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
//    gl_Position = projection * view * model * vec4(position, 1.0);
    gl_Position = vec4(position, 1.0);
    passFragPos = vec3(model * vec4(position, 1.0));
    passNormal = normalize(vec3(model * vec4(normal, 0.0)));
    passColor = color;
}
