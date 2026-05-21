package dev.rivasjf.digitalbrain.Common.Dto;

import lombok.Builder;

@Builder
public record ApiErrorResponse (
    Object details
){
}
