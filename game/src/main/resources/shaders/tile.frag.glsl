#version 450 core
#define MAX_LIGHTS 100

uniform float uTime;
uniform sampler2D uTex;

//layout (std430,packed) uniform Lights {
//    vec2[MAX_LIGHTS] positions;
//    float[MAX_LIGHTS] strengths;
//};
uniform vec2 lpos;
uniform int numLights;

in vec2 fUV;
in vec4 fPos;
in vec2 fPos2;

out vec4 colorOut;

//float dst2(vec2 a, vec2 b) {
//    float x = a.x - b.x;
//    float y = a.y - b.y;
//    return x * x + y * y;
//}
//
//float invSqr(vec2 pos, vec2 lightPos) {
//    return 1.0 / (dst2(pos,lightPos));
//}

void main() {
//    vec3 tint = vec3(1.0,1.0,1.0);
//
////    for (int i = 0 ; i < numLights ; i++) {
////        if (i >= MAX_LIGHTS) break;
////        float intensity = max(0,min(invSqr(fPos.xy, positions[i]), 1));
////        tint = tint * intensity;
////
////    }
//    float intensity = max(0,min(invSqr(fPos2, lpos) * (20000 + 16000 * sin(uTime * 10)), 1));
//    tint = tint * intensity * vec3(1.0, 0.9254901960784314, 0.7411764705882353);
////
//    colorOut = vec4(tint, 1.0) * texture(uTex, fUV);

    colorOut = texture(uTex, fUV);
}