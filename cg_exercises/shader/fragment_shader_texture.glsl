varying vec2 texture_coordinate;
uniform sampler2D my_color_texture;

/**
 * Fragment shader used for texture mapping.
 */
void main()
{
    // Sampling the texture and passing it to the frame buffer
    gl_FragColor = texture2D(my_color_texture, texture_coordinate);
}