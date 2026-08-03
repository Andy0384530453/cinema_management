package com.example.demo.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.mail.internet.InternetAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.VerifyEmailIdentityRequest;

@ExtendWith(MockitoExtension.class)
class EmailAddressVerifierTest {
  @Mock private EmailConf emailConf;

  @Mock private SesClient sesClient;

  @Test
  void accept_shouldVerifyEmailIdentity() throws Exception {
    when(emailConf.getSesClient()).thenReturn(sesClient);
    EmailAddressVerifier verifier = new EmailAddressVerifier(emailConf);

    verifier.accept(new InternetAddress("to@example.com"));

    verify(sesClient).verifyEmailIdentity(any(VerifyEmailIdentityRequest.class));
  }
}
