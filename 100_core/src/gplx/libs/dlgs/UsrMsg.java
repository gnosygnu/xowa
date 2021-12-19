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
package gplx.libs.dlgs;
import gplx.core.envs.*;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.GfoMsg_;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
public class UsrMsg {
	public int VisibilityDuration() {return visibilityDuration;} public UsrMsg VisibilityDuration_(int v) {visibilityDuration = v; return this;} int visibilityDuration = 3000;
	public String Hdr() {return hdr;} public UsrMsg Hdr_(String val) {hdr = val; return this;} private String hdr;
	public Ordered_hash Args() {return args;} Ordered_hash args = Ordered_hash_.New();
	public UsrMsg Add(String k, Object v) {
		args.Add(k, KeyVal.NewStr(k, v));
		return this;
	}
	public UsrMsg Add_if_dupe_use_nth(String k, Object v) {
		args.AddIfDupeUseNth(k, KeyVal.NewStr(k, v));
		return this;
	}
	public String XtoStrSingleLine()    {return To_str(" ");}
	public String To_str()                {return To_str(Op_sys.Cur().Nl_str());}
	String To_str(String spr) {
		if (hdr == null) {
			GfoMsg m = GfoMsg_.new_cast_(cmd);
			for (int i = 0; i < args.Len(); i++) {
				KeyVal kv = (KeyVal)args.GetAt(i);
				m.Add(kv.KeyToStr(), kv.Val());
			}
			return ObjectUtl.ToStrOrNullMark(invk.Invk(GfsCtx.Instance, 0, cmd, m));
		}
		String_bldr sb = String_bldr_.new_();
		sb.Add(hdr).Add(spr);
		for (int i = 0; i < args.Len(); i++) {
			KeyVal kv = (KeyVal)args.GetAt(i);
			sb.AddSprUnlessFirst("", " ", i);
			sb.AddFmt("{0}={1}", kv.KeyToStr(), kv.Val(), spr);
		}
		return sb.ToStr();
	}       
		public static UsrMsg fmt_(String hdr, Object... ary) {
		UsrMsg rv = new UsrMsg();
		rv.hdr = StringUtl.Format(hdr, ary);
		return rv;
	}   UsrMsg() {}
		public static UsrMsg new_(String hdr) {
		UsrMsg rv = new UsrMsg();
		rv.hdr = hdr;
		return rv;
	}
		public static UsrMsg invk_(Gfo_invk invk, String cmd) {
		UsrMsg rv = new UsrMsg();
		rv.invk = invk;
		rv.cmd = cmd;
		return rv;
	}   Gfo_invk invk; String cmd;
}
