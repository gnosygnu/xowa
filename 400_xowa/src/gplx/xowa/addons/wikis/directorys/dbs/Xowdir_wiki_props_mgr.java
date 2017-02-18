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
import gplx.xowa.wikis.data.*;
public interface Xowdir_wiki_props_mgr {
	void                Wiki_cfg__upsert(String key, String val);
	String              Wiki_cfg__select_or(String key, String or);
	void                User_reg__upsert(String domain, String val);
	String              User_reg__select(String domain);
	Xowdir_wiki_props Verify(boolean mode_is_import, String domain, Io_url core_db_url);
}
abstract class Xowdir_wiki_props_mgr__base implements Xowdir_wiki_props_mgr {
	private final    Gfo_usr_dlg usr_dlg;
	public Xowdir_wiki_props_mgr__base(Gfo_usr_dlg usr_dlg) {
		this.usr_dlg = usr_dlg;
	}
	public abstract void                Wiki_cfg__upsert(String key, String val);
	public abstract String              Wiki_cfg__select_or(String key, String or);
	public abstract void                User_reg__upsert(String domain, String val);
	public abstract String              User_reg__select(String domain);
	public Xowdir_wiki_props Verify(boolean mode_is_import, String domain, Io_url core_db_url) {
		Xowdir_wiki_props rv = new Xowdir_wiki_props();

		Verify_or_fix(rv, core_db_url, Xowd_cfg_key_.Key__wiki_core__domain);
		Verify_or_fix(rv, core_db_url, Xowd_cfg_key_.Key__init__main_page);
		Verify_or_fix(rv, core_db_url, Xowd_cfg_key_.Key__wiki_core__name);

		return rv;
	}
	private String Verify_or_fix(Xowdir_wiki_props props, Io_url core_db_url, String key) {
		String val = Wiki_cfg__select_or(key, null);
		if (val == null) {
			props.Dirty_y_();
			usr_dlg.Warn_many("", "", "xowdir: core_db.xowa_cfg does not have val; url=~{0} key=~{1}", core_db_url, key);
			val = Fix(props, core_db_url, key);
			Wiki_cfg__upsert(key, val);
		}
		props.Set(key, val);
		return val;
	}
	private static String Fix(Xowdir_wiki_props props, Io_url core_db_url, String key) {
		if (String_.Eq(key, Xowd_cfg_key_.Key__wiki_core__domain)) {
			String rv = core_db_url.NameOnly();
			if (String_.Has_at_end(rv, "-core"))
				rv = String_.Mid(rv, 0, String_.Len(rv) - 5);
			return rv;
		}
		else if (String_.Eq(key, Xowd_cfg_key_.Key__wiki_core__name)) {
			return props.Domain();	// NOTE: must be called after domain
		}
		else if (String_.Eq(key, Xowd_cfg_key_.Key__init__main_page)) {
			return Xoa_page_.Main_page_str;
		}
		else throw Err_.new_unhandled_default(key);
	}
}
class Xowdir_wiki_props_mgr__mock extends Xowdir_wiki_props_mgr__base {
	private final    Hash_adp wiki_cfg_hash = Hash_adp_.New();
	private final    Hash_adp user_reg_hash = Hash_adp_.New();
	public Xowdir_wiki_props_mgr__mock() {super(Gfo_usr_dlg_.Noop);}
	@Override public void                Wiki_cfg__upsert(String key, String val) {
		if (val != null)
			wiki_cfg_hash.Add_if_dupe_use_nth(key, val);
	}
	@Override public String              Wiki_cfg__select_or(String key, String or)   {
		String rv = (String)wiki_cfg_hash.Get_by(key);
		return rv == null ? or : rv;
	}
	@Override public void                User_reg__upsert(String domain, String val)  {user_reg_hash.Add_if_dupe_use_nth(domain, val);}
	@Override public String              User_reg__select(String domain)              {return (String)user_reg_hash.Get_by(domain);}
}
