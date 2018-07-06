package schedulling.abstractions;

import standart.Standart;
import util.Injector;

import java.io.IOException;

public abstract class ResolverInOutData {
    public String operator;
    Injector inj = new Injector();
    public String getOperator(){
        return this.operator;
    }
    public void setOperator(String operator){
        this.operator=operator;
    };
    public void setDataToWork(byte[] input){
        this.DataToWork = input;
    }
    private byte[] DataToWork;
    public abstract void putResult() throws IOException;
    public byte[] getDataWork(){
      return DataToWork;
    };

}
