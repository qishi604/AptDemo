package com.lanayru.apt.api;

public class ViewBinder {

  public static void bind(Object target) {
    String className = target.getClass().getCanonicalName();
    String helperName = className + "$$ViewBinder";
    try {
      IViewBinder helper = (IViewBinder) (Class.forName(helperName).getConstructor().newInstance());
      helper.bind(target);
    }   catch (Exception e) {
      e.printStackTrace();
    }
  }
}
