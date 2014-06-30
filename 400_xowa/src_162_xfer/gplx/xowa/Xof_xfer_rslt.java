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
package gplx.xowa; import gplx.*;
public class Xof_xfer_rslt {
	public boolean Pass() {return pass;} private boolean pass = true;
	public String Err_msg() {return err_msg;} private String err_msg = String_.Empty;
	public String Src() {return src;} private String src;
	public Io_url Trg() {return trg;} public Xof_xfer_rslt Trg_(Io_url v) {trg = v; return this;}  Io_url trg;
	public void Atrs_src_trg_(String src, Io_url trg) {this.src = src; this.trg = trg;}		
	public boolean Fail(String msg) {
		pass = false;
		err_msg = msg;
		return false;
	}
	public void Clear() {pass = true; err_msg = src = String_.Empty; trg = Io_url_.Null;}
}
