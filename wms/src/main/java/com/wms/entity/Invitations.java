package com.wms.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jrd
 * @since 2023-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Invitations对象", description="")
public class Invitations implements Serializable {

    private static final long serialVersionUID = 1L;

    private String invitation;

    private Integer role;


}
