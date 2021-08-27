#version 330 core

// input vectors
in vec4 passColor;
in vec3 passNormal;
in vec3 passFragPos;

// the lighting values for shading
uniform vec3 lightPos;
uniform vec3 lightColor;
uniform float lightLevel;

// the position of the camera
uniform vec3 viewPos;

uniform float reflectiveness;

// the output color
out vec4 outColor;

void main() {

    vec3 lightDir = normalize(lightPos - passFragPos); // find the direction of the light
    float intensity = dot(lightDir, passNormal);

    vec3 colorComp = passColor.xyz;
    colorComp = vec3(1.0, 1.0, 1.0);

    vec4 color;

    if (intensity >  0.95) {
        color = vec4(0.95 * colorComp, 1.0);
    } else if (intensity > 0.80) {
        color = vec4(0.80 * colorComp, 1.0);
    } else if (intensity > 0.70) {
        color = vec4(0.70 * colorComp, 1.0);
    } else if (intensity > 0.60) {
        color = vec4(0.60 * colorComp, 1.0);
    } else if (intensity > 0.50) {
        color = vec4(0.50 * colorComp, 1.0);
    } else if (intensity > 0.40) {
        color = vec4(0.40 * colorComp, 1.0);
    } else if (intensity > 0.30) {
        color = vec4(0.30 * colorComp, 1.0);
    } else if (intensity > 0.20) {
        color = vec4(0.20 * colorComp, 1.0);
    } else {
        color = vec4(0.15 * colorComp, 1.0);
    }

    outColor = color;
}