package pl.socketbyte.opensectors.system.logging;

import pl.socketbyte.opensectors.system.OpenSectorSystem;

public class ByteInformatorTask implements Runnable {
    @Override
    public void run() {
        int sent = ByteInformator.getBytesSent();
        int received = ByteInformator.getBytesReceived();

        if (sent == 0 && received == 0)
            return;

        OpenSectorSystem.log().info("Received " + ByteInformator.getBytesReceived()
                + " and sent " + ByteInformator.getBytesSent() + " bytes!");

        ByteInformator.setBytesReceived(0);
        ByteInformator.setBytesSent(0);
    }
}
