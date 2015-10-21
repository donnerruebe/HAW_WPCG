varying vec2 texture_coordinate;

/**
 * Vertex shader used for texturing. No color computations
 * are required here, the information is passed to the
 * corresponding fragment shader.
 */
void main()
{
    // Position in 3-space
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    
    // Just pass the texture coordinate at the vertex to the fragment
    // shader.
    texture_coordinate = vec2(gl_MultiTexCoord0);
}