package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Object2d extends ShaderProgram{
    List<Vector3f> vertices;
    List<Vector3f> curveVertices;

    public void setVertices(List<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

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
        this.curveVertices = new ArrayList<>();
    }

    public Object2d(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, List<Vector3f> verticesColor) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.verticesColor = verticesColor;
        setupVAOVBOWithVerticesColor();
        this.curveVertices = new ArrayList<>();
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
        glDrawArrays(GL_TRIANGLE_FAN, 0, vertices.size());

    }

    public void drawWithVerticesColor(){
        drawSetupWithVerticesColor();
        // draw vertices
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_TRIANGLES, 0, vertices.size());

    }

    public void drawLine(){
        setupVAOVBO();
        drawSetup();
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_LINE_STRIP, 0, vertices.size());
    }
    public void addVertices(Vector3f newVector){
        vertices.add(newVector);
        setupVAOVBO();
    }

    public void setupVAOVBOCurve(){
        // set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        // mengirim vertices
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(curveVertices), GL_STATIC_DRAW);
    }

    public void drawCurve(){
        if(vertices.size()<3) {
            if(vertices.size()==2) drawLine();
            return;
        }
        setupVAOVBOCurve();
        drawSetup();
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_LINE_STRIP, 0, curveVertices.size());
    }

    public void updateCurve(){
        if(vertices.size() < 1) return;
        int size = vertices.size();

        curveVertices.clear();
        curveVertices.add(vertices.get(0));
        double interval = 0.02;
        for (double i = 0; i <= 1; i += interval) {
            float tempx = 0, tempy = 0;
            for(int p=0; p<size; p+=1){
                //float coeff = (float)C(size-1,p) * (float)Math.pow(i,p) * (float)Math.pow(1-i,size-1-p);
                float coeff = binomialCoefficient(size-1,p) * (float)Math.pow(i,p) * (float)Math.pow(1-i,size-1-p);
                tempx += coeff * (vertices.get(p).x);
                //System.out.println(coeff);
                tempy += coeff * (vertices.get(p).y);
            }
            //System.out.println(Math.round(i/interval));
            //System.out.println("tempx: " + tempx + "  tempy: " + tempy);
            //System.out.println((tempx-curveVertices.get(curveVertices.size()-1).x) + " " + (tempy-curveVertices.get(curveVertices.size()-1).y));
            curveVertices.add(new Vector3f(tempx, tempy, 0));
        }
        curveVertices.add(vertices.get(vertices.size()-1));
    }

    private static int binomialCoefficient(int n, int k) {
        if (k == 0 || k == n) {
            return 1;
        } else {
            return binomialCoefficient(n - 1, k - 1) + binomialCoefficient(n - 1, k);
        }
    }

}
