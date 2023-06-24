#version 400 core

//fragment shader executes setiap pixel menentukan pixel ini warna apa

//variable vec3 colour ini hasil dari out vertex shader trs diambil msk fragment sini
in vec3 colour;

out vec4 out_Color;

void main() {
    out_Color = vec4(colour,1.0);
}