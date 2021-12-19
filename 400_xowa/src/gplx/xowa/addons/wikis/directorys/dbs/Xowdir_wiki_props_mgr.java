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
package gplx.xowa.addons.wikis.directorys.dbs;
import gplx.langs.jsons.Json_parser;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.libs.files.Io_url;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_page_;
import gplx.xowa.wikis.data.Xowd_cfg_key_;
public interface Xowdir_wiki_props_mgr {
	void                Wiki_cfg__upsert(String key, String val);
	String              Wiki_cfg__select_or(String key, String or);
	void                User_reg__upsert(String domain, String val);
	String              User_reg__select(String domain);
	Xowdir_wiki_props Verify(boolean mode_is_import, String domain, Io_url core_db_url);
}
abstract class Xowdir_wiki_props_mgr__base implements Xowdir_wiki_props_mgr {
	private final Gfo_usr_dlg usr_dlg;
	public Xowdir_wiki_props_mgr__base(Gfo_usr_dlg usr_dlg) {
		this.usr_dlg = usr_dlg;
	}
	public abstract void                Wiki_cfg__upsert(String key, String val);
	public abstract String              Wiki_cfg__select_or(String key, String or);
	public abstract void                User_reg__upsert(String domain, String val);
	public abstract String              User_reg__select(String domain);
	public Xowdir_wiki_props Verify(boolean mode_is_import, String domain, Io_url core_db_url) {
		Xowdir_wiki_props rv = new Xowdir_wiki_props();

		Verify_or_fix(rv, mode_is_import, core_db_url, Xowd_cfg_key_.Key__wiki__core__domain);
		Verify_or_fix(rv, mode_is_import, core_db_url, Xowd_cfg_key_.Key__init__main_page);
		Verify_or_fix(rv, mode_is_import, core_db_url, Xowd_cfg_key_.Key__wiki__core__name);

		return rv;
	}
	private String Verify_or_fix(Xowdir_wiki_props props, boolean mode_is_import, Io_url core_db_url, String key) {
		String val = Wiki_cfg__select_or(key, null);
		if (val == null) {
			props.Dirty_y_();
			usr_dlg.Log_many("", "", "xowdir: core_db.xowa_cfg does not have val; url=~{0} key=~{1}", core_db_url, key);
			val = Fix(props, mode_is_import, core_db_url, key);
			Wiki_cfg__upsert(key, val);
		}
		props.Set(key, val);
		return val;
	}
	private String Fix(Xowdir_wiki_props props, boolean mode_is_import, Io_url core_db_url, String key) {
		if (StringUtl.Eq(key, Xowd_cfg_key_.Key__wiki__core__domain)) {
			String rv = core_db_url.NameOnly();
			if (StringUtl.HasAtEnd(rv, "-core"))
				rv = StringUtl.Mid(rv, 0, StringUtl.Len(rv) - 5);
			return rv;
		}
		else if (StringUtl.Eq(key, Xowd_cfg_key_.Key__wiki__core__name)) {
			if (mode_is_import)
				return props.Domain();	// NOTE: must be called after domain
			else {
				Xowdir_wiki_json wiki_json = Xowdir_wiki_json.New_by_json(new Json_parser(), User_reg__select(props.Domain()));
				return wiki_json.Name();
			}
		}
		else if (StringUtl.Eq(key, Xowd_cfg_key_.Key__init__main_page)) {
			return Xoa_page_.Main_page_str;
		}
		else throw ErrUtl.NewUnhandled(key);
	}
}
class Xowdir_wiki_props_mgr__mock extends Xowdir_wiki_props_mgr__base {
	private final Hash_adp wiki_cfg_hash = Hash_adp_.New();
	private final Hash_adp user_reg_hash = Hash_adp_.New();
	public Xowdir_wiki_props_mgr__mock() {super(Gfo_usr_dlg_.Noop);}
	@Override public void                Wiki_cfg__upsert(String key, String val) {
		if (val != null)
			wiki_cfg_hash.AddIfDupeUseNth(key, val);
	}
	@Override public String              Wiki_cfg__select_or(String key, String or)   {
		String rv = (String)wiki_cfg_hash.GetByOrNull(key);
		return rv == null ? or : rv;
	}
	@Override public void                User_reg__upsert(String domain, String val)  {user_reg_hash.AddIfDupeUseNth(domain, val);}
	@Override public String              User_reg__select(String domain)              {return (String)user_reg_hash.GetByOrNull(domain);}
}
