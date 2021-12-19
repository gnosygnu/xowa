/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.security.algos.gplx_crypto;
import gplx.types.basics.utls.IntUtl;
public class Crc32 {
	int crc = 0; /** The crc data checksum so far. */
	public String CalcHash(byte[] ary) {
		Reset();
		Calc(ary);
		return IntUtl.ToStrHex(Crc());
	}
	public int Crc() {return crc;}//(int)(crc & 0xffffffffL);}
	public void Reset() {crc = 0;}
	public void Calc(int val) {
		int tmp = ~crc;
		tmp = CrcTable[(tmp ^ val) & 0xff] ^ (0x7FFFFFFF & tmp >>> 8);    //#<>(int)((uint)tmp  >> 8)~tmp >>> 8
		crc = ~tmp;
	}
	public void Calc(byte[] ary) {Calc(ary, 0, ary.length);}
	public void Calc(byte[] ary, int off, int len) {
		int tmp = ~crc;
		while (--len >= 0)
			tmp = CrcTable[(tmp ^ ary[off++]) & 0xff] ^ (0x7FFFFFFF & tmp >>> 8);//#<>(int)((uint)tmp  >> 8)~tmp >>> 8
		crc = ~tmp;
	}
	static int[] CrcTable = CrcTable_make();
	static int[] CrcTable_make () {
		int[] rv = new int[256];
		for (int i = 0; i < 256; i++) {
			int tmp = i;
			for (int k = 8; --k >= 0;) {
				if ((tmp & 1) != 0)
					tmp = (int)(0xedb88320 ^    tmp >>> 1);    //#<>(int)((uint)tmp  >> 1)~tmp >>> 1
				else
					tmp =                        tmp >>> 1;        //#<>(int)((uint)tmp  >> 1)~tmp >>> 1
			}
			rv[i] = tmp;
		}
		return rv;
	}
}
