#version 400 core


//vertex shader ini diproses setiap vertex, trs nanti dikirim ke fragment shader buat coloring
//vertexShader kasih tau GPU dimana vertex ini harus dirender di screen dengan gl_Position

// input vector 3d dimensi
in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

void main() {
    gl_Position = vec4(position.xyz, 1.0);
    pass_textureCoords = textureCoords;
}