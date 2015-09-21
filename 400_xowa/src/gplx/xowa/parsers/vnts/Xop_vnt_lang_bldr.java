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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.langs.vnts.*;
class Xop_vnt_lang_bldr {	// performant way of building langs; EX: -{zh;zh-hans;zh-cn|B}-
	private final Xol_vnt_regy vnt_regy;
	private int rslt_mask;
	public Xop_vnt_lang_bldr(Xol_vnt_mgr vnt_mgr) {this.vnt_regy = vnt_mgr.Regy();}
	public void Clear() {rslt_mask = 0;}
	public void Add(byte[] key) {
		Xol_vnt_itm vnt = vnt_regy.Get_by(key); if (vnt == null) return;	// ignore invalid vnts; EX: -{zh;zhx}-
		int vnt_mask = vnt.Mask__vnt();
		this.rslt_mask = (rslt_mask == 0) ? vnt_mask : Enm_.Flip_int(true, rslt_mask, vnt_mask);
	}
	public Xop_vnt_flag Bld() {
		return (rslt_mask == 0) ? Xop_vnt_flag_.Flag_unknown : Xop_vnt_flag.new_lang(rslt_mask);
	}
}
