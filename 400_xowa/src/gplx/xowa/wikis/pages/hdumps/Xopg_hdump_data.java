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
package gplx.xowa.wikis.pages.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.htmls.tocs.*;
public class Xopg_hdump_data {
	public List_adp					Imgs()		{return imgs;}		private final    List_adp imgs = List_adp_.New();
	public Xoh_toc_wtr				Toc_wtr()	{return toc_wtr;}	private final    Xoh_toc_wtr toc_wtr = new Xoh_toc_wtr(); 
	public void Clear() {
		imgs.Clear();
		toc_wtr.Clear();
	}
}
