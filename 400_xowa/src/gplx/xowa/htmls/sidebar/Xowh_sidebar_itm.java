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
package gplx.xowa.htmls.sidebar; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.brys.fmtrs.*;
public class Xowh_sidebar_itm {
	public Xowh_sidebar_itm(byte tid) {this.tid = tid;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Id() {return id;} public Xowh_sidebar_itm Id_(byte[] v) {id = v; return this;} private byte[] id;
	public byte[] Href() {return href;} public Xowh_sidebar_itm Href_(byte[] v) {href = v; return this;} private byte[] href;
	public byte[] Title() {return title;} public Xowh_sidebar_itm Title_(byte[] v) {title = v; return this;} private byte[] title = Bry_.Empty;
	public byte[] Accesskey() {return accesskey;} public Xowh_sidebar_itm Accesskey_(byte[] v) {accesskey = v; return this;} private byte[] accesskey;
	public byte[] Atr_accesskey_and_title() {return atr_accesskey_and_title;} public Xowh_sidebar_itm Atr_accesskey_and_title_(byte[] v) {atr_accesskey_and_title = v; return this;} private byte[] atr_accesskey_and_title = Bry_.Empty;
	public byte[] Text() {return text;} public Xowh_sidebar_itm Text_(byte[] v) {text = v; return this;} private byte[] text;
	public int Itms_len() {return itms.Count();} List_adp itms = List_adp_.new_();
	public Xowh_sidebar_itm Itms_get_at(int i) {return (Xowh_sidebar_itm)itms.Get_at(i);}
	public Xowh_sidebar_itm Itms_add(Xowh_sidebar_itm... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++)
			itms.Add(ary[i]);
		return this;
	}
	public static final byte Tid_grp = 1, Tid_itm = 2;	// NOTE: values are used by parse to indicate # of asterisks
}
class Xowh_sidebar_grp_fmtr_arg extends gplx.core.brys.Bfr_arg_base {
	private Xowe_wiki wiki; private Xowh_sidebar_itm grp; private Bry_fmtr fmtr;
	public void Grp_(Xowe_wiki wiki, Xowh_sidebar_itm grp, Bry_fmtr fmtr) {this.wiki = wiki; this.grp = grp; this.fmtr = fmtr;}
	@Override public void Bfr_arg__add(Bry_bfr bfr) {
		int len = grp.Itms_len();
		boolean popups_enabled = wiki.Appe().Api_root().Html().Modules().Popups().Enabled();
		String itm_cls = popups_enabled ? " class='xowa-hover-off'" : "";
		for (int i = 0; i < len; i++) {
			Xowh_sidebar_itm itm = (Xowh_sidebar_itm )grp.Itms_get_at(i);
			fmtr.Bld_bfr_many(bfr, itm.Id(), itm.Href(), itm_cls, itm.Atr_accesskey_and_title(), itm.Text());
		}		
	}
}
