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
    private ArrayList<Sphere> solarSystem = new ArrayList<>();
    private long count;
    private long countmoon;
    private boolean click;

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

        // code

        solarSystem.add(new Sphere( //sun
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<Vector3f>(),
                new Vector4f(1.0f,0.9f,0.4f,1.0f),
                0.0f, 0.0f, 0.0f,
                0.5f, 0.5f, 0.5f,
                40, 60
        ));
        solarSystem.get(0).scaleObject(0.4f, 0.4f, 0.4f);

        solarSystem.add(new Sphere( //mercury
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<Vector3f>(),
                new Vector4f(0.9f,0.9f,0.9f,1.0f),
                0.0f, 0.28f, 0.0f,
                0.05f, 0.05f, 0.05f,
                40, 60
        ));
        solarSystem.get(1).scaleObject(0.1f,0.1f,0.1f);
        solarSystem.get(1).translateObject(0.0f,0.28f,0.0f);

        solarSystem.add(new Sphere( //venus
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<Vector3f>(),
                new Vector4f(0.64f,0.49f,0.11f,1.0f),
                0.0f, 0.46f, 0.0f,
                0.05f, 0.05f, 0.05f,
                20, 30
        ));
        solarSystem.get(2).scaleObject(0.18f,0.18f,0.18f);
        solarSystem.get(2).translateObject(0.0f,0.46f,0.0f);

        solarSystem.add(new Sphere( //mars
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<Vector3f>(),
                new Vector4f(0.27f,0.1f,0.0f,1.0f),
                0.0f, 1.0f, 0.0f,
                0.05f, 0.05f, 0.05f,
                40, 60
        ));
        solarSystem.get(3).scaleObject(0.13f,0.13f,0.13f);
        solarSystem.get(3).translateObject(0.0f,1.0f,0.0f);

        solarSystem.add(new Sphere(//earth
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<Vector3f>(),
                new Vector4f(0.3f,0.3f,0.7f,1.0f),
                0.0f, 0.71f, 0.0f,
                0.05f, 0.05f, 0.05f,
                40, 60
        ));
        solarSystem.get(4).scaleObject(0.17f,0.17f,0.17f);
        solarSystem.get(4).translateObject(0.0f,0.71f,0.0f);

        solarSystem.add(new Sphere(//moon
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<Vector3f>(),
                new Vector4f(1.0f,1.0f,0.84f,1.0f),
                0.0f, 0.85f, 0.0f,
                0.05f, 0.05f, 0.05f,
                40, 60
        ));
        solarSystem.get(5).scaleObject(0.05f,0.05f,0.05f);
        solarSystem.get(5).translateObject(0.0f,0.85f,0.0f);

        count = 0;
        countmoon=0;
        click=false;
    }
    private void loop() {
        while (window.isOpen()){
            window.update();
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL.createCapabilities();
            input();

            // code
            for(Sphere sphere: solarSystem){
                sphere.draw();
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
        if (window.isKeyPressed(GLFW_KEY_F)) {
            count++;
            solarSystem.get(0).rotateObject((float) Math.toRadians(24.6f/4), 0.0f, 0.0f, 1.0f);

            solarSystem.get(1).rotateObject((float) Math.toRadians(8.2f/4), 0.0f, 0.0f, 1.0f);

            solarSystem.get(2).rotateObject((float) Math.toRadians(3.2f/4), 0.0f, 0.0f, 1.0f);

            solarSystem.get(3).rotateObject((float) Math.toRadians(1.1f/4), 0.0f, 0.0f, 1.0f);

            solarSystem.get(4).rotateObject((float) Math.toRadians(2.0f/4), 0.0f, 0.0f, 1.0f);

            solarSystem.get(5).rotateObject((float) Math.toRadians(2.0f/4), 0.0f, 0.0f, 1.0f);
            System.out.println(count);
        }
        if (window.isKeyPressed(GLFW_KEY_G)) {
            solarSystem.get(0).rotateObject((float) Math.toRadians(24.6f/4), 0.0f, 0.0f, 1.0f);

            solarSystem.get(1).rotateObject((float) Math.toRadians(8.2f/4) * -count, 0.0f, 0.0f, 1.0f);
            solarSystem.get(1).translateObject(0.0f, -solarSystem.get(1).getCenterY(), 0.0f);
            solarSystem.get(1).rotateObject((float) Math.toRadians(24.6f/4), 0.0f, 0.0f, 1.0f);
            solarSystem.get(1).translateObject(0.0f, solarSystem.get(1).getCenterY(), 0.0f);
            solarSystem.get(1).rotateObject((float) Math.toRadians(8.2f/4) * count, 0.0f, 0.0f, 1.0f);

            solarSystem.get(2).rotateObject((float) Math.toRadians(3.2f/4) * -count, 0.0f, 0.0f, 1.0f);
            solarSystem.get(2).translateObject(0.0f, -solarSystem.get(2).getCenterY(), 0.0f);
            solarSystem.get(2).rotateObject((float) Math.toRadians(24.6f/4), 0.0f, 0.0f, 1.0f);
            solarSystem.get(2).translateObject(0.0f, solarSystem.get(2).getCenterY(), 0.0f);
            solarSystem.get(2).rotateObject((float) Math.toRadians(3.2f/4) * count, 0.0f, 0.0f, 1.0f);

            solarSystem.get(3).rotateObject((float) Math.toRadians(1.1f/4) * -count, 0.0f, 0.0f, 1.0f);
            solarSystem.get(3).translateObject(0.0f, -solarSystem.get(3).getCenterY(), 0.0f);
            solarSystem.get(3).rotateObject((float) Math.toRadians(24.6f/4), 0.0f, 0.0f, 1.0f);
            solarSystem.get(3).translateObject(0.0f, solarSystem.get(3).getCenterY(), 0.0f);
            solarSystem.get(3).rotateObject((float) Math.toRadians(1.1f/4) * count, 0.0f, 0.0f, 1.0f);

            solarSystem.get(4).rotateObject((float) Math.toRadians(2.0f/4) * -count, 0.0f, 0.0f, 1.0f);
            solarSystem.get(4).translateObject(0.0f, -solarSystem.get(4).getCenterY(), 0.0f);
            solarSystem.get(4).rotateObject((float) Math.toRadians(24.6f/4), 0.0f, 0.0f, 1.0f);
            solarSystem.get(4).translateObject(0.0f, solarSystem.get(4).getCenterY(), 0.0f);
            solarSystem.get(4).rotateObject((float) Math.toRadians(2.0f/4) * count, 0.0f, 0.0f, 1.0f);


            solarSystem.get(5).rotateObject((float) Math.toRadians(2.0f/4) * -count, 0.0f, 0.0f, 1.0f);
            solarSystem.get(5).translateObject(0.0f, -solarSystem.get(4).getCenterY(), 0.0f);
            solarSystem.get(5).rotateObject((float) Math.toRadians(24.6f/4)*-countmoon, 0.0f, 0.0f, 1.0f);
            solarSystem.get(5).translateObject(0.0f, solarSystem.get(4).getCenterY(), 0.0f);
            solarSystem.get(5).rotateObject((float) Math.toRadians(2.0f/4) * count, 0.0f, 0.0f, 1.0f);

            solarSystem.get(5).rotateObject((float) Math.toRadians(2.0f/4) * -count, 0.0f, 0.0f, 1.0f);
            solarSystem.get(5).translateObject(0.0f, -solarSystem.get(5).getCenterY(), 0.0f);
            solarSystem.get(5).rotateObject((float) Math.toRadians(24.6f/4), 0.0f, 0.0f, 1.0f);
            solarSystem.get(5).translateObject(0.0f, solarSystem.get(5).getCenterY(), 0.0f);
            solarSystem.get(5).rotateObject((float) Math.toRadians(2.0f/4) * count, 0.0f, 0.0f, 1.0f);


            solarSystem.get(5).rotateObject((float) Math.toRadians(2.0f/4) * -count, 0.0f, 0.0f, 1.0f);
            solarSystem.get(5).translateObject(0.0f, -solarSystem.get(4).getCenterY(), 0.0f);
            solarSystem.get(5).rotateObject((float) Math.toRadians(24.6f/4)*countmoon, 0.0f, 0.0f, 1.0f);
            solarSystem.get(5).translateObject(0.0f, solarSystem.get(4).getCenterY(), 0.0f);
            solarSystem.get(5).rotateObject((float) Math.toRadians(2.0f/4) * count, 0.0f, 0.0f, 1.0f);

        }

        if (window.isKeyPressed(GLFW_KEY_H)) {
            countmoon += 1;
            solarSystem.get(5).rotateObject((float) Math.toRadians(2.0f/4) * -count, 0.0f, 0.0f, 1.0f);
            solarSystem.get(5).translateObject(0.0f, -solarSystem.get(4).getCenterY(), 0.0f);
            solarSystem.get(5).rotateObject((float) Math.toRadians(24.6f/4), 0.0f, 0.0f, 1.0f);
            solarSystem.get(5).translateObject(0.0f, solarSystem.get(4).getCenterY(), 0.0f);
            solarSystem.get(5).rotateObject((float) Math.toRadians(2.0f/4) * count, 0.0f, 0.0f, 1.0f);
        }

    }
}