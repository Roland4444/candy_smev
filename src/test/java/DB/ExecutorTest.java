package DB;

import crypto.Gost3411Hash;
import org.junit.Test;
import readfile.Readfile;
import util.buildSql;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotEquals;

public class ExecutorTest {

    @Test
    public void getLenth() {
    }

    @Test
    public void submit() throws SQLException, IOException {
        Readfile r = new Readfile("sqlset");
        Executor f = new Executor(r.read(), true);
        buildSql sql = new buildSql("3567");
        assertNotEquals(null, f.submit(sql.result()) );
        FileWriter wr = new FileWriter("3567");
        ResultSet Select2 = f.submit("set concat_null_yields_null off; SELECT f_body_a FROM gkh_files WHERE f_key=724442;");
        Gost3411Hash gost = new Gost3411Hash();
        if (Select2.next()){
            String res = String.valueOf(gost.getBytesFromBase64(Select2.getString("f_body_a")));
            System.out.print(res);
        }
        wr.close();
    }
}