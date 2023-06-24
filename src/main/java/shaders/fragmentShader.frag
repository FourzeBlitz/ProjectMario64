#version 400 core

//fragment shader executes setiap pixel menentukan pixel ini warna apa dari vertex shader
in vec3 colour;
in vec2 pass_textureCoords;

out vec4 out_Color;

//basically represents the texture that we are using
uniform sampler2D modelTexture;

void main() {
    // ngasih warna pixel sesuai dengan texture sampler di coord texture coords
    out_Color = texture(modelTexture, pass_textureCoords);
}