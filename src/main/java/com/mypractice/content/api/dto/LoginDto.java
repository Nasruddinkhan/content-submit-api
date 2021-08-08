package com.mypractice.content.api.dto;

import com.mypractice.content.api.constant.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable {

    private static final long serialVersionUID = -6986746375915710855L;

    @NotBlank(message = MessageConstants.FLD_EMPTY_VLD)
    private String username;

    @NotBlank(message = MessageConstants.FLD_EMPTY_VLD)
    private String password;
}
