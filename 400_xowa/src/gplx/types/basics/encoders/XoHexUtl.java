package gplx.types.basics.encoders;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class XoHexUtl extends HexUtl {
	public static void EncodeBfr(BryWtr bfr, byte[] src) {
		int src_len = src.length;
		for (int src_idx = 0; src_idx < src_len; ++src_idx) {
			byte src_byte = src[src_idx];
			bfr.AddByte(ToByteLcase(0xf & src_byte >>> 4));
			bfr.AddByte(ToByteLcase(0xf & src_byte));
		}
	}
	public static void WriteBfr(BryWtr bfr, boolean lcase, int val) {
		// count bytes
		int val_len = 0;
		int tmp = val;
		while (true) {
			tmp /= 16;
			val_len++;
			if (tmp == 0) break;
		}

		// fill bytes from right to left
		int hex_bgn = bfr.Len();
		bfr.AddByteRepeat(AsciiByte.Null, val_len);
		byte[] bry = bfr.Bry();
		for (int i = 0; i < val_len; i++) {
			int b = val % 16;
			bry[hex_bgn + val_len - i - 1] = lcase ? ToByteLcase(b) : ToByteUcase(b);
			val /= 16;
		}
	}
}
