package chapter6;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class Test7_AtomicReferenceFieldUpdater {
    public static void main(String[] args) {
        Student stu = new Student();
        AtomicReferenceFieldUpdater arf = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        System.out.println(arf.compareAndSet(stu, null, "张三"));
        System.out.println(stu.toString());
    }

}

class Student{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
