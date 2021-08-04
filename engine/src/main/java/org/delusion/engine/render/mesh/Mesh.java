package org.delusion.engine.render.mesh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.delusion.engine.render.buffer.ElementBuffer;
import org.delusion.engine.render.buffer.VertexArray;
import org.delusion.engine.render.buffer.VertexBuffer;

import javax.swing.text.Element;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Mesh {

    private VertexBuffer vbo;
    private ElementBuffer ebo;
    private VertexArray vao;
    private int indexCount;

    private static ObjectMapper objectMapper;

    public Mesh(String path) throws IOException {
        if (objectMapper == null) { objectMapper = new ObjectMapper(); }

        InputStream istream = Mesh.class.getResourceAsStream(path);
        MeshData data = objectMapper.readValue(istream, MeshData.class);

        vbo = new VertexBuffer(data.vertices);
        ebo = new ElementBuffer(data.indices);
        vao = new VertexArray().bindVertexBuffer(vbo, data.vertexAttribSizes).elementBuffer(ebo);
        indexCount = data.indices.size();
    }

    public Mesh(VertexBuffer vbo, ElementBuffer ebo, VertexArray vao) {
        this.vbo = vbo;
        this.ebo = ebo;
        this.vao = vao;
        indexCount = ebo.getCount();
    }

    public static Mesh quad(float x0, float y0, float x1, float y1, float s0, float t0, float s1, float t1) {
        VertexBuffer vbo = new VertexBuffer(List.of(x0, y0, s0, t0, x1, y0, s1, t0, x1, y1, s1, t1, x0, y1, s0, t1));
        ElementBuffer ebo = new ElementBuffer(List.of(0,1,2,0,2,3));
        VertexArray vao = new VertexArray().bindVertexBuffer(vbo,2,2).elementBuffer(ebo);
        return new Mesh(vbo, ebo, vao);
    }


    public VertexArray getVAO() {
        return vao;
    }

    public int getIndexCount() {
        return indexCount;
    }

    public VertexBuffer getVbo() {
        return vbo;
    }

    public ElementBuffer getEbo() {
        return ebo;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class MeshData {
        @JsonProperty("vertices")
        public List<Float> vertices;

        @JsonProperty("indices")
        public List<Integer> indices;

        @JsonProperty("attrib_sizes")
        public List<Integer> vertexAttribSizes;
    }

}
