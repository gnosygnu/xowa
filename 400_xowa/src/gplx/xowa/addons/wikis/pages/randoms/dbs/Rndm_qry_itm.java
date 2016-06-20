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
package gplx.xowa.addons.wikis.pages.randoms.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.randoms.*;
public class Rndm_qry_itm {
	public Rndm_qry_itm(int qry_idx, int rng_end, String qry_key, String qry_data, String qry_name) {
		this.qry_idx = qry_idx;
		this.rng_end = rng_end;
		this.qry_key = qry_key;
		this.qry_data = qry_data;
		this.qry_name = qry_name;
	}
	public int Qry_idx()		{return qry_idx;}	private final    int qry_idx;
	public int Rng_end()		{return rng_end;}	private final    int rng_end;
	public String Qry_key()		{return qry_key;}	private final    String qry_key;
	public String Qry_data()	{return qry_data;}	private final    String qry_data;
	public String Qry_name()	{return qry_name;}	private final    String qry_name;
	public int Seq_max()		{return seq_max;}	private int seq_max; public void Seq_max_(int v) {this.seq_max = v;}

	public static Rndm_qry_itm New_by_ns(Xow_wiki wiki, int ns_id) {
		String ns_str = Int_.To_str(ns_id);
		String qry_key  = "xowa.ns." + ns_str;		// xowa.ns.0
		String qry_data = "type=ns;ns=" + ns_str;	// type=ns;ns=0
		String qry_name = "Namespace " + ns_str + " - All";
		return new Rndm_qry_itm(-1, -1, qry_key, qry_data, qry_name);
	}
}
