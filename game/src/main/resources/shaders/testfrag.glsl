#version 450 core

const int MAX_MARCHING_STEPS = 255;
const float MIN_DIST = 0.0;
const float MAX_DIST = 100.0;
const float EPSILON = 0.0001;

uniform float uTime;

uniform sampler2D uTex;

in vec3 fColor;
in vec2 fUV;
in vec4 fPos;

out vec4 colorOut;

void main() {
    colorOut = texture(uTex, fUV);
}