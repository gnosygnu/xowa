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
package gplx.ios; import gplx.*;
import org.junit.*; //using System.IO; /*Stream*/
public class IoStream_mem_tst {
	@Test  public void Write() { // confirm that changes written to Stream acquired via .AdpObj are written to IoStream_mem.Buffer
		IoStream_mem stream = IoStream_mem.wtr_data_(Io_url_.Empty, 0);
		byte[] data = Bry_.new_ints(1);
		stream.Write(data, 0, Array_.Len(data));

		Tfds.Eq(1L		, stream.Len());
		Tfds.Eq((byte)1	, stream.Buffer()[0]);
		stream.Rls();
	}
}
