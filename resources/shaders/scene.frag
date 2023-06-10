#version 330

out vec4 fragColor;
uniform vec4 uniColor;
void main()
{
    //vec4(red,green,blue,alpha)
    //rgba -> red 100/255
    //fragColor = vec4(1.0,0.0,0.0,1.0);
    fragColor = uniColor;
}