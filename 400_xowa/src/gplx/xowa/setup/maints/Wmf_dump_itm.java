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
package gplx.xowa.setup.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.setup.*;
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
	private static byte[] Status_msg_dump_complete = Bry_.new_ascii_("Dump complete"), Status_msg_dump_in_progress = Bry_.new_ascii_("Dump in progress");
	public static final byte Status_tid_complete = 0, Status_tid_working = 1, Status_tid_error = 2;
}