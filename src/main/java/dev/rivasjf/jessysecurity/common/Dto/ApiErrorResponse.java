package dev.rivasjf.jessysecurity.common.Dto;

import lombok.Builder;

@Builder
public record ApiErrorResponse (
    Object details
){
}
