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
package gplx.core.caches; import gplx.*; import gplx.core.*;
import gplx.core.envs.*;
class Gfo_cache_data implements gplx.CompareAble, Rls_able {
	private int count = 0;
	public Gfo_cache_data(byte[] key, Rls_able val, int size) {
		this.key = key; this.val = val;
		this.size = size;
		this.count = 1;
	}
	public byte[]		Key()			{return key;} private final    byte[] key;
	public Rls_able		Val()			{return val;} private Rls_able val;
	public int			Size()			{return size;} private int size;

	public Object		Val_and_update() {
		++count;
		return val;
	}
	public void Val_(Rls_able val, int size) {
		this.val = val;
		this.size = size;
	}
	public int compareTo(Object obj) {
		Gfo_cache_data comp = (Gfo_cache_data)obj;
		return -Long_.Compare(count, comp.count);	// "-" to sort most-recent first
	}
	public void Rls() {
		val.Rls();
	}
}
