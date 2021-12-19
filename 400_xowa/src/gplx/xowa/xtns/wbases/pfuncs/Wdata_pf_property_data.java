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
package gplx.xowa.xtns.wbases.pfuncs;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.tmpls.Arg_itm_tkn;
import gplx.xowa.parsers.tmpls.Arg_nde_tkn;
import gplx.xowa.parsers.tmpls.Xot_invk;
public class Wdata_pf_property_data {
	public Wdata_pf_property_data(byte[] of, byte[] q, byte[] from) {
		this.Of = of; this.Q = q; this.From = from;
	}
	public final byte[]		Of;			// EX: "earth"
	public final byte[]		Q;			// EX: "Q123"
	public final byte[]		From;		// EX: "p2"

	public static Wdata_pf_property_data Parse(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self) {
		BryWtr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().GetB512();
		byte[] of = null, q = null, from = null;
		int args_len = self.Args_len();
		for (int i = 0; i < args_len; ++i) {
			Arg_nde_tkn nde = self.Args_get_by_idx(i);

			// get key; EX: "of=", "q=", "from="
			Arg_itm_tkn nde_key = nde.Key_tkn();
			int key_bgn = nde_key.Src_bgn(), key_end = nde_key.Src_end();
			if (key_bgn == key_end && key_bgn == -1) continue;	// null arg; ignore, else will throw warning below; EX: {{#property:p1|}}; DATE:2013-11-15

			// trim ws; ISSUE#:361; DATE:2019-02-11
			key_bgn = BryFind.FindFwdWhileSpaceOrTab(src, key_bgn, key_end);
			key_end = BryFind.FindBwdWhileSpaceOrTab(src, key_end, key_bgn);

			// get key_tid
			byte key_tid = atrs_hash.Get_as_byte_or(src, key_bgn, key_end, ByteUtl.MaxValue127);
			switch (key_tid) {
				case ByteUtl.MaxValue127:
					ctx.App().Usr_dlg().Warn_many("", "", "unknown key for property: ~{0} ~{1}", StringUtl.NewU8(ctx.Page().Ttl().Full_txt()), StringUtl.NewU8(src, self.Src_bgn(), self.Src_end()));
					continue;
				case Tid__id:	// same as "not-found", but don't warn; 
					continue;
			}

			// get val
			nde.Val_tkn().Tmpl_evaluate(ctx, src, caller, tmp_bfr);	// NOTE: changed from self to caller; DATE:2016-03-16
			byte[] val = tmp_bfr.ToBryAndClear();
			switch (key_tid) {
				case Tid__of:		of = val; break;
				case Tid__q:		q = val; break;
				case Tid__from:		from = val; break;
				default:			throw ErrUtl.NewUnhandled(key_tid);
			}
		}
		tmp_bfr.MkrRls();
		return new Wdata_pf_property_data(of, q, from);
	}
	private static final byte Tid__of = 0, Tid__q = 1, Tid__from = 2, Tid__id = 3;
	private static final Hash_adp_bry atrs_hash = Hash_adp_bry.ci_a7()
	.Add_str_byte("of"		, Tid__of)
	.Add_str_byte("q"		, Tid__q)
	.Add_str_byte("from"	, Tid__from)	// "from" is alias as "q" except it seems to handle properties; EX: {{#property:p1|from=Q2}} == {{#property:p1|q=Q2}}; EX: {{#property:p1|from=p2}}
	.Add_str_byte("id"		, Tid__id)		// "id" has no effect, but appears in articles; ignore and don't warn; EX:{{#property:P277|id=Q1322933}} PAGE:en.w:Symfony; DATE:2016-08-13
	;
} 
