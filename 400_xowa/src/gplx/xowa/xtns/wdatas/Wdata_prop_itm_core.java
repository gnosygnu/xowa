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
public class Wdata_prop_itm_core extends Wdata_prop_itm_base {
	public Wdata_prop_itm_core(byte snak_tid, int pid, byte val_tid_byte, byte[] val) {this.Ctor(snak_tid, pid, val_tid_byte, val);}
	public byte[] Wguid() {return wguid;} public void Wguid_(byte[] v) {this.wguid = v;} private byte[] wguid;
	public String Prop_type() {return Wdata_prop_itm_base_.Prop_type_statement;}
	public byte Rank_tid() {return rank_tid;}
	public void Rank_tid_(byte v) {this.rank_tid = v;} private byte rank_tid;
	public String Rank_str() {return Wdata_prop_itm_base_.Rank_string(rank_tid);}
	public Wdata_prop_itm_base[] Qual_ary() {return qual_ary;} public void Qual_ary_(Wdata_prop_itm_base[] v) {this.qual_ary = v;} Wdata_prop_itm_base[] qual_ary;
	public Wdata_prop_itm_base[] Ref_ary() {return ref_ary;} public void Ref_ary_(Wdata_prop_itm_base[] v) {this.ref_ary = v;} Wdata_prop_itm_base[] ref_ary;

	public static final byte Prop_dlm = Byte_ascii.Pipe;
	public static Wdata_prop_itm_core new_novalue_(int pid)							{return new Wdata_prop_itm_core(Wdata_prop_itm_base_.Snak_tid_novalue  , pid, Wdata_prop_itm_base_.Val_tid_unknown, Bry_.Empty);}
	public static Wdata_prop_itm_core new_somevalue_(int pid)						{return new Wdata_prop_itm_core(Wdata_prop_itm_base_.Snak_tid_somevalue, pid, Wdata_prop_itm_base_.Val_tid_unknown, Bry_.Empty);}
	public static Wdata_prop_itm_core new_str_(int pid, String val)					{return new_(Wdata_prop_itm_base_.Val_tid_string, pid, Bry_.new_utf8_(val));}
	public static Wdata_prop_itm_core new_str_(int pid, byte[] val)					{return new_(Wdata_prop_itm_base_.Val_tid_string, pid, val);}
	public static Wdata_prop_itm_core new_time_(int pid, String val)				{return new_(Wdata_prop_itm_base_.Val_tid_time, pid, Wdata_doc_bldr.Xto_time(DateAdp_.parse_fmt(val, "yyyy-MM-dd HH:mm:ss")));}
	public static Wdata_prop_itm_core new_geodata_(int pid, String lat, String lon)	{return new_(Wdata_prop_itm_base_.Val_tid_globecoordinate, pid, Bry_.Add_w_dlm(Prop_dlm, Bry_.new_ascii_(lat), Bry_.new_ascii_(lon)));}
	public static Wdata_prop_itm_core new_quantity_(int pid, String amount, String unit, String ubound, String lbound)	
																					{return new_(Wdata_prop_itm_base_.Val_tid_quantity, pid, Bry_.Add_w_dlm(Prop_dlm, Bry_.new_ascii_(amount), Bry_.new_ascii_(unit), Bry_.new_ascii_(ubound), Bry_.new_ascii_(lbound)));}
	public static Wdata_prop_itm_core new_entity_(int pid, int v)					{return new_(Wdata_prop_itm_base_.Val_tid_entity, pid, Bry_.XtoStrBytesByInt(v, 0));}
	private static Wdata_prop_itm_core new_(byte tid, int pid, byte[] val)			{return new Wdata_prop_itm_core(Wdata_prop_itm_base_.Snak_tid_value, pid, tid, val);}
}
