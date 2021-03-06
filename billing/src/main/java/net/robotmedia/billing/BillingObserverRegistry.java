package net.robotmedia.billing;

import android.app.PendingIntent;
import net.robotmedia.billing.model.Transaction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: serso
 * Date: 1/18/12
 * Time: 11:49 AM
 */

/**
 * This class is used for storing all the registered observers of billing service.
 * Use this class to notify all the net.robotmedia.billing.IBillingObserver observers
 */
class BillingObserverRegistry {

	// synchronized field
	@NotNull
	private static final Set<IBillingObserver> observers = new HashSet<IBillingObserver>();

	static void onCheckBillingSupportedResponse(boolean supported) {
		for (IBillingObserver o : getSynchronizedObservers()) {
			o.onCheckBillingSupportedResponse(supported);
		}
	}

	/**
	 * Called after the response to a
	 * {@link net.robotmedia.billing.BillingRequest.Purchase} request is
	 * received.
	 *
	 * @param productId		 id of the item whose purchase was requested.
	 * @param purchaseIntent intent to purchase the item.
	 */
	static void onPurchaseIntent(@NotNull String productId, @NotNull PendingIntent purchaseIntent) {
		for (IBillingObserver o : getSynchronizedObservers()) {
			o.onPurchaseIntentOK(productId, purchaseIntent);
		}
	}

	static void onTransactionsRestored() {
		for (IBillingObserver o : getSynchronizedObservers()) {
			o.onTransactionsRestored();
		}
	}

	/**
	 * Registers the specified billing observer.
	 *
	 * @param observer the billing observer to add.
	 * @return true if the observer wasn't previously registered, false
	 *         otherwise.
	 * @see #unregisterObserver(IBillingObserver)
	 */
	static boolean registerObserver(@NotNull IBillingObserver observer) {
		synchronized (observers) {
			return observers.add(observer);
		}
	}

	/**
	 * Unregisters the specified billing observer.
	 *
	 * @param observer the billing observer to unregister.
	 * @return true if the billing observer was unregistered, false otherwise.
	 * @see #registerObserver(IBillingObserver)
	 */
	static boolean unregisterObserver(@NotNull IBillingObserver observer) {
		synchronized (observers) {
			return observers.remove(observer);
		}
	}

	/**
	 * Notifies observers of the purchase state change of the specified item.
	 *
	 * @param productId id of the item whose purchase state has changed.
	 * @param state  new purchase state of the item.
	 */
	static void notifyPurchaseStateChange(@NotNull String productId, @NotNull Transaction.PurchaseState state) {
		for (IBillingObserver o : getSynchronizedObservers()) {
			o.onPurchaseStateChanged(productId, state);
		}
	}

	// method gets a synchronized copy of list
	@NotNull
	private static List<IBillingObserver> getSynchronizedObservers() {
		final List<IBillingObserver> result;
		synchronized (observers) {
			result = new ArrayList<IBillingObserver>(observers);
		}
		return result;
	}

	static void onRequestPurchaseResponse(@NotNull String productId, @NotNull ResponseCode response) {
		for (IBillingObserver o : getSynchronizedObservers()) {
			o.onRequestPurchaseResponse(productId, response);
		}
	}

	public static void onPurchaseIntentFailure(@NotNull String productId, @NotNull ResponseCode responseCode) {
		for (IBillingObserver o : getSynchronizedObservers()) {
			o.onPurchaseIntentFailure(productId, responseCode);
		}
	}

	public static void onErrorRestoreTransactions(@NotNull ResponseCode response) {
		for (IBillingObserver o : getSynchronizedObservers()) {
			o.onErrorRestoreTransactions(response);
		}
	}
}
