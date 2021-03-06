#version 450 core

uniform float uTime;
uniform vec4 uUV;

in vec2 fUV;
in vec4 fPos;
//in vec2 fPos2;

out vec4 colorOut;

uniform sampler2D uTexture;
uniform vec4 uTint;
uniform vec4 uColor;

vec2 moduv(vec2 in_) {
    return vec2((uUV.z - uUV.x) * in_.x, (uUV.w - uUV.y) * in_.y);
//    return in_;
}

void main() {
    colorOut = texture(uTexture, fUV) * uTint;
//    colorOut = vec4(1.0,1.0,1.0,1.0) * uTint;
    if (colorOut.a == 0) {
        discard;
    }
}
