import Engine.CircleSquare;
import Engine.Object2d;
import Engine.ShaderProgram;
import Engine.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window= new Window(1200, 1200, "yes");
    private ArrayList<Object2d> lines = new ArrayList<>();
    private ArrayList<CircleSquare> squares = new ArrayList<>();

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

        // code

        lines.add(new Object2d(
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan object
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,1.0f,0.0f,1.0f)
        ));


//        lines.get(0).addVertices(new Vector3f(0.0f, 0.0f, 0.0f));
//        squares.add(new CircleSquare(
//                Arrays.asList(
//                        // shaderFile lokasi menyesuaikan object
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(1.0f,0.0f,0.0f,1.0f),
//                0.0f,0.0f,0.03f,0.03f
//        ));
//        lines.get(0).addVertices(new Vector3f(0.4f, 0.8f, 0.0f));
//        squares.add(new CircleSquare(
//                Arrays.asList(
//                        // shaderFile lokasi menyesuaikan object
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(1.0f,0.0f,0.0f,1.0f),
//                0.4f,0.8f,0.03f,0.03f
//        ));
//        lines.get(0).addVertices(new Vector3f(0.8f, 0.0f, 0.0f));
//        squares.add(new CircleSquare(
//                Arrays.asList(
//                        // shaderFile lokasi menyesuaikan object
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(1.0f,0.0f,0.0f,1.0f),
//                0.8f,0.0f,0.03f,0.03f
//        ));
//
//        lines.get(0).updateCurve();

        // squares add di bawah
    }
    private void loop() {
        while (window.isOpen()){
            input();
            window.update();
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL.createCapabilities();

            // code


            for(Object2d line:lines){
                line.setupVAOVBOCurve();
                line.drawCurve();
            }
            for(Object2d line:lines){
                line.setupVAOVBO();
                line.drawLine();
            }
            for(CircleSquare square:squares){
                square.draw();
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

    public void input(){
        if (window.isKeyPressed(GLFW_KEY_W)){
            System.out.println("W");
        }
        if (window.getMouseInput().isLeftButtonPressed()){
            Vector2f pos = window.getMouseInput().getCurrentPos();
            pos.x = (pos.x - (window.getWidth())/2.0f) / (window.getWidth()/2.0f);
            pos.y = (pos.y - (window.getHeight())/2.0f) / (window.getHeight()/2.0f)*-1;

            //System.out.printf("%f %f%n",pos.x,pos.y);

            if((!(pos.x>0.97||pos.x<-0.97) && !(pos.y>0.97||pos.y<-0.97))){
                List<Vector3f> curPoints = lines.get(0).getVertices();
                boolean valid = true;
                int index = 0;
                for(Vector3f curPoint:curPoints){ //cek jarak, ideally reversed tapi mls
                    // System.out.println(distance(pos.x,pos.y,curPoint.x,curPoint.y));
                    if(distance(pos.x,pos.y,curPoint.x,curPoint.y) < 0.075f){
                        valid = false;
                        break;
                    }
                    index += 1;
                }
                if(!valid) {
                    // System.out.println(index);
                    squares.get(index).setCenterX(pos.x);
                    squares.get(index).setCenterY(pos.y);
                    squares.get(index).createCircle(); //generate koordinat sudut kotak
                    squares.get(index).setupVAOVBO(); //setup

                    List<Vector3f> temp = lines.get(0).getVertices();
                    temp.set(index, new Vector3f(pos.x, pos.y, 0));
                    lines.get(0).setVertices(temp);
                    lines.get(0).setupVAOVBO();

                    lines.get(0).updateCurve();

                    return;
                }

                
                //System.out.println("x: "+pos.x+" y: "+pos.y);
                lines.get(0).addVertices(new Vector3f(pos.x, pos.y, 0.0f));
                squares.add(new CircleSquare(
                        Arrays.asList(
                                // shaderFile lokasi menyesuaikan object
                                new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                                new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                        ),
                        new ArrayList<>(),
                        new Vector4f(1.0f,0.0f,0.0f,1.0f),
                        pos.x,pos.y,0.03f,0.03f
                ));

                lines.get(0).updateCurve();
            }
        }
    }

    public float distance(float x1, float y1, float x2, float y2){
        return (float) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }


}