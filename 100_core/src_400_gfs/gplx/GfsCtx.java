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
package gplx;
public class GfsCtx {
	public OrderedHash Vars() {return vars;} OrderedHash vars = OrderedHash_.new_();
	public boolean Fail_if_unhandled() {return fail_if_unhandled;} public GfsCtx Fail_if_unhandled_(boolean v) {fail_if_unhandled = v; return this;} private boolean fail_if_unhandled;
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} public GfsCtx Usr_dlg_(Gfo_usr_dlg v) {usr_dlg = v; return this;} Gfo_usr_dlg usr_dlg;
	public boolean Help_browseMode() {return help_browseMode;} public GfsCtx Help_browseMode_(boolean v) {help_browseMode = v; return this;} private boolean help_browseMode;
	@gplx.Internal protected ListAdp Help_browseList() {return help_browseList;} ListAdp help_browseList = ListAdp_.new_();
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
	public boolean Write_note(String fmt, Object... ary) {UsrDlg_._.Note(fmt, ary); return false;}
	public boolean Write_warn(String fmt, Object... ary) {UsrDlg_._.Note("! " + fmt, ary); return false;}
	public boolean Write_stop(UsrMsg umsg) {UsrDlg_._.Note("* " + umsg.XtoStr()); return false;}
	public boolean Write_stop(String fmt, Object... ary) {UsrDlg_._.Note("* " + fmt, ary); return false;}
	public boolean Deny() {return deny;} private boolean deny;
        public static final GfsCtx _ = new GfsCtx();
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
	public static final String Mode_write = "write";
	public static final int Ikey_null = -1;
}
