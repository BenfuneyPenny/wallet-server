package eu.cryptoeuro.service;

import eu.cryptoeuro.rest.command.CreateBankTransferCommand;
import eu.cryptoeuro.rest.command.CreateTransferCommand
import eu.cryptoeuro.rest.model.Transfer;

public class TransferFixture {

    public static CreateTransferCommand sampleCreateTransferCommand() {
        return [
                sourceAccount:
                        "0x65fa6548764c08c0dd77495b33ed302d0c212691",
                targetAccount: "0x833898875a12a3d61ef18dc3d2b475c7ca3a4a72",
                amount       : 1,
                fee          : 1,
                nonce        : 2,
                signature    :
                        "cc193b8fe8c8b986f676fdb142cc9cea5c8369ef494b5207abdaf0de4a27efd07cfc8c5094b6418384169ab467fc9c5e880730a502c0b6b87c35eb30baa6a8431b"
        ]
    }

    public static CreateBankTransferCommand sampleCreateBankTransferCommand() {
        return [
                sourceAccount        :
                        "0x65fa6548764c08c0dd77495b33ed302d0c212691",
                targetBankAccountIBAN: "IBAN_NOT_USED_IN_ETH",
                amount               : 1,
                fee                  : 5,
                nonce                : 2,
                signature            :
                        "cc193b8fe8c8b986f676fdb142cc9cea5c8369ef494b5207abdaf0de4a27efd07cfc8c5094b6418384169ab467fc9c5e880730a502c0b6b87c35eb30baa6a8431b"
        ]
    }

    public static Transfer sampleTransfer() {
        [id: 0x3c4ce49a42bd00d039010416ddf850411796a0bdbfb8ef7639e4e71d0d12d0a8]
    }

}
