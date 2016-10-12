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
package gplx.xowa.addons.bldrs.centrals.dbs.datas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
public class Xobc_task_regy_itm {
	public Xobc_task_regy_itm(int id, int seqn, byte[] key, byte[] name, int step_count) {
		this.id = id;
		this.seqn = seqn;
		this.key = key;
		this.name = name;
		this.step_count = step_count;
	}
	public int Id() {return id;} private final    int id;
	public int Seqn() {return seqn;} private final    int seqn;
	public byte[] Key() {return key;} private final    byte[] key;
	public byte[] Name() {return name;} private final    byte[] name;
	public int Step_count() {return step_count;} private final    int step_count;
}
