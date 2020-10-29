public class test {
    public static void main(String[] args) {
//        B b = new B();
//        b.print(); b.print_();
//        A a = (A)b;
//        a.print(); a.print_();
//        ((B)a).print(); ((B)a).print_();

          A[] a = new A[5];
          System.out.println(a[0]==null);
    }
}
class A {
    static void print() {
        System.out.println("A");
    }
    public void print_() {
        System.out.println("A");
    }
}
class B extends A{
    static void print() {
        System.out.println("B");
    }
    public void print_() {
        System.out.println("B");
    }
}

