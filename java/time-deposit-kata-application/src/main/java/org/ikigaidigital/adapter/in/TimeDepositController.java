package org.ikigaidigital.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ikigaidigital.adapter.in.dto.TimeDepositDto;
import org.ikigaidigital.adapter.in.dto.TimeDepositWithWithdrawalsDto;
import org.ikigaidigital.adapter.in.dto.WithdrawalDto;
import org.ikigaidigital.domain.Withdrawal;
import org.ikigaidigital.port.in.TimeDepositService;
import org.ikigaidigital.port.in.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Time Deposits", description = "Manage time deposits and accrue monthly interests")
@RestController
@RequestMapping("/time-deposits")
public class TimeDepositController {

    private final TimeDepositService timeDepositService;
    private final WithdrawalService withdrawalService;

    @Autowired
    public TimeDepositController(TimeDepositService timeDepositService, WithdrawalService withdrawalService) {
        this.timeDepositService = timeDepositService;
        this.withdrawalService = withdrawalService;
    }

    @Operation(
        summary = "List all time deposits",
        description = "Returns every time deposit together with its withdrawal history."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Time deposits retrieved successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = TimeDepositWithWithdrawalsDto.class))
        )
    )
    @GetMapping
    public List<TimeDepositWithWithdrawalsDto> getTimeDeposits() {
        return timeDepositService.getTimeDeposits()
                .stream()
                .map(deposit -> new TimeDepositWithWithdrawalsDto(
                        deposit.id(),
                        deposit.planType(),
                        deposit.balance(),
                        deposit.days(),
                        toWithdrawalDtos(withdrawalService.getWithdrawalsByTimeDepositId(deposit.id()))
                ))
                .toList();
    }

    @Operation(
        summary = "Apply daily interest to all time deposits",
        description = "Calculates and persists one day of interest for every active time deposit, then returns the updated balances."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Interest applied and updated deposits returned",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            array = @ArraySchema(schema = @Schema(implementation = TimeDepositDto.class))
        )
    )
    @PostMapping("/interests")
    public List<TimeDepositDto> saveInterests() {
        return timeDepositService.updateTimeDepositBalances()
                .stream()
                .map(timeDeposit -> new TimeDepositDto(
                        timeDeposit.id(),
                        timeDeposit.planType(),
                        timeDeposit.balance(),
                        timeDeposit.days()
                ))
                .toList();
    }

    private List<WithdrawalDto> toWithdrawalDtos(List<Withdrawal> withdrawals) {
        return withdrawals
                .stream()
                .map(w -> new WithdrawalDto(w.id(), w.timeDepositId(), w.amount(), w.date()))
                .toList();
    }
}
