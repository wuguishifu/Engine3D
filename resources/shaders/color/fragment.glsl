#version 330

in vec4 passColor;
in vec3 passNormal;
in vec3 passFragPos;

uniform vec3 lightPos;
uniform vec3 lightColor;
uniform float lightLevel;
uniform float specularStrength;
uniform float specularPower;
uniform vec3 viewPos;

out vec4 outColor;

void main() {
    vec3 color = passColor.xyz;
    float alpha = passColor.w;
    vec3 normal = passNormal;

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
    vec3 specularLight = specularStrength * spec * lightColor;

    // combine color components
    color = (ambientLight + diffuseLight + specularLight) * color;
//    outColor = vec4(color, alpha);
//    outColor = vColor;
    outColor = vec4(1.0, 1.0, 1.0, 1.0);
}
