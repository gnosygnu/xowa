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
package gplx.xowa.wikis.nss; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.bldrs.cmds.*; import gplx.xowa.apps.utls.*;
public class Xow_ns implements Gfo_invk {
	public Xow_ns(int id, byte case_match, byte[] name, boolean is_alias) {
		this.id = id; this.case_match = case_match; this.is_alias = is_alias;
		Name_bry_(name);
	}
	public void Name_bry_(byte[] v) {
		if (id == Xow_ns_.Tid__main) {	// NOTE: Main will never prefix titles; EX: "Test" vs "Category:Test"
			this.name_db_str			= "";
			this.name_db			= Bry_.Empty;
			this.name_db_w_colon	= Bry_.Empty;
		}
		else {
			this.name_db			= v;
			this.name_db_w_colon	= Bry_.Add(v, Byte_ascii.Colon);
			this.name_db_str		= String_.new_u8(v);
		}
		this.num_str = Int_.To_str_pad_bgn_zero(id, 3);
		this.num_bry = Bry_.new_a7(num_str);
		synchronized (url_encoder) {	// LOCK:static-obj
			this.name_enc = url_encoder.Encode(name_db);
		}
		this.name_ui = Bry_.Replace(name_enc, Byte_ascii.Underline, Byte_ascii.Space);
		this.name_ui_w_colon = Bry_.Replace(name_db_w_colon, Byte_ascii.Underline, Byte_ascii.Space);
	}
	public boolean		Exists()				{return exists;} public Xow_ns Exists_(boolean v) {exists = v; return this;} private boolean exists;
	public byte[]	Name_db()				{return name_db;} private byte[] name_db;
	public byte[]	Name_db_w_colon()		{return name_db_w_colon;} private byte[] name_db_w_colon;
	public String	Name_db_str()			{return name_db_str;} private String name_db_str;
	public byte[]	Name_ui()				{return name_ui;} private byte[] name_ui;
	public byte[]	Name_ui_w_colon()		{return name_ui_w_colon;} private byte[] name_ui_w_colon;		// PERF: for Xoa_ttl
	public byte[]	Name_enc()				{return name_enc;} private byte[] name_enc;
	public byte[]	Name_combo()			{return id == Xow_ns_.Tid__main ? Xow_ns_.Bry__main: name_ui;}	// for combo boxes; namely "(Main)"
	public String	Num_str()				{return num_str;} private String num_str;
	public byte[]	Num_bry()				{return num_bry;} private byte[] num_bry;
	public int		Id()					{return id;} private int id;
	public int		Id_subj_id()			{if (id < 0) return id; return Id_is_talk() ? id - 1	: id;}					// id< 0: special/media return themself; REF.MW: Namespace.php|getSubject
	public int		Id_talk_id()			{if (id < 0) return id; return Id_is_talk() ? id		: id + 1;}				// REF.MW: Namespace.php|getTalk
	public int		Id_alt_id()				{if (id < 0) return id; return Id_is_talk() ? Id_subj_id() : Id_talk_id();}		// REF.MW: Namespace.php|getTalk
	public boolean		Id_is_subj()			{return !Id_is_talk();}								// REF.MW: Namespace.php|isMain
	public boolean		Id_is_talk()			{return id > Xow_ns_.Tid__main && id % 2 == 1;}		// REF.MW: Namespace.php|isTalk
	public boolean		Id_is_media()			{return id == Xow_ns_.Tid__media;}
	public boolean		Id_is_special()			{return id == Xow_ns_.Tid__special;}
	public boolean		Id_is_main()			{return id == Xow_ns_.Tid__main;}
	public boolean		Id_is_file()			{return id == Xow_ns_.Tid__file;}
	public boolean		Id_is_file_or_media()	{return id == Xow_ns_.Tid__file || id == Xow_ns_.Tid__media;}
	public boolean		Id_is_tmpl()			{return id == Xow_ns_.Tid__template;}
	public boolean		Id_is_ctg()				{return id == Xow_ns_.Tid__category;}
	public boolean		Id_is_module()			{return id == Xow_ns_.Tid__module;}
	public int		Ord()					{return ord;} public void Ord_(int v) {this.ord = v;} private int ord;
	public int		Ord_subj_id()			{if (id < 0) return ord; return Id_is_talk() ? ord - 1 : ord;}  // id< 0: special/media returns self
	public int		Ord_talk_id()			{if (id < 0) return ord; return Id_is_talk() ? ord : ord + 1;}
	public byte		Case_match()			{return case_match;} public void Case_match_(byte v) {case_match = v;} private byte case_match;
	public boolean		Subpages_enabled()		{return subpages_enabled;} private boolean subpages_enabled = false;// CHANGED: id > Xow_ns_.Tid__special; only Special, Media does not have subpages; DATE:2013-10-07
	public Xow_ns	Subpages_enabled_(boolean v) {subpages_enabled = v; return this;}
	public boolean		Is_gender_aware()		{return id == Xow_ns_.Tid__user || id == Xow_ns_.Tid__user_talk;}	// ASSUME: only User, User_talk are gender aware
	public boolean		Is_capitalized()		{return false;}														// ASSUME: always false (?)
	public boolean		Is_content()			{return id == Xow_ns_.Tid__main;}									// ASSUME: only Main
	public boolean		Is_includable()			{return true;}														// ASSUME: always true (be transcluded?)
	public boolean		Is_movable()			{return id > Xow_ns_.Tid__special;}									// ASSUME: only Special, Media cannot move (be renamed?)
	public boolean		Is_meta()				{return id < Xow_ns_.Tid__main;}									// ASSUME: only Special, Media
	public boolean		Is_alias()				{return is_alias;} private boolean is_alias;
	public int		Count()					{return count;} public Xow_ns Count_(int v) {count = v; return this;} private int count;
	public byte[]	Gen_ttl(byte[] page)	{return id == Xow_ns_.Tid__main ? page : Bry_.Add(name_db_w_colon, page);}
	public void		Aliases_add(String alias) {
		if (String_.Eq(alias, name_db_str)) return;
		if (aliases == null) aliases = Ordered_hash_.New();
		aliases.Add_if_dupe_use_1st(alias, alias);
	}	private Ordered_hash aliases;
	public Keyval[] Aliases_as_scrib_ary() {	// NOTE: intended for Scrib_lib_site; DATE:2014-02-15
		if (aliases == null) return Keyval_.Ary_empty;
		int len = aliases.Count();
		Keyval[] rv = new Keyval[len];
		for (int i = 0; i < len; i++) {
			String alias = (String)aliases.Get_at(i);
			rv[i] = Keyval_.int_(i + List_adp_.Base1, alias);
		}
		return rv;
	}
	public Xob_ns_file_itm Bldr_data() {return bldr_data;} public void Bldr_data_(Xob_ns_file_itm v) {bldr_data = v;} private Xob_ns_file_itm bldr_data;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_subpages_enabled_))	this.subpages_enabled = m.ReadYn("v");
		else if (ctx.Match(k, Invk_id))					return id;
		else if (ctx.Match(k, Invk_name_txt))			return name_ui;
		else if (ctx.Match(k, Invk_name_ui))			return Name_combo();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_subpages_enabled_ = "subpages_enabled_", Invk_id = "id", Invk_name_txt = "name_txt", Invk_name_ui = "name_ui";

	private static final    Xoa_url_encoder url_encoder = new Xoa_url_encoder();
}
