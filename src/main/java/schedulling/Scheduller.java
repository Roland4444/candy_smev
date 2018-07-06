package schedulling;

import DB.Executor;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import schedulling.ProcessorImplements.ProcessorPuttinDB;
import schedulling.TaskerImplements.TaskerFromDB;
import schedulling.abstractions.*;
import util.*;
import java.sql.SQLException;

import static java.lang.Thread.sleep;

public class Scheduller implements Controller {
    public TaskerFromDB tasker;
    public ProcessorPuttinDB processor;
    public DependencyContainer deps;
    public Scheduller(DependencyContainer deps) throws SQLException, ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
        this.deps = deps;
        tasker=new TaskerFromDB(this.deps);
        processor= new ProcessorPuttinDB(this.deps);
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
