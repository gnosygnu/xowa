package gplx.types.custom.brys.wtrs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.StringLni;
public class BryWtrLni {
	public BryWtrLni(int bfrMax) {
		this.bfr = new byte[bfrMax];
		this.bfrMax = bfrMax;
	}
	public byte[] Bry() {return bfr;} protected byte[] bfr;
	public int Len() {return bfrLen;} public void LenSet(int v) {this.bfrLen = v;} protected int bfrLen;
	public int Max() {return bfrMax;} protected int bfrMax;
	public boolean HasNone() {return bfrLen == 0;}
	public boolean HasSome() {return bfrLen > 0;}
	public void Resize(int v) {
		this.bfrMax = v;
		this.bfr = BryLni.Resize(bfr, 0, v);
	}
	public final void LniAdd(byte[] val) {
		int valLen = val.length;
		if (bfrLen + valLen > bfrMax) Resize((bfrMax + valLen) * 2);
		BryLni.CopyTo(val, 0, valLen, bfr, bfrLen);
		bfrLen += valLen;
	}
	public final void LniAddByte(byte val) {
		int newLen = bfrLen + 1;
		if (newLen > bfrMax) Resize(bfrLen * 2);
		bfr[bfrLen] = val;
		bfrLen = newLen;
	}
	public final void LniAddStrU8(String str) {
		int strLen = str.length();
		int bryLen = BryLni.NewU8ByLen(str, strLen);
		if (bfrLen + bryLen > bfrMax) Resize((bfrMax + bryLen) * 2);
		BryLni.NewU8Write(str, strLen, bfr, bfrLen);
		bfrLen += bryLen;
	}
	public String ToStr() {return StringLni.NewU8(ToBry());}
	public byte[] ToBry() {return bfrLen == 0 ? BryLni.Empty : BryLni.Mid(bfr, 0, bfrLen);}
}
