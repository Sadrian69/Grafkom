#version 330
uniform vec4 uniColor;
out vec4 fragColor;

void main() {
    //vec4(red,green,blue,alpha)
    //rgba -> red 100/255
    fragColor = uniColor;
}