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
package gplx.xowa.specials.movePage; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.primitives.*; import gplx.core.brys.fmtrs.*; import gplx.core.net.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.langs.msgs.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.utils.*;
public class Move_page implements Xows_page {
	private Move_trg_ns_list_fmtr ns_list_fmtr = new Move_trg_ns_list_fmtr();
	private Move_url_args args = new Move_url_args();
	private Xoa_ttl src_ttl;
	public Xows_special_meta Special_meta() {return Xows_special_meta_.Itm__move_page;}
	public void Special_gen(Xowe_wiki wiki, Xoae_page page, Xoa_url url, Xoa_ttl ttl) {
		args.Parse(url);
		byte[] src_ttl_bry = args.Src_ttl();
		src_ttl = Xoa_ttl.parse(wiki, src_ttl_bry);
		if (args.Submitted()) {
			Exec_rename(wiki, page);
			return;
		}
		byte[] html = Bld_html(page);
		page.Html_data().Html_restricted_n_();	// [[Special:]] pages allow all HTML
		page.Data_raw_(html);
	}
	private void Exec_rename(Xowe_wiki wiki, Xoae_page page) {
		gplx.xowa.wikis.dbs.Xodb_save_mgr save_mgr = wiki.Db_mgr().Save_mgr();
		int trg_ns_id = args.Trg_ns();
		Xow_ns trg_ns = wiki.Ns_mgr().Ids_get_or_null(trg_ns_id); if (trg_ns == null) throw Err_.new_wo_type("unknown ns", "ns", trg_ns_id);
		byte[] trg_ttl_bry = args.Trg_ttl();
		Xoa_ttl trg_ttl = Xoa_ttl.parse(wiki, trg_ns_id, trg_ttl_bry);
		Xowd_page_itm src_page = new Xowd_page_itm();
		wiki.Db_mgr().Load_mgr().Load_by_ttl(src_page, src_ttl.Ns(), src_ttl.Page_db());
		page.Revision_data().Id_(src_page.Id());
		page.Revision_data().Modified_on_(src_page.Modified_on());
		page.Data_raw_(src_page.Text());
		if (args.Create_redirect()) {	// NOTE: not tested; DATE:2014-02-27
			save_mgr.Data_update(page, Xop_redirect_mgr.Make_redirect_text(trg_ttl.Full_db()));
			Xowd_page_itm trg_page = new Xowd_page_itm();
			boolean trg_page_exists = wiki.Db_mgr().Load_mgr().Load_by_ttl(trg_page, trg_ns, trg_ttl_bry);
			if (trg_page_exists)
				save_mgr.Data_update(page, page.Data_raw());
			else
				save_mgr.Data_create(trg_ttl, page.Data_raw());
		}
		else
			save_mgr.Data_rename(page, trg_ns_id, trg_ttl_bry);
		wiki.Data_mgr().Redirect(page, trg_ns.Gen_ttl(trg_ttl_bry));
	}
	private byte[] Bld_html(Xoae_page page) {
		Xowe_wiki wiki = page.Wikie(); Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		if (src_ttl == null) return Bry_.Empty;
		ns_list_fmtr.Init_by_page(wiki, page, src_ttl);
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
		wiki.Parser_mgr().Main().Parse_text_to_html(tmp_bfr, page, true, msg_mgr.Val_by_key_obj("movepagetext"));
		fmtr_all.Bld_bfr_many(tmp_bfr
		, msg_mgr.Val_by_key_obj("move-page-legend")
		, Bry_.Add(Xoh_href_.Bry__wiki, src_ttl.Full_db())
		, gplx.langs.htmls.Gfh_utl.Escape_html_as_bry(src_ttl.Full_txt_w_ttl_case())
		, src_ttl.Full_txt_w_ttl_case()
		, msg_mgr.Val_by_key_obj("newtitle")
		, ns_list_fmtr
		, args.Trg_ttl()
		, msg_mgr.Val_by_key_obj("move-leave-redirect")
		, msg_mgr.Val_by_key_obj("movepagebtn")
		);
		return tmp_bfr.To_bry_and_rls();
	}
	private Bry_fmtr fmtr_all = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "<form action='/wiki/Special:MovePage' id='movepage'>"
	, "  <fieldset>"
	, "  <legend>~{move-page-legend}</legend>"
	, "    <table id='mw-movepage-table'>"
	, "      <tr>"
	, "        <td class='mw-label'>Move page:</td>"
	, "        <td class='mw-input'>"
	, "          <strong><a href='~{src_href}' title='~{src_title}'>~{src_text}</a></strong>"
	, "        </td>"
	, "      </tr>"
	, "      <tr>"
	, "        <td class='mw-label'>"
	, "          <label for='wpNewTitleMain'>~{newtitle}</label>"
	, "        </td>"
	, "        <td class='mw-input'>"
	, "            <select id='wpNewTitleNs' name='wpNewTitleNs'>~{trg_ns_list}"
	, "            </select>"
	, "            <input name='wpNewTitleMain' size='60' value='~{trg_title}' type='text' id='wpNewTitleMain' maxlength='255' />"
	, "            <input type='hidden' value='~{src_title}' name='wpOldTitle' />"
	, "        </td>"
	, "      </tr>"
	, "<!--  <tr>"
	, "        <td></td>"
	, "        <td class='mw-input'>"
	, "          <input name='wpLeaveRedirect' type='checkbox' value='1' checked='checked' id='wpLeaveRedirect' />&#160;<label for='wpLeaveRedirect'>~{move-leave-redirect}</label>"
	, "        </td>"
	, "      </tr> -->"
	, "      <tr>"
	, "        <td>&#160;</td>"
	, "        <td class='mw-submit'>"
	, "          <input type='submit' value='~{movepagebtn}' name='wpMove' />"
	, "        </td>"
	, "      </tr>"
	, "    </table>"
	, "  </fieldset>"
	, "</form>"
	), "move-page-legend", "src_href", "src_title", "src_text", "newtitle", "trg_ns_list", "trg_title", "move-leave-redirect", "movepagebtn");
}
class Move_trg_ns_list_fmtr implements gplx.core.brys.Bfr_arg {
	private Xowe_wiki wiki; private Xoa_ttl ttl;
	public void Init_by_page(Xowe_wiki wiki, Xoae_page page, Xoa_ttl ttl) {
		this.wiki = wiki;
		this.ttl = ttl;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		int ns_len = ns_mgr.Ids_len();
		for (int i = 0; i < ns_len; i++) {
			Xow_ns ns = ns_mgr.Ids_get_at(i);
			if (ns.Is_meta()) continue;	// ignore [[Special:]] and [[Media:]]
			byte[] bry_selected = ttl.Ns().Id() == ns.Id() ? Bry_selected : Bry_.Empty;
			fmtr.Bld_bfr_many(bfr, ns.Id(), bry_selected, ns.Name_combo());
		}
	}
	private static final    byte[] Bry_selected = Bry_.new_a7(" selected=''");
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	(	""
	,	"  <option value='~{ns_id}' ~{ns_selected}>~{ns_name}</option>"
	), "ns_id", "ns_selected", "ns_name");
}
class Move_url_args {
	public boolean Submitted() {return submitted;} private boolean submitted;
	public byte[] Src_ttl() {return src_ttl;} private byte[] src_ttl;
	public byte[] Trg_ttl() {return trg_ttl;} private byte[] trg_ttl;
	public int Trg_ns() {return trg_ns;} private int trg_ns;
	public boolean Create_redirect() {return create_redirect;} private boolean create_redirect;
	public void Parse(Xoa_url url) {
		this.Clear();
		Gfo_qarg_itm[] args = url.Qargs_ary();
		int args_len = args.length;
		for (int i = 0; i < args_len; i++) {
			Gfo_qarg_itm arg = args[i];
			Object tid_obj = arg_keys.Get_by(arg.Key_bry());
			byte[] val_bry = arg.Val_bry();
			if (tid_obj != null) {
				switch (((Byte_obj_val)tid_obj).Val()) {
					case Key_submitted:				submitted = true; break;	// wpMove will only be in query_args if move button is pressed
					case Key_src_ttl:				src_ttl = val_bry; break;
					case Key_trg_ns:				trg_ns = Bry_.To_int(val_bry); break;
					case Key_trg_ttl:				trg_ttl = val_bry; break;
					case Key_create_redirect:		create_redirect = Bry_.To_bool_by_int(val_bry); break;
				}
			}
		}
	}
	private void Clear() {
		submitted = false;
		src_ttl = trg_ttl = null;
		trg_ns = Int_.Min_value;
		create_redirect = false;
	}
	private static final byte Key_submitted = 1, Key_src_ttl = 2, Key_trg_ns = 3, Key_trg_ttl = 4, Key_create_redirect = 5;
	private static final    Hash_adp_bry arg_keys = Hash_adp_bry.ci_a7()
	.Add_str_byte("wpMove"			, Key_submitted)
	.Add_str_byte("wpOldTitle"		, Key_src_ttl)
	.Add_str_byte("wpNewTitleNs"	, Key_trg_ns)
	.Add_str_byte("wpNewTitleMain"	, Key_trg_ttl)
	.Add_str_byte("wpLeaveRedirect"	, Key_create_redirect)
	;
}
