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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.xowa.apps.fsys.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.langs.cases.*; import gplx.intl.*;
public class Xoav_app {
	public Xoav_app(Gfo_usr_dlg usr_dlg, Io_url root_dir, String plat_name) {
		this.usr_dlg = usr_dlg;
		this.fsys_mgr = new Xoa_fsys_mgr(root_dir, plat_name);
		this.wiki_mgr = new Xoav_wiki_mgr(this, fsys_mgr.Wiki_dir(), utl_case_mgr);
		this.db_mgr = new Db_provider_mkr();
		this.utl_msg_log = Gfo_msg_log.Test();
		db_mgr.Engines_add(gplx.dbs.Sqlite_engine_.New_engine());
	}
	public Xoa_fsys_mgr Fsys_mgr() {return fsys_mgr;} private Xoa_fsys_mgr fsys_mgr;
	public Xoav_wiki_mgr Wiki_mgr() {return wiki_mgr;} private final Xoav_wiki_mgr wiki_mgr;
	public Db_provider_mkr Db_mgr() {return db_mgr;} private Db_provider_mkr db_mgr;

	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} private Gfo_usr_dlg usr_dlg;
	public Bry_bfr_mkr Utl_bfr_mkr() {return utl_bfr_mkr;} private Bry_bfr_mkr utl_bfr_mkr = new Bry_bfr_mkr();
	public Xop_amp_mgr Utl_amp_mgr() {return utl_amp_mgr;} private Xop_amp_mgr utl_amp_mgr = new Xop_amp_mgr();
	public Xol_case_mgr Utl_case_mgr() {return utl_case_mgr;} private Xol_case_mgr utl_case_mgr = new Xol_case_mgr(Gfo_case_mgr_.Tid_utf8);
	public Url_encoder Utl_encoder_fsys() {return utl_encoder_fsys;} private Url_encoder utl_encoder_fsys = Url_encoder.new_fsys_lnx_();
	public Gfo_msg_log Utl_msg_log() {return utl_msg_log;} private Gfo_msg_log utl_msg_log;
}
