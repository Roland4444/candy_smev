package schedulling.abstractions;

import standart.Standart;

import java.io.IOException;

public abstract class ResolverInOutData {
    public Standart operator;
    public Standart getOperator(){
        return this.operator;
    }
    public void setOperator(Standart operator){
        this.operator=operator;
    };
    public void setGetDataToWork(byte[] input){
        this.DataToWork = input;
    }
    public byte[] DataToWork;
    public abstract void putResult() throws IOException;
}
