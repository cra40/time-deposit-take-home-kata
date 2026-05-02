package org.ikigaidigital.adapter.in;

import org.ikigaidigital.adapter.in.dto.TimeDepositDto;
import org.ikigaidigital.adapter.in.dto.TimeDepositWithWithdrawalsDto;
import org.ikigaidigital.adapter.in.dto.WithdrawalDto;
import org.ikigaidigital.domain.Withdrawal;
import org.ikigaidigital.port.in.TimeDepositService;
import org.ikigaidigital.port.in.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
