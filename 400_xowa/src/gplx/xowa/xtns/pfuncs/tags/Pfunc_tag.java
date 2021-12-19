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
package gplx.xowa.xtns.pfuncs.tags;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.langs.kwds.Xol_kwd_grp_;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.tmpls.Xot_invk;
import gplx.xowa.parsers.xndes.Tag_html_mkr;
import gplx.xowa.parsers.xndes.Tag_html_mkr_;
import gplx.xowa.parsers.xndes.Tag_html_wkr;
import gplx.xowa.parsers.xndes.Xop_xnde_tag;
import gplx.xowa.xtns.pfuncs.Pf_func;
import gplx.xowa.xtns.pfuncs.Pf_func_;
import gplx.xowa.xtns.pfuncs.Pf_func_base;
public class Pfunc_tag extends Pf_func_base {// REF:/includes/parser/CoreParserFunctions.php|tagObj
	@Override public int Id() {return Xol_kwd_grp_.Id_misc_tag;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_tag().Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}

	// make <xnde> based on {{#tag}}; EX: {{#tag:ref|a|name=1}} -> <ref name='1'>a</ref>
	@Override public void Func_evaluate(BryWtr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		// get tag_name
		byte[] tag_name = Eval_argx(ctx, src, caller, self);
		if (tag_name.length == 0) return; // EX: {{#tag}}

		// get html_mkr; similar to MW "call_user_func_array"
		Tag_html_mkr html_mkr = null;
		Xop_xnde_tag xnde_tkn = ctx.Xnde_tag_regy().Get_tag_in_tmpl(tag_name);
		if (xnde_tkn != null) html_mkr = xnde_tkn.Html_mkr();
		if (html_mkr == null) html_mkr = Tag_html_mkr_.Basic(true);

		// build html
		Xowe_wiki wiki = ctx.Wiki();
		Tag_html_wkr html_wkr = html_mkr.Tag__create(wiki, ctx);
		try {
			// process name
			html_wkr.Tag__process_name(tag_name);

			// process args; EX: "|a=1|b=2" -> "a='1' b='2'"
			int args_len = self.Args_len();
			Eval_attrs(ctx, wiki, caller, self, src, args_len, html_wkr);

			// process body
			byte[] body = BryUtl.Empty;
			if (args_len > 0) {
				body = Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, args_len, 0);
				// REF.MW: $inner = $frame->expand( array_shift( $args ) );
				// body = Xop_parser_.Parse_text_to_html(wiki, ctx, ctx.Page(), body, false);
			}
			html_wkr.Tag__process_body(body);

			// add to UNIQ hash; DATE:2017-03-31
			byte[] val = html_wkr.Tag__build(ctx.Wiki(), ctx);
			byte[] key = wiki.Parser_mgr().Uniq_mgr().Add(BoolUtl.Y, tag_name, val);
			bfr.Add(key);
		}
		finally {
			html_wkr.Tag__rls();
		}
	}
	private void Eval_attrs(Xop_ctx ctx, Xowe_wiki wiki, Xot_invk caller, Xot_invk self, byte[] src, int args_len, Tag_html_wkr html_wkr) {
		if (args_len <= 1) return; // NOTE: 1 b/c 0 is innerText
		BryWtr atr_bfr = wiki.Utl__bfr_mkr().GetB512();
		try {
			byte[][] kvp = new byte[2][];
			for (int i = 1; i < args_len; i++) {
				// extract kv
				if (!Pf_func_.Eval_arg_to_kvp(kvp, ctx, src, caller, self, args_len, atr_bfr, i)) // skip empty atrs
					continue;

				// strip flanking-matching quotes; EX: "'abc'" -> "abc"; REF.MW:preg_match( '/^(?:["\'](.+)["\']|""|\'\')$/s', $value, $m )
				byte[] atr_val = kvp[1];
				int atr_len = BryUtl.Len(atr_val);
				if (atr_len > 1) {
					int atr_bgn = 0;
					boolean trim_bgn = false, trim_end = false;
					switch (atr_val[0]) {
						case AsciiByte.Quote:
						case AsciiByte.Apos:
							atr_bgn++;
							trim_bgn = true;
							break;
					}
					int atr_end = atr_len - 1;
					switch (atr_val[atr_end]) {
						case AsciiByte.Quote:
						case AsciiByte.Apos:
							trim_end = true;
							break;
					}
					if (trim_bgn && trim_end)
						kvp[1] = BryLni.Mid(atr_val, atr_bgn, atr_end);
				}

				// process attr
				html_wkr.Tag__process_attr(kvp[0], kvp[1]);
			}
		} finally {
			atr_bfr.MkrRls();
		}
	}
}
