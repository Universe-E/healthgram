package com.example.gp;

import com.example.gp.Utils.MethodUtil;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class GetMethodTest {

    public static class TestClass1 {
        private int i;
        private int j;

        public void testFunc1(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public void testFunc2() {
        }
    }

    @Test
    public void test1() throws NoSuchMethodException {
        TestClass1 testClass1 = new TestClass1();
        Method method = MethodUtil.getMethod(testClass1, "testFunc1");
        assertEquals("testFunc1", method.getName());
        Class<?>[] parameterTypes = method.getParameterTypes();
        assertEquals(2, parameterTypes.length);
        assertEquals(int.class, parameterTypes[0]);
        assertEquals(int.class, parameterTypes[1]);
    }

    @Test
    public void testInvoke() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        TestClass1 testClass1 = new TestClass1();
        Method method1 = MethodUtil.getMethod(testClass1, "testFunc1");
        method1.invoke(testClass1, 1, 2);
        assertEquals(1, testClass1.i);
        assertEquals(2, testClass1.j);

        Method method2 = MethodUtil.getMethod(testClass1, "testFunc2");
        method2.invoke(testClass1);
    }
}