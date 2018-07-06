package schedulling;

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
    public void loop() throws Exception {
        while (true) {
            this.tasker.run();
            sleep(12);
            this.processor.run();
        }
    };

    public void loop(int counts) throws Exception {
        for (int i =0; i<counts; i++){
            System.out.println(i+"    trying");
            // tasker.loop();
            processor.sendAll();
            sleep(5000);
            processor.run();
        }
    }



}
