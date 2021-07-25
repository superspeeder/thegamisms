#version 450 core

uniform float uTime;
uniform vec4 uColor;

//in vec2 fUV;
in vec4 fPos;
//in vec2 fPos2;

out vec4 colorOut;


void main() {
    colorOut = uColor;
}
