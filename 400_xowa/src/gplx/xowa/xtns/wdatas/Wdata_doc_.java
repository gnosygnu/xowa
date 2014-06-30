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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.json.*;
public class Wdata_doc_ {
	public static byte[] Link_extract(Json_itm_kv kv) {
		Json_itm kv_val = kv.Val();
		switch (kv_val.Tid()) {
			case Json_itm_.Tid_string:	// "enwiki":"Earth"
				return kv_val.Data_bry();
			case Json_itm_.Tid_nde:		// "enwiki":{name:"Earth", badges:[]}
				Json_itm_nde kv_val_as_nde = (Json_itm_nde)kv_val;
				Json_itm_kv name_itm = (Json_itm_kv)kv_val_as_nde.Subs_get_by_key(Key_name);
				return name_itm.Val().Data_bry();
			default:
				throw Err_.unhandled(kv_val.Tid());
		}
	}
	public static byte[] Entity_extract(Json_doc doc) {
		Json_itm kv_val = doc.Find_nde(Wdata_doc_consts.Key_atr_entity_bry);
		switch (kv_val.Tid()) {
			case Json_itm_.Tid_string:	// "entity":"q1"
				return kv_val.Data_bry();
			case Json_itm_.Tid_array:	// "entity":["item",1]
				Json_itm_ary kv_val_as_ary = (Json_itm_ary)kv_val;
				Json_itm entity_id = kv_val_as_ary.Subs_get_at(1);
				return Bry_.Add(Byte_ascii.Ltr_q, entity_id.Data_bry());
			default:
				throw Err_.unhandled(kv_val.Tid());
		}
	}
	public static final byte[] Key_name = Bry_.new_ascii_("name");
}
