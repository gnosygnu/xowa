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
package gplx.gfui; import gplx.*;
public class GfuiElem_ {
	public static final String 
		  InitKey_focusAble = "focusAble"
		, InitKey_ownerWin = "ownerForm";
	public static GfuiElem as_(Object obj) {return obj instanceof GfuiElem ? (GfuiElem)obj : null;}
	public static GfuiElem cast(Object obj) {try {return (GfuiElem)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfuiElem.class, obj);}}
	public static GfuiElemBase sub_(String key, GfuiElem owner) {
		GfuiElemBase rv = new_();
		rv.Owner_(owner, key);
		return rv;
	}
	public static GfuiElemBase new_() {
		GfuiElemBase rv = new GfuiElemBase();
		rv.ctor_GfuiBox_base(GfuiElem_.init_focusAble_true_());
		return rv;
	}
	public static Keyval_hash init_focusAble_true_()			{return new Keyval_hash().Add(GfuiElem_.InitKey_focusAble, true);}
	public static Keyval_hash init_focusAble_false_()		{return new Keyval_hash().Add(GfuiElem_.InitKey_focusAble, false);}
	public static void Y_adj(int adj, GfuiElem... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			GfuiElem itm = ary[i];
			itm.Y_(itm.Y() + adj);
		}
	}
}
