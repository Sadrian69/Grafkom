package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class Line extends Object2d{

    public Line(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color) {
        super(shaderModuleDataList, vertices, color);
    }

    public void draw(){
        drawSetup();
        glLineWidth(2);
        glPointSize(2);
        glDrawArrays(GL_LINE_STRIP, 0, vertices.size());
    }
}
