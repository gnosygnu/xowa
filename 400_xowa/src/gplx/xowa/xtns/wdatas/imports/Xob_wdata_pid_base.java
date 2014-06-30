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
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.json.*;
public abstract class Xob_wdata_pid_base extends Xob_itm_dump_base implements Xobd_wkr, GfoInvkAble {
	public Xob_wdata_pid_base Ctor(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki); return this;}
	public abstract String Wkr_key();
	public abstract void Pid_bgn();
	public abstract void Pid_add(byte[] src_lang, byte[] src_ttl, byte[] trg_ttl);
	public abstract void Pid_end();
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_bgn(Xob_bldr bldr) {
		this.Init_dump(this.Wkr_key(), wiki.Fsys_mgr().Site_dir().GenSubDir_nest("data", "pid"));	// NOTE: must pass in correct make_dir in order to delete earlier version (else make_dirs will append)
		parser = bldr.App().Wiki_mgr().Wdata_mgr().Parser();
		this.Pid_bgn();
	}	Json_parser parser;
	public void Wkr_run(Xodb_page page) {
		if (page.Ns_id() != Wdata_wiki_mgr.Ns_property) return;
		Json_doc doc = parser.Parse(page.Text()); 
		if (doc == null) {
			bldr.Usr_dlg().Warn_many(GRP_KEY, "json.invalid", "json is invalid: ns=~{0} id=~{1}", page.Ns_id(), String_.new_utf8_(page.Ttl_wo_ns()));
			return;
		}
		byte[] qid = Wdata_doc_.Entity_extract(doc);
		Json_itm_nde label_nde = Json_itm_nde.cast_(doc.Get_grp(Wdata_doc_consts.Key_atr_label_bry)); if (label_nde == null) return; // no labels; ignore
		int len = label_nde.Subs_len();
		for (int i = 0; i < len; i++) {
			Json_itm_kv kv = (Json_itm_kv)label_nde.Subs_get_at(i);
			byte[] lang_key = kv.Key().Data_bry();
			byte[] prop_key = kv.Val().Data_bry();
			this.Pid_add(lang_key, prop_key, qid);
		}
	}
	public void Wkr_end() {this.Pid_end();}
	public void Wkr_print() {}
	static final String GRP_KEY = "xowa.wdata.pid_wkr";
}
