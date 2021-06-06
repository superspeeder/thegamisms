#version 450 core

uniform float uTime;

uniform sampler2D uTex;

in vec2 fUV;
in vec4 fPos;

out vec4 colorOut;

void main() {
    colorOut = texture(uTex, fUV);
}