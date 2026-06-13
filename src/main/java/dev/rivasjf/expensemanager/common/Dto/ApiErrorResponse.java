package dev.rivasjf.expensemanager.common.Dto;

import lombok.Builder;

@Builder
public record ApiErrorResponse (
    Object details
){
}
