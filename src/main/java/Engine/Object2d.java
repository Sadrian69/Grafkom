package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Object2d extends ShaderProgram{
    List<Vector3f> vertices;
    List<Vector3f> verticesColor;

    int vao;
    int vbo;
    int vboColor;

    Vector4f color;

    UniformsMap uniformsMap;

    public Object2d(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.color = color;
        setupVAOVBO();
        uniformsMap = new UniformsMap(getProgramId());
        uniformsMap.createUniform("uniColor");
    }

    public Object2d(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, List<Vector3f> verticesColor) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.verticesColor = verticesColor;
        setupVAOVBOWithVerticesColor();
    }

    public void setupVAOVBO(){
        // set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        // mengirim vertices
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);
    }

    public void setupVAOVBOWithVerticesColor(){
        // set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        // mengirim vertices
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);

        // set vbo color
        vboColor = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        // mengirim vertices color
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(verticesColor), GL_STATIC_DRAW);
    }

    public void drawSetup(){
        bind();
        uniformsMap.setUniform("uniColor", color);
        // bind vbo
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0,3, GL_FLOAT, false, 0, 0);
    }

    public void drawSetupWithVerticesColor(){
        bind();
        // bind vbo
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0,3, GL_FLOAT, false, 0, 0);

        // bind vbo color
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glVertexAttribPointer(1,3, GL_FLOAT, false, 0, 0);
    }

    public void draw(){
        drawSetup();
        // draw vertices
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_TRIANGLES, 0, vertices.size());

    }

    public void drawWithVerticesColor(){
        drawSetupWithVerticesColor();
        // draw vertices
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_TRIANGLES, 0, vertices.size());

    }
}
