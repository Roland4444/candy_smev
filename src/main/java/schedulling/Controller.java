package schedulling;

import java.util.HashMap;
import java.util.Map;

public interface Controller {
    public Map<String, ResolverInOutData> DataConveer=new HashMap();
    public void setTasker(Tasker tasker);
    public void setProcessor(Tasker tasker);
}
