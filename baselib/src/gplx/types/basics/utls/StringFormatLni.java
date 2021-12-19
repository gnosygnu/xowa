package gplx.types.basics.utls;
import gplx.types.basics.strings.bfrs.StringBuilderLni;
public class StringFormatLni {
	// use C# flavor ("a {0}") rather than Java format ("a %s"); also: (a) don't fail on format errors; (b) escape brackets by doubling
	private static final char FormatItmLhs = '{', FormatItmRhs = '}';
	public static String Format(String fmt, Object... args) {
		// init vars
		int argsLen = args.length;
		if (argsLen == 0) return fmt; // nothing to format
		int fmtLen = StringLni.Len(fmt);

		// loop vars
		int pos = 0; String argIdxStr = ""; boolean insideBrackets = false;
		StringBuilderLni bfr = new StringBuilderLni();
		while (pos < fmtLen) { // loop over every char; NOTE: UT8-SAFE b/c only checking for "{"; "}"
			char c = StringLni.CharAt(fmt, pos);
			if (insideBrackets) {
				if (c == FormatItmLhs) { // first FormatItmLhs is fake; add FormatItmLhs and whatever is in argIdxStr
					bfr.LniAddChar(FormatItmLhs);
					bfr.LniAdd(argIdxStr);
					argIdxStr = "";
				}
				else if (c == FormatItmRhs) { // itm completed
					int argsIdx = IntLni.ParseOr(argIdxStr, IntLni.MinValue);
					String itm = argsIdx != IntLni.MinValue && IntLni.Between(argsIdx, 0, argsLen - 1) // check (a) argsIdx is num; (b) argsIdx is in bounds
						? ObjectLni.ToStrOrNullMark(args[argsIdx]) // valid; add itm
						: FormatItmLhs + argIdxStr + FormatItmRhs; // not valid; just add String
					bfr.LniAdd(itm);
					insideBrackets = false;
					argIdxStr = "";
				}
				else
					argIdxStr += c;
			}
			else {
				if (c == FormatItmLhs || c == FormatItmRhs) {
					boolean posIsEnd = pos == fmtLen - 1;
					if (posIsEnd) // last char is "{" or "}" (and not insideBrackets); ignore and just ad
						bfr.LniAddChar(c);
					else {
						char next = StringLni.CharAt(fmt, pos + 1);
						if (next == c) {    // "{{" or "}}": escape by doubling
							bfr.LniAddChar(c);
							pos++;
						}
						else
							insideBrackets = true;
					}
				}
				else
					bfr.LniAddChar(c);
			}
			pos++;
		}
		if (StringLni.Len(argIdxStr) > 0) {    // unclosed bracket; add FormatItmLhs and whatever is in argIdxStr; ex: "{0"
			bfr.LniAddChar(FormatItmLhs);
			bfr.LniAdd(argIdxStr);
		}
		return bfr.ToStr();
	}
}
