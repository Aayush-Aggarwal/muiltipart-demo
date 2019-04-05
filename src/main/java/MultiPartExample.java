
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MultiPartExample {
    
    public static void main(String[] args) throws IOException {
        usingCloseableHttpClient();
    }
    
    private static void usingCloseableHttpClient() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // It will basically vuild a default HttpClient using HttpClientBuilder
        
        HttpPost uploadFile = new HttpPost("https://demo.docusign.net/restapi/v2/accounts/4b84e552-c934-4aaa-8440-4497c54583c0/envelopes");
        // Here is the url where the envelop is to be updated
        
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        
        builder.addTextBody("jsond", "{ \"emailSubject\": \"Royal Embedded Email Subject\", \"emailBlurb\": \"Royal Embedded Testing Email Blurb\", \"status\": \"created\", \"notification\": { \"reminders\": { \"reminderDelay\": \"0\", \"reminderEnabled\": \"false\", \"reminderFrequency\": \"0\" }, \"expirations\": { \"expireAfter\": \"15\", \"expireEnabled\": \"true\", \"expireWarn\": \"0\" }, \"useAccountDefaults\": \"false\" }, \"compositeTemplates\": [{ \"inlineTemplates\": [{ \"sequence\": \"1\", \"customFields\": { \"textCustomFields\": [{ \"fieldId\": \"1\", \"name\": \"Ship Name\", \"show\": \"false\", \"required\": \"false\", \"value\": \"Ms Allure of the Seas\" }, { \"fieldId\": \"2\", \"name\": \"SignOnDate\", \"show\": \"false\", \"required\": \"false\", \"value\": \"12/13/2018\" } ] }, \"documents\": [{ \"documentId\": \"1\", \"name\": \"corp_lorem.pdf\" }], \"recipients\": { \"signers\": [{ \"name\": \"Crew Member\", \"email\": \"ayush.aggarwal@knoldus.in\", \"recipientId\": \"1\", \"routingOrder\": \"1\", \"clientUserId\": \"82474ebe-9710-4015-a29c-47aa86bf1e06\", \"tabs\": { \"signHereTabs\": [{ \"anchorString\": \"World Wide Corp\", \"tabLabel\": \"CrewMemberSignature\" }] } }] } }] }] }", ContentType.APPLICATION_JSON);
        // Here we are adding a text body to the builder.basically an email template.
        
        uploadFile.setHeader("Authorization", "Bearer eyJ0eXAiOiJNVCIsImFsZyI6IlJTMjU2Iiwia2lkIjoiNjgxODVmZjEtNGU1MS00Y2U5LWFmMWMtNjg5ODEyMjAzMzE3In0.AQkAAAABAAUABwAA_zgDkLnWSAgAAGf9ZJi51kgCADdA5t01h_BEomb4nIrJOkgVAAEAAAAYAAEAAAAFAAAADQAkAAAAODI0NzRlYmUtOTcxMC00MDE1LWEyOWMtNDdhYTg2YmYxZTA2IwAkAAAAODI0NzRlYmUtOTcxMC00MDE1LWEyOWMtNDdhYTg2YmYxZTA2EgABAAAABgAAAGp3dF9icg.g7vKeHzr5mpOgMwAUYreiv8gpTkyVcdpHwyvh96y7qSy2h0M1D4QClrGkUyqzwji6ul_fFarjFJfmtmGwhN9FTpuqcU3L7oMrw0xgwuQFOJmYVaeGTAKRA7wYLOC-kD4EQYCsGW8Dh320sN4HJxEVoLhw-Df_iJ8mbmiGP1xCcbp-yKUe2I950bY2xJto2ISazVwAhg8NgveP3kTpbeggYKzSFPsCTfsROKe29jmaL32Ybj1ZSDqQ3VqCoMdBje28sfOM32MjQS_gRKioFuqvEuGBr-zqrI-lGadSr8fZCexR2zTapw3DBEcCeE_WgL_Jmn2lr0zjN9FgsDouZg1Zg");
        // This attaches the file to the POST:
        
        File f = new File("src/main/resources/corp_lorem.pdf");
        
        builder.addBinaryBody(
                "file",
                new FileInputStream(f),
                ContentType.APPLICATION_OCTET_STREAM,
                f.getName()
        );
        
        HttpEntity multipart = builder.build();
        
        uploadFile.setEntity(multipart);
        
        CloseableHttpResponse response = httpClient.execute(uploadFile);
        
        System.out.println(response.toString());
        
        //HttpEntity responseEntity = response.getEntity();
    }
}
