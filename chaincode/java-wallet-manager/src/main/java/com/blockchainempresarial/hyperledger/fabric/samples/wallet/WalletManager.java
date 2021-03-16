/*
	Business Blockchain Training & Consulting SpA. All Rights Reserved.
	www.blockchainempresarial.com
	email: ricardo@blockchainempresarial.com
*/
package com.blockchainempresarial.hyperledger.fabric.samples.wallet;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

import com.owlike.genson.Genson;

@Contract(name = "WalletManager", info = @Info(title = "WalletManager contract", description = "WalletManager sample contract", version = "0.0.1-SNAPSHOT", license = @License(name = "Apache 2.0 License", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), contact = @Contact(email = "ricardo@blockchainempresarial.com", name = "F Wallet", url = "https://hyperledger.blockchainempresarial.com")))
@Default
public final class WalletManager implements ContractInterface {

	private final Genson genson = new Genson();

	private enum WalletManagerErrors{
		WALLET_NOT_FOUND,
		WALLET_ALREADY_EXISTS,
		AMOUNTFORMAT_ERROR,
		TOKENAMOUNTNOTENOUGH
	}
	
	@Transaction()
	public void initLedger(final Context ctx) {
		
	}
	
	/**
	 * User wallet creation
	 * 
	 * @param ctx
	 * @param walletId
	 * @param tokenAmountStr
	 * @param owner
	 * @return
	 */
	@Transaction()
	public Wallet createWallet(final Context ctx, final String walletId, final String tokenAmount, final String owner) {

		// Aqui deberia hacer las validaciones respecto a la chaincode para qeu no hubiera error, ej qu eno exista etc etc
		
	}

	/**
	 * User wallet query
	 * 
	 * @param ctx
	 * @param walletId
	 * @return
	 */
	@Transaction()
	public Wallet getWallet(final Context ctx, final String walletId) {

		// Aqui deberia hacer las validaciones respecto a la chaincode para qeu no hubiera error

	}

	@Transaction()
	public String transfer(final Context ctx, final String fromWalletId, final String toWalletId, final String tokenAmountStr) {

		// Aqui deberia hacer las validaciones respecto a la chaincode para qeu no hubiera error

	}
}
