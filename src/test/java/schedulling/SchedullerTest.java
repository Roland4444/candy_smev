package schedulling;

import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.transforms.InvalidTransformException;
import org.junit.Test;
import schedulling.abstractions.DependencyContainer;
import util.SignatureProcessorException;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class SchedullerTest {
    DependencyContainer deps = new DependencyContainer();

    Scheduller sch = new Scheduller(deps);

    public SchedullerTest() throws SQLException, ClassNotFoundException, SignatureProcessorException, InvalidTransformException, AlgorithmAlreadyRegisteredException {
    }

    @Test
    public void setTasker() throws SQLException {
        sch.setTasker();
        sch.setProcessor();
        assertNotEquals(null, sch);
        assertNotEquals(null, sch.getProcessor());
        assertNotEquals(null, sch.getTasker());
    }

    @Test
    public void run() throws InterruptedException, SQLException {
        sch.setTasker();
        sch.setProcessor();
        sch.run();
    }

    @Test
    public void run1() throws SQLException, InterruptedException {
        sch.setTasker();
        sch.setProcessor();
        sch.run();
        String msgIOd="f6a09006-689a-11e8-8058-012ae3068118";
        assertNotEquals(null, sch.deps.datamap.DataConveer.get(msgIOd));
        Iterator it = sch.deps.datamap.DataConveer.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("Iterator=>"+pair.getKey() );
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    @Test
    public void run2() throws SQLException, InterruptedException {
        sch.setTasker();
        sch.setProcessor();
        sch.run(2);
        String msgIOd="f6a09006-689a-11e8-8058-012ae3068118";
        assertNotEquals(null, sch.deps.datamap.DataConveer.get(msgIOd));
        Iterator it = sch.deps.datamap.DataConveer.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("Iterator=>"+pair.getKey() );
            it.remove(); // avoids a ConcurrentModificationException
        }
    }


}