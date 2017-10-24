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
package gplx.xowa.bldrs.setups.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
public class Wmf_dump_itm implements gplx.CompareAble {
	public byte[] Wiki_abrv() {return wiki_abrv;} public void Wiki_abrv_(byte[] v) {this.wiki_abrv = v;} private byte[] wiki_abrv;				// EX: enwiki
	public DateAdp Dump_date() {return dump_date;} public void Dump_date_(DateAdp v) {this.dump_date = v;} private DateAdp dump_date;			// EX: 20140304
	public DateAdp Status_time() {return status_time;} public void Status_time_(DateAdp v) {this.status_time = v;} private DateAdp status_time;	// EX: 2014-03-15 23:22:06
	public byte[] Status_msg() {return status_msg;}																								// EX: Dump in progress / Dump complete
	public void Status_msg_(byte[] v) {
		this.status_msg = v;
		if 		(Bry_.Eq(status_msg, Status_msg_dump_complete))
			status_tid = Status_tid_complete;
		else if (Bry_.Eq(status_msg, Status_msg_dump_in_progress))
			status_tid = Status_tid_working;
		else
			status_tid = Status_tid_error;
	} 	private byte[] status_msg;
	public byte Status_tid() {return status_tid;} private byte status_tid;
	public int compareTo(Object obj) {Wmf_dump_itm comp = (Wmf_dump_itm)obj; return Bry_.Compare(wiki_abrv, comp.wiki_abrv);}
	private static byte[] Status_msg_dump_complete = Bry_.new_a7("Dump complete"), Status_msg_dump_in_progress = Bry_.new_a7("Dump in progress");
	public static final byte Status_tid_complete = 0, Status_tid_working = 1, Status_tid_error = 2;
}
