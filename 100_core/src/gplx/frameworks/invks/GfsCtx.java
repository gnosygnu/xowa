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
package gplx.frameworks.invks;
import gplx.types.basics.utls.StringUtl;
public class GfsCtx {
	private GfsCtx(boolean deny) {
		this.deny = deny;
	}
	public Object MsgSrc() {return msgSrc;} public GfsCtx MsgSrc_(Object v) {msgSrc = v; return this;} private Object msgSrc;
	public boolean Fail_if_unhandled() {return fail_if_unhandled;} public GfsCtx Fail_if_unhandled_(boolean v) {fail_if_unhandled = v; return this;} private boolean fail_if_unhandled;
	public boolean Deny() {return deny;} private final boolean deny;
	public boolean Match(String k, String match) {return StringUtl.Eq(k, match);}
	public boolean MatchPriv(String k, String match) {return StringUtl.Eq(k, match);}
	public boolean MatchIn(String k, String... match) {return StringUtl.In(k, match);}

	public static final GfsCtx Instance = new_();
	public static GfsCtx new_() {return new GfsCtx(false);}
	public static GfsCtx NewDeny() {return new GfsCtx(true);}
}
