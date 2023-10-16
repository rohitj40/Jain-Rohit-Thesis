package org.example.consumerportal.request.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PrivateDataAccessModel {
    private String grantedEmail;
    private String response;
    private String owningProducerUsername;
    private String dataFieldNames;
}
