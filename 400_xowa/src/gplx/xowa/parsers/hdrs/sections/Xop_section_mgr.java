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
package gplx.xowa.parsers.hdrs.sections;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoa_url;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.core.htmls.tidy.Xow_tidy_mgr_interface;
public class Xop_section_mgr implements Gfo_invk {
	private Xoae_app app; private Xowe_wiki wiki;
	private Xow_tidy_mgr_interface tidy_mgr;
	private final BryWtr tmp_bfr = BryWtr.New();
	private byte[] bry__edit_text;
	private final BryFmt fmt__edit_hint = BryFmt.New("")
	, fmt__section_editable = BryFmt.Auto_nl_apos
	( "<span class='mw-editsection'><span class='mw-editsection-bracket'>[</span><a href='/wiki/~{page_ttl}?action=edit&section_key=~{section_key}' title='~{edit_hint}' class='xowa-hover-off'>~{edit_text}</a><span class='mw-editsection-bracket'>]</span></span>"
	)
	;
	public boolean Enabled() {return enabled;} private boolean enabled;
	public void Init_by_wiki(Xowe_wiki wiki) {
		this.app = wiki.Appe();
		this.wiki = wiki;
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__section_editing__enabled);
		this.tidy_mgr = wiki.Html_mgr().Tidy_mgr();
	}
	public byte[] Slice_section(Xoa_url url, Xoa_ttl ttl, byte[] src) {
		// return orig if section_editing not enabled
		if (!enabled) return src;

		// return orig if section_key not in qargs
		byte[] section_key = url.Qargs_mgr().Get_val_bry_or(Qarg__section_key, null);
		if (section_key == null) return src;

		// parse wikitext into list of headers
		Xop_section_list section_list = new Xop_section_list().Parse(wiki, tidy_mgr, url, src);
		byte[] rv = section_list.Slice_bry_or_null(section_key);
		if (rv == null) {
			app.Gui_mgr().Kit().Ask_ok("", "", StringUtl.Format("Section extraction failed!\nPlease do not edit this page else data will be lost!!\n\nwiki={0}\npage={1}\nsection={2}", url.Wiki_bry(), ttl.Full_db(), section_key));
			throw ErrUtl.NewArgs("section_key not found", "wiki", url.Wiki_bry(), "page", ttl.Full_db(), "section_key", section_key);
		}
		return rv;
	}
	public byte[] Merge_section(Xoa_url url, byte[] edit, byte[] orig) {
		// return edit if not enabled
		if (!enabled) return edit;

		// return edit if section_key not in qargs
		byte[] section_key = url.Qargs_mgr().Get_val_bry_or(Qarg__section_key, null);
		if (section_key == null) return edit;

		// parse orig
		Xop_section_list section_list = new Xop_section_list().Parse(wiki, tidy_mgr, url, orig);
		byte[] rv = section_list.Merge_bry_or_null(section_key, edit);
		if (rv == null)
			throw ErrUtl.NewArgs("could not merge section_key", "page", url.To_str(), "section_key", section_key);
		return rv;
	}
	public void Write_html(BryWtr bfr, byte[] page_ttl, byte[] section_key, byte[] section_hint) {
		if (bry__edit_text == null) {	// LAZY: cannot call in Init_by_wiki b/c of circularity; section_mgr is init'd by parser_mgr which is init'd before msg_mgr which is used below
			this.bry__edit_text = wiki.Msg_mgr().Val_by_key_obj("editlink");
			this.fmt__edit_hint.Fmt_(StringUtl.NewU8(wiki.Msg_mgr().Val_by_key_obj("editsectionhint")));
		}

		section_key = wiki.Parser_mgr().Uniq_mgr().Parse(section_key);	// need to swap out uniqs for Math; DATE:2016-12-09
		byte[] edit_hint = fmt__edit_hint.Bld_many_to_bry(tmp_bfr, section_hint);
		fmt__section_editable.Bld_many(bfr, page_ttl, section_key, edit_hint, bry__edit_text);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__section_editing__enabled)) enabled = m.ReadBool("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}

	public static final byte[] Bry__meta = BryUtl.NewA7("<!--xo_meta|section_edit|");
	public static final int Len__meta = Bry__meta.length;
	private static final byte[] Qarg__section_key = BryUtl.NewU8("section_key");
	private static final String Cfg__section_editing__enabled = "xowa.wiki.edit.section.enabled";
}
