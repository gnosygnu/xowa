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
package gplx.xowa.addons.bldrs.centrals.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
public class Xobc_task_step_hash {
	private final    Ordered_hash tasks_hash = Ordered_hash_.New();
	private final    Ordered_hash steps_hash = Ordered_hash_.New();
	public int Tasks__len() {return tasks_hash.Len();}
	public int Tasks__get_at(int i) {return Int_.cast(tasks_hash.Get_at(i));}
	public int Steps__len() {return steps_hash.Len();}
	public int Steps__get_at(int i) {return Int_.cast(steps_hash.Get_at(i));}
	public void Clear() {tasks_hash.Clear(); steps_hash.Clear();}
	public void Add(int task_id, int step_id) {
		tasks_hash.Add_if_dupe_use_nth(task_id, task_id);
		steps_hash.Add_if_dupe_use_nth(step_id, step_id);
	}
}
