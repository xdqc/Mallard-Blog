package utililties;

import java.io.Serializable;

public class Tuple<T1, T2> implements Serializable{
    public T1 Val1;
    public T2 Val2;

    public Tuple(T1 val1, T2 val2) {
        Val1 = val1;
        Val2 = val2;
    }

    public T1 getVal1() {
        return Val1;
    }

    public void setVal1(T1 val1) {
        Val1 = val1;
    }

    public T2 getVal2() {
        return Val2;
    }

    public void setVal2(T2 val2) {
        Val2 = val2;
    }
}
