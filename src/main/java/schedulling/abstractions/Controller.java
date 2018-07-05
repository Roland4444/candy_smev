package schedulling.abstractions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface Controller {
    public void setTasker() throws SQLException;
    public void setProcessor() throws SQLException;
}
