package com.lanayru.aptdemo.ad;

import com.lanayru.apt.annotation.Adapter;
import com.lanayru.apt.api.AdAdapter;
import com.lanayru.apt.api.ad.Const;

/**
 * @author zhengqi
 * @since 2020/3/8
 **/
@Adapter(source = Const.SOURCE_GP)
public class GPAdAdapter implements AdAdapter {
  @Override public void fetchAd() {

  }

  @Override public String getAd() {
    return "google ad";
  }
}
