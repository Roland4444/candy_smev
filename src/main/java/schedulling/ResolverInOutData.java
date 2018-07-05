package schedulling;

import standart.Standart;

public abstract class ResolverInOutData {
    public Standart operator;
    public void setOperator(Standart operator){
        this.operator=operator;
    };
    public void setGetDataToWork(byte[] input){
        this.DataToWork = input;
    }
    public byte[] DataToWork;
    public abstract void putResult();
}
