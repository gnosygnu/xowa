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
package gplx.xowa.htmls.core.wkrs.xndes.tags; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.core.primitives.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.xndes.atrs.*;
public class Xohz_tag_regy {
	private final Ordered_hash keys = Ordered_hash_.New_bry();
	private final Hash_adp uids = Hash_adp_.new_(); private final Byte_obj_ref uids_ref = Byte_obj_ref.zero_();
	public Xohz_atr_regy Atr_regy() {return atr_regy;} private final Xohz_atr_regy atr_regy = new Xohz_atr_regy();
	public Xohz_tag Get_by_key(byte[] key)	{return (Xohz_tag)keys.Get_by(key);}
	public Xohz_tag Get_by_uid(byte uid)	{return (Xohz_tag)uids.Get_by(uids_ref.Val_(uid));}
	public Xohz_tag_regy Add(int uid, byte[] key, int flag_len, byte[] atr_key) {
		Xohz_tag tag = new Xohz_tag(uid, key, flag_len, atr_regy.Resolve(atr_key));
		keys.Add(key, tag);
		uids.Add(Byte_obj_ref.new_((byte)uid), tag);
		return this;
	}
}
