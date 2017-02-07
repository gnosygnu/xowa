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
package gplx.core.security.files; import gplx.*; import gplx.core.*; import gplx.core.security.*;
public class Cksum_itm implements gplx.core.brys.Bry_bfr_able {
	public Cksum_itm(byte[] hash, Io_url file_url, long file_size) {
		this.Hash = hash; this.File_url = file_url; this.File_size = file_size;
	}
	public final    byte[] Hash;
	public final    Io_url File_url;
	public final    long File_size;
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add(Hash).Add_byte_pipe();
		bfr.Add_str_u8(File_url.Raw()).Add_byte_pipe();
		bfr.Add_long_variable(File_size);
	}
}
