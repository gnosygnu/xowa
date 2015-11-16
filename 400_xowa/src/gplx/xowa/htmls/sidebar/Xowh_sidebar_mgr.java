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
package gplx.xowa.htmls.sidebar; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.btries.*; import gplx.core.brys.fmtrs.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.parsers.lnkis.*;
public class Xowh_sidebar_mgr implements GfoInvkAble {
	public Xowh_sidebar_mgr(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;		
	public int Grps_len() {return grps.Count();} List_adp grps = List_adp_.new_();
	public Xowh_sidebar_itm Grps_get_at(int i) {return (Xowh_sidebar_itm)grps.Get_at(i);}
	public byte[] Html_bry() {return html_bry;} private byte[] html_bry;
	public void Init() {
		try {
			Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
			Xol_msg_itm sidebar_msg = Xol_msg_mgr_.Get_msg_itm(bfr, wiki, wiki.Lang(), CONST_sidebar_ttl);
			if (	sidebar_msg.Src() == Xol_msg_itm.Src_missing
				||	(	sidebar_msg.Src() == Xol_msg_itm.Src_lang
					&&	wiki.Domain_tid() == gplx.xowa.wikis.domains.Xow_domain_tid_.Int__home
				)) {
				html_bry = Bry_.Empty;
				bfr.Mkr_rls();
				return;
			}
			Bry_bfr comment_bfr = wiki.Utl__bfr_mkr().Get_b512();
			Parse(bfr, comment_bfr, sidebar_msg.Val());
			Bld_html(bfr);
			html_bry = bfr.To_bry_and_rls();
			comment_bfr.Clear_and_rls();
		} catch (Exception e) {
			wiki.Appe().Usr_dlg().Warn_many(GRP_KEY, "sidebar.init", "sidebar init failed: ~{0} ~{1}", wiki.Domain_str(), Err_.Message_gplx_full(e));
			html_bry = Bry_.Empty;
		}
	}
	private static boolean Ignore(byte[] wiki, byte[] item) {
		return 
			(	Bry_.Eq(wiki, Ignore_wiki_ess)			// occurs in 2014-02-03 dump; ignored by MW
			&&	Bry_.Eq(item, Ignore_item_ess_random)
			);
	}	private static byte[] Ignore_wiki_ess = Bry_.new_a7("es.wikisource.org"), Ignore_item_ess_random = Bry_.new_u8("special:Random/Página djvu");
	public void Parse(Bry_bfr bfr, Bry_bfr comment_bfr, byte[] src) {
		byte[][] lines = Bry_split_.Split(src, Byte_ascii.Nl);
		int lines_len = lines.length;
		Url_encoder id_encoder = Xoa_app_.Utl__encoder_mgr().Id();
		Xowh_sidebar_itm cur_grp = null;
		Xop_link_parser link_parser = new Xop_link_parser();
		for (int i = 0; i < lines_len; i++) {
			byte[] line = lines[i]; int line_len = line.length;
			if	(line_len == 0) continue;					// skip blank lines
			if	(line[0] != Byte_ascii.Star) continue;	// skip non-list items; must begin with "*"
			byte tid = line[1] == Byte_ascii.Star ? Xowh_sidebar_itm.Tid_itm : Xowh_sidebar_itm.Tid_grp;
			byte[] bry = Bry_.Trim(line, tid, line_len);	// trim *, **; note that tid indicates # of asterisks
			bry = gplx.langs.htmls.Html_utl.Del_comments(comment_bfr, bry);	// strip comments; DATE:2014-03-08
			if (ignore_trie.Match_bgn(bry, 0, bry.length) != null) continue; // ignore SEARCH, TOOLBOX, LANGUAGES
			int pipe_pos = Bry_find_.Find_fwd(bry, Byte_ascii.Pipe);
			byte[] text_key = tid == Xowh_sidebar_itm.Tid_grp ? bry : Bry_.Mid(bry, pipe_pos + 1, bry.length);	// get text_key; note that grp is entire bry, while itm is after |
			byte[] text_val = Resolve_key(text_key);
			byte[] id = id_encoder.Encode(bfr.Add(CONST_id_prefix), text_key).To_bry_and_clear();	// build id; "n-encoded_id"
			Xowh_sidebar_itm cur_itm = new Xowh_sidebar_itm(tid).Id_(id).Text_(text_val);
			wiki.Msg_mgr().Val_html_accesskey_and_title(id, bfr, cur_itm);
			if (tid == Xowh_sidebar_itm.Tid_grp) {
				cur_grp = cur_itm;
				grps.Add(cur_grp);
			}
			else {
				if (pipe_pos == Bry_find_.Not_found) {		// not of format of "href|main"; (EX: "href_only")
					if (!Ignore(wiki.Domain_bry(), bry))	// suppress warning if ignored; DATE:2014-02-11
						wiki.Appe().Usr_dlg().Warn_many(GRP_KEY, "parse.line.missing_text", "sidebar item is missing pipe; only href is available; item will be hidden: item=~{0}", String_.new_u8(bry));
					continue;
				}
				byte[] href_key = Bry_.Mid(bry, 0, pipe_pos);
				byte[] href_val = Resolve_key(href_key);
				href_val = link_parser.Parse(bfr, tmp_url, wiki, href_val, Bry_.Empty);
				cur_itm.Href_(href_val);
				if (cur_grp == null)	// handle null_ref; should only occur for tests
					grps.Add(cur_itm);
				else
					cur_grp.Itms_add(cur_itm);
			}
		}
	}	private Xoa_url tmp_url = Xoa_url.blank();
	public void Bld_html(Bry_bfr bfr) {
		int len = grps.Count();
		for (int i = 0; i < len; i++) {
			Xowh_sidebar_itm grp = (Xowh_sidebar_itm)grps.Get_at(i);
			html_grp_fmtr_arg.Grp_(wiki, grp, html_itm_fmtr);
			html_grp_fmtr.Bld_bfr_many(bfr, grp.Id(), grp.Text(), html_grp_fmtr_arg);
		}
	}	private Xowh_sidebar_grp_fmtr_arg html_grp_fmtr_arg = new Xowh_sidebar_grp_fmtr_arg();
	private Bry_fmtr html_grp_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl
	(	"<div class=\"portal\" id='~{grp_id}'>"
	,	"  <h3>~{grp_text}</h3>"
	,	"  <div class=\"body\">"
	,	"    <ul>~{itms}"
	,	"    </ul>"
	,	"  </div>"
	,	"</div>")
	,	"grp_id", "grp_text", "itms");
	private Bry_fmtr html_itm_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	(	""
	,	"      <li id=\"~{itm_id}\"><a href=\"~{itm_href}\"~{itm_cls}~{itm_accesskey_and_title}>~{itm_text}</a></li>"
	),	"itm_id", "itm_href", "itm_cls", "itm_accesskey_and_title", "itm_text"); 
	private byte[] Resolve_key(byte[] key) {
		byte[] val = wiki.Msg_mgr().Val_by_key_obj(key);
		if (Bry_.Len_eq_0(val)) val = key;	// if key is not found, default to val
		return wiki.Parser_mgr().Main().Parse_text_to_wtxt(val);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_grp_fmt_))		html_grp_fmtr.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_itm_fmt_))		html_itm_fmtr.Fmt_(m.ReadBry("v"));
		else											return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_html_grp_fmt_ = "html_grp_fmt_", Invk_html_itm_fmt_ = "html_itm_fmt_";
	private static final byte[] CONST_id_prefix = Bry_.new_a7("n-");
	private static final byte Ignore_tid_search = 1, Ignore_tid_toolbox = 2, Ignore_tid_toolbox_end = 3, Ignore_tid_languages = 4;
	private static final Btrie_slim_mgr ignore_trie = Btrie_slim_mgr.ci_a7()
	.Add_str_byte("SEARCH", Ignore_tid_search)
	.Add_str_byte("TOOLBOX", Ignore_tid_toolbox)
	.Add_str_byte("TOOLBOXEND", Ignore_tid_toolbox_end)
	.Add_str_byte("LANGUAGES", Ignore_tid_languages)
	;
	private static final String GRP_KEY = "xowa.wiki.gui.skin.mgr";
	private static final byte[] CONST_sidebar_ttl = Bry_.new_a7("Sidebar");
}
