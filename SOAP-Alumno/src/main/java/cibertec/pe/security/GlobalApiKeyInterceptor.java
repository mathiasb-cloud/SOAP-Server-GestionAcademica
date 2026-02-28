package cibertec.pe.security;

import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalApiKeyInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    @Autowired
    private GlobalApiKeyValidator validator;

    public GlobalApiKeyInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        List<Header> headers = message.getHeaders();
        String apiKey = null;

        for (Header header : headers) {
            if ("ApiKey".equals(header.getName().getLocalPart())) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) header.getObject();
                apiKey = element.getTextContent().trim();
                System.out.println("API KEY RECIBIDA: [" + apiKey + "]");
            }
        }

        if (apiKey == null || !validator.validate(apiKey)) {
            throw new Fault(new Exception("API Key inválida o no enviada"));
        }
    }
}