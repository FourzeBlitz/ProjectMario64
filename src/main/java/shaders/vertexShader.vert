#version 400 core


//vertex shader ini diproses setiap vertex, trs nanti dikirim ke fragment shader buat coloring
//vertexShader kasih tau GPU dimana vertex ini harus dirender di screen dengan gl_Position

// input vector 3d dimensi
in vec3 position;
in vec2 textureCoords;

out vec3 colour;
out vec2 pass_textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
//    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position,1.0);
    gl_Position = transformationMatrix * vec4(position,1.0);
    pass_textureCoords = textureCoords;
    colour = vec3(position.x+0.5,0.0,position.y+0.5);
}