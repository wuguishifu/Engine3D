#version 460 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec3 tangent;
layout(location = 3) in vec3 bitangent;
layout(location = 4) in vec2 textureCoord;

out vec3 passFragPos;
out vec3 passNormal;
out vec3 passTangent;
out vec3 passBitangent;
out vec2 passTextureCoord;
out mat3 TBN;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
    gl_Position = projection * view * model * vec4(position, 1.0);
    passFragPos = vec3(model * vec4(position, 1.0));
    passNormal = normalize(vec3(model * vec4(normal, 0.0)));
    passTangent = normalize(vec3(model * vec4(tangent, 0.0)));
    passBitangent = normalize(vec3(model * vec4(bitangent, 0.0)));
    TBN = mat3(passTangent, passBitangent, passNormal);
    passTextureCoord = textureCoord;
}
