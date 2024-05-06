package com.example.gp;

import com.example.gp.Utils.MethodUtil;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GetMethodTest {

    public static class TestClass1 {
        private int i;
        private int j;

        public void testFunc1(int i, int j) {
            this.i = i;
            this.j = j;
            System.out.println(1);
        }

        public void testFunc2() {
            System.out.println(2);
        }
    }

    @Test
    public void test1() throws NoSuchMethodException {
        TestClass1 testClass1 = new TestClass1();
        System.out.println(MethodUtil.getMethod(testClass1, "testFunc1"));
    }

    @Test
    public void testInvoke() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TestClass1 testClass1 = new TestClass1();
        MethodUtil.getMethod(testClass1, "testFunc1").invoke(testClass1, 1, 2);
        MethodUtil.getMethod(testClass1, "testFunc2").invoke(testClass1);
    }
}