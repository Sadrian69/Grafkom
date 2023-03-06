package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class CircleSquare extends Object2d {
    float centerX;
    float centerY;
    float radiusX;
    float radiusY;

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    float x,y;
    public CircleSquare(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, float centerX, float centerY, float radiusX, float radiusY) {
        super(shaderModuleDataList, vertices, color);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        createCircle();
        setupVAOVBO();
    }

    public void draw(){
        drawSetup();
        // draw vertices
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_TRIANGLE_FAN, 0, vertices.size());

    }

    public void createCircle(){
        vertices.clear();
        int degree;
        degree = 45;
        for (double i = degree; i < 360; i += 90) {
            x = (float) (centerX + radiusX * Math.cos(Math.toRadians(i)));
            y = (float) (centerY + radiusY * Math.sin(Math.toRadians(i)));

            // Add koordinat ke dalam vertices
            vertices.add(new Vector3f(x, y, 0.0f));
        }

    }
}
