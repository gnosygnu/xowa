package gplx.types.commons;
import gplx.types.errs.ErrUtl;
public class IntBldr {
	private int[] digits;
	private int idx, digitsLen;
	private int sign = 1;
	public IntBldr(int digitsMax) {
		this.digits = new int[digitsMax];
		this.digitsLen = digitsMax;
	}
	public void Clear() {idx = 0; sign = 1;}
	public int Add(char c) {
		if (idx > digitsLen - 1) throw ErrUtl.NewArgs("index is out of bound", "idx", idx, "len", digitsLen);
		digits[idx++] = ToIntByChar(c);
		return idx;
	}
	public int Add(int i) {
		if (idx > digitsLen - 1) throw ErrUtl.NewArgs("index is out of bound", "idx", idx, "len", digitsLen);
		digits[idx++] = i;
		return idx;
	}
	public int ToInt() {
		int rv = 0, exponent = 1;
		for (int i = idx - 1; i > -1; i--) {
			int digit = digits[i];
			if (digit < 0) throw ErrUtl.NewArgs("invalid char", "char", (char)-digits[i], "ascii", -digits[i]);
			rv += digit * exponent;
			exponent *= 10;
		}
		return sign * rv;
	}
	public int ToIntAndClear() {
		int rv = ToInt();
		this.Clear();
		return rv;
	}
	public int Parse(String raw) {
		ParseStr(raw);
		try {return ToInt();}
		catch (Exception exc) {throw ErrUtl.NewParse(int.class, raw);}
	}
	public boolean ParseTry(String raw) {
		ParseStr(raw);
		for (int i = 0; i < idx; i++)
			if (digits[i] < 0) return false;
		return true;
	}
	private void ParseStr(String raw) {
		this.Clear();
		int rawLength = raw.length();
		for (int i = 0; i < rawLength; i++) {
			char c = raw.charAt(i);
			if (i == 0 && c == '-')
				sign = -1;
			else
				Add(c);
		}
	}
	private int ToIntByChar(char c) {
		if      (c == '0') return 0; else if (c == '1') return 1; else if (c == '2') return 2; else if (c == '3') return 3; else if (c == '4') return 4;
		else if (c == '5') return 5; else if (c == '6') return 6; else if (c == '7') return 7; else if (c == '8') return 8; else if (c == '9') return 9;
		else               return -(int)c; // error handling: don't throw error; store ascii value in digit and throw if needed
	}
}
