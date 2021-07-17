package com.mypractice.content.api.dto;

import com.mypractice.content.api.constant.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	@Size(message = "Size minimum  10 max 20 charecher are allowed", min = 10, max = 20)
	private String description;
}
