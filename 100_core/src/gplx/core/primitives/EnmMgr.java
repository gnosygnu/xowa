/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.primitives; import gplx.*; import gplx.core.*;
import gplx.core.strings.*;
public class EnmMgr {
	public String BitRngSpr() {return bitRngSpr;} public EnmMgr BitRngSpr_(String val) {bitRngSpr = val; return this;} private String bitRngSpr = "+";
	public String Prefix() {return prefix;} public EnmMgr Prefix_(String val) {prefix = val; return this;} private String prefix;
	public int BitRngBgn() {return bitRngBgn;} public EnmMgr BitRngBgn_(int val) {bitRngBgn = val; return this;} int bitRngBgn = 1;
	public int BitRngEnd() {return bitRngEnd;} public EnmMgr BitRngEnd_(int val) {bitRngEnd = val; return this;} int bitRngEnd = Int_.Max_value;
	public void RegObj(int val, String raw, Object o) {
		rawRegy.Add(raw, val);
		valRegy.Add(val, raw);
		objRegy.Add(val, o);
	}
	public Object Get(int val) {return objRegy.Get_by(val);}
	public int GetVal(String raw) {
		String[] ary = String_.Split(raw, bitRngSpr);
		int rv = 0;
		for (int i = 0; i < ary.length; i++) {
			String term = String_.Trim(ary[i]);	// ex: key.ctrl + key.a
			if (prefix != null) term = String_.Replace(term, prefix, "");
			int cur = -1;
			if (String_.Has_at_bgn(term, "#"))
				cur = Int_.parse(String_.Mid(term, 1));
			else
				cur = Int_.cast(rawRegy.Get_by(term));			
			rv |= cur;
		}
		return rv;
	}
	public String GetStr(int v) {
		String_bldr sb = String_bldr_.new_();
		int cur = v, curModifier = bitRngBgn;
		while (true) {					
			if (cur == 0						
				|| curModifier > bitRngEnd					// loop until all Modifers have been shifted out
				) break;
			if ((cur & curModifier) == curModifier) {		// cur has Modifier
				AppendRaw(sb, curModifier);
				cur ^= curModifier;							// shift Modifier out
			}
			curModifier *= 2;								// move to next Modifier; relies on Shift, Ctrl, Alt enum values
		}
		if (cur > 0											// cur is non-Modifier; NOTE: check needed for args that are just a Modifier;
			|| sb.Count() == 0)								// cur is IptKey.None; cur == 0, but sb.length will also be 0
			AppendRaw(sb, cur);
		return sb.To_str();
	}
	void AppendRaw(String_bldr sb, int key) {
		String raw = (String)valRegy.Get_by(key);
		if (sb.Count() > 0) sb.Add(bitRngSpr);
		if (prefix != null) sb.Add(prefix);
		sb.Add(raw);
	}
	Hash_adp rawRegy = Hash_adp_.New(), valRegy = Hash_adp_.New(), objRegy = Hash_adp_.New();
	public static EnmMgr new_() {return new EnmMgr();} EnmMgr() {}
}
