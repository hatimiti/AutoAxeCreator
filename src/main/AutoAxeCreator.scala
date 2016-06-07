import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;

object AutoAxeCreator {

  def main(args: Array[String]): Unit = {
    val tomcat = new Tomcat();
    tomcat.setPort(8080);

    val ctx = tomcat.addContext("/app", null); 
    Tomcat.addServlet(ctx, "hello", new  HttpServlet() {
      override def service(req: HttpServletRequest, res: HttpServletResponse) = {
        try {
          val robot = new Robot();
          robot.mouseMove(500, 500);
          robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
          robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
          robot.delay(1000);
          robot.keyPress(KeyEvent.VK_ESCAPE);
          robot.delay(1000);
          robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
          robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
          robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//          robot.mouseWheel(9);
//          robot.delay(1000);
//          robot.mouseWheel(-9);
        } catch {
	  case e: AWTException => e.printStackTrace();
        }
      }
    }).addMapping("/hello");
    tomcat.start();
    tomcat.getServer().await();
  }

}
