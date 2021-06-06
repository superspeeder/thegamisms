#version 450 core

layout(location=0) in vec3 vPos;
layout(location=1) in vec3 vColor;
layout(location=2) in vec2 vUV;

uniform mat4 model;
uniform mat4 viewProjection;

out vec3 fColor;
out vec2 fUV;
out vec4 fPos;

void main() {
    gl_Position = viewProjection * model * vec4(vPos, 1.0);

    fPos = gl_Position;
    fColor = vColor;
    fUV = vUV;
}
