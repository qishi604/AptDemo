package com.lanayru.aptdemo;

import com.lanayru.apt.api.ad.Const;
import com.lanayru.aptdemo.ad.AdAdapterFactory;
import com.lanayru.aptdemo.ad.GPAdAdapter;
import com.lanayru.aptdemo.ad.InMobAdAdapter;
import com.lanayru.aptdemo.ad.TTAdAdapter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() {
    assertEquals(4, 2 + 2);
  }

  @Test public void testFactory() {
    assert AdAdapterFactory.getAdapter(Const.SOURCE_GP).getClass() == GPAdAdapter.class;
    assert AdAdapterFactory.getAdapter(Const.SOURCE_TT).getClass() == TTAdAdapter.class;
    assert AdAdapterFactory.getAdapter(Const.SOURCE_INMOB).getClass() == InMobAdAdapter.class;
  }
}