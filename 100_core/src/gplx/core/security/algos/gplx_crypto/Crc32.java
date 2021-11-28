package gplx.core.security.algos.gplx_crypto; import gplx.Int_;
public class Crc32 {
	int crc = 0; /** The crc data checksum so far. */
	public String CalcHash(byte[] ary) {
		Reset();
		Calc(ary);
		return Int_.To_str_hex(Crc());
	}
	public int Crc() {return crc;}//(int)(crc & 0xffffffffL);}
	public void Reset() {crc = 0;}
	public void Calc(int val) {
		int tmp = ~crc;
		tmp = CrcTable[(tmp ^ val) & 0xff] ^ (0x7FFFFFFF & tmp >>> 8);	//#<>(int)((uint)tmp  >> 8)~tmp >>> 8
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
					tmp = (int)(0xedb88320 ^	tmp >>> 1);	//#<>(int)((uint)tmp  >> 1)~tmp >>> 1
				else
					tmp =						tmp >>> 1;		//#<>(int)((uint)tmp  >> 1)~tmp >>> 1
			}
			rv[i] = tmp;
		}
		return rv;
	}
}
