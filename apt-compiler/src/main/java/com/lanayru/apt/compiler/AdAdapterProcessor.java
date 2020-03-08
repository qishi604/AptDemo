package com.lanayru.apt.compiler;

import com.lanayru.apt.annotation.Adapter;
import com.lanayru.apt.api.AdAdapter;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author zhengqi
 * @since 2020/3/8
 **/
//@AutoService(Processor.class)
public class AdAdapterProcessor extends BaseProcessor {

  @Override public Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(Adapter.class.getCanonicalName());
  }

  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
    if (null == set || set.isEmpty()) return false;
    return genFactory(roundEnvironment);
  }

  private boolean genFactory(RoundEnvironment roundEnvironment) {
    mLogger.i(" Start gen Factory .....");
    Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Adapter.class);
    if (null == elements || elements.isEmpty()) return false;

    CodeBlock.Builder initMap = CodeBlock.builder();
    for (Element element : elements) {
      TypeMirror typeMirror = element.asType();
      Adapter adapter = element.getAnnotation(Adapter.class);
      //map.put(adapter.source(), typeMirror.toString());
      String type = typeMirror.toString();
      mLogger.i(type);
      try {
        String o = String.format("new %s()", type);
        initMap.add("sMap.put(\"$1L\",$2L);\n", adapter.source(), o);
      } catch (Exception e) {
        mLogger.e(e);
      }
    }

    FieldSpec sMap = FieldSpec.builder(HashMap.class, "sMap")
        .addModifiers(Modifier.PRIVATE,Modifier.STATIC,Modifier.FINAL)
        .initializer("new HashMap<String, AdAdapter>();")
        .build();

    MethodSpec getAdapter = MethodSpec.methodBuilder("getAdapter")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(AdAdapter.class)
        .addParameter(String.class, "source")
        .addStatement("return (AdAdapter)sMap.get($N)", "source")
        .build();

    TypeSpec factory = TypeSpec.classBuilder("AdAdapterFactory")
        .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
        .addField(sMap)
        .addStaticBlock(initMap.build())
        .addMethod(getAdapter)
        .build();

    JavaFile javaFile = JavaFile.builder("com.lanayru.aptdemo.ad", factory).build();

    try {
      javaFile.writeTo(processingEnv.getFiler());
    } catch (IOException e) {
      mLogger.e(e);
    }

    mLogger.i(" End gen Factory .....");

    return true;
  }
}
