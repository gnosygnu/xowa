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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_urlfunc extends Pf_func_base {	// EX: {{lc:A}} -> a
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] argx = Eval_argx(ctx, src, caller, self); if (argx.length == 0) return;
		byte[] qry_arg = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self.Args_len(), 0);
		UrlString(ctx, tid, encode, argx, bfr, qry_arg);
	}
	public static void UrlString(Xop_ctx ctx, byte tid, boolean encode, byte[] src, Bry_bfr trg, byte[] qry_arg) {
		Xowe_wiki wiki = ctx.Wiki();
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, src, 0, src.length);
		if (ttl == null) return; // NOTE: guard against null ttl; EX: {{fullurl:{{transclude|{{{1}}}}}|action=edit}} ->  {{fullurl:Template:{{{1}}}|action=edit}} -> Template:{{{1}}} will be a null ttl
		byte[] ttl_ary = ttl.Full_url();// NOTE: Full_url does encoding; don't encode again
		Xow_xwiki_itm xwiki = ttl.Wik_itm();
		if (xwiki != null) {	// xwiki exists; add as //commons.wikimedia.org/wiki/A#b?c=d
			if (tid == Tid_canonical)
				trg.Add(Xoh_href_.Bry__https);									//	"https://"
			else
				trg.Add(gplx.core.net.Gfo_protocol_itm.Bry_relative);			//	"//"
			trg.Add(xwiki.Domain_bry())											//  "commons.wikimedia.org"
				.Add(Xoh_href_.Bry__wiki)										//	"/wiki/"
				.Add_mid(ttl_ary, xwiki.Key_bry().length + 1, ttl_ary.length);	//	"A#b?c=d"; +1 for colon after "commons:"; NOTE: ugly way of getting rest of url, but ttl currently does not have Full_wo_wiki
		}
		else {
			Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b512();
			try {
				switch (tid) {
					case Tid_local:		tmp_bfr.Add(ctx.Wiki().Props().Article_path());break;
					case Tid_full:		tmp_bfr.Add(Bry_relative_url).Add(ctx.Wiki().Props().Server_name()).Add(ctx.Wiki().Props().Article_path()); break;
					case Tid_canonical:	tmp_bfr.Add(ctx.Wiki().Props().Server()).Add(ctx.Wiki().Props().Article_path()); break;
					default:			throw Err_.new_unhandled(tid);
				}
				tmp_bfr.Add(ttl_ary);
				trg.Add_bfr_and_clear(tmp_bfr);
			} finally {tmp_bfr.Mkr_rls();}
		}
		if (qry_arg != Bry_.Empty) trg.Add_byte(Byte_ascii.Question).Add(qry_arg);
	}
	public Pfunc_urlfunc(int id, byte tid, boolean encode) {this.id = id; this.tid = tid; this.encode = encode;} private byte tid; boolean encode;
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_urlfunc(id, tid, encode).Name_(name);}
	public static final byte Tid_local = 0, Tid_full = 1, Tid_canonical = 2;
	public static final    byte[] Bry_relative_url = Bry_.new_a7("//");
}	
/*
NOTE: Both fullurle: and localurle: performed additional character escaping on the resulting link, but no example is known where that still has any additional effect.
http://meta.wikimedia.org/wiki/Help:Parser_function
*/