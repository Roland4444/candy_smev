package schedulling.ProcessorImplements;

import DB.Executor;
import schedulling.ResolverImpl.PutResult;
import schedulling.abstractions.DataMapContainer;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.Processor;
import schedulling.abstractions.ResolverInOutData;
import standart.Standart;

import java.io.FileWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class ProcessorPuttinDB implements Processor {
    public DependencyContainer deps;
    public Standart operator;
    public ProcessorPuttinDB(DependencyContainer deps) throws SQLException {
        this.deps=deps;
    }

    public void succesquued(){
        System.out.println("sucess!");
    }

    public void errorquued(){
        System.out.println("error!");
    }

    public void sendAll() throws Exception {
        Iterator it = deps.datamap.DataConveer.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("Iterator=>"+pair.getKey() );
            ResolverInOutData resolver = (ResolverInOutData) pair.getValue();
            if (resolver.getOperator().equals("gis")) {
                this.deps.gis.setinput(new String(resolver.getDataWork()));
                String result = new String(this.deps.gis.SendSoapSigned());
            //    System.out.println(result);
                if (result.indexOf("requestIsQueued")>0 ){
                    this.succesquued();
                }
                else this.errorquued();
            }
            //   resolver.getOperator().setinput(new String(resolver.DataToWork));
            //   resolver.getOperator().SendSoapSigned();
         //   it.remove(); // avoids a ConcurrentModificationException
        }

    }



    public void run() throws Exception {
        String result = new String(deps.gis.GetResponceRequestCompiled());
        System.out.println("result>>>>\n"+result);
        while (result.indexOf("ns2:MessageID")>0){
            String id=deps.ext.extractTagValue(result, ":MessageID");
            if (id.equals(null))
                break;
            System.out.println("Extract id="+ id);
            perfom(result);
            deps.gis.Ack(id);
            result = new String(deps.gis.GetResponceRequestCompiled());
        }
    };

    public void perfom(String result){};
}
