package eu.cryptoeuro.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;
import eu.cryptoeuro.rest.command.NotifyEscrowCommand;
import eu.cryptoeuro.rest.command.CreateTransferCommand;
import eu.cryptoeuro.rest.command.CreateBankTransferCommand;
import eu.cryptoeuro.rest.exception.ValidationException;
import eu.cryptoeuro.rest.model.Transfer;
import eu.cryptoeuro.rest.model.ContractInfo;
import eu.cryptoeuro.service.TransferService;
import eu.cryptoeuro.service.EmailService;
import eu.cryptoeuro.service.ContractService;
import eu.cryptoeuro.config.ContractConfig;
import java.io.IOException;

@Api(value="transfers",
        description="transfers desc",
        produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping("/v1/transfers")
@Slf4j
@CrossOrigin(origins = "*")
public class TransferController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private ContractConfig contractConfig;


    @ApiOperation(value = "Get transfer.")
    @RequestMapping(method = RequestMethod.GET, value = "/{transactionHash}")
    public ResponseEntity<Transfer> getTransfer(
            @PathVariable String transactionHash
    ){
        log.info("Getting a transfer " + transactionHash);

        return new ResponseEntity<>(
                transferService.get(transactionHash),
                new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Initiates a transfer to another Ethereum account.")
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<Transfer> postWithTransferRequest(
            @Valid @RequestBody @ApiParam CreateTransferCommand createTransferCommand,
            @ApiIgnore Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }

        return new ResponseEntity<>(
                transferService.delegatedTransfer(createTransferCommand),
                new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Initiates a transfer to a SEPA bank account.")
    @RequestMapping(method = RequestMethod.POST, value = "/bank")
    public ResponseEntity<Transfer> postWithSepaTransferRequest(
            @Valid @RequestBody @ApiParam CreateBankTransferCommand createBankTransferCommand,
            @ApiIgnore Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }

        return new ResponseEntity<>(
                transferService.delegatedBankTransfer(createBankTransferCommand),
                new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Notifies the escrow recipient of an inbound transfer.")
    @RequestMapping(method = RequestMethod.POST, value = "/notifyEscrow")
    public ResponseEntity<String> notifyEscrow(
            @Valid @RequestBody @ApiParam NotifyEscrowCommand notifyEscrowCommand,
            @ApiIgnore Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }

        emailService.notifyEscrow(notifyEscrowCommand);

        return new ResponseEntity<String>("Sent",
                new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value = "Display contract addresses from base contract.")
    @RequestMapping(method = RequestMethod.GET, value = "/contractInfo")
    public ResponseEntity<ContractInfo> contractInfo() throws IOException {

	ContractInfo ci = contractService.contractInfo();

        return new ResponseEntity<ContractInfo>(ci,
                new HttpHeaders(), HttpStatus.OK);
    }

    /*
    @ApiOperation(value = "Test call sent transaction.")
    @RequestMapping(method = RequestMethod.POST, value = "/testSendTransaction")
    public ResponseEntity<String> postTestSendTransaction(
            @Valid @RequestParam(value = "account", required = false) Optional<String> account,
            @Valid @RequestParam(value = "amount", required = false) Optional<Long> amount
    ){
        log.info("Test send transaction for account " + account.toString() + ", " + amount.toString());
        return new ResponseEntity<>(
                transferService.mintToken(account, amount),
                new HttpHeaders(), HttpStatus.OK);
    }
    */

}
