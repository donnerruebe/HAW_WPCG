varying vec3 N;
varying vec3 v;
varying vec3 lightPosition;
varying vec4 color;

/**
 * Vertex shader: Phong lighting model, Phong shading, one light
 * source taken from GL. 
 */
void main(void)
{
    // Eye position
    v = vec3(gl_ModelViewMatrix * gl_Vertex);
    // Surface normal
    N = normalize(gl_NormalMatrix * gl_Normal);
    // Transformed vertex
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    // Light position
    lightPosition = (gl_ModelViewMatrix * gl_LightSource[0].position).xyz;
    lightPosition = gl_LightSource[0].position.xyz;
    // Color
    color = gl_Color;
}
