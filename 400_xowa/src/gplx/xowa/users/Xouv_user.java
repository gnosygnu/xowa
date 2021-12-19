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
package gplx.xowa.users;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_app;
import gplx.xowa.Xow_wiki;
import gplx.xowa.langs.genders.Xol_gender_;
import gplx.xowa.users.data.Xou_db_mgr;
import gplx.xowa.users.history.Xou_history_mgr;
import gplx.xowa.wikis.Xoa_wiki_mgr;
import gplx.xowa.wikis.domains.Xow_domain_itm_;
public class Xouv_user implements Xou_user {
	private Xoa_wiki_mgr wiki_mgr;
	public Xouv_user(Xoa_app app, String key, Io_url user_dir) {
		this.key = key;
		this.fsys_mgr = new Xou_fsys_mgr(user_dir);
		this.history_mgr = new Xou_history_mgr(fsys_mgr.App_data_history_fil());
	}
	public String					Key() {return key;} private String key;
	public Xou_fsys_mgr				Fsys_mgr() {return fsys_mgr;} private final Xou_fsys_mgr fsys_mgr;
	public Xou_history_mgr			History_mgr() {return history_mgr;} private final Xou_history_mgr history_mgr;
	public Xou_db_mgr				User_db_mgr()  {return user_db_mgr;} private Xou_db_mgr user_db_mgr;
	public int						Gender() {return Xol_gender_.Tid_unknown;}
	public Xow_wiki					Wikii() {if (wiki == null) wiki = wiki_mgr.Get_by_or_make_init_y(Xow_domain_itm_.Bry__home); return wiki;} private Xow_wiki wiki;
	public void Init_db(Xoa_app app, Xoa_wiki_mgr wiki_mgr, Io_url db_url) {
		this.wiki_mgr = wiki_mgr;
		this.user_db_mgr = new Xou_db_mgr(app);
		user_db_mgr.Init_by_app(BoolUtl.Y, db_url);
	}
}
