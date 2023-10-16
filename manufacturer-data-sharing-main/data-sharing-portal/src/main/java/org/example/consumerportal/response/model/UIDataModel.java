package org.example.consumerportal.response.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class UIDataModel {
    private Integer id;
    private String value;
    private String owningProducerUsername;
}
