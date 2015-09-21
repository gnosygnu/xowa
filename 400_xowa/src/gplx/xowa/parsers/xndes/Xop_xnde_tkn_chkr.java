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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xop_xnde_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_xnde_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_xnde;}
	public String Xnde_tagId() {return xnde_tagId;}
	public Xop_xnde_tkn_chkr Xnde_tagId_(int v) {xnde_tagId = Xop_xnde_tag_.Ary[v].Name_str(); return this;} private String xnde_tagId = null;
	public Tst_chkr Xnde_xtn() {return xnde_data;} public Xop_xnde_tkn_chkr Xnde_xtn_(Tst_chkr v) {xnde_data = v; return this;} Tst_chkr xnde_data = null;
	public byte CloseMode() {return closeMode;} public Xop_xnde_tkn_chkr CloseMode_(byte v) {closeMode = v; return this;} private byte closeMode = Xop_xnde_tkn.CloseMode_null;		
	public Xop_xnde_tkn_chkr Name_rng_(int bgn, int end) {name_bgn = bgn; name_end = end; return this;} private int name_bgn = String_.Pos_neg1; int name_end = String_.Pos_neg1;
	public Xop_xnde_tkn_chkr Atrs_rng_(int bgn, int end) {atrs_bgn = bgn; atrs_end = end; return this;} private int atrs_bgn = String_.Pos_neg1; int atrs_end = String_.Pos_neg1;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_xnde_tkn actl = (Xop_xnde_tkn)actl_obj;
		err += mgr.Tst_val(xnde_tagId == null, path, "xnde_tagId", xnde_tagId, Xop_xnde_tag_.Ary[actl.Tag().Id()].Name_str());
		err += mgr.Tst_val(closeMode == Xop_xnde_tkn.CloseMode_null, path, "close_mode", closeMode, actl.CloseMode());
		err += mgr.Tst_val(name_bgn == String_.Pos_neg1, path, "name_bgn", name_bgn, actl.Name_bgn());
		err += mgr.Tst_val(name_end == String_.Pos_neg1, path, "name_end", name_end, actl.Name_end());
		err += mgr.Tst_val(atrs_bgn == String_.Pos_neg1, path, "atrs_bgn", atrs_bgn, actl.Atrs_bgn());
		err += mgr.Tst_val(atrs_end == String_.Pos_neg1, path, "atrs_end", atrs_end, actl.Atrs_end());
		if (xnde_data != null)
			err += mgr.Tst_sub_obj(xnde_data, actl.Xnde_xtn(), path + "." + "xndeData", err);
		return err;
	}
}
