import Engine.Object;
import Engine.*;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window= new Window(1200, 1200, "yes");
    private MouseInput mouseInput;
    private ArrayList<Object> objects = new ArrayList<>();
    private ArrayList<Object> objectsRectangle = new ArrayList<>();
    private ArrayList<Object> objectsPointsControl = new ArrayList<>();

    int countDegree = 0;
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();

    public void run() {
        init();
        loop();

        // Terminate glfw and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init(){
        window.init();
        GL.createCapabilities(); // don't put below stuff
        mouseInput = window.getMouseInput();
        camera.setPosition(0,0,1.7f);
        camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(30.0f));
        // code
        objects.add(new Sphere(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<Vector3f>(),
                new Vector4f(0.0f,1.0f,0.0f,1.0f),
                0f,0f,0f,
                0.125f, 0.125f, 0.125f,
                200, 100
        ));
//        objects.get(0).translateObject(0.5f,0.0f,0.0f);
        objects.get(0).scaleObject(2f,2f,2f);

        objects.get(0).getChildObject().add(new Sphere(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<Vector3f>(),
                new Vector4f(0.0f,1.0f,0.0f,1.0f),
                0f,0f,0f,
                0.125f, 0.125f, 0.125f,
                200, 100
        ));
        objects.get(0).getChildObject().get(0).translateObject(0.5f,0.0f,0.0f);

        objects.get(0).getChildObject().get(0).getChildObject().add(new Sphere(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<Vector3f>(),
                new Vector4f(0.0f,1.0f,0.0f,1.0f),
                0f,0f,0f,
                0.125f, 0.125f, 0.125f,
                200, 100
        ));
        objects.get(0).getChildObject().get(0).getChildObject().get(0).scaleObject(0.5f,0.5f,0.5f);
        objects.get(0).getChildObject().get(0).getChildObject().get(0).translateObject(0.5f,-0.1f,0.0f);


        countDegree = 0;
    }
    private void loop() {
        while (window.isOpen()){
            window.update();
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL.createCapabilities();
            input();

            // code
            for(Object object:objects){
                object.draw(camera, projection);
            }


            // restore state
            glDisableVertexAttribArray(0);
            // poll for window events
            // the key callback above will only be
            // invoked during this call
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public void input() {
        if (window.isKeyPressed(GLFW_KEY_W)) {
            countDegree++;
            objects.get(0).rotateObject((float) Math.toRadians(2f),0.0f,0.0f,1.0f);
            for(Object child:objects.get(0).getChildObject()){
                Vector3f tempCenterPoint = child.updateCenterPoint();
                child.translateObject(tempCenterPoint.x*-1, tempCenterPoint.y*-1, tempCenterPoint.z*-1);
                child.rotateObject((float) Math.toRadians(5f),0f,0f,1f);
                child.translateObject(tempCenterPoint.x, tempCenterPoint.y, tempCenterPoint.z);
                for(Object child2:objects.get(0).getChildObject().get(0).getChildObject()){
                    Vector3f tempCenterPoint2 = child2.updateCenterPoint();
                    child2.translateObject(tempCenterPoint2.x*-1, tempCenterPoint2.y*-1, tempCenterPoint2.z*-1);
                    child2.rotateObject((float) Math.toRadians(10f),0f,0f,1f);
                    child2.translateObject(tempCenterPoint2.x, tempCenterPoint2.y, tempCenterPoint2.z);
                }
            }
//                child.rotateObject((float) Math.toRadians(0.5f),0.0f,0.0f,1.0f);
            }

    }
}