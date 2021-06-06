package org.delusion.engine.render.shader;

import org.delusion.engine.utils.Utils;

import static org.lwjgl.opengl.GL46.*;

public class Shader {
    private int handle;

    public enum Type {

        Vertex(GL_VERTEX_SHADER),
        Fragment(GL_FRAGMENT_SHADER),
        Geometry(GL_GEOMETRY_SHADER),
        Compute(GL_COMPUTE_SHADER),
        TessControl(GL_TESS_CONTROL_SHADER),
        TessEvaluation(GL_TESS_EVALUATION_SHADER)
        ;

        private final int val;

        Type(int val) {

            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    public Shader(Type type, String path) {
        String text = Utils.readResourceToString(path);
        handle = glCreateShader(type.getVal());
        glShaderSource(handle, text);
        glCompileShader(handle);
        if (glGetShaderi(handle, GL_COMPILE_STATUS) != GL_TRUE) {
            System.out.println(glGetShaderInfoLog(handle));
        }
    }

    public int getHandle() {
        return handle;
    }


}
