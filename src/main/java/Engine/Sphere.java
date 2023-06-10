package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Sphere extends Circle {
    float centerZ, radiusZ;

    int stackCount, sectorCount;
    List<Integer> index;
    int ibo;

    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                  float centerX, float centerY, float centerZ, float radiusX, float radiusY,  float radiusZ,
                  int stackCount, int sectorCount) {
        super(shaderModuleDataList, vertices, color, centerX, centerY, radiusX, radiusY);
        this.centerZ = centerZ;
        this.radiusZ = radiusZ;
        this.stackCount = stackCount;
        this.sectorCount = sectorCount;
        createBox();
        //createSphere();
        // createEllipsoid();
        // createHyperboloid1();
        // createHyperboloid2();
        // createEllipticCone();
        // createEllipticParaboloid();
        // createHyperboloidParaboloid();
        setupVAOVBO();
    }

//    public void draw(){
//        drawSetup();
//        // draw vertices
//        glLineWidth(2);
//        glPointSize(2);
//        glDrawArrays(GL_LINE_STRIP, 0, vertices.size());
//
//    }

    public void createBox(){
        vertices.clear();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        // --- --+ -+- -++ +-- +-+ ++- +++
        //  0   1   2   3   4   5   6   7
        for(int i=0; i<8; i++) {
            Vector3f temp = new Vector3f();
            float coX=1, coY=1, coZ=1;
            if(i%2 == 0) coX = -1;
            if((i/2)%2 == 0) coY = -1;
            if((i/4)%2 == 0) coZ = -1;

            temp.x = centerX - coX*radiusX/2;
            temp.y = centerY - coY*radiusY/2;
            temp.z = centerZ - coZ*radiusZ/2;
            tempVertices.add(temp);
        }

        // kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(2));
        // belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));
        // kanan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(5));

        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(5));
        // depan
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(5));

        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(5));
        // atas
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));
        // bawah
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(4));

        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
    }

    public void createSphere(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();
        float x,y,z,xy,nx,ny,nz;
        float sectorStep = (float)(2* Math.PI )/ sectorCount; //sector count
        float stackStep = (float)Math.PI / stackCount; // stack count
        float sectorAngle, stackAngle;

        for(int i=0;i<=stackCount;i++){
            stackAngle = (float)Math.PI/2 - i * stackStep;
            xy = (float) (0.5f * Math.cos(stackAngle));
            z = (float) (0.5f * Math.sin(stackAngle));
            for(int j=0;j<sectorCount;j++){
                sectorAngle = j * sectorStep;
                x = (float) (xy * Math.cos(sectorAngle));
                y = (float) (xy * Math.sin(sectorAngle));
                temp.add(new Vector3f(x,y,z));
            }
        }

        int k1, k2;
        ArrayList<Integer> temp_indices = new ArrayList<>();
        for(int i=0;i<stackCount;i++){
            k1 = i * (sectorCount + 1);
            k2 = k1 + sectorCount + 1;

            for(int j=0;j<sectorCount;++j, ++k1, ++k2){
                if(i != 0){
                    temp_indices.add(k1);
                    temp_indices.add(k2);
                    temp_indices.add(k1+1);
                }
                if(i!=(18-1)){
                    temp_indices.add(k1+1);
                    temp_indices.add(k2);
                    temp_indices.add(k2+1);
                }
            }
        }

        vertices = temp;

        this.index = temp_indices;
        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.listoInt(index), GL_STATIC_DRAW);
    }

    public void createEllipsoid(){
        vertices.clear();
        float pi = (float)Math.PI;

        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = radiusX * (float) (Math.cos(StackAngle));
            y = radiusY * (float) (Math.cos(StackAngle));
            z = radiusZ * (float) (Math.sin(StackAngle));

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.x = centerX + x * (float)Math.cos(sectorAngle);
                temp_vector.y = centerY + y * (float)Math.sin(sectorAngle);
                temp_vector.z = centerZ + z;
                vertices.add(temp_vector);
            }
        }
    }

    public void createHyperboloid1(){
        vertices.clear();
        float pi = (float)Math.PI;

        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = radiusX * (float) (1.0/Math.cos(StackAngle));
            y = radiusY * (float) (1.0/Math.cos(StackAngle));
            z = radiusZ * (float) (Math.tan(StackAngle));

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.z = centerX + x * (float)Math.cos(sectorAngle);
                temp_vector.y = centerY + y * (float)Math.sin(sectorAngle);
                temp_vector.x = centerZ + z;
                vertices.add(temp_vector);
            }
        }
    }

    public void createHyperboloid2(){
        vertices.clear();
        float pi = (float)Math.PI;

        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = radiusX * (float) (Math.tan(StackAngle));
            y = radiusY * (float) (Math.tan(StackAngle));
            z = radiusZ * (float) (1.0/Math.cos(StackAngle));

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.z = centerX + x * (float)Math.cos(sectorAngle);
                temp_vector.y = centerY + y * (float)Math.sin(sectorAngle);
                temp_vector.x = centerZ + z;
                vertices.add(temp_vector);
            }
        }
    }

    public void createEllipticCone(){
        vertices.clear();
        float pi = (float)Math.PI;

        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = radiusX * StackAngle;
            y = radiusY * StackAngle;
            z = radiusZ * StackAngle;

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.z = centerX + x * (float)Math.cos(sectorAngle);
                temp_vector.y = centerY + y * (float)Math.sin(sectorAngle);
                temp_vector.x = centerZ + z;
                vertices.add(temp_vector);
            }
        }
    }

    public void createEllipticParaboloid(){
        vertices.clear();
        float pi = (float)Math.PI;

        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = radiusX * StackAngle;
            y = radiusY * StackAngle;
            z = StackAngle * StackAngle;

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.z = centerX + x * (float)Math.cos(sectorAngle);
                temp_vector.y = centerY + y * (float)Math.sin(sectorAngle);
                temp_vector.x = centerZ + z;
                vertices.add(temp_vector);
            }
        }
    }

    public void createHyperboloidParaboloid(){
        vertices.clear();
        float pi = (float)Math.PI;

        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = radiusX * StackAngle;
            y = radiusY * StackAngle;
            z = StackAngle * StackAngle;

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.x = centerX + x * (float)Math.tan(sectorAngle);
                temp_vector.y = centerY + y * (float) (1.0 / Math.cos(sectorAngle));
                temp_vector.z = centerZ + z;
                vertices.add(temp_vector);
            }
        }
    }

//    public void drawIndices(){
//        drawSetup();
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
//        glDrawElements(GL_LINE_STRIP,index.size(), GL_UNSIGNED_INT, 0);
//    }

}
