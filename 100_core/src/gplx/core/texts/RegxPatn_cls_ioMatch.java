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
package gplx.core.texts;
import gplx.langs.regxs.*;
import gplx.types.basics.utls.StringUtl;
public class RegxPatn_cls_ioMatch {
	public String Raw() {return raw;} private String raw;
	public boolean CaseSensitive() {return caseSensitive;} private boolean caseSensitive;
	public boolean Matches(String text) {
		text = StringUtl.CaseNormalize(caseSensitive, text);
		return Regx_adp_.Match(text, compiled);}    // WNT-centric: Io_mgr paths are case-insensitive;
	@Override public String toString() {return raw;}

	String compiled;
	public RegxPatn_cls_ioMatch(String raw, String compiled, boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
		this.raw = raw;
		compiled = StringUtl.CaseNormalize(caseSensitive, compiled);
		this.compiled = compiled;
	}
}
