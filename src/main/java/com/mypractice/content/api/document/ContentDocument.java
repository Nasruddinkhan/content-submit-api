package com.mypractice.content.api.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document
public class ContentDocument {
	@Id
	private String contenId;
	private String description;
}
