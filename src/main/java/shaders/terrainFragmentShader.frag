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
uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;

void main() {
    // get the color of blend map
    vec4 blendMapColour = texture(blendMap, pass_textureCoords);

    float backTextureAmount = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
    vec2 tiledCoords = pass_textureCoords * 40.0;
    // untuk warna itu dirender sbyk warna yang ada di blendMap
    vec4 backgroundTextureColour = texture(backgroundTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColour = texture(rTexture, tiledCoords) * blendMapColour.r;
    vec4 gTextureColour = texture(gTexture, tiledCoords) * blendMapColour.g;
    vec4 bTextureColour = texture(bTexture, tiledCoords) * blendMapColour.b;

    vec4 totalColour = backgroundTextureColour + rTextureColour + gTextureColour + bTextureColour;

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