package schedulling.abstractions;

import standart.Standart;

import java.io.IOException;

public abstract class ResolverInOutData {
    public String operator;
    public String getOperator(){
        return this.operator;
    }
    public void setOperator(String operator){
        this.operator=operator;
    };
    public void setDataToWork(byte[] input){
        this.DataToWork = input;
    }
    public byte[] DataToWork;
    public abstract void putResult() throws IOException;
}
