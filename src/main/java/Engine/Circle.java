package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Circle extends Object {
    float centerX, centerY, radiusX, radiusY;

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public Circle(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                  float centerX, float centerY, float radiusX, float radiusY) {
        super(shaderModuleDataList, vertices, color);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        createCircle();
        setupVAOVBO();
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

//    public void draw(){
//        drawSetup();
//        // draw vertices
//        glLineWidth(10);
//        glPointSize(10);
//        glDrawArrays(GL_TRIANGLE_FAN, 0, vertices.size());
//
//    }

    public void createCircle(){
        vertices.clear();
        for(double i=0; i<2*Math.PI; i+=0.05f){
              float tempx = centerX + radiusX * (float) Math.cos(i);
              float tempy = centerY + radiusY * (float) Math.sin(i);
              vertices.add(new Vector3f(tempx, tempy, 0.0f));
              System.out.println(tempx);
              System.out.println(tempy);
        }
    }
}
