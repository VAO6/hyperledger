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
	public Wallet createWallet(final Context ctx, final String walletId, final String tokenAmountStr, final String owner) {

		// Aqui deberia hacer las validaciones respecto a la chaincode para qeu no hubiera error, ej qu eno exista etc etc
		double tokenAmountDouble = 0.0;
		try {
			tokenAmountDouble = Double.parseDouble(tokenAmountStr);
			if (tokenAmountDouble < 0) {
				String errorMessage = String.format("Ampount %s error", tokenAmountDouble);
				throw new ChaincodeException(errorMessage, WalletManagerErrors.AMOUNTFORMAT_ERROR.toString());
			}
		} catch (numberFormatException e) {
			throw new ChaincodeException(e);
		}

		CahincodeStub stub = ctx.getStub();

		String walletState = stub.getStringState(walletId); // ojo si le mando algo que no existe levanta error, por eso tengo que hacer val
		if (!walletState.isEmpty()) {
			String errorMessage = String.format("Wallet %s already exists", walletId);
			System.out.print(errorMessage);
			throw new ChaincodeException(errorMessage, WalletManagerErrors.WALLET_ALREADY_EXISTS.toString());
		}

		Wallet wallet = new Wallet(tokenAmountDouble, owner);
		walletState = genson.serialize(wallet);
		stub.putStringState(walletId, walletState);
		return wallet;

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
		ChaincodeStub stub = ctx.getStub();
		String walletState = stub.getStringState();
		if (!walletState.isEmpty()) {
			String errorMessage = String.format("Wallet %s already exists", walletId);
			System.out.print(errorMessage);
			throw new ChaincodeException(errorMessage, WalletManagerErrors.WALLET_ALREADY_EXISTS.toString());
		}

		Wallet wallet = genson.deserialize(walletState, Wallet.class);
		return wallet;

	}

	@Transaction()
	public String transfer(final Context ctx, final String fromWalletId, final String toWalletId, final String tokenAmountStr) {

		// Aqui deberia hacer las validaciones respecto a la chaincode para qeu no hubiera error
		double tokenAmountDouble = 0.0;
		try {
			tokenAmountDouble = Double.parseDouble(tokenAmountStr);
			if (tokenAmountDouble < 0) {
				String errorMessage = String.format("Ampount %s error", tokenAmountDouble);
				throw new ChaincodeException(errorMessage, WalletManagerErrors.AMOUNTFORMAT_ERROR.toString());
			}
		} catch (numberFormatException e) {
			throw new ChaincodeException(e);
		}

		CahincodeStub stub = ctx.getStub();
		String fromWalletState = stub.getStringState(fromWalletId);
		if (!fromWalletState.isEmpty()) {
			String errorMessage = String.format("Wallet %s already exists", fromWalletId);
			System.out.print(errorMessage);
			throw new ChaincodeException(errorMessage, WalletManagerErrors.WALLET_NOT_FOUND.toString());
		}
		String toWalletState = stub.getStringState(toWalletId);
		if (!toWalletState.isEmpty()) {
			String errorMessage = String.format("Wallet %s already exists", toWalletId);
			System.out.print(errorMessage);
			throw new ChaincodeException(errorMessage, WalletManagerErrors.WALLET_NOT_FOUND.toString());
		}

		Wallet fromWallet = genson.deserialize(fromWalletState, Wallet.class);
		Wallet toWallet = genson.deserialize(toWalletState, Wallet.class);

		if (fromWallet.getTokenAmount() < tokenAmountDouble) {
			throw new ChaincodeException("Token amount not enough", WalletManagerErrors.TOKENAMOUNTNOTENOUGH.toString());
		}

		Wallet newFromWallet = new Wallet(fromWallet.getTokenAmount() - tokenAmountDouble, fromWallet.getOwner());
		Wallet newToWallet = new Wallet(toWallet.getTokenAmount() + tokenAmountDouble, toWallet.getOwner());

		String newFromWalletState = genson.serialize(newFromWallet);
		String newToWalletState = genson.serialize(newToWallet);
		stub.putStringState(fromWalletId, newFromWalletState);
		stub.putStringState(toWalletId, newToWalletState);

		return "transfered";
	}
}
