package promo.it.senders;

import org.smpp.Connection;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.SubmitSM;
import promo.it.settings.SettingsManager;

public class SmppSender extends BaseSender {

    public SmppSender(SettingsManager sm) {
        super(sm);
    }

    @Override
    public void send(String code) {
        Connection connection;
        Session session;

        try {
            connection = new TCPIPConnection(settingsManager.getSmppHost(), settingsManager.getSmppPort());
            session = new Session(connection);
            BindTransmitter bindRequest = new BindTransmitter();
            bindRequest.setSystemId(settingsManager.getSmppSystemId());
            bindRequest.setPassword(settingsManager.getSmppPassword());
            bindRequest.setSystemType(settingsManager.getSmppSystemType());
            bindRequest.setInterfaceVersion((byte) 0x34); // SMPP v3.4
            bindRequest.setAddressRange(settingsManager.getSmppSourceAddress());
            BindResponse bindResponse = session.bind(bindRequest);
            if (bindResponse.getCommandStatus() != 0) {
                throw new Exception("Bind failed: " + bindResponse.getCommandStatus());
            }
            SubmitSM submitSM = new SubmitSM();
            submitSM.setSourceAddr(settingsManager.getSmppSourceAddress());
            submitSM.setDestAddr(settingsManager.getSmppDestinationAddress());
            submitSM.setShortMessage("Your code: " + code);

            session.submit(submitSM);
            //logSuccess();
        } catch (Exception e) {
            throw new RuntimeException("Shit happened with SMPP sender: " + e.getMessage());
        }
    }
}
