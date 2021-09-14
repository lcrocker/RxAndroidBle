package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import androidx.annotation.RequiresApi;

import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;

import bleshadow.javax.inject.Inject;

import io.reactivex.Single;

public class PreferredPhyUpdateOperation extends SingleResponseOperation<Integer> {

    private final int txPhy;
    private final int rxPhy;
    private final int phyOptions;

    @Inject
    PreferredPhyUpdateOperation(
            RxBleGattCallback rxBleGattCallback,
            BluetoothGatt bluetoothGatt,
            TimeoutConfiguration timeoutConfiguration,
            int txPhy, int rxPhy, int phyOptions) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.ON_PHY_CHANGED, timeoutConfiguration);
        this.txPhy = txPhy;
        this.rxPhy = rxPhy;
        this.phyOptions = phyOptions;
    }

    @Override
    protected Single<Integer> getCallback(RxBleGattCallback rxBleGatCallback) {
        return rxBleGattCallback.getOnPhyChanged().firstOrError();
    }

    @RequiresApi(26 /* Build.VERSION_CODES.O */)
    @Override
    protected boolean startOperation(BluetoothGatt bluetoothGatt) {
        bluetoothGatt.setPreferredPhy(this.txPhy, this.rxPhy, this.phyOptions);
        return true;
    }

    @Override
    public String toString() {
        return "PreferredPhyUpdateOperation{"
                + super.toString()
                + ", txPhy=" + this.txPhy
                + ", rxPhy=" + this.rxPhy
                + ", phyOptions=" + this.phyOptions
                + '}';
    }
}
