package gplx.types.custom.brys.wtrs;
import gplx.langs.html.HtmlEntityCodes;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.BryLni;
public class HtmlBryBfr {
	public static byte[] EscapeHtml(byte[] src) {
		return EscapeHtml(null, BoolUtl.N, src, 0, src.length);
	}
	public static byte[] EscapeHtml(BryWtr bfr, boolean ws, byte[] src, int src_bgn, int src_end) {    // uses PHP rules for htmlspecialchars; REF.PHP:http://php.net/manual/en/function.htmlspecialchars.php
		boolean dirty = false;
		int cur = src_bgn;
		int prv = cur;
		boolean called_by_bry = bfr == null;

		// loop over chars
		while (true) {
			// if EOS, exit
			if (cur == src_end) {
				if (dirty) {
					bfr.AddMid(src, prv, src_end);
				}
				break;
			}

			// check current byte if escaped
			byte b = src[cur];
			byte[] escaped = null;
			switch (b) {
				case AsciiByte.Amp:        escaped = HtmlEntityCodes.AmpBry; break;
				case AsciiByte.Quote:      escaped = HtmlEntityCodes.QuoteBry; break;
				case AsciiByte.Apos:       escaped = HtmlEntityCodes.AposNumBry; break;
				case AsciiByte.Lt:         escaped = HtmlEntityCodes.LtBry; break;
				case AsciiByte.Gt:         escaped = HtmlEntityCodes.GtBry; break;
				case AsciiByte.Nl:         if (ws) escaped = HtmlEntityCodes.NlBry; break;
				case AsciiByte.Cr:         if (ws) escaped = HtmlEntityCodes.CrBry; break;
				case AsciiByte.Tab:        if (ws) escaped = HtmlEntityCodes.TabBry; break;
			}

			// not escaped; increment and continue
			if (escaped == null) {
				cur++;
				continue;
			}
			// escaped
			else {
				dirty = true;
				if (bfr == null) bfr = BryWtr.New();

				if (prv < cur)
					bfr.AddMid(src, prv, cur);
				bfr.Add(escaped);
				cur++;
				prv = cur;
			}
		}

		if (dirty) {
			if (called_by_bry)
				return bfr.ToBryAndClear();
			else
				return null;
		}
		else {
			if (called_by_bry) {
				if (src_bgn == 0 && src_end == src.length)
					return src;
				else
					return BryLni.Mid(src, src_bgn, src_end);
			}
			else {
				bfr.AddMid(src, src_bgn, src_end);
				return null;
			}
		}
	}
}
