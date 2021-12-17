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
    return vec2(uUV.x + in_.x * (uUV.z - uUV.x), uUV.y + in_.y * (uUV.w - uUV.y));
//    return in_;
}

void main() {
    colorOut = texture(uTexture, moduv(fUV)) * uTint;
    colorOut.r = 1.0f;
//    colorOut = vec4(1.0,1.0,1.0,1.0) * uTint;
    if (colorOut.a == 0) {
        discard;
    }
}
