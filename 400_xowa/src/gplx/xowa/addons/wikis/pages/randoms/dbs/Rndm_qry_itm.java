/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
