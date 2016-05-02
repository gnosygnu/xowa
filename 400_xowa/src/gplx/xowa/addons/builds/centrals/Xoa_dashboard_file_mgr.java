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
package gplx.xowa.addons.builds.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*;
import gplx.core.gfobjs.*; import gplx.xowa.guis.cbks.*;
class Xoa_dashboard_file_mgr {
	private final    Xog_cbk_mgr cbk_mgr;
	private final    Ordered_hash hash = Ordered_hash_.New();
	public Xoa_dashboard_file_mgr(Xog_cbk_mgr cbk_mgr) {
		this.cbk_mgr = cbk_mgr;
	}
	public void Add(Xobc_file_itm itm) {hash.Add(itm.Job_uid(), itm);}
	public void Clear() {hash.Clear();}
	public int Len() {return hash.Len();}
	public Xobc_file_itm Get_at(int i)		{return (Xobc_file_itm)hash.Get_at(i);}
	public Xobc_file_itm Get_by(String k)	{return (Xobc_file_itm)hash.Get_by(k);}
	public Xobc_file_itm Del_by(String k)	{
		Xobc_file_itm rv = (Xobc_file_itm)hash.Get_by(k);
		hash.Del(k);
		return rv;
	}
	public void Exec_all(String invk_cmd) {
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Xobc_file_itm itm = (Xobc_file_itm)hash.Get_at(i);
			GfoInvkAble_.Invk(itm, invk_cmd);
		}
	}
	public void Exec_one(String invk_cmd, String file_id) {this.Exec_one(invk_cmd, file_id, null, null);}
	public void Exec_one(String invk_cmd, String file_id, String send_func, Gfobj_nde send_data) {
		Xobc_file_itm itm = (Xobc_file_itm)hash.Get_by(file_id);
		GfoInvkAble_.Invk(itm, invk_cmd);
		if (send_func != null) cbk_mgr.Send_json(send_func, send_data);
	}
	public void Move_all(Xoa_dashboard_file_mgr trg) {this.Move_all(trg, null);}
	public void Move_all(Xoa_dashboard_file_mgr trg, String invk_cmd) {
		List_adp tmp = List_adp_.new_();
		int len = hash.Len();
		for (int i = 0; i < len; ++i)
			tmp.Add((Xobc_file_itm)hash.Get_at(i));
		for (int i = 0; i < len; ++i) {
			Xobc_file_itm itm = (Xobc_file_itm)tmp.Get_at(i);
			if (invk_cmd != null) GfoInvkAble_.Invk(itm, invk_cmd);
			hash.Del(itm.Job_uid());
			trg.Add(itm);
		}
	}
	public void Move_one(String file_id, Xoa_dashboard_file_mgr trg)						{this.Move_one(file_id, trg, null, null, null);}
	public void Move_one(String file_id, Xoa_dashboard_file_mgr trg, String invk_cmd)	{this.Move_one(file_id, trg, invk_cmd, null, null);}
	public void Move_one(String file_id, Xoa_dashboard_file_mgr trg, String invk_cmd, String send_func, Gfobj_nde send_data) {
		Xobc_file_itm itm = (Xobc_file_itm)hash.Get_by(file_id);
		if (invk_cmd != null) GfoInvkAble_.Invk(itm, invk_cmd);
		hash.Del(file_id);
		trg.Add(itm);
		if (send_func != null) cbk_mgr.Send_json(send_func, send_data);
	}
	public void To_nde(Bry_bfr tmp_bfr, Gfobj_ary ary_obj) {
		Gfobj_ary_nde ary = (Gfobj_ary_nde)ary_obj;
		List_adp list = List_adp_.new_();
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Xobc_file_itm sub_itm = (Xobc_file_itm)hash.Get_at(i);
			Gfobj_nde sub_nde = Gfobj_nde.New();
			sub_itm.Write_to_nde(tmp_bfr, sub_nde);
			list.Add(sub_nde);
		}
		ary.Ary_nde_((Gfobj_nde[])list.To_ary_and_clear(Gfobj_nde.class));
	}
}
