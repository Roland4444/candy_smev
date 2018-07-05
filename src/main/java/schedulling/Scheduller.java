package schedulling;

import DB.Executor;
import readfile.Readfile;
import schedulling.ProcessorImplements.ProcessorPuttinDB;
import schedulling.TaskerImplements.TaskerFromDB;
import schedulling.abstractions.Controller;
import schedulling.abstractions.DataMapContainer;
import schedulling.abstractions.Processor;
import schedulling.abstractions.Tasker;
import util.Extractor;

import java.sql.SQLException;

import static java.lang.Thread.sleep;

public class Scheduller implements Controller {
    public Tasker tasker;
    public Processor processor;
    public Executor executor;
    public Extractor ext;
    public DataMapContainer datamap;
    public Scheduller() throws SQLException {
        this.datamap=new DataMapContainer();
        this.ext=new Extractor();
        Readfile r = new Readfile("sqlset");
        this.executor=new Executor(r.read(), true);
    }
    public void setTasker() throws SQLException {
        this.tasker= new TaskerFromDB(this.executor);
        ((TaskerFromDB) this.tasker).setDatamapContainer(this.datamap);
        ((TaskerFromDB) this.tasker).setExtractor(this.ext);
    }
    public void setProcessor() throws SQLException {
        this.processor= new ProcessorPuttinDB(this.executor);
        ((ProcessorPuttinDB) this.processor).setDatamapContainer(this.datamap);
    }
    public Tasker getTasker(){
        return this.tasker;
    }
    public Processor getProcessor(){
        return this.processor;
    }
    public void run() throws InterruptedException {
        while (true) {
            this.tasker.run();
            sleep(12);
            this.processor.run();
        }
    };

    public void run(int counts) throws InterruptedException {
        for (int i =0; i<counts; i++){
            this.tasker.run();
            this.processor.run();
        }
    }

}
