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
package gplx.types.commons;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
public class GfoDateParser {
	private static int[] MaxLens = new int[]
	{ 4 // y
	, 2 // M
	, 2 // d
	, 2 // H
	, 2 // m
	, 2 // s
	, 3 // S (fracs)
	, 0 // (TimeZone)
	};
	private final IntBldr intBldr = new IntBldr(4);
	public int[] ParseIso8651Like(String raw_str) {
		int[] tmpRv = new int[7];
		ParseIso8651Like(tmpRv, raw_str);
		return tmpRv;
	}
	public void ParseIso8651Like(int[] rv, String rawStr) {
		byte[] rawBry = BryUtl.NewU8(rawStr);
		ParseIso8651Like(rv, rawBry, 0, rawBry.length);
	}
	public void ParseIso8651Like(int[] rv, byte[] src, int bgn, int end) {
		/*
			1981-04-05T14:30:30.000-05:00    ISO 8601: http://en.wikipedia.org/wiki/ISO_8601
			1981.04.05 14.30.30.000-05.00    ISO 8601 loose
			99991231_235959.999              gplx
			yyyy-MM-ddTHH:mm:ss.fffzzzz      Format String
		*/
		// clear array
		int rvLen = rv.length;
		for (int i = 0; i < rvLen; i++)
			rv[i] = 0;

		int pos = bgn, rvIdx = 0, intLen = 0, maxLen = MaxLens[rvIdx];
		while (true) {
			int intVal = -1;
			byte b = pos < end ? src[pos] : AsciiByte.Null;
			switch (b) {
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
					intVal = b - AsciiByte.Num0;    // convert ascii to val; EX: 49 -> 1
					intLen = intBldr.Add(intVal);
					break;
			}
			if (  (intVal == -1 && intLen > 0)    // char is not number && number exists (ignore consecutive delimiters: EX: "1981  01")
				|| intLen == maxLen) {            // maxLen reached; necessary for gplxFormat
				rv[rvIdx++] = intBldr.ToIntAndClear();
				if (rvIdx == 7)                   // past frac; break;
					break;
				intLen = 0;
				maxLen = MaxLens[rvIdx];
			}
			if (pos == end)
				break;
			++pos;
		}
	}       
}
