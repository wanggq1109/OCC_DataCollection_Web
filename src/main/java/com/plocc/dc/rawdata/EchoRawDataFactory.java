package com.plocc.dc.rawdata;

public class EchoRawDataFactory implements RawDataFactory {
	private RawData rawData;

	public EchoRawDataFactory(RawData rawData) {
		this.rawData = rawData;
	}

	public RawData create() {
		return rawData;
	}
}
