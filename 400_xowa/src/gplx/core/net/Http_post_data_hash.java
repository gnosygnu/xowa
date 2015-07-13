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
public class Http_post_data_hash {
	private final Ordered_hash hash = Ordered_hash_.new_bry_();
	public int Len() {return hash.Count();}
	public Http_post_data_itm Get_at(int i)		{return (Http_post_data_itm)hash.Get_at(i);}
	public Http_post_data_itm Get_by(byte[] k)	{return (Http_post_data_itm)hash.Get_by(k);}
	public void Add(byte[] key, byte[] val) {
		hash.Add(key, new Http_post_data_itm(key, val));
	}
}
