/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.*; import gplx.core.envs.*;
import gplx.xowa.files.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.wbases.hwtrs.*; import gplx.xowa.xtns.pfuncs.ifs.*; import gplx.xowa.xtns.pfuncs.times.*; import gplx.xowa.xtns.pfuncs.ttls.*;
import gplx.xowa.parsers.uniqs.*; import gplx.xowa.parsers.hdrs.sections.*;
public class Xow_parser_mgr {
	private final    Xowe_wiki wiki; private final    Xop_tkn_mkr tkn_mkr;
	public Xow_parser_mgr(Xowe_wiki wiki) {
		this.wiki = wiki; this.tkn_mkr = wiki.Appe().Parser_mgr().Tkn_mkr();
		this.ctx = Xop_ctx.New__top(wiki);
		this.parser = Xop_parser.new_wiki(wiki);
	}
	public Xop_ctx					Ctx()				{return ctx;} private final    Xop_ctx ctx;
	public Xop_parser				Main()				{return parser;} private final    Xop_parser parser;
	public Scrib_core_mgr			Scrib()				{return scrib;} private final    Scrib_core_mgr scrib = new Scrib_core_mgr();
	public Xof_img_size				Img_size()			{return img_size;} private final    Xof_img_size img_size = new Xof_img_size();
	public Pfunc_ifexist_mgr		Ifexist_mgr()		{return ifexist_mgr;} private final    Pfunc_ifexist_mgr ifexist_mgr = new Pfunc_ifexist_mgr();
	public Xof_url_bldr				Url_bldr()			{return url_bldr;} private final    Xof_url_bldr url_bldr = Xof_url_bldr.new_v2();
	public List_adp					Time_parser_itms()	{return time_parser_itms;} private final    List_adp time_parser_itms = List_adp_.New();
	public Pft_func_formatdate_bldr Date_fmt_bldr()		{return date_fmt_bldr;} private final    Pft_func_formatdate_bldr date_fmt_bldr = new Pft_func_formatdate_bldr();
	public Gfo_number_parser		Pp_num_parser()		{return pp_num_parser;} private final    Gfo_number_parser pp_num_parser = new Gfo_number_parser().Ignore_space_at_end_y_();
	public int[]					Rel2abs_ary()		{return rel2abs_ary;} private final    int[] rel2abs_ary = new int[Pfunc_rel2abs.Ttl_max];
	public Xop_uniq_mgr				Uniq_mgr()			{return uniq_mgr;} private final    Xop_uniq_mgr uniq_mgr = new Xop_uniq_mgr();
	public boolean						Lst__recursing()	{return lst_recursing;} private boolean lst_recursing; public void	Lst__recursing_(boolean v) {lst_recursing = v;}
	public Bry_bfr					Wbase__time__bfr()  {return wbase__time__bfr;} private final    Bry_bfr wbase__time__bfr = Bry_bfr_.New();
	public Bry_fmtr					Wbase__time__fmtr() {return wbase__time__fmtr;} private final    Bry_fmtr wbase__time__fmtr = Bry_fmtr.new_();
	public Xop_section_mgr			Hdr__section_editable__mgr()		{return hdr__section_editable__mgr;} private final    Xop_section_mgr hdr__section_editable__mgr = new Xop_section_mgr();
	public Wdata_hwtr_msgs			Wbase__time__msgs() {
		if (wbase__time__msgs == null)
			wbase__time__msgs = Wdata_hwtr_msgs.new_(wiki.Msg_mgr());
		return wbase__time__msgs;
	}	private Wdata_hwtr_msgs wbase__time__msgs;
	public int						Tag__next_idx() {return ++tag_idx;} private int tag_idx; // NOTE:must be wiki-level variable, not page-level, b/c pre-compiled templates can reserve tag #s; PAGE:de.s:Seite:NewtonPrincipien.djvu/465 DATE:2015-02-03
	public void						Tmpl_stack_del() {--tmpl_stack_ary_len;}
	public boolean						Tmpl_stack_add(byte[] key) {
		for (int i = 0; i < tmpl_stack_ary_len; i++) {
			if (Bry_.Match(key, tmpl_stack_ary[i])) return false;
		}
		int new_len = tmpl_stack_ary_len + 1;
		if (new_len > tmpl_stack_ary_max) {
			tmpl_stack_ary_max = new_len * 2;
			tmpl_stack_ary = (byte[][])Array_.Resize(tmpl_stack_ary, tmpl_stack_ary_max);
		}
		tmpl_stack_ary[tmpl_stack_ary_len] = key;
		tmpl_stack_ary_len = new_len;
		return true;
	}	private byte[][] tmpl_stack_ary = Bry_.Ary_empty; private int tmpl_stack_ary_len = 0, tmpl_stack_ary_max = 0;
	public Pfunc_anchorencode_mgr Anchor_encoder_mgr__dflt_or_new(Xop_ctx calling_ctx) {
		// lazy-instantiate anchor_encoder_mgr
		if (anchor_encoder_mgr == null) anchor_encoder_mgr = new Pfunc_anchorencode_mgr(wiki);

		// default to member instance
		Pfunc_anchorencode_mgr rv = anchor_encoder_mgr;
		// if used, create a new one; only occurs if {{anchorencode}} is nested
		if (rv.Used()) rv = new Pfunc_anchorencode_mgr(wiki);
		rv.Used_(Bool_.Y);
		return rv;
	}	private Pfunc_anchorencode_mgr anchor_encoder_mgr;
	public void Init_by_wiki() {
		hdr__section_editable__mgr.Init_by_wiki(wiki);
	}
	public void Parse(Xoae_page page, boolean clear) {	// main parse method; should never be called nested
		// init
		if (!Env_.Mode_testing()) wiki.Init_assert();	// needed for html_server?
		tmpl_stack_ary = Bry_.Ary_empty;
		tmpl_stack_ary_len = tmpl_stack_ary_max = 0;
		uniq_mgr.Clear();

		scrib.When_page_changed(page);	// notify scribunto about page changed
		ctx.Page_(page);
		Xop_root_tkn root = ctx.Tkn_mkr().Root(page.Db().Text().Text_bry());
		if (clear) {page.Clear_all();}
		Xoa_ttl ttl = page.Ttl();
		if (	Xow_page_tid.Identify(wiki.Domain_tid(), ttl.Ns().Id(), ttl.Page_db()) == Xow_page_tid.Tid_wikitext) {	// only parse page if wikitext; skip .js, .css, Module; DATE:2013-11-10
			byte[] data_raw = page.Db().Text().Text_bry();
			parser.Parse_text_to_wdom(root, ctx, tkn_mkr, data_raw , Xop_parser_.Doc_bgn_bos);
		}
		page.Root_(root);
		root.Data_htm_(root.Root_src());
	}
}
