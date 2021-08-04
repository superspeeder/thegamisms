#version 450 core

uniform float uTime;
uniform vec4 uUV;

in vec2 fUV;
in vec4 fPos;
//in vec2 fPos2;

out vec4 colorOut;

uniform sampler2D uTexture;
uniform vec4 uColor;

void main() {
    colorOut = texture(uTexture, fUV).rrrr * uColor;
}
