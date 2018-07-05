package schedulling.ProcessorImplements;

import DB.Executor;
import schedulling.ResolverImpl.PutResult;
import schedulling.abstractions.DataMapContainer;
import schedulling.abstractions.Processor;

import java.sql.ResultSet;
import java.sql.SQLException;
import static java.lang.Thread.sleep;

public class ProcessorPuttinDB implements Processor {
    public Executor executor;
    public DataMapContainer map;
    public void setDatamapContainer(DataMapContainer map){
        this.map=map;
    }
    public ProcessorPuttinDB(Executor exc) throws SQLException {
        this.executor=exc;

    }

    public void run(){
        System.out.println("in processor==>");
    };
}
