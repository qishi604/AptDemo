package com.lanayru.apt.compiler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;

/**
 * @author zhengqi
 * @since 2020/3/8
 **/
public abstract class BaseProcessor extends AbstractProcessor {

  protected Logs mLogger;

  @Override public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    mLogger = new Logs(processingEnv.getMessager());
  }

  @Override public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }
}
