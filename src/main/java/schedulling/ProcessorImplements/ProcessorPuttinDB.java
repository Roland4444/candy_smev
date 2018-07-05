package schedulling.ProcessorImplements;

import DB.Executor;
import schedulling.ResolverImpl.PutResult;
import schedulling.abstractions.DataMapContainer;
import schedulling.abstractions.Processor;
import standart.Standart;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class ProcessorPuttinDB implements Processor {
    public Executor executor;
    public Standart operator;
    public DataMapContainer map;
    public void setDatamapContainer(DataMapContainer map){
        this.map=map;
    }
    public ProcessorPuttinDB(Executor exc) throws SQLException {
        this.executor=exc;
    }

    public void run(){
        System.out.println("in processor==>");
        Iterator it = map.DataConveer.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("Processing MessageID=>"+pair.getKey() );
            PutResult result = (PutResult) pair.getValue();
            this.operator=result.operator;
            it.remove(); // avoids a ConcurrentModificationException
        }
    };
}
