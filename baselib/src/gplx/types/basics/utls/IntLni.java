package gplx.types.basics.utls;
public class IntLni {
	public static final int
		MinValue   = Integer.MIN_VALUE,
		MaxValue   = Integer.MAX_VALUE,
		MaxValue31 = 2147483647,
		Neg1       = -1,
		Null       = MinValue,
		Base1      = 1, // for base-1 lists / arrays; EX: PHP; [a, b, c]; [1] => a
		Offset1    = 1, // common symbol for + 1 after current pos; EX: StringUtl.Mid(lhs + Offset1, rhs)
		Zero       = 0;

	public static String ToStr(int v) {return new Integer(v).toString();}
	public static boolean Between(int v, int lhs, int rhs) {
		int lhsComp = v == lhs ? 0 : (v < lhs ? -1 : 1);
		int rhsComp = v == rhs ? 0 : (v < rhs ? -1 : 1);
		return (lhsComp * rhsComp) != 1;    // 1 when v is (a) greater than both or (b) less than both
	}
	public static int ParseOr(String raw, int or) {
		// process args
		if (raw == null) return or;
		int rawLen = StringUtl.Len(raw);
		if (rawLen == 0) return or;

		// loop backwards from nth to 0th char
		int rv = 0, powerOf10 = 1;
		for (int idx = rawLen - 1; idx >= 0; idx--) {
			char cur = StringUtl.CharAt(raw, idx);
			int digit = -1;
			switch (cur) {
				// numbers -> assign digit
				case '0': digit = 0; break; case '1': digit = 1; break; case '2': digit = 2; break; case '3': digit = 3; break; case '4': digit = 4; break;
				case '5': digit = 5; break; case '6': digit = 6; break; case '7': digit = 7; break; case '8': digit = 8; break; case '9': digit = 9; break;

				// negative sign
				case '-':
					if (idx != 0) { // invalid if not 1st
						return or;
					}
					else { // is first; multiply by -1
						rv *= -1;
						continue;
					}

				// anything else
				default:
					return or;
			}
			rv += (digit * powerOf10);
			powerOf10 *= 10;
		}
		return rv;
	}
}
