#version 400 core


//vertex shader ini diproses setiap vertex, trs nanti dikirim ke fragment shader buat coloring
//vertexShader kasih tau GPU dimana vertex ini harus dirender di screen dengan gl_Position

// input vector 3d dimensi
in vec3 position;

out vec3 colour;

void main() {
    gl_Position = vec4(position.xyz, 1.0);
    //creating output colour urutane r,g,b
    colour = vec3(position.x+0.5,1.0,position.y+0.5);
}