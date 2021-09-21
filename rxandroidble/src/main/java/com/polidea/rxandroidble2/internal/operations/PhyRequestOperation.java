package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import androidx.annotation.RequiresApi;

import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;

import bleshadow.javax.inject.Inject;

import io.reactivex.Single;

@RequiresApi(26 /* Build.VERSION_CODES.O */)
public class PhyRequestOperation extends SingleResponseOperation<Integer> {

    private final int phy;

    @Inject
    PhyRequestOperation(
            RxBleGattCallback rxBleGattCallback,
            BluetoothGatt bluetoothGatt,
            TimeoutConfiguration timeoutConfiguration, int requestedPhy) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.ON_PHY_CHANGED, timeoutConfiguration);
        this.phy = requestedPhy;
    }

    @Override
    protected Single<Integer> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnPhyUpdate().firstOrError();
    }

    @Override
    protected boolean startOperation(BluetoothGatt bluetoothGatt) {
        bluetoothGatt.setPreferredPhy((0xff & phy), (0xff & (phy >> 8)), (0xff & (phy >> 16)));
        return true;
    }

    @Override
    public String toString() {
        return "PhyRequestOperation{"
                + super.toString()
                + ", phy=" + phy
                + '}';
    }
}
