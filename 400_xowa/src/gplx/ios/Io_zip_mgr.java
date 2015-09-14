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
public interface Io_zip_mgr {
	void Zip_fil(Io_url src_fil, Io_url trg_fil);
	byte[] Zip_bry(byte[] src, int bgn, int len);
	byte[] Unzip_bry(byte[] src, int bgn, int len);
	void Unzip_to_dir(Io_url src_fil, Io_url trg_dir);
	void Zip_dir(Io_url src_dir, Io_url trg_fil);
}
