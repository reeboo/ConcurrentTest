package com.concurrent.jmm;

/**
 * 实现描述: JMMDoubleCheck
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-08-18 13:58
 */
public class JMMDoubleCheck {
    private volatile static JMMDoubleCheck sigletonInstance;
    private CacheValue cacheValue;

    public JMMDoubleCheck() {
        CacheValue cacheValue1 = new CacheValue();
        cacheValue1.setAge(1);
        cacheValue1.setName("lucy");
    }

    /**
     * 获取单例实例
     *
     * @return
     */
    public static JMMDoubleCheck getInstance() {
        if (sigletonInstance == null) {
            synchronized (JMMDoubleCheck.class) {
                if (sigletonInstance == null) {
                    sigletonInstance = new JMMDoubleCheck();
                }
            }
        }
        return sigletonInstance;
    }

    public CacheValue getCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(CacheValue cacheValue) {
        this.cacheValue = cacheValue;
    }

    public static class CacheValue {
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
