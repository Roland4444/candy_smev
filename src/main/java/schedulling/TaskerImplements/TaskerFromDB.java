package schedulling.TaskerImplements;

import DB.Executor;

import schedulling.ResolverImpl.PutResult;
import schedulling.abstractions.DependencyContainer;
import schedulling.abstractions.Tasker;
import standart.gis;
import java.sql.ResultSet;
import java.sql.SQLException;
public class TaskerFromDB implements Tasker {
    public DependencyContainer deps;
    public TaskerFromDB(DependencyContainer deps) throws SQLException {
        this.deps=deps;
    }
    public void run(){
        System.out.println("in tasker==>");
        ResultSet Select2 = null;
        try {
            Select2 = this.deps.executor.submit("set concat_null_yields_null off; SELECT f_body_xml FROM gis_files WHERE f_stat='0';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (Select2.next()){
                String res =Select2.getString("f_body_xml");
                String msgId = this.deps.ext.extractTagValue(res, ":MessageID");
                if (msgId == null) break;
                if (deps.datamap.DataConveer.get(msgId)==null){
                    PutResult resulter = new PutResult();
                    resulter.setOperator(new gis(this.deps.sr, this.deps.xmlsign, this.deps.personalSign, this.deps.sign));
                    deps.datamap.DataConveer.put(msgId, new PutResult());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
}
