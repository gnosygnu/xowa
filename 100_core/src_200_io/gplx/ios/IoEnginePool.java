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
public class IoEnginePool {
	public void AddReplace(IoEngine engine) {
		hash.Del(engine.Key());
		hash.Add(engine.Key(), engine);
	}
	public IoEngine Fetch(String key) {
		IoEngine rv = (IoEngine)hash.Fetch(key); 
		return rv == null ? IoEngine_.Mem : rv; // rv == null when url is null or empty; return Mem which should be a noop; DATE:2013-06-04
	}
	HashAdp hash = HashAdp_.new_();
	public static final IoEnginePool _ = new IoEnginePool();
	IoEnginePool() {
		this.AddReplace(IoEngine_.Sys);
		this.AddReplace(IoEngine_.Mem);
	}
}