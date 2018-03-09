package com.thankjava.wqq.entity.sys;

import java.util.ArrayDeque;

import com.thankjava.toolkit.math.MathUtils;
import com.thankjava.wqq.consts.ConfigParams;

public class MonitoringData {

	private ArrayDeque<Long> frequencyData = new ArrayDeque<>(ConfigParams.MONITOR_THE_NUMBER_OF_DATA_SAMPLES);

	public void addData() {
		if (frequencyData.size() == ConfigParams.MONITOR_THE_NUMBER_OF_DATA_SAMPLES) {
			frequencyData.removeFirst();
			frequencyData.add(System.currentTimeMillis());
		} else {
			frequencyData.add(System.currentTimeMillis());
		}
	}

	public double getAverageValueOfOneSecound() {
		if (frequencyData.size() < ConfigParams.MONITOR_THE_NUMBER_OF_DATA_SAMPLES) {
			return 0;
		}
		long first = frequencyData.getFirst();
		long last = frequencyData.getLast();
		long timeSecound = (last - first) / 1000;
		return MathUtils.divide(frequencyData.size(), timeSecound);
	}
}
