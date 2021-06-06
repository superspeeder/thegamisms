package org.delusion.engine.render.mesh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.delusion.engine.render.buffer.ElementBuffer;
import org.delusion.engine.render.buffer.VertexArray;
import org.delusion.engine.render.buffer.VertexBuffer;

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
