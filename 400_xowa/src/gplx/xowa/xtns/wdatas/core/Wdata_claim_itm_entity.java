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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.xtns.wdatas.hwtrs.*;
public class Wdata_claim_itm_entity extends Wdata_claim_itm_core { 	public Wdata_claim_itm_entity(int pid, byte snak_tid, byte entity_tid, byte[] entity_id_bry) {
		this.Ctor(pid, snak_tid);
		this.entity_tid = entity_tid;
		this.entity_id_bry = entity_id_bry;
		this.entity_id = Bry_.To_int(entity_id_bry);
	}
	@Override public byte Val_tid() {return Wdata_dict_val_tid.Tid_entity;}
	public int Entity_id() {return entity_id;} private final int entity_id;
	public byte[] Entity_id_bry() {return entity_id_bry;} private final byte[] entity_id_bry;
	public byte Entity_tid() {return entity_tid;} private final byte entity_tid;
	public boolean Entity_tid_is_qid() {return entity_tid == Wdata_dict_value_entity_tid.Tid_item;}
	public String Entity_tid_str() {return Wdata_dict_value_entity_tid.Xto_str(entity_tid);}
	public byte[] Entity_tid_bry() {return Wdata_dict_value_entity_tid.Xto_bry(entity_tid);}
	public byte[] Page_ttl_db() {
		return entity_tid == Wdata_dict_value_entity_tid.Tid_item
			? Bry_.Add(Wdata_wiki_mgr.Ttl_prefix_qid_bry_db, entity_id_bry)
			: Bry_.Add(Wdata_wiki_mgr.Ttl_prefix_pid_bry, entity_id_bry)
			;
	}
	public byte[] Page_ttl_gui() {
		return entity_tid == Wdata_dict_value_entity_tid.Tid_item
			? Bry_.Add(Wdata_wiki_mgr.Ttl_prefix_qid_bry_gui, entity_id_bry)
			: Bry_.Add(Wdata_wiki_mgr.Ttl_prefix_pid_bry, entity_id_bry)
			;
	}
	@Override public void Welcome(Wdata_claim_visitor visitor) {visitor.Visit_entity(this);}
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", Wdata_dict_snak_tid.Xto_str(this.Snak_tid()), Wdata_dict_val_tid.Xto_str(this.Val_tid()), this.Entity_tid_str(), Int_.Xto_str(entity_id));
	}
}
