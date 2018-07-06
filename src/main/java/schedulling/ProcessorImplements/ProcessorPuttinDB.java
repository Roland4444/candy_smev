package schedulling.ProcessorImplements;

import DB.Executor;
import schedulling.ResolverImpl.PutResult;
import schedulling.abstractions.DataMapContainer;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.Processor;
import standart.Standart;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class ProcessorPuttinDB implements Processor {
    public DependencyContainer deps;
    public Standart operator;
    public ProcessorPuttinDB(DependencyContainer deps) throws SQLException {
        this.deps=deps;
    }

    public void run(){/*
        System.out.println("in processor==>");
        System.out.println(deps.datamap.DataConveer.size());
        if (deps.datamap.DataConveer.size()>0) {
            Iterator it = deps.datamap.DataConveer.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                System.out.println("Processing MessageID=>"+pair.getKey() );
                PutResult result = (PutResult) pair.getValue();
                try {
                    this.operator.SendSoapSigned();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
*/
    };
}
