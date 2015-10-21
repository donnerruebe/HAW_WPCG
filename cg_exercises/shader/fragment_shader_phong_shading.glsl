// These vectors need to be provided by the vertex shader
// Normal direction
varying vec3 N;
// Viewer direction
varying vec3 v;
varying vec3 lightPosition;
varying vec4 color;

/**
 * Fragment shader: Phong lighting model, Phong shading, one light
 * source taken from GL. 
 */
void main (void)
{
    // Helping vectors
    vec3 L = normalize(lightPosition - v);
    vec3 E = normalize(-v);
    vec3 R = normalize(reflect(-L,N));
    
    vec4 ambientColor = vec4(1,1,1,1);
    float rAmbient = 0.2;
    vec4 specularColor = vec4(1,1,1,1);
    float exponentSpecular = 20.0;
 
    // Phong lighting model
    vec4 ambient = ambientColor * rAmbient;
    vec4 diffuse = vec4(0,0,0,0);
    if ( dot(N,L) > 0.0 ){
    	diffuse = clamp( color * dot(N,L), 0.0, 1.0 );
    }
    vec4 spec = vec4(0,0,0,0);
    if ( dot(R,E) > 0.0 ){
    	spec = clamp ( specularColor * pow(dot(R,E), exponentSpecular) , 0.0, 1.0 );
    }
    gl_FragColor = ambient + diffuse + spec;
}
