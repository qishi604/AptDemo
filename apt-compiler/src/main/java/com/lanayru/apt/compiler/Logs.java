package com.lanayru.apt.compiler;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * @author zhengqi
 * @since 2020/3/8
 **/
public class Logs {
  private Messager msg;

  public Logs(Messager messager) {
    msg = messager;
  }

  /**
   * Print info log.
   */
  public void i(CharSequence info) {
    msg.printMessage(Diagnostic.Kind.NOTE, "[" + info + "]");
  }

  public void e(CharSequence error) {
    msg.printMessage(Diagnostic.Kind.ERROR, "An exception is encountered, [" + error + "]");
  }

  public void e(Throwable error) {
    if (null != error) {
      msg.printMessage(Diagnostic.Kind.ERROR,
          "An exception is encountered, [" + error.getMessage() + "]"
              + "\n" + formatStackTrace(error.getStackTrace()));
    }
  }

  public void w(CharSequence warning) {
      msg.printMessage(Diagnostic.Kind.WARNING, warning);
  }

  private String formatStackTrace(StackTraceElement[] stackTrace) {
    StringBuilder sb = new StringBuilder();
    for (StackTraceElement element : stackTrace) {
      sb.append("    at ").append(element.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
}
