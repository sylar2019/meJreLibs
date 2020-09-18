package me.java.library.rpc.thrift.client.discovery;

public interface ServerListUpdater {

    void start(UpdateAction updateAction);

    void stop();

    public interface UpdateAction {
        void doUpdate();
    }

}
