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
package gplx.xowa.addons.wikis.directorys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*;
import gplx.langs.jsons.*;
public interface Xowdir_wiki_json_mgr {
	void                Wiki_cfg__upsert(String key, String val);
	String              Wiki_cfg__select_or(String key, String or);
	void                User_reg__upsert(String domain, String val);
	String              User_reg__select(String domain);
	boolean             Dirty();
	Xowdir_wiki_json    Verify(boolean mode_is_import, String domain_str, Io_url core_db_url);
}
class Xowdir_wiki_cfg_ {
	public static final String
	  Key__wiki_json = "wiki_json"
	, Key__domain    = "props.domain"
	, Key__main_page = "props.main_page"
	;
}
abstract class Xowdir_wiki_json_mgr__base implements Xowdir_wiki_json_mgr {
	private final    Gfo_usr_dlg usr_dlg;
	public Xowdir_wiki_json_mgr__base(Gfo_usr_dlg usr_dlg) {
		this.usr_dlg = usr_dlg;
	}
	public abstract void                Wiki_cfg__upsert(String key, String val);
	public abstract String              Wiki_cfg__select_or(String key, String or);
	public abstract void                User_reg__upsert(String domain, String val);
	public abstract String              User_reg__select(String domain);
	public boolean                      Dirty() {return dirty;} private boolean dirty;
	public Xowdir_wiki_json Verify(boolean mode_is_import, String domain_str, Io_url core_db_url) {
		Xowdir_wiki_json rv = null;
		dirty = false;

		// read from core_db.xowa_cfg
		String wiki_json_str = Wiki_cfg__select_or(Xowdir_wiki_cfg_.Key__wiki_json, null);

		// not in core_db; check user_db.user_wiki
		if (wiki_json_str == null) {
			usr_dlg.Warn_many("", "", "wiki_json not found in core_db; url=~{0}", core_db_url);
			dirty = true;
			if (!mode_is_import) {
				wiki_json_str = User_reg__select(domain_str);
			}
		}

		if (wiki_json_str != null) {
			rv = Xowdir_wiki_json.New_by_json(new Json_parser(), wiki_json_str);
		}

		// not in core_db or user_db; infer
		if (rv == null) {
			usr_dlg.Warn_many("", "", "wiki_json not found in core_db or user_db; url=~{0}", core_db_url);
			dirty = true;
			rv = Infer_new(core_db_url);
		}

		// check domain exists
		if (String_.Eq(rv.Domain(), String_.Empty)) {
			usr_dlg.Warn_many("", "", "wiki_json does not have domain; url=~{0}", core_db_url);
			dirty = true;
			String domain = Wiki_cfg__select_or(Xowdir_wiki_cfg_.Key__domain, core_db_url.NameOnly());
			rv.Domain_(domain);
		}

		// something changed; save it
		if (dirty) {
			wiki_json_str = rv.To_str(new gplx.langs.jsons.Json_wtr());
			Wiki_cfg__upsert(Xowdir_wiki_cfg_.Key__wiki_json, wiki_json_str);

			if (!mode_is_import) {
				User_reg__upsert(domain_str, wiki_json_str);
			}
		}
		return rv;
	}
	private Xowdir_wiki_json Infer_new(Io_url core_db_url) {
		String domain    = this.Wiki_cfg__select_or(Xowdir_wiki_cfg_.Key__domain, core_db_url.NameOnly());
		String main_page = this.Wiki_cfg__select_or(Xowdir_wiki_cfg_.Key__main_page, Xoa_page_.Main_page_str);
		return new Xowdir_wiki_json(domain, domain, main_page);
	}
}
class Xowdir_wiki_json_mgr__mock extends Xowdir_wiki_json_mgr__base {
	private final    Hash_adp wiki_cfg_hash = Hash_adp_.New();
	private final    Hash_adp user_reg_hash = Hash_adp_.New();
	public Xowdir_wiki_json_mgr__mock() {super(Gfo_usr_dlg_.Noop);}
	@Override public void                Wiki_cfg__upsert(String key, String val)     {wiki_cfg_hash.Add_if_dupe_use_nth(key, val);}
	@Override public void                User_reg__upsert(String domain, String val)  {user_reg_hash.Add_if_dupe_use_nth(domain, val);}
	@Override public String              User_reg__select(String domain)              {return (String)user_reg_hash.Get_by(domain);}
	@Override public String              Wiki_cfg__select_or(String key, String or)   {
		String rv = (String)wiki_cfg_hash.Get_by(key);
		return rv == null ? or : rv;
	}
}
