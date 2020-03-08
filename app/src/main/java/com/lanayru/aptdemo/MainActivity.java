package com.lanayru.aptdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.lanayru.apt.annotation.View;
import com.lanayru.apt.api.ViewBinder;

public class MainActivity extends AppCompatActivity {

  @View(id = R.id.tv_hello, title = "hello")
  TextView tvHello;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ViewBinder.bind(this);

    tvHello.setText("this is  bind from apt");
  }
}
