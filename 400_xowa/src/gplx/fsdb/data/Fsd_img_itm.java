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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
public class Fsd_img_itm {
	public Fsd_img_itm(int mnt_id, int dir_id, int fil_id, int bin_db_id) {
		this.mnt_id = mnt_id; this.dir_id = dir_id; this.fil_id = fil_id; this.bin_db_id = bin_db_id;
	}
	public int Mnt_id()		{return mnt_id;}		private final    int mnt_id;
	public int Dir_id()		{return dir_id;}		private final    int dir_id;
	public int Fil_id()		{return fil_id;}		private final    int fil_id;
	public int Bin_db_id()	{return bin_db_id;}		private final    int bin_db_id;
}
