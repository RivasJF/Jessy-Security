package dev.rivasjf.expensemanager.Common.Dto;

import lombok.Builder;

@Builder
public record ApiErrorResponse (
    Object details
){
}
