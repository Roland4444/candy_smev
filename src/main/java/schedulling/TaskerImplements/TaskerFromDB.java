package schedulling.TaskerImplements;

import DB.Executor;

import schedulling.ResolverImpl.PutResult;
import schedulling.abstractions.DataMapContainer;
import schedulling.abstractions.Tasker;
import util.Extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
public class TaskerFromDB implements Tasker {
    public Executor executor;
    public Extractor ext;
    public DataMapContainer map;
    public void setExtractor(Extractor input){
        this.ext = input;
    };
    public void setDatamapContainer(DataMapContainer map){
        this.map=map;
    }
    public TaskerFromDB(Executor exc) throws SQLException {
        this.executor=exc;
    }

    public void run(){
        System.out.println("in tasker==>");
        ResultSet Select2 = null;
        try {
            Select2 = this.executor.submit("set concat_null_yields_null off; SELECT f_body_xml FROM gis_files WHERE f_stat='0';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (Select2.next()){
                String res =Select2.getString("f_body_xml");
                String msgId = ext.extractTagValue(res, ":MessageID");
                if (msgId == null) break;
                if (map.DataConveer.get(msgId)==null){
                    map.DataConveer.put(msgId, new PutResult());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
}
