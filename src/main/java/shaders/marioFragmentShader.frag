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
uniform sampler2D texture1;
uniform sampler2D texture2;
uniform sampler2D texture3;
uniform sampler2D texture4;
uniform sampler2D texture5;
uniform sampler2D texture6;
uniform sampler2D texture7;
uniform sampler2D texture8;
uniform sampler2D texture9;
uniform sampler2D texture10;

uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;

void main() {
    // texture mario
    vec4 texture1Colour = texture(texture1, pass_textureCoords);
    vec4 texture2Colour = texture(texture2, pass_textureCoords);
    vec4 texture3Colour = texture(texture3, pass_textureCoords);
    vec4 texture4Colour = texture(texture4, pass_textureCoords);
    vec4 texture5Colour = texture(texture5, pass_textureCoords);
    vec4 texture6Colour = texture(texture6, pass_textureCoords);
    vec4 texture7Colour = texture(texture7, pass_textureCoords);
    vec4 texture8Colour = texture(texture8, pass_textureCoords);
    vec4 texture9Colour = texture(texture9, pass_textureCoords);
    vec4 texture10Colour = texture(texture10, pass_textureCoords);
    // digabung jd 1
    vec4 totalColour = texture1Colour + texture2Colour + texture3Colour + texture4Colour + texture5Colour +
    texture6Colour + texture7Colour + texture8Colour + texture9Colour + texture10Colour;

    // dot product calc buat tau perbedaan derajat arah light position dengan vector normal
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    // kalau ndot1 < 0 brightness di set ke 0, kalau ndot1 > 0 set ke ndot1
    // semakin ndot1 mendekati 1 brightness nya makin terang
    float brightness = max(nDot1, 0.2);
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
    out_Color = vec4(diffuse, 1.0) * totalColour + vec4(finalSpecular, 1.0);
}