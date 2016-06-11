import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.PointerInfo;

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
        val r = new MinecraftRobot();

        r.changeWindow;
        r.keyEsc;

        r.dig(4);
        r.forward(1);

        r.oepnInventory;
        r.clickLItem1;
        r.clickLWork1;
        r.clickLCreated;

        r.clickRWork1;
        r.clickLWork2;
        r.clickLCreated;

        r.clickLItem1;
        r.keyEsc;

        r.keyF5;
        r.keyF5;

        r.dig(5);

        r.keyF5;
      }
    }).addMapping("/hello");
    tomcat.start();
    tomcat.getServer().await();
  }

}

class MinecraftRobot {

  private val r = new Robot;
  private val OP_DELAY_MS = 200;
  private val ITEM1_X = 350;
  private val ITEM1_Y = 520;
  private val WORK1_X = 550;
  private val WORK1_Y = 280;
  private val WORK2_X = WORK1_X;
  private val WORK2_Y = WORK1_Y + 20;
  private val CREAT_X = WORK1_X + 100;
  private val CREAT_Y = WORK1_Y + 12;

  def changeWindow: Unit = {
    r.keyPress(KeyEvent.VK_META);
    key(KeyEvent.VK_TAB);
    r.keyRelease(KeyEvent.VK_META);
    delay;
  }

  def attack(num: Int): Unit = {
    for (i <- 0 until num) {
      clickL;
      delay;
    }
  }

  def dig(second: Int): Unit = {
    r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    r.delay(second * 1000);
    r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    delay;
  }

  def forward(second: Int): Unit = {
    r.keyPress(KeyEvent.VK_W);
    r.delay(second * 1000);
    r.keyRelease(KeyEvent.VK_W);
    delay;
  }

  def oepnInventory: Unit = {
    key(KeyEvent.VK_E);
  }

  def keyEsc: Unit = key(KeyEvent.VK_ESCAPE);
  def keyF5: Unit = key(KeyEvent.VK_F5);

  def clickLCreated: Unit = {
    move(CREAT_X, CREAT_Y);
    clickL;
  }
	
  def clickLItem1: Unit = {
    move(ITEM1_X, ITEM1_Y);
    clickL;
  }

  def clickLWork1: Unit = {
    move(WORK1_X, WORK1_Y);
    clickL;
  }

  def clickRWork1: Unit = {
    move(WORK1_X, WORK1_Y);
    clickR;
  }

  def clickLWork2: Unit = {
    move(WORK2_X, WORK2_Y);
    clickL;
  }

  def clickRWork2: Unit = {
    move(WORK2_X, WORK2_Y);
    clickR;
  }

  def key(key: Int): Unit = {
    r.keyPress(key);
    r.keyRelease(key);
    delay;
  }

  def mouse(key: Int): Unit = {
    r.mousePress(key);
    r.mouseRelease(key);
    delay;
  }

  private def clickL: Unit = mouse(InputEvent.BUTTON1_DOWN_MASK);
  private def clickR: Unit = mouse(InputEvent.BUTTON3_DOWN_MASK);

  private def move(x: Int, y: Int): Unit = {
    val cur = currentMousePoint;

    var isDoneX = false;
    var isDoneY = false;
    var cx = cur.x;
    var cy = cur.y;

    val dx = if (cx <= x) 1 else -1;
    val dy = if (cy <= y) 1 else -1;

    while (!(isDoneX && isDoneY)) {
      if (!isDoneX && cx + dx - x != 0) {
        cx += dx;
      } else {
        isDoneX = true;
      }

      if (!isDoneY && cy + dy - y != 0) {
        cy += dy;
      } else {
        isDoneY = true;
      }

      r.mouseMove(cx, cy);
      r.delay(3);
    }
  }

  private def currentMousePoint: Point = {
    val mi = MouseInfo.getPointerInfo();
    val mp = mi.getLocation();
    mp;
  }

  private def delay:Unit = {
    r.delay(OP_DELAY_MS);
  }
}
