package com.concurrent.juc.atomic;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 实现描述: AtomicReferenceTest
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-15 13:38
 */
public class AtomicReferenceTest {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Test
    public void testAll() throws InterruptedException {

        final AtomicStack stack = new AtomicStack();

        //压栈
        final int threadSize = 10;
        Thread[] ts = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            ts[i] = new Thread() {
                public void run() {
                    Integer uuid = atomicInteger.incrementAndGet();
                    stack.push("姓名" + uuid, uuid);
                }
            };
        }
        for (Thread t : ts) {
            t.start();
        }
        for (Thread t : ts) {
            t.join();
        }

        // 打印所有信息
        System.out.println(stack.printInfo());
    }

    public static class AtomicStack {
        private AtomicReference<User> headUser = new AtomicReference<User>();

        /**
         * 压栈
         *
         * @param name
         * @param age
         * @return
         */
        public User push(String name, Integer age) {
            User conrrentUser, newUser;
            while (true) {
                conrrentUser = headUser.get();
                newUser = new User(name, age, conrrentUser);
                if (headUser.compareAndSet(conrrentUser, newUser)) {
                    return conrrentUser;
                }
            }
        }

        /**
         * 出栈
         *
         * @return
         */
        public User pop() {
            User conrrentUser, nexUser;
            while (true) {
                conrrentUser = headUser.get();
                nexUser = conrrentUser.nextUser;
                if (headUser.compareAndSet(conrrentUser, nexUser)) {
                    return nexUser;
                }
            }
        }

        public String printInfo() {
            StringBuilder sb = new StringBuilder();
            User headUserTemp = headUser.get();
            while (headUserTemp.getNextUser() != null) {
                sb.append(JSON.toJSONString(headUserTemp));
                headUserTemp = headUserTemp.getNextUser();
            }
            return sb.toString();
        }
    }

    public static class User {
        private String name;
        private Integer age;
        private User nextUser;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public User(String name, Integer age, User nextUser) {
            this.name = name;
            this.age = age;
            this.nextUser = nextUser;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public User getNextUser() {
            return nextUser;
        }

        public void setNextUser(User nextUser) {
            this.nextUser = nextUser;
        }
    }
}
