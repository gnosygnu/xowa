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
package gplx.xowa.addons.bldrs.exports.splits.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
public class Split_cfg implements Gfo_invk {
	public boolean				Force_rebuild() {return force_rebuild;} private boolean force_rebuild;
	public long				Trg_max() {return trg_max;} private long trg_max = 32 * Io_mgr.Len_mb;
	public Split_ns_itm[]	Ns_itms() {return ns_itms;} private Split_ns_itm[] ns_itms;
	public int				Loader_rows() {return loader_rows;} private int loader_rows = 10000;
	public Split_type_cfg	Text() {return text;} private final    Split_type_cfg text = new Split_type_cfg("text", 1000);
	public Split_type_cfg	Html() {return html;} private final    Split_type_cfg html = new Split_type_cfg("html", 2000);
	public Split_type_cfg	File() {return file;} private final    Split_type_cfg file = new Split_type_cfg("file", 3000);
	public void Ns_itms_(int[] ary) {
		List_adp list = List_adp_.New();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			list.Add(new Split_ns_itm(ary[i]));
		}
		this.ns_itms = (Split_ns_itm[])list.To_ary_and_clear(Split_ns_itm.class);
	}
	
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__ns_ids_))			this.Ns_itms_(Int_.Ary_parse(m.ReadStr("v"), "|"));
		else if	(ctx.Match(k, Invk__loader_rows_))		this.loader_rows = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__force_rebuild_))	this.force_rebuild = m.ReadBool("v");
		else if	(ctx.Match(k, Invk__trg_max_))			this.trg_max = m.ReadLong("v");
		else if	(ctx.Match(k, Invk__text))				return text;
		else if	(ctx.Match(k, Invk__html))				return html;
		else if	(ctx.Match(k, Invk__file))				return file;
		else											return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__ns_ids_ = "ns_ids_", Invk__loader_rows_ = "loader_rows_", Invk__trg_max_ = "trg_max_", Invk__force_rebuild_ = "force_rebuild_"
	, Invk__text = "text", Invk__html = "html", Invk__file = "file"
	;
}
