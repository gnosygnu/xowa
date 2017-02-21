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
package gplx;
public class GfsCtx {
	public Ordered_hash Vars() {return vars;} Ordered_hash vars = Ordered_hash_.New();
	public boolean Fail_if_unhandled() {return fail_if_unhandled;} public GfsCtx Fail_if_unhandled_(boolean v) {fail_if_unhandled = v; return this;} private boolean fail_if_unhandled;
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} public GfsCtx Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v; return this;} Gfo_usr_dlg usr_dlg;
	public boolean Help_browseMode() {return help_browseMode;} public GfsCtx Help_browseMode_(boolean v) {help_browseMode = v; return this;} private boolean help_browseMode;
	public List_adp Help_browseList() {return help_browseList;} List_adp help_browseList = List_adp_.New();
	public Object MsgSrc() {return msgSrc;} public GfsCtx MsgSrc_(Object v) {msgSrc = v; return this;} Object msgSrc;
	public boolean Match(String k, String match) {
		if (help_browseMode) {
			help_browseList.Add(match);
			return false;
		}
		else
			return String_.Eq(k, match);
	}
	public boolean MatchPriv(String k, String match) {return help_browseMode ? false : String_.Eq(k, match);}
	public boolean MatchIn(String k, String... match) {
		if (help_browseMode) {
			for (String i : match)
				help_browseList.Add(i);
			return false;
		}
		return String_.In(k, match);
	}
	public boolean Write_note(String fmt, Object... ary) {UsrDlg_.Instance.Note(fmt, ary); return false;}
	public boolean Write_warn(String fmt, Object... ary) {UsrDlg_.Instance.Note("! " + fmt, ary); return false;}
	public boolean Write_stop(UsrMsg umsg) {UsrDlg_.Instance.Note("* " + umsg.To_str()); return false;}
	public boolean Write_stop(String fmt, Object... ary) {UsrDlg_.Instance.Note("* " + fmt, ary); return false;}
	public boolean Deny() {return deny;} private boolean deny;
        public static final    GfsCtx Instance = new GfsCtx();
        public static GfsCtx new_() {return new GfsCtx();} GfsCtx() {}
        public static GfsCtx rdr_() {
		GfsCtx rv = new GfsCtx();
		rv.deny = true;
		rv.mode = "read";
		return rv;
	}
        public static GfsCtx wtr_() {
		GfsCtx rv = new GfsCtx();
		rv.deny = true;
		rv.mode = Mode_write;
		return rv;
	}
	public String Mode() {return mode;} public GfsCtx Mode_(String v) {mode = v; return this;} private String mode = "regular";
	public static final    String Mode_write = "write";
	public static final int Ikey_null = -1;
}
