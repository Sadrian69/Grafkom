package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class CircleTriangle extends Object {

    public CircleTriangle(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color) {
        super(shaderModuleDataList, vertices, color);
    }

    public void draw(){
        drawSetup();
        // draw vertices
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_TRIANGLE_FAN, 0, vertices.size());

    }

    public static List<Vector3f> createCircle(float centerX, float centerY, float radiusX, float radiusY){
        List<Vector3f> vertices = new ArrayList<>();
        for(float i=0; i<2*Math.PI; i+=Math.PI*2/3f){
              float tempx = centerX + radiusX * (float) Math.cos(i);
              float tempy = centerY + radiusY * (float) Math.sin(i);
              vertices.add(new Vector3f(tempx, tempy, 0.0f));
        }
        return vertices;
    }
}
