package schedulling.ResolverImpl;

import schedulling.abstractions.ResolverInOutData;

import java.io.FileWriter;
import java.io.IOException;

public class PutResult extends ResolverInOutData {
    public byte[] result;
    public void putResult() throws IOException {
        FileWriter wr = new FileWriter("xml4test/resulter.info", true);
        wr.write(new String(result));
        wr.close();

    }
}
