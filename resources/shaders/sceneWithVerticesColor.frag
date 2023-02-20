#version 330
out vec4 fragColor;
in vec4 outColor;

void main() {
    //vec4(red,green,blue,alpha)
    //rgba -> red 100/255
    //fragColor = vec4(1.0,0.0,0.0,1.0);
    fragColor = outColor;
}