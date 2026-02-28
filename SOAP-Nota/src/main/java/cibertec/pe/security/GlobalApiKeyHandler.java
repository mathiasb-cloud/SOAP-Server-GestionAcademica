package cibertec.pe.security;

import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import org.springframework.stereotype.Component;
import javax.xml.namespace.QName;
import java.util.Set;

@Component
public class GlobalApiKeyHandler implements SOAPHandler<SOAPMessageContext> {

    private GlobalApiKeyValidator validator;

    private GlobalApiKeyValidator getValidator() {
        if (validator == null) {
            validator = ApplicationContextProvider.getBean(GlobalApiKeyValidator.class);
        }
        return validator;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        System.out.println("🔐 HANDLER EJECUTADO - isOutbound: " + isOutbound);
        if (isOutbound) return true;

        try {
            SOAPMessage message = context.getMessage();
            SOAPHeader header = message.getSOAPHeader();

            String apiKey = null;

            if (header != null) {
                org.w3c.dom.NodeList children = header.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    org.w3c.dom.Node node = children.item(i);
                    String localName = node.getLocalName() != null ? node.getLocalName() : node.getNodeName();
                    if ("ApiKey".equals(localName)) {
                        apiKey = node.getTextContent().trim();
                        System.out.println("API KEY RECIBIDA: [" + apiKey + "]");
                        break;
                    }
                }
            }

            if (apiKey == null) throw new RuntimeException("API Key no enviada");

            if (!getValidator().validate(apiKey)) {
                throw new RuntimeException("API Key inválida");
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar API Key", e);
        }

        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) { return true; }

    @Override
    public void close(MessageContext context) {}

    @Override
    public Set<QName> getHeaders() { return null; }
}