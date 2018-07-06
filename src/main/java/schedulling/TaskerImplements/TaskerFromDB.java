package schedulling.TaskerImplements;

import DB.Executor;

import schedulling.ResolverImpl.PutResult;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.Tasker;
import java.sql.ResultSet;
import java.sql.SQLException;
public class TaskerFromDB implements Tasker {
    public DependencyContainer deps;
    ResultSet Select2 = null;
    public TaskerFromDB(DependencyContainer deps) throws SQLException {
        this.deps=deps;
    }
    public void run() throws SQLException {
        System.out.println("in tasker==>");
        Select2 = this.deps.executor.submit("set concat_null_yields_null off; SELECT f_body_xml FROM gis_files WHERE f_stat='0';");
        if (Select2==null){
            System.out.println("NULL in Result");
            return;
        }
        while (Select2.next()){
                String res =Select2.getString("f_body_xml");
                String msgId = this.deps.ext.extractTagValue(res, ":MessageID");
                if (msgId == null) break;
                if (deps.datamap.DataConveer.get(msgId)==null){
                    PutResult resulter = new PutResult();
                    resulter.setOperator("gis");
                    resulter.setDataToWork(res.getBytes());
                    deps.datamap.DataConveer.put(msgId, new PutResult());
                }
        }

    };
}
