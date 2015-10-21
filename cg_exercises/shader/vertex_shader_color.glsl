varying vec4 color;

/**
 * Vertex shader: Use the color attribute. 
 */
void main(void)
{
    // Transformed vertex
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    // Color
    color = gl_Color;
}
