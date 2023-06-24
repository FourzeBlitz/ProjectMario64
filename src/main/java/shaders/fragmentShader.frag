#version 400 core

//fragment shader executes setiap pixel menentukan pixel ini warna apa dari vertex shader
//in vec3 colour;
in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_Color;

//basically represents the texture that we are using
//setiap tambah uniform jgn lupa tambah di static shader jg
uniform sampler2D modelTexture;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;

void main() {
    // dot product calc buat tau perbedaan derajat arah light position dengan vector normal
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    // kalau ndot1 < 0 brightness di set ke 0, kalau ndot1 > 0 set ke ndot1
    // semakin ndot1 mendekati 1 brightness nya makin terang
    float brightness = max(nDot1, 0.0);
    // final lighting value of a pixel
    vec3 diffuse = brightness * lightColour;

    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDir = -unitLightVector;
    vec3 reflectedLightDir = reflect(lightDir, unitNormal);

    // hitung brp byk reflected light msk ke camera
    float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);

    // damper calc
    float dampedFactor = pow(specularFactor, shineDamper);

    vec3 finalSpecular = dampedFactor * lightColour;

    // ngasih warna pixel sesuai dengan texture sampler di coord texture coords
    out_Color = vec4(diffuse, 1.0) * texture(modelTexture, pass_textureCoords) + vec4(finalSpecular, 1.0);
}