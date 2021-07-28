#version 330 core

in vec2 passTextureCoord;
in vec3 passFragPos;
in vec3 passTangent;
in vec3 passBitangent;
in vec3 passNormal;
in mat3 TBN;

uniform vec3 lightPos;
uniform vec3 lightColor;
uniform float lightLevel;
uniform vec3 viewPos;
uniform float specularStrength;
uniform float specularPower;

layout(binding = 0) uniform sampler2D baseMap;
layout(binding = 1) uniform sampler2D specularMap;
layout(binding = 2) uniform sampler2D normalMap;

out vec4 outColor;

void main() {
    vec4 textureColor = texture(baseMap, passTextureCoord);
    vec3 color = textureColor.xyz;
    float alpha = textureColor.w;

    vec3 normal = texture(normalMap, passTextureCoord).rgb;
    normal = normal * 2.0 - 1.0; // set range [-1, 1]
    normal = normalize(TBN * normal);

    // calculate ambient lighting
    vec3 ambientLight = lightLevel * lightColor;

    // calculate diffusion lighting
    vec3 lightDir = normalize(lightPos - passFragPos);
    float diff = max(dot(normal, lightDir), 0.0);
    vec3 diffuseLight = diff * lightColor;

    // calculate specular lighting
    vec3 viewDir = normalize(viewPos - passFragPos);
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), specularPower);
    vec3 specularLight = specularStrength * spec * vec3(texture(specularMap, passTextureCoord));

    // combine color components
    color = (ambientLight + diffuseLight + specularLight) * color;
    outColor = vec4(color, alpha);

}
