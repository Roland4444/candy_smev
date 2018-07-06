package schedulling.abstractions;

public interface Processor extends Runnable{
    public void succesquued();

    public void errorquued();
}
