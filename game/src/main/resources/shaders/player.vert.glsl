#version 450 core

layout(location=0) in vec3 vPos;
layout(location=1) in vec2 vUV;

uniform mat4 uModel;
uniform mat4 uViewProjection;

out vec2 fUV;
out vec4 fPos;

void main() {
    gl_Position = uViewProjection * uModel * vec4(vPos, 1.0);

    fPos = gl_Position;
    fUV = vUV;
}
