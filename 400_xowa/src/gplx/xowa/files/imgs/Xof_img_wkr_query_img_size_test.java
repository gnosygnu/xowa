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
package gplx.xowa.files.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.gfui.*;
public class Xof_img_wkr_query_img_size_test implements Xof_img_wkr_query_img_size {
	public SizeAdp Exec(Io_url url) {
		ImageAdp image = ImageAdp_.txt_fil_(url);
		return image.Size();
	}
}
