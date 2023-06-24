#version 400 core


//vertex shader ini diproses setiap vertex, trs nanti dikirim ke fragment shader buat coloring
//vertexShader kasih tau GPU dimana vertex ini harus dirender di screen dengan gl_Position

// input vector 3d dimensi
in vec3 position;
in vec2 textureCoords;
in vec3 normal;

//out vec3 colour;
out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

void main() {
    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    pass_textureCoords = textureCoords;

    surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
    toLightVector = lightPosition - worldPosition.xyz;
    toCameraVector = (inverse(viewMatrix) * vec4(0,0,0,1)).xyz - worldPosition.xyz;
}