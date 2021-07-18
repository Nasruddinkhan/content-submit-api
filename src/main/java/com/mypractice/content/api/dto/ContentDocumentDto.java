package com.mypractice.content.api.dto;

import com.mypractice.content.api.constant.MessageConstants;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContentDocumentDto {
	private String contenId;
	@NotEmpty(message = MessageConstants.FLD_EMPTY_VLD)
	@Size(message = "Size minimum  {min} charecher are allowed", min = 10)
	private String description;
}
