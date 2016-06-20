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
package gplx.core.net; import gplx.*; import gplx.core.*;
import gplx.core.net.qargs.*;
public class Gfo_url {
	public byte[] Raw() {return raw;} private byte[] raw;
	public byte Protocol_tid() {return protocol_tid;} private byte protocol_tid;
	public byte[] Protocol_bry() {return protocol_bry;} private byte[] protocol_bry;
	public byte[] Anch() {return anch;} private byte[] anch;
	public Gfo_qarg_itm[] Qargs() {return qargs;} private Gfo_qarg_itm[] qargs;
	public byte[][] Segs() {return segs;} private byte[][] segs; private int segs__len;
	public byte[] Segs__get_at(int i) {return i < segs__len ? segs[i] : null;}
	public byte[] Segs__get_at_1st() {return segs__len > 0 ? segs[0] : null;}
	public byte[] Segs__get_at_nth() {return segs__len > 1 ? segs[segs__len - 1] : null;}
	public Gfo_url Ctor(byte[] raw, byte protocol_tid, byte[] protocol_bry, byte[][] segs, Gfo_qarg_itm[] qargs, byte[] anch) {
		this.raw = raw;
		this.protocol_tid = protocol_tid;  this.protocol_bry = protocol_bry;
		this.segs = segs; this.segs__len = segs.length;
		this.qargs = qargs;
		this.anch = anch;
		return this;
	}
	public static final    Gfo_url Empty = new Gfo_url().Ctor(Bry_.Empty, Gfo_protocol_itm.Tid_unknown, Bry_.Empty, Bry_.Ary_empty, null, null);
}
